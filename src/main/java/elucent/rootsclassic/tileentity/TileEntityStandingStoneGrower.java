package elucent.rootsclassic.tileentity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import elucent.rootsclassic.Roots;
import elucent.rootsclassic.item.ItemGrowthSalve;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityStandingStoneGrower extends TEBase implements ITickable {

  private static final double PCT_CHANCE_PER_BLOCK = 0.03;
  private static final int TICK_SPEED = 100;
  private static final int MAX_TICKER = 500 * 20;
  private static final int RADIUS = 4;
  int ticker = 0;

  public TileEntityStandingStoneGrower() {
    super();
  }

  @Override
  public void readFromNBT(NBTTagCompound tag) {
    super.readFromNBT(tag);
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound tag) {
    super.writeToNBT(tag);
    return tag;
  }

  @Override
  public void update() {
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
    List<BlockPos> positions = new ArrayList<BlockPos>();
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
        ItemGrowthSalve.applyGrowthHere(world, pos);
      }
    }
  }

  private void spawnParticles() {
    if (ticker % 5 == 0) {
      for (double i = 0; i < 720; i += 45.0) {
        double xShift = 0.5 * Math.sin(Math.PI * (i / 360.0));
        double zShift = 0.5 * Math.cos(Math.PI * (i / 360.0));
        Roots.proxy.spawnParticleMagicAuraFX(this.getWorld(), this.getPos().getX() + 0.5 + xShift, this.getPos().getY() + 0.5, this.getPos().getZ() + 0.5 + zShift, 0, 0, 0, 32, 255, 32);
      }
    }
  }
}
