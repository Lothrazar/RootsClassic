package elucent.rootsclassic.component.components;

import java.util.ArrayList;
import java.util.Random;
import elucent.rootsclassic.ConfigManager;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class ComponentNetherWart extends ComponentBase {

  Random random = new Random();

  public ComponentNetherWart() {
    super("netherwart", "Inferno", Items.NETHER_WART, 10);
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
            damageDealt += (int) (5 + 3 * potency);
            targets.get(i).attackEntityFrom(DamageSource.inFire, (int) (5 + 3 * potency));
            targets.get(i).setFire((int) (4 + 3 * potency));
            targets.get(i).setLastAttacker(caster);
            targets.get(i).setRevengeTarget((EntityLivingBase) caster);
          }
        }
      }
      //      if (damageDealt > 80) {
      //        if (caster instanceof EntityPlayer) {
      //          if (!((EntityPlayer) caster).hasAchievement(RegistryManager.achieveLotsDamage)) {
      //            PlayerManager.addAchievement(((EntityPlayer) caster), RegistryManager.achieveLotsDamage);
      //          }
      //        }
      //      }
    }
  }
}
