package elucent.rootsclassic.component.components;

import java.util.ArrayList;
import java.util.Random;
import elucent.rootsclassic.ConfigManager;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ComponentAllium extends ComponentBase {

  Random random = new Random();

  public ComponentAllium() {
    super("allium", Blocks.RED_FLOWER, 8);
  }

  public void destroyBlockSafe(World world, BlockPos pos, int potency) {
    if (world.getBlockState(pos).getBlock().getHarvestLevel(world.getBlockState(pos)) <= 2 + potency && world.getBlockState(pos).getBlock().getBlockHardness(world.getBlockState(pos), world, pos) != -1) {
      world.destroyBlock(pos, true);
    }
  }

  @Override
  public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL) {
      int damageDealt = 0;
      ArrayList<EntityLivingBase> targets = (ArrayList<EntityLivingBase>) world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(x - size, y - size, z - size, x + size, y + size, z + size));
      for (int i = 0; i < targets.size(); i++) {
        if (targets.get(i).getUniqueID() != caster.getUniqueID()) {
          if (targets.get(i) instanceof EntityPlayer && ConfigManager.disablePVP) {}
          else {
            damageDealt += (int) (4 + 2 * potency);
            targets.get(i).attackEntityFrom(DamageSource.GENERIC, (int) (5 + 2 * potency));
            targets.get(i).setLastAttackedEntity(caster);
            targets.get(i).setRevengeTarget((EntityLivingBase) caster);
            targets.get(i).getEntityData().setDouble("RMOD_vuln", 1.0 + 0.5 * potency);
          }
        }
      }

    }
  }
}
