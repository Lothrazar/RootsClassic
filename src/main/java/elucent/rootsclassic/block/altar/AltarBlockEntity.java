package elucent.rootsclassic.block.altar;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import elucent.rootsclassic.block.brazier.BrazierBlockEntity;
import elucent.rootsclassic.blockentity.BEBase;
import elucent.rootsclassic.client.particles.MagicAltarParticleData;
import elucent.rootsclassic.client.particles.MagicLineParticleData;
import elucent.rootsclassic.recipe.RitualRecipe;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.ritual.RitualPillars;
import elucent.rootsclassic.ritual.RitualRegistry;
import elucent.rootsclassic.util.InventoryUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jspecify.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class AltarBlockEntity extends BEBase {

  private static final int RECIPE_PROGRESS_TIME = 200;
  private List<ItemStack> incenses = new ArrayList<>();
  private int ticker = 0;
  private int progress = 0;
  @Nullable
  private RecipeHolder<RitualRecipe> currentRitual = null;
  private int clientRitualLevel;
  private int clientRitualColor;
  private int clientRitualSecondaryColor;
  //	private ItemStack resultItem = ItemStack.EMPTY; TODO: Unused
  public final ItemStacksResourceHandler inventory = new ItemStacksResourceHandler(3) {

    @Override
    protected int getCapacity(int slot, @Nonnull ItemResource stack) {
      return 1;
    }
  };

  public AltarBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
    super(tileEntityTypeIn, pos, state);
  }

  public AltarBlockEntity(BlockPos pos, BlockState state) {
    super(RootsRegistry.ALTAR_TILE.get(), pos, state);
  }

  @Override
  protected void loadAdditional(ValueInput input) {
    super.loadAdditional(input);
    inventory.deserialize(input);

    List<ItemStack> incenses = input.read("incenses", ItemStack.CODEC.listOf()).orElse(new ArrayList<>());
    setIncenses(incenses);

    setProgress(input.getIntOr("progress", 0));

    RitualInfo ritualInfo = input.read("ritual", RitualInfo.CODEC).orElse(null);
		if (level != null && level.isClientSide() && ritualInfo != null) {
			clientRitualLevel = ritualInfo.level;
			clientRitualColor = ritualInfo.color;
			clientRitualSecondaryColor = ritualInfo.secondaryColor;
		}
	}

  @Override
  protected void saveAdditional(ValueOutput output) {
    super.saveAdditional(output);
    inventory.serialize(output);
		if (!getIncenses().isEmpty()) {
      output.store("incenses", ItemStack.CODEC.listOf(), getIncenses());
		}

    output.putInt("progress", getProgress());
		if (level != null && !level.isClientSide() && currentRitual != null) {
			var ritualTag = new CompoundTag();
			RitualRecipe ritual = currentRitual.value();
      output.store("ritual", RitualInfo.CODEC, new RitualInfo(ritual.level, ritual.getColorInt(), ritual.getSecondaryColorInt()));
		}
	}

  @Override
  public void preRemoveSideEffects(BlockPos pos, BlockState state) {
    super.preRemoveSideEffects(pos, state);
    try (Transaction tx = Transaction.openRoot()) {
      for (int i = 0; i < inventory.size(); ++i) {
        if (!inventory.getResource(i).isEmpty())
          Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), inventory.getResource(i).toStack());
      }
      tx.commit();
    }
  }

  @Override
  public InteractionResult activate(Level levelAccessor, BlockPos pos, BlockState state, Player player, InteractionHand hand, ItemStack heldItem, BlockHitResult hit) {
    if (hand == InteractionHand.MAIN_HAND) {
      if (heldItem.isEmpty() && !player.isShiftKeyDown() && this.getProgress() == 0) {
        //try to withdraw an item
        if (inventory.size() > 0) {
          try (Transaction tx = Transaction.openRoot()) {
            Pair<Integer, ItemResource> lastResourcePair = InventoryUtil.getLastResource(inventory);
            if (lastResourcePair.getFirst() == -1) {
              return InteractionResult.PASS;
            } else {
              if (!lastResourcePair.getSecond().isEmpty()) {
                if (inventory.extract(lastResourcePair.getSecond(), 1, tx) != 1) return InteractionResult.PASS;

                if (!levelAccessor.isClientSide()) {
                  levelAccessor.addFreshEntity(new ItemEntity(levelAccessor,
                    pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5,
                    lastResourcePair.getSecond().toStack()));
                }
                setChanged();
                levelAccessor.sendBlockUpdated(pos, state, levelAccessor.getBlockState(pos), 3);
                tx.commit();
              }

            }

            return InteractionResult.SUCCESS;
          }
        }
      }
      else if (player.isShiftKeyDown() && heldItem.isEmpty() && this.getProgress() == 0) {
        // Try to start a new ritual
        setCurrentRitual(null);
        var optionalRitual = RitualRegistry.findMatchingByIngredients(this);
        if (optionalRitual.isEmpty()) {
          player.sendOverlayMessage(Component.translatable("rootsclassic.error.noritual.ingredients"));
          return InteractionResult.FAIL;
        }
        var recipeHolder = optionalRitual.get();
				var recipe = recipeHolder.value();
        if (!RitualPillars.verifyPositionBlocks(recipe, levelAccessor, pos)) {
          player.sendOverlayMessage(Component.translatable("rootsclassic.error.noritual.stones"));
          return InteractionResult.FAIL;
        }
        //does it match everything else?
        if (recipe.incenseMatches(level, pos)) {
          setCurrentRitual(recipeHolder);
          setIncenses(RitualRegistry.getIncenses(levelAccessor, pos));
          setProgress(RECIPE_PROGRESS_TIME);
          for (BrazierBlockEntity brazier : RitualPillars.getRecipeBraziers(levelAccessor, pos)) {
            brazier.setBurning(true);
            brazier.setHeldItem(ItemStack.EMPTY);
          }
          //        this.emptyNearbyBraziers();
          //          System.out.println(" ritual STARTED " + ritual.name);
          setChanged();
          levelAccessor.sendBlockUpdated(pos, state, levelAccessor.getBlockState(pos), 3);
          player.sendOverlayMessage(Component.translatable("rootsclassic.ritual.started"));
        }
        else {
          player.sendOverlayMessage(Component.translatable("rootsclassic.error.noritual.incense"));
        }
        return InteractionResult.SUCCESS;
      }
      else {
        //try to insert an item into the altar
        if (!InventoryUtil.isFull(inventory) && getProgress() == 0) {
          try (Transaction tx = Transaction.openRoot()) {
            ItemStack copyStack = heldItem.copy();
            copyStack.setCount(1);
            if (inventory.insert(ItemResource.of(copyStack), 1, tx) != 1) {
              return InteractionResult.PASS;
            } else {
              tx.commit();
              heldItem.shrink(1);
              setChanged();
              levelAccessor.sendBlockUpdated(pos, state, levelAccessor.getBlockState(pos), 3);
            }
          }
          return InteractionResult.SUCCESS;
        }
      }
    }
    return InteractionResult.PASS;
  }

  public static void serverTick(Level level, BlockPos pos, BlockState state, AltarBlockEntity tile) {
    tile.setTicker(tile.getTicker() + 3);
    if (tile.getTicker() > 360) {
      tile.setTicker(0);
    }
    if (tile.getProgress() > 0 && tile.getCurrentRitual() != null) {
      tile.setProgress(tile.getProgress() - 1);
      //      if (tile.getProgress() % 40 == 0) {
      //        setIncenses(RitualManager.getIncenses(getWorld(), getPos()));
      //        boolean doesMatch = false;
      //        for (int i = 0; i < RitualManager.rituals.size(); i++) {
      //          if (RitualManager.rituals.get(i).incenseMatches(getWorld(), getPos())) {
      //            doesMatch = true;
      //          }
      //        }
      //        if (!doesMatch) {
      //          setRitualCurrent(null);
      //          setRitualName(null);
      //        }
      //      }
      if (tile.getProgress() == 0 && tile.getCurrentRitual() != null) {
        tile.getCurrentRitual().value().doEffect(level, pos, InventoryUtil.createIInventory(tile.inventory), tile.getIncenses());
        tile.setCurrentRitual(null);
        tile.emptyAltar();
        tile.setChanged();
        level.sendBlockUpdated(pos, state, state, 3);
      }
    }
  }

  public static void clientTick(Level level, BlockPos pos, BlockState state, AltarBlockEntity tile) {
    if (tile.getProgress() > 0 && tile.getCurrentRitual() != null) {
      tile.setProgress(tile.getProgress() - 1);
      var pillars = RitualPillars.getRitualPillars(tile.clientRitualLevel);
      var pillarPositions = pillars.keySet().stream().toList();
      var color = tile.clientRitualColor;
      var secondaryColor = tile.clientRitualSecondaryColor;
      if (color == -1 || secondaryColor == -1) return;
      if (pillarPositions.size() > 0) {
        BlockPos particlePos = pillarPositions.get(level.getRandom().nextInt(pillarPositions.size())).above()
            .offset(pos.getX(), pos.getY(), pos.getZ());
        if (level.getRandom().nextInt(6) == 0) {
          level.addParticle(MagicLineParticleData.createData(ARGB.red(color), ARGB.green(color), ARGB.blue(color)),
              particlePos.getX() + 0.5, particlePos.getY() + 0.125, particlePos.getZ() + 0.5,
              particlePos.getX() + 0.5, particlePos.getY() + 0.875, particlePos.getZ() + 0.5);
        }
        else {
          level.addParticle(MagicLineParticleData.createData(ARGB.red(secondaryColor), ARGB.green(secondaryColor), ARGB.blue(secondaryColor)),
              particlePos.getX() + 0.5, particlePos.getY() + 0.125, particlePos.getZ() + 0.5,
              particlePos.getX() + 0.5, particlePos.getY() + 0.875, particlePos.getZ() + 0.5);
        }
      }
      if (level.getRandom().nextInt(4) == 0) {
        level.addParticle(MagicAltarParticleData.createData(ARGB.red(color), ARGB.green(color), ARGB.blue(color)),
            pos.getX() + 0.5, pos.getY() + 0.875, pos.getZ() + 0.5,
            0.125 * Math.sin(Math.toRadians(360.0 * (tile.getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(360.0 * (tile.getProgress() % 100) / 100.0)));
      }
      else {
        level.addParticle(MagicAltarParticleData.createData(ARGB.red(secondaryColor), ARGB.green(secondaryColor), ARGB.blue(secondaryColor)),
            pos.getX() + 0.5, pos.getY() + 0.875, pos.getZ() + 0.5,
            0.125 * Math.sin(Math.toRadians(360.0 * (tile.getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(360.0 * (tile.getProgress() % 100) / 100.0)));
      }
      if (level.getRandom().nextInt(4) == 0) {
        level.addParticle(MagicAltarParticleData.createData(ARGB.red(color), ARGB.green(color), ARGB.blue(color)),
            pos.getX() + 0.5, pos.getY() + 0.875, pos.getZ() + 0.5,
            .125 * Math.sin(Math.toRadians(90.0 + 360.0 * (tile.getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(90.0 + 360.0 * (tile.getProgress() % 100) / 100.0)));
      }
      else {
        level.addParticle(MagicAltarParticleData.createData(ARGB.red(secondaryColor), ARGB.green(secondaryColor), ARGB.blue(secondaryColor)),
            pos.getX() + 0.5, pos.getY() + 0.875, pos.getZ() + 0.5,
            .125 * Math.sin(Math.toRadians(90.0 + 360.0 * (tile.getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(90.0 + 360.0 * (tile.getProgress() % 100) / 100.0)));
      }
      if (level.getRandom().nextInt(4) == 0) {
        level.addParticle(MagicAltarParticleData.createData(ARGB.red(color), ARGB.green(color), ARGB.blue(color)),
            pos.getX() + 0.5, pos.getY() + 0.875, pos.getZ() + 0.5,
            .125 * Math.sin(Math.toRadians(180.0 + 360.0 * (tile.getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(180.0 + 360.0 * (tile.getProgress() % 100) / 100.0)));
      }
      else {
        level.addParticle(MagicAltarParticleData.createData(ARGB.red(secondaryColor), ARGB.green(secondaryColor), ARGB.blue(secondaryColor)),
            pos.getX() + 0.5, pos.getY() + 0.875, pos.getZ() + 0.5,
            .125 * Math.sin(Math.toRadians(180.0 + 360.0 * (tile.getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(180.0 + 360.0 * (tile.getProgress() % 100) / 100.0)));
      }
      if (level.getRandom().nextInt(4) == 0) {
        level.addParticle(MagicAltarParticleData.createData(ARGB.red(color), ARGB.green(color), ARGB.blue(color)),
            pos.getX() + 0.5, pos.getY() + 0.875, pos.getZ() + 0.5,
            .125 * Math.sin(Math.toRadians(270.0 + 360.0 * (tile.getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(270.0 + 360.0 * (tile.getProgress() % 100) / 100.0)));
      }
      else {
        level.addParticle(MagicAltarParticleData.createData(ARGB.red(secondaryColor), ARGB.green(secondaryColor), ARGB.blue(secondaryColor)),
            pos.getX() + 0.5, pos.getY() + 0.875, pos.getZ() + 0.5,
            .125 * Math.sin(Math.toRadians(270.0 + 360.0 * (tile.getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(270.0 + 360.0 * (tile.getProgress() % 100) / 100.0)));
      }
    }
  }
  //  private void emptyNearbyBraziers() {
  //    for (TileEntityBrazier brazier : getRitualCurrent().getRecipeBraziers(world, pos)) {
  //      brazier.setHeldItem(ItemStack.EMPTY);
  //      brazier.setBurning(false);
  //    }
  //  }

  public List<ItemStack> getIncenses() {
    return incenses;
  }

  public void setIncenses(List<ItemStack> incenses) {
    this.incenses = incenses;
  }

  public int getTicker() {
    return ticker;
  }

  public void setTicker(int ticker) {
    this.ticker = ticker;
  }

  public int getProgress() {
    return progress;
  }

  public void setProgress(int progress) {
    this.progress = progress;
  }

  @Nullable
  public RecipeHolder<RitualRecipe> getCurrentRitual() {
    return this.currentRitual;
  }

  public void setCurrentRitual(RecipeHolder<RitualRecipe> currentRitual) {
    this.currentRitual = currentRitual;
    setChanged();
  }

  public void emptyAltar() {
    try (Transaction tx = Transaction.openRoot()) {
      for (int i = 0; i < inventory.size(); i++) {
        inventory.set(i, ItemResource.EMPTY, 0);
      }
      tx.commit();
    }
  }
  //	public ItemStack getResultItem() { TODO: Unused?
  //		return resultItem;
  //	}
  //
  //	public void setResultItem(ItemStack resultItem) {
  //		if (resultItem == null) {
  //			resultItem = ItemStack.EMPTY;
  //		}
  //		this.resultItem = resultItem;
  //	}

  private static record RitualInfo(int level, int color, int secondaryColor) {
    private static final Codec<RitualInfo> CODEC = RecordCodecBuilder.create(instance -> instance.group(
      Codec.INT.fieldOf("level").forGetter(RitualInfo::level),
      Codec.INT.fieldOf("color").forGetter(RitualInfo::color),
      Codec.INT.fieldOf("secondaryColor").forGetter(RitualInfo::secondaryColor)
    ).apply(instance, RitualInfo::new));
  }
}
