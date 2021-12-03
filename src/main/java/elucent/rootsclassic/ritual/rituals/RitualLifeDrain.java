package elucent.rootsclassic.ritual.rituals;

import elucent.rootsclassic.ritual.RitualBase;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class RitualLifeDrain extends RitualBase {

  public RitualLifeDrain(ResourceLocation name, int level, double r, double g, double b) {
    super(name, level, r, g, b);
  }

  @Override
  public void doEffect(Level world, BlockPos pos, Container inventory, List<ItemStack> incenses) {
    inventory.clearContent();
    List<Monster> enemies = world.getEntitiesOfClass(Monster.class, new AABB(pos.getX() - 22, pos.getY() - 8, pos.getZ() - 22,
        pos.getX() + 23, pos.getY() + 9, pos.getZ() + 23));
    float drainedHealth = 0;
    if (enemies.size() > 0) {
      for (Monster enemy : enemies) {
        enemy.hurt(DamageSource.CACTUS, 9);
        drainedHealth += 9;
      }
    }
    List<Player> players = world.getEntitiesOfClass(Player.class, new AABB(pos.getX() - 22, pos.getY() - 8, pos.getZ() - 22,
        pos.getX() + 23, pos.getY() + 9, pos.getZ() + 23));
    float numPlayers = players.size();
    for (Player player : players) {
      player.heal(drainedHealth / (numPlayers * 2.5f));
    }
  }
}
