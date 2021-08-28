package elucent.rootsclassic.component.components;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.client.particles.MagicAuraParticleData;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.util.RootsUtil;

public class ComponentPeony extends ComponentBase {

  List<BlockPos> pos = new CopyOnWriteArrayList<>();

  public ComponentPeony() {
    super(new ResourceLocation(Const.MODID, "peony"), Blocks.PEONY, 24);
  }

  @Override
  public void castingAction(PlayerEntity player, int count, int potency, int efficiency, int size) {
    World world = player.world;
    int x = player.getPosition().getX();
    int y = player.getPosition().getY();
    int z = player.getPosition().getZ();
    int range = 5;
    for (int x2 = -range; x2 < range; x2++) {
      for (int z2 = -range; z2 < range; z2++) {
        for (int y2 = -1; y2 < 2; y2++) {
          if (world.getBlockState(new BlockPos(x + x2, y + y2, z + z2)).getBlock() instanceof TallFlowerBlock ||
              world.getBlockState(new BlockPos(x + x2, y + y2, z + z2)).getBlock() instanceof FlowerBlock ||
              world.getBlockState(new BlockPos(x + x2, y + y2, z + z2)).getBlock() instanceof DoublePlantBlock) {
            pos.add(new BlockPos(x + x2, y + y2, z + z2));
          }
        }
      }
    }
    pos.forEach((temp) -> {
      int fX = temp.getX();
      int fY = temp.getY();
      int fZ = temp.getZ();
      double factor = 0.15;
      if (world.rand.nextInt(10 * pos.size()) == 0) {
        world.addParticle(MagicAuraParticleData.createData(138, 42, 235),
            fX + RootsUtil.randomDouble(0.0, 0.5), fY + RootsUtil.randomDouble(0.1, 0.5), fZ + RootsUtil.randomDouble(0.0, 0.5),
            (player.getPosX() - fX) * factor, (player.getPosY() - fY) * factor, (player.getPosZ() - fZ) * factor);
        world.addParticle(MagicAuraParticleData.createData(138, 42, 235),
            fX + RootsUtil.randomDouble(0.0, 0.5), fY + RootsUtil.randomDouble(0.1, 0.5), fZ + RootsUtil.randomDouble(0.0, 0.5),
            (player.getPosX() - fX) * factor, (player.getPosY() - fY) * factor, (player.getPosZ() - fZ) * factor);
      }
    });
  }

  @Override
  public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL) {
      ((LivingEntity) caster).heal((float) (pos.size() / 2 + (potency * 2)));
      pos.clear();
    }
  }
}