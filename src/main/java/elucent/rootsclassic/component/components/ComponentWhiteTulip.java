package elucent.rootsclassic.component.components;

import java.util.ArrayList;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.config.RootsConfig;

public class ComponentWhiteTulip extends ComponentBase {

  public ComponentWhiteTulip() {
    super(new ResourceLocation(Const.MODID, "white_tulip"), Blocks.WHITE_TULIP, 10);
  }

  @Override
  public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (type == EnumCastType.SPELL) {
      //   int damageDealt = 0;
      ArrayList<LivingEntity> targets = (ArrayList<LivingEntity>) world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(x - size, y - size, z - size, x + size, y + size, z + size));
      for (LivingEntity target : targets) {
        if (target.getUniqueID() != caster.getUniqueID()) {
          if (target instanceof PlayerEntity && RootsConfig.COMMON.disablePVP.get()) {}
          else {
            target.attackEntityFrom(DamageSource.GENERIC, (int) (5 + 3 * potency));
            //     damageDealt += (int) (5 + 3 * potency);
            target.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 200 + 100 * (int) potency, (int) potency));
            target.setLastAttackedEntity(caster);
            target.setRevengeTarget((LivingEntity) caster);
          }
        }
      }
    }
  }
}
