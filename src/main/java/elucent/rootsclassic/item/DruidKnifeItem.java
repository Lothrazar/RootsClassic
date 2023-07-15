package elucent.rootsclassic.item;

import java.util.Map;
import java.util.function.Supplier;
import com.google.common.collect.ImmutableMap.Builder;
import com.lothrazar.library.item.ItemFlib;
import com.lothrazar.library.util.ItemStackUtil;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

public class DruidKnifeItem extends ItemFlib {

  protected static final Map<Block, Block> BLOCK_STRIPPING_MAP = (new Builder<Block, Block>())
      .put(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD).put(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG)
      .put(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD).put(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG)
      .put(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD).put(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG)
      .put(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD).put(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG)
      .put(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD).put(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG)
      .put(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD).put(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG)
      //			.put(Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM).put(Blocks.WARPED_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE)
      //			.put(Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM).put(Blocks.CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_HYPHAE)
      .build();
  protected static final Map<Block, Supplier<Item>> BLOCK_BARK_MAP = (new Builder<Block, Supplier<Item>>())
      .put(Blocks.OAK_WOOD, RootsRegistry.OAK_BARK).put(Blocks.OAK_LOG, RootsRegistry.OAK_BARK)
      .put(Blocks.DARK_OAK_WOOD, RootsRegistry.DARK_OAK_BARK).put(Blocks.DARK_OAK_LOG, RootsRegistry.DARK_OAK_BARK)
      .put(Blocks.ACACIA_WOOD, RootsRegistry.ACACIA_BARK).put(Blocks.ACACIA_LOG, RootsRegistry.ACACIA_BARK)
      .put(Blocks.BIRCH_WOOD, RootsRegistry.BIRCH_BARK).put(Blocks.BIRCH_LOG, RootsRegistry.BIRCH_BARK)
      .put(Blocks.JUNGLE_WOOD, RootsRegistry.JUNGLE_BARK).put(Blocks.JUNGLE_LOG, RootsRegistry.JUNGLE_BARK)
      .put(Blocks.SPRUCE_WOOD, RootsRegistry.SPRUCE_BARK).put(Blocks.SPRUCE_LOG, RootsRegistry.SPRUCE_BARK)
      //			.put(Blocks.WARPED_STEM, Items.STRIPPED_WARPED_STEM).put(Blocks.WARPED_HYPHAE, Items.STRIPPED_WARPED_HYPHAE)
      //			.put(Blocks.CRIMSON_STEM, Items.STRIPPED_CRIMSON_STEM).put(Blocks.CRIMSON_HYPHAE, Items.STRIPPED_CRIMSON_HYPHAE)
      .build();

  public DruidKnifeItem(Properties properties) {
    super(properties, new ItemFlib.Settings().tooltip());
  }

  @Override
  public void inventoryTick(ItemStack stack, Level levelAccessor, Entity entity, int slot, boolean selected) {
    ItemStackUtil.randomlyRepair(levelAccessor.random, stack, 80);
  }

  public InteractionResult useOn(UseOnContext context) {
    Level levelAccessor = context.getLevel();
    BlockPos pos = context.getClickedPos();
    BlockState state = levelAccessor.getBlockState(pos);
    BlockState strippedState = getStrippingState(state);
    ItemStack barkDrop = getBarkDrop(state);
    if (!barkDrop.isEmpty() && strippedState != null) {
      ItemStack stack = context.getItemInHand();
      InteractionHand hand = context.getHand();
      Player playerIn = context.getPlayer();
      playerIn.spawnAtLocation(barkDrop, 1.0f);
      stack.hurtAndBreak(1, playerIn, e -> e.broadcastBreakEvent(hand));
      if (levelAccessor.random.nextDouble() < RootsConfig.barkKnifeBlockStripChance.get()) {
        levelAccessor.playSound(playerIn, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
        if (!levelAccessor.isClientSide) {
          levelAccessor.setBlock(pos, strippedState, 11);
        }
      }
      return InteractionResult.sidedSuccess(levelAccessor.isClientSide);
    }
    else {
      return InteractionResult.PASS;
    }
  }

  public static BlockState getStrippingState(BlockState originalState) {
    Block block = BLOCK_STRIPPING_MAP.get(originalState.getBlock());
    return block != null ? block.defaultBlockState().setValue(RotatedPillarBlock.AXIS, originalState.getValue(RotatedPillarBlock.AXIS)) : null;
  }

  public static ItemStack getBarkDrop(BlockState originalState) {
    Block block = originalState.getBlock();
    if (BLOCK_BARK_MAP.containsKey(block)) {
      return new ItemStack(BLOCK_BARK_MAP.get(block).get());
    }
    else {
      return ItemStack.EMPTY;
    }
  }
}
