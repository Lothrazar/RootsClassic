package elucent.rootsclassic.component.components;

import java.util.List;
import java.util.Random;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class ComponentSunflower extends ComponentBase {

  private static final int POTION_DURATION = 15;
  Random random = new Random();

  public ComponentSunflower() {
    super("sunflower", Blocks.DOUBLE_PLANT, 0, 16);
  }

  @Override
  public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL && caster instanceof EntityLivingBase) {
      int damageDealt = 0;
      List<EntityLivingBase> targets = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(x - size, y - size, z - size, x + size, y + size, z + size));
      EntityLivingBase target;
      for (int i = 0; i < targets.size(); i++) {
        target = targets.get(i);
        if (target.getUniqueID() == caster.getUniqueID()) {
          continue;//dont hurt self
        }
        if (world.getMinecraftServer() != null) {
          if (target instanceof EntityPlayer && !world.getMinecraftServer().isPVPEnabled()) {
            continue;//no pvp allowed
          }
        }
        if (target.isEntityUndead()) {
          damageDealt += (int) (5 + 4 * potency);
          target.attackEntityFrom(DamageSource.IN_FIRE, damageDealt);
          target.setFire(damageDealt);
          target.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("minecraft:weakness"), POTION_DURATION, 2 + (int) potency));
          target.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("minecraft:slowness"), POTION_DURATION, 2 + (int) potency));
        }
        else {
          damageDealt += (int) (3 + 2 * potency);
        }
        target.attackEntityFrom(DamageSource.IN_FIRE, damageDealt);
        target.setLastAttackedEntity(caster);
        target.setRevengeTarget((EntityLivingBase) caster);
      }
    }
  }
}
