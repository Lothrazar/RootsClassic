package elucent.rootsclassic.component.components;

import java.util.ArrayList;
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

  Random random = new Random();

  public ComponentSunflower() {
    super("sunflower", "Solar Smite", Blocks.DOUBLE_PLANT, 0, 16);
  }

  @Override
  public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL) {
      int damageDealt = 0;
      ArrayList<EntityLivingBase> targets = (ArrayList<EntityLivingBase>) world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(x - size, y - size, z - size, x + size, y + size, z + size));
      for (int i = 0; i < targets.size(); i++) {
        if (targets.get(i).getUniqueID() != caster.getUniqueID()) {
          if (targets.get(i) instanceof EntityPlayer && !world.getMinecraftServer().isPVPEnabled()) {}
          else {
            targets.get(i).attackEntityFrom(DamageSource.inFire, (int) (3 + 2 * potency));
            targets.get(i).setLastAttacker(caster);
            targets.get(i).setRevengeTarget((EntityLivingBase) caster);
            damageDealt += (int) (3 + 2 * potency);
            if (targets.get(i).isEntityUndead()) {
              targets.get(i).attackEntityFrom(DamageSource.inFire, (int) (5 + 4 * potency));
              damageDealt += (int) (5 + 4 * potency);
              targets.get(i).setFire((int) (7 + 4 * potency));
              targets.get(i).addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("minecraft:weakness"), 15, 2 + (int) potency));
              targets.get(i).addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("minecraft:slowness"), 15, 2 + (int) potency));
            }
          }
        }
      }

    }
  }
}
