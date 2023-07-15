package elucent.rootsclassic.ritual.rituals;

import java.util.List;
import elucent.rootsclassic.ritual.SimpleRitualEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class RitualLifeDrain extends SimpleRitualEffect {

  @Override
  public void doEffect(Level levelAccessor, BlockPos pos, Container inventory, List<ItemStack> incenses) {
    inventory.clearContent();
    List<Monster> enemies = levelAccessor.getEntitiesOfClass(Monster.class, new AABB(pos.getX() - 22, pos.getY() - 8, pos.getZ() - 22,
        pos.getX() + 23, pos.getY() + 9, pos.getZ() + 23));
    float drainedHealth = 0;
    if (enemies.size() > 0) {
      for (Monster enemy : enemies) {
        enemy.hurt(levelAccessor.damageSources().cactus(), 9);
        drainedHealth += 9;
      }
    }
    List<Player> players = levelAccessor.getEntitiesOfClass(Player.class, new AABB(pos.getX() - 22, pos.getY() - 8, pos.getZ() - 22,
        pos.getX() + 23, pos.getY() + 9, pos.getZ() + 23));
    float numPlayers = players.size();
    for (Player player : players) {
      player.heal(drainedHealth / (numPlayers * 2.5f));
    }
  }
}
