package elucent.rootsclassic.component.components;

import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.util.RootsUtil;

public class ComponentBlueOrchid extends ComponentBase {

	public ComponentBlueOrchid() {
		super(new ResourceLocation(Const.MODID, "blue_orchid"), Blocks.BLUE_ORCHID, 14);
	}

	@Override
	public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
		if (type == EnumCastType.SPELL) {
			if (caster instanceof PlayerEntity && !world.isRemote) {
				BlockPos pos = RootsUtil.getRayTrace(world, (PlayerEntity) caster, 4 + 2 * (int) size);
				BlockState state = world.getBlockState(pos);
				Block block = world.getBlockState(pos).getBlock();
				if (block == Blocks.STONE || block == Blocks.DIRT || block == Blocks.GRASS || block == Blocks.SAND || block == Blocks.GRAVEL) {
					if (block == Blocks.GRASS) {
						state = Blocks.DIRT.getDefaultState();
						world.setBlockState(pos, state);
					}
					world.setBlockState(pos.up(), state);
					ArrayList<LivingEntity> targets = (ArrayList<LivingEntity>) world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(pos.getX() - size, pos.getY() - size, pos.getZ() - size, pos.getX() + size, pos.getY() + size, pos.getZ() + size));
					for (LivingEntity target : targets) {
						if (target.getUniqueID() != caster.getUniqueID()) {
							target.addVelocity(0, 3, 0);
							Vector3d motion = target.getMotion();
							target.setMotion(motion.x, 0.65 + world.rand.nextDouble() + 0.25 * potency, motion.z);
							if (target instanceof PlayerEntity) {
								((PlayerEntity) target).velocityChanged = true;
							}
						}
					}
					if (world.rand.nextInt(3) == 0) {
						world.setBlockState(pos.up().west().north(), state);
					}
					if (world.rand.nextInt(3) == 0) {
						world.setBlockState(pos.up().east().south(), state);
					}
					if (world.rand.nextInt(3) == 0) {
						world.setBlockState(pos.up().north().east(), state);
					}
					if (world.rand.nextInt(3) == 0) {
						world.setBlockState(pos.up().south().west(), state);
					}
					if (world.rand.nextInt(1) == 0) {
						world.setBlockState(pos.up().west(), state);
					}
					if (world.rand.nextInt(1) == 0) {
						world.setBlockState(pos.up().east(), state);
					}
					if (world.rand.nextInt(1) == 0) {
						world.setBlockState(pos.up().north(), state);
					}
					if (world.rand.nextInt(1) == 0) {
						world.setBlockState(pos.up().south(), state);
					}
					world.setBlockState(pos.up().up(), state);
					if (world.rand.nextInt(3) == 0) {
						world.setBlockState(pos.up().up().west(), state);
					}
					if (world.rand.nextInt(3) == 0) {
						world.setBlockState(pos.up().up().east(), state);
					}
					if (world.rand.nextInt(3) == 0) {
						world.setBlockState(pos.up().up().north(), state);
					}
					if (world.rand.nextInt(3) == 0) {
						world.setBlockState(pos.up().up().south(), state);
					}
					world.setBlockState(pos.up().up().up(), state);
				}
			}
		}
	}
}
