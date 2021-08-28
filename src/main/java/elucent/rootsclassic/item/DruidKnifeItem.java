package elucent.rootsclassic.item;

import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.registry.RootsRegistry;

public class DruidKnifeItem extends Item {
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
		super(properties);
	}

	public ActionResultType onItemUse(ItemUseContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getPos();
		BlockState state = world.getBlockState(pos);
		BlockState strippedState = getStrippingState(state);
		ItemStack barkDrop = getBarkDrop(state);
		if (!barkDrop.isEmpty() && strippedState != null) {
			ItemStack stack = context.getItem();
			Hand hand = context.getHand();
			PlayerEntity playerIn = context.getPlayer();
			playerIn.entityDropItem(barkDrop, 1.0f);
			stack.damageItem(1, playerIn, e -> e.sendBreakAnimation(hand));
			if (world.rand.nextDouble() < RootsConfig.COMMON.barkKnifeBlockStripChance.get()) {
				world.playSound(playerIn, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
				if (!world.isRemote) {
					world.setBlockState(pos, strippedState, 11);
				}
			}

			return ActionResultType.func_233537_a_(world.isRemote);
		} else {
			return ActionResultType.PASS;
		}
	}

	public static BlockState getStrippingState(BlockState originalState) {
		Block block = BLOCK_STRIPPING_MAP.get(originalState.getBlock());
		return block != null ? block.getDefaultState().with(RotatedPillarBlock.AXIS, originalState.get(RotatedPillarBlock.AXIS)) : null;
	}

	public static ItemStack getBarkDrop(BlockState originalState) {
		Block block = originalState.getBlock();
		if(BLOCK_BARK_MAP.containsKey(block)) {
			return new ItemStack(BLOCK_BARK_MAP.get(block).get());
		} else {
			return ItemStack.EMPTY;
		}
	}
}
