package elucent.rootsclassic.component.components;

import java.util.ArrayList;
import java.util.Random;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.config.ConfigManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class ComponentWhiteTulip extends ComponentBase {

  Random random = new Random();

  public ComponentWhiteTulip() {
    super("whitetulip", Blocks.RED_FLOWER, 6, 10);
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
            targets.get(i).attackEntityFrom(DamageSource.GENERIC, (int) (5 + 3 * potency));
            damageDealt += (int) (5 + 3 * potency);
            targets.get(i).addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("slowness"), 200 + 100 * (int) potency, (int) potency));
            targets.get(i).setLastAttackedEntity(caster);
            targets.get(i).setRevengeTarget((EntityLivingBase) caster);
          }
        }
      }
    }
  }
}
