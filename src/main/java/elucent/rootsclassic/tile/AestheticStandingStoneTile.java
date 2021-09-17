package elucent.rootsclassic.tile;

import elucent.rootsclassic.client.particles.MagicLineParticleData;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.Tags.Items;

public class AestheticStandingStoneTile extends TEBase implements ITickableTileEntity {

  int ticker = 0;
  int r = 0;
  int g = 0;
  int b = 0;

  public AestheticStandingStoneTile(TileEntityType<?> tileEntityTypeIn) {
    super(tileEntityTypeIn);
  }

  public AestheticStandingStoneTile() {
    this(RootsRegistry.AESTHETIC_STANDING_STONE_TILE.get());
  }

  @Override
  public ActionResultType activate(World world, BlockPos pos, BlockState state, PlayerEntity player, Hand hand, ItemStack heldItem, BlockRayTraceResult hit) {
    if (heldItem != null) {
      int amount = 5;
      if (heldItem.getItem().isIn(Tags.Items.DYES_RED)) {
        if (r < 255) {
          r += amount;
          return ActionResultType.SUCCESS;
        }
      }
      if (heldItem.getItem().isIn(Tags.Items.DYES_GREEN)) {
        if (g < 255) {
          g += amount;
          return ActionResultType.SUCCESS;
        }
      }
      if (heldItem.getItem().isIn(Tags.Items.DYES_BLUE)) {
        if (b < 255) {
          b += amount;
          return ActionResultType.SUCCESS;
        }
      }
      if (heldItem.getItem().isIn(Items.DYES_WHITE)) {
        r = 0;
        g = 0;
        b = 0;
        return ActionResultType.SUCCESS;
      }
    }
    return ActionResultType.SUCCESS;
  }

  @Override
  public void read(BlockState state, CompoundNBT nbt) {
    super.read(state, nbt);
    this.r = nbt.getInt("red");
    this.b = nbt.getInt("blue");
    this.g = nbt.getInt("green");
  }

  @Override
  public CompoundNBT write(CompoundNBT tag) {
    tag = super.write(tag);
    tag.putInt("red", r);
    tag.putInt("blue", b);
    tag.putInt("green", g);
    return tag;
  }

  @Override
  public void tick() {
    ticker++;
    if (ticker % 5 == 0 && world.isRemote) {
      for (double i = 0; i < 720; i += 45.0) {
        double xShift = 0.5 * Math.sin(Math.PI * (i / 360.0));
        double zShift = 0.5 * Math.cos(Math.PI * (i / 360.0));
        world.addParticle(MagicLineParticleData.createData(r, g, b),
            pos.getX() + 0.5 + xShift, pos.getY() + 0.5, pos.getZ() + 0.5 + zShift, 0, 0, 0);
      }
    }
  }
}
