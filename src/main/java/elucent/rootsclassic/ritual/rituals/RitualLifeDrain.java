package elucent.rootsclassic.ritual.rituals;

import java.util.List;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import elucent.rootsclassic.ritual.RitualBase;

public class RitualLifeDrain extends RitualBase {

  public RitualLifeDrain(ResourceLocation name, int level, double r, double g, double b) {
    super(name, level, r, g, b);
  }

  @Override
  public void doEffect(World world, BlockPos pos, IInventory inventory, List<ItemStack> incenses) {
    inventory.clearContent();
    List<MonsterEntity> enemies = world.getEntitiesOfClass(MonsterEntity.class, new AxisAlignedBB(pos.getX() - 22, pos.getY() - 8, pos.getZ() - 22,
        pos.getX() + 23, pos.getY() + 9, pos.getZ() + 23));
    float drainedHealth = 0;
    if (enemies.size() > 0) {
      for (MonsterEntity enemy : enemies) {
        enemy.hurt(DamageSource.CACTUS, 9);
        drainedHealth += 9;
      }
    }
    List<PlayerEntity> players = world.getEntitiesOfClass(PlayerEntity.class, new AxisAlignedBB(pos.getX() - 22, pos.getY() - 8, pos.getZ() - 22,
        pos.getX() + 23, pos.getY() + 9, pos.getZ() + 23));
    float numPlayers = players.size();
    for (PlayerEntity player : players) {
      player.heal(drainedHealth / (numPlayers * 2.5f));
    }
  }
}
