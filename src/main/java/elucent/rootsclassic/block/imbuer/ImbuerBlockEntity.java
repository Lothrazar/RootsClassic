package elucent.rootsclassic.block.imbuer;

import elucent.rootsclassic.blockentity.BEBase;
import elucent.rootsclassic.client.particles.MagicLineParticleData;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.ComponentBaseRegistry;
import elucent.rootsclassic.datacomponent.SpellData;
import elucent.rootsclassic.item.StaffItem;
import elucent.rootsclassic.item.powder.SpellPowderItem;
import elucent.rootsclassic.registry.RootsComponents;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

import javax.annotation.Nonnull;

public class ImbuerBlockEntity extends BEBase {

  private static final int STICK = 0;
  private static final int DUST = 1;
  public int progress = 0;
  public int spin = 0;
  public final ItemStacksResourceHandler inventory = new ItemStacksResourceHandler(2) {

    @Override
    protected int getCapacity(int slot, @Nonnull ItemResource stack) {
      return 1;
    }

    @Override
    public boolean isValid(int index, ItemResource resource) {
      if (index == 0) {
        return resource.is(Tags.Items.RODS_WOODEN);
      }
      else {
        return resource.getItem() instanceof SpellPowderItem;
      }
    }
  };

  public ImbuerBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
    super(tileEntityTypeIn, pos, state);
  }

  public ImbuerBlockEntity(BlockPos pos, BlockState state) {
    super(RootsRegistry.IMBUER_TILE.get(), pos, state);
  }

  /**
   * @return Immutable copy of the stick in the imbuer, or ItemStack.EMPTY if there is no stick
   */
  public ItemStack getStick() {
    return inventory.getResource(STICK).toStack();
  }

  /**
   * @return Immutable copy of the spell powder in the imbuer, or ItemStack.EMPTY if there is no spell powder
   */
  public ItemStack getSpellPowder() {
    return inventory.getResource(DUST).toStack();
  }

	@Override
  protected void loadAdditional(ValueInput input) {
    super.loadAdditional(input);
    inventory.deserialize(input);
  }

  @Override
  protected void saveAdditional(ValueOutput output) {
    super.saveAdditional(output);
    inventory.serialize(output);
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
    if (progress == 0 && hand == InteractionHand.MAIN_HAND) {
      try (Transaction tx = Transaction.openRoot()) {
        if (heldItem.isEmpty()) {
          if (!getStick().isEmpty()) {
            if (!levelAccessor.isClientSide()) {
              levelAccessor.addFreshEntity(new ItemEntity(levelAccessor, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, getStick()));
            }
            inventory.set(STICK, ItemResource.EMPTY, 0);
            setChanged();
            levelAccessor.sendBlockUpdated(getBlockPos(), state, levelAccessor.getBlockState(pos), 3);
            return InteractionResult.SUCCESS;
          }
          else if (!getSpellPowder().isEmpty()) {
            if (!levelAccessor.isClientSide()) {
              levelAccessor.addFreshEntity(new ItemEntity(levelAccessor, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, getSpellPowder()));
            }
            inventory.set(DUST, ItemResource.EMPTY, 0);
            setChanged();
            levelAccessor.sendBlockUpdated(getBlockPos(), state, levelAccessor.getBlockState(pos), 3);
            return InteractionResult.SUCCESS;
          }
        }
        else if (heldItem.is(Tags.Items.RODS_WOODEN)) {
          if (getStick().isEmpty()) {
            ItemStack copyStack = heldItem.copy();
            copyStack.setCount(1);
            inventory.set(STICK, ItemResource.of(copyStack), 1);
            heldItem.consume(1, player);
            setChanged();
            levelAccessor.sendBlockUpdated(getBlockPos(), state, levelAccessor.getBlockState(pos), 3);
            return InteractionResult.SUCCESS;
          }
        }
        else if (heldItem.getItem() == RootsRegistry.SPELL_POWDER.get()) {
          if (getSpellPowder().isEmpty()) {
            ItemStack copyStack = heldItem.copy();
            copyStack.setCount(1);
            inventory.set(DUST, ItemResource.of(copyStack), 1);
            heldItem.consume(1, player);
            setChanged();
            levelAccessor.sendBlockUpdated(getBlockPos(), state, levelAccessor.getBlockState(pos), 3);
            return InteractionResult.SUCCESS;
          }
        }
        tx.commit();
      }
    }
    return InteractionResult.PASS;
  }

  public static void serverTick(Level level, BlockPos pos, BlockState state, ImbuerBlockEntity tile) {
    if (tile.progress == 0) {
      tile.spin += 4;
    }
    else {
      tile.spin += 12;
    }
    ItemStack dustStack = tile.getSpellPowder();
    ItemStack stickStack = tile.getStick();
    if (!dustStack.isEmpty() && !stickStack.isEmpty()) {
      tile.progress++;
    }
    if (tile.progress != 0 && tile.progress % 1 == 0) {
      if (tile.progress > 40) {
        tile.progress = 0;
        if (!dustStack.isEmpty() && !stickStack.isEmpty()) {
	        if (dustStack.has(RootsComponents.SPELL)) {
		        SpellData data = dustStack.get(RootsComponents.SPELL);
            ItemStack staff = new ItemStack(RootsRegistry.STAFF.get(), 1);
            String effectName = data.effect();
            int potency = data.potency();
            int efficiency = data.efficiency();
            int size = data.size();
            StaffItem.createData(staff, effectName, potency, efficiency, size);
            if (!level.isClientSide()) {
              level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, staff));
            }
            tile.inventory.set(STICK, ItemResource.EMPTY, 0);
            tile.inventory.set(DUST, ItemResource.EMPTY, 0);
            tile.setChanged();
            level.sendBlockUpdated(pos, state, state, 3);
          }
        }
      }
    }
  }

  public static void clientTick(Level level, BlockPos pos, BlockState state, ImbuerBlockEntity tile) {
    if (tile.progress == 0) {
      tile.spin += 4;
    }
    else {
      tile.spin += 12;
    }
    ItemStack dustStack = tile.getSpellPowder();
    ItemStack stickStack = tile.getStick();
    if (!dustStack.isEmpty() && !stickStack.isEmpty()) {
      tile.progress++;
    }
    if (tile.progress != 0 && tile.progress % 1 == 0) {
      int chance = level.getRandom().nextInt(4);
      if (dustStack.has(RootsComponents.SPELL)) {
	      SpellData data = dustStack.get(RootsComponents.SPELL);
	      Identifier compName = Identifier.tryParse(data.effect());
	      if (compName != null) {
		      ComponentBase comp = ComponentBaseRegistry.COMPONENTS.getValue(compName);
		      if (comp != null && level.isClientSide()) {
			      if (chance == 0) {
				      if (level.getRandom().nextBoolean()) {
					      level.addParticle(MagicLineParticleData.createData(comp.primaryColor.x, comp.primaryColor.y, comp.primaryColor.z),
						      pos.getX() + 0.125, pos.getY() + 0.125, pos.getZ() + 0.125,
						      pos.getX() + 0.5, pos.getY() + 0.625, pos.getZ() + 0.5);
				      }
				      else {
					      level.addParticle(MagicLineParticleData.createData(comp.secondaryColor.x, comp.secondaryColor.y, comp.secondaryColor.z),
						      pos.getX() + 0.125, pos.getY() + 0.125, pos.getZ() + 0.125,
						      pos.getX() + 0.5, pos.getY() + 0.625, pos.getZ() + 0.5);
				      }
			      }
			      if (chance == 1) {
				      if (level.getRandom().nextBoolean()) {
					      level.addParticle(MagicLineParticleData.createData(comp.primaryColor.x, comp.primaryColor.y, comp.primaryColor.z),
						      pos.getX() + 0.875, pos.getY() + 0.125, pos.getZ() + 0.125,
						      pos.getX() + 0.5, pos.getY() + 0.625, pos.getZ() + 0.5);
				      }
				      else {
					      level.addParticle(MagicLineParticleData.createData(comp.secondaryColor.x, comp.secondaryColor.y, comp.secondaryColor.z),
						      pos.getX() + 0.875, pos.getY() + 0.125, pos.getZ() + 0.125,
						      pos.getX() + 0.5, pos.getY() + 0.625, pos.getZ() + 0.5);
				      }
			      }
			      if (chance == 2) {
				      if (level.getRandom().nextBoolean()) {
					      level.addParticle(MagicLineParticleData.createData(comp.primaryColor.x, comp.primaryColor.y, comp.primaryColor.z),
						      pos.getX() + 0.875, pos.getY() + 0.125, pos.getZ() + 0.875,
						      pos.getX() + 0.5, pos.getY() + 0.625, pos.getZ() + 0.5);
				      }
				      else {
					      level.addParticle(MagicLineParticleData.createData(comp.secondaryColor.x, comp.secondaryColor.y, comp.secondaryColor.z),
						      pos.getX() + 0.875, pos.getY() + 0.125, pos.getZ() + 0.875,
						      pos.getX() + 0.5, pos.getY() + 0.625, pos.getZ() + 0.5);
				      }
			      }
			      if (chance == 3) {
				      if (level.getRandom().nextBoolean()) {
					      level.addParticle(MagicLineParticleData.createData(comp.primaryColor.x, comp.primaryColor.y, comp.primaryColor.z),
						      pos.getX() + 0.125, pos.getY() + 0.125, pos.getZ() + 0.875,
						      pos.getX() + 0.5, pos.getY() + 0.625, pos.getZ() + 0.5);
				      }
				      else {
					      level.addParticle(MagicLineParticleData.createData(comp.secondaryColor.x, comp.secondaryColor.y, comp.secondaryColor.z),
						      pos.getX() + 0.125, pos.getY() + 0.125, pos.getZ() + 0.875,
						      pos.getX() + 0.5, pos.getY() + 0.625, pos.getZ() + 0.5);
				      }
			      }
		      }
	      }
      }
      if (tile.progress > 40) {
        tile.progress = 0;
      }
    }
  }
}
