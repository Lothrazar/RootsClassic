package elucent.rootsclassic.tile;

import elucent.rootsclassic.client.particles.MagicLineParticleData;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.Tags.Items;

public class AestheticStandingStoneTile extends TEBase {

  int ticker = 0;
  int r = 0;
  int g = 0;
  int b = 0;

  public AestheticStandingStoneTile(BlockPos pos, BlockState state) {
    super(RootsRegistry.AESTHETIC_STANDING_STONE_TILE.get(), pos, state);
  }

  @Override
  public InteractionResult activate(Level world, BlockPos pos, BlockState state, Player player, InteractionHand hand, ItemStack heldItem, BlockHitResult hit) {
    if (heldItem != null) {
      int amount = 5;
      if (heldItem.is(Tags.Items.DYES_RED)) {
        if (r < 255) {
          r += amount;
          return InteractionResult.SUCCESS;
        }
      }
      if (heldItem.is(Tags.Items.DYES_GREEN)) {
        if (g < 255) {
          g += amount;
          return InteractionResult.SUCCESS;
        }
      }
      if (heldItem.is(Tags.Items.DYES_BLUE)) {
        if (b < 255) {
          b += amount;
          return InteractionResult.SUCCESS;
        }
      }
      if (heldItem.is(Items.DYES_WHITE)) {
        r = 0;
        g = 0;
        b = 0;
        return InteractionResult.SUCCESS;
      }
    }
    return InteractionResult.SUCCESS;
  }

  @Override
  public void load(CompoundTag nbt) {
    super.load(nbt);
    this.r = nbt.getInt("red");
    this.b = nbt.getInt("blue");
    this.g = nbt.getInt("green");
  }

  @Override
  public CompoundTag save(CompoundTag tag) {
    tag = super.save(tag);
    tag.putInt("red", r);
    tag.putInt("blue", b);
    tag.putInt("green", g);
    return tag;
  }

  private void tick() {
    ticker++;
    if (ticker % 5 == 0) {
      for (double i = 0; i < 720; i += 45.0) {
        double xShift = 0.5 * Math.sin(Math.PI * (i / 360.0));
        double zShift = 0.5 * Math.cos(Math.PI * (i / 360.0));
        level.addParticle(MagicLineParticleData.createData(r, g, b),
            worldPosition.getX() + 0.5 + xShift, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5 + zShift, 0, 0, 0);
      }
    }
  }
}
