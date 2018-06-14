package elucent.rootsclassic;

import java.util.ArrayList;
import elucent.rootsclassic.component.ComponentEffect;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerManager {

  public static ArrayList<ComponentEffect> playerEffects = new ArrayList<ComponentEffect>();

  public static void addEffect(ComponentEffect effect) {
    for (int i = 0; i < playerEffects.size(); i++) {
      ComponentEffect eff = playerEffects.get(i);
      if (eff.player.getUniqueID() == effect.player.getUniqueID()) {
        if (eff.name == effect.name) {
          eff.duration = effect.duration;
          return;
        }
      }
    }
    playerEffects.add(effect);
  }

  public static boolean hasEffect(EntityPlayer player, String name) {
    for (int i = 0; i < playerEffects.size(); i++) {
      ComponentEffect eff = playerEffects.get(i);
      if (eff.player.getUniqueID() == player.getUniqueID() && name == eff.name) {
        return true;
      }
    }
    return false;
  }

  public static void updateEffects() {
    for (int i = 0; i < playerEffects.size(); i++) {
      ComponentEffect eff = playerEffects.get(i);
      eff.duration--;
      if (eff.duration <= 0) {
        playerEffects.remove(i);
      }
    }
  }

}
