package elucent.rootsclassic.ritual.rituals;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import elucent.rootsclassic.PlayerManager;
import elucent.rootsclassic.RegistryManager;
import elucent.rootsclassic.Util;
import elucent.rootsclassic.ritual.RitualBase;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.ServerStatusResponse.Players;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RitualLifeDrain extends RitualBase {

  public RitualLifeDrain(String name, double r, double g, double b) {
    super(name, r, g, b);
  }

  @Override
  public void doEffect(World world, BlockPos pos, List<ItemStack> inventory, List<ItemStack> incenses) {
    inventory.clear();
    List<EntityMob> enemies = (List<EntityMob>) world.getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB(pos.getX() - 22, pos.getY() - 8, pos.getZ() - 22, pos.getX() + 23, pos.getY() + 9, pos.getZ() + 23));
    float drainedHealth = 0;
    if (enemies.size() > 0) {
      for (int i = 0; i < enemies.size(); i++) {
        enemies.get(i).attackEntityFrom(DamageSource.cactus, 9);
        drainedHealth += 9;
      }
    }
    List<EntityPlayer> players = (List<EntityPlayer>) world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos.getX() - 22, pos.getY() - 8, pos.getZ() - 22, pos.getX() + 23, pos.getY() + 9, pos.getZ() + 23));
    float numPlayers = players.size();
    for (int i = 0; i < numPlayers; i++) {
      players.get(i).heal(drainedHealth / (numPlayers * 2.5f));
      if (drainedHealth / (numPlayers * 2.5f) > 20.0) {
        if (!players.get(i).hasAchievement(RegistryManager.achieveVampire)) {
          PlayerManager.addAchievement(players.get(i), RegistryManager.achieveVampire);
        }
      }
    }
  }
}
