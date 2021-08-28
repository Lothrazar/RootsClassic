package elucent.rootsclassic.component.components;

import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.util.RootsUtil;

public class ComponentLilyPad extends ComponentBase {

	public ComponentLilyPad() {
		super(new ResourceLocation(Const.MODID, "lily_pad"), Blocks.LILY_PAD, 8);
	}

	@Override
	public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
		if (type == EnumCastType.SPELL) {
			if (caster instanceof PlayerEntity && !world.isRemote) {
				BlockPos pos = RootsUtil.getRayTrace(world, (PlayerEntity) caster, 4 + 2 * (int) size);
				if (world.getBlockState(pos.up()).isReplaceable(Fluids.WATER)) {
					world.setBlockState(pos.up(), Blocks.WATER.getDefaultState().with(FlowingFluidBlock.LEVEL, 15), 3);
				}
				if (world.getBlockState(pos.up().west()).isReplaceable(Fluids.WATER)) {
					world.setBlockState(pos.up().west(), Blocks.WATER.getDefaultState().with(FlowingFluidBlock.LEVEL, 15), 3);
				}
				if (world.getBlockState(pos.up().east()).isReplaceable(Fluids.WATER)) {
					world.setBlockState(pos.up().east(), Blocks.WATER.getDefaultState().with(FlowingFluidBlock.LEVEL, 15), 3);
				}
				if (world.getBlockState(pos.up().north()).isReplaceable(Fluids.WATER)) {
					world.setBlockState(pos.up().north(), Blocks.WATER.getDefaultState().with(FlowingFluidBlock.LEVEL, 15), 3);
				}
				if (world.getBlockState(pos.up().south()).isReplaceable(Fluids.WATER)) {
					world.setBlockState(pos.up().south(), Blocks.WATER.getDefaultState().with(FlowingFluidBlock.LEVEL, 15), 3);
				}
			}
		}
	}
}
