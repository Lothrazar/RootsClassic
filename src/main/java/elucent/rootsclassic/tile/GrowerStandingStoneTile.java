package elucent.rootsclassic.tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import elucent.rootsclassic.client.particles.MagicAuraParticleData;
import elucent.rootsclassic.item.GrowthPowderItem;
import elucent.rootsclassic.registry.RootsRegistry;

public class GrowerStandingStoneTile extends TEBase implements ITickableTileEntity {

	private static final double PCT_CHANCE_PER_BLOCK = 0.03;
	private static final int TICK_SPEED = 100;
	private static final int MAX_TICKER = 500 * 20;
	private static final int RADIUS = 4;
	int ticker = 0;

	public GrowerStandingStoneTile(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}

	public GrowerStandingStoneTile() {
		this(RootsRegistry.GROWER_STANDING_STONE_TILE.get());
	}

	@Override
	public void read(BlockState state, CompoundNBT tag) {
		super.read(state, tag);
	}

	@Override
	public CompoundNBT write(CompoundNBT tag) {
		super.write(tag);
		return tag;
	}

	@Override
	public void tick() {
		updateTicker();
		spawnParticles();
		if (ticker % TICK_SPEED == 0) {
			applyGrowthToArea();
		}
	}

	private void updateTicker() {
		ticker++;
		if (ticker >= MAX_TICKER) {
			ticker = 0;
		}
	}

	private void applyGrowthToArea() {
		List<BlockPos> positions = new ArrayList<>();
		for (int xp = -1 * RADIUS; xp <= RADIUS; xp++) {
			for (int zp = -1 * RADIUS; zp <= RADIUS; zp++) {
				positions.add(this.getPos().add(xp, -1, zp));
				positions.add(this.getPos().add(xp, -2, zp));
			}
		}
		Collections.shuffle(positions);
		for (BlockPos pos : positions) {
			if (world.rand.nextDouble() < PCT_CHANCE_PER_BLOCK) {
				//this percent chance to actually fire
				GrowthPowderItem.applyGrowthHere(world, pos);
			}
		}
	}

	private void spawnParticles() {
		if (ticker % 5 == 0) {
			for (double i = 0; i < 720; i += 45.0) {
				double xShift = 0.5 * Math.sin(Math.PI * (i / 360.0));
				double zShift = 0.5 * Math.cos(Math.PI * (i / 360.0));
				world.addParticle(MagicAuraParticleData.createData(32, 255, 32),
						pos.getX() + 0.5 + xShift, pos.getY() + 0.5, pos.getZ() + 0.5 + zShift, 0, 0, 0);
			}
		}
	}
}
