package elucent.rootsclassic;

import java.util.ArrayList;
import elucent.rootsclassic.component.ComponentEffect;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.Achievement;

public class PlayerManager {

  public static ArrayList<ComponentEffect> playerEffects = new ArrayList<ComponentEffect>();

  public static void addEffect(ComponentEffect effect) {
    for (int i = 0; i < playerEffects.size(); i++) {
      if (playerEffects.get(i).player.getUniqueID() == effect.player.getUniqueID()) {
        if (playerEffects.get(i).name == effect.name) {
          playerEffects.get(i).duration = effect.duration;
          return;
        }
      }
    }
    playerEffects.add(effect);
  }

  public static boolean hasEffect(EntityPlayer player, String name) {
    for (int i = 0; i < playerEffects.size(); i++) {
      if (playerEffects.get(i).player.getUniqueID() == player.getUniqueID() && name == playerEffects.get(i).name) {
        return true;
      }
    }
    return false;
  }

  public static void updateEffects() {
    for (int i = 0; i < playerEffects.size(); i++) {
      playerEffects.get(i).duration--;
      if (playerEffects.get(i).duration <= 0) {
        playerEffects.remove(i);
      }
    }
  }

  public static void addAchievement(EntityPlayer player, Achievement ach) {
    if (!player.getEntityWorld().isRemote) {
      player.addStat(ach, 1);
    }
  }
}
