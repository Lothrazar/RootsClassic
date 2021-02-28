package elucent.rootsclassic.event;

import java.util.Random;
import elucent.rootsclassic.RegistryManager;
import elucent.rootsclassic.Util;
import elucent.rootsclassic.config.ConfigManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHarvestDrops {

  @SubscribeEvent
  public void onBlockHarvested(HarvestDropsEvent event) {
    Random random = event.getWorld().rand;
    IBlockState blockState = event.getState();
    Block block = blockState.getBlock();
    if (block == Blocks.TALLGRASS) {
      if (random.nextInt(ConfigManager.oldRootDropChance) == 0) {
        event.getDrops().add(new ItemStack(RegistryManager.oldRoot, 1));
      }
    }
    if (block == Blocks.WHEAT || block == Blocks.CARROTS || block == Blocks.POTATOES || block == Blocks.BEETROOTS) {
      if (((BlockCrops) block).isMaxAge(blockState)) {
        if (random.nextInt(ConfigManager.verdantSprigDropChance) == 0) {
          event.getDrops().add(new ItemStack(RegistryManager.verdantSprig, 1));
        }
      }
    }
    if (block == Blocks.NETHER_WART) {
      if (block.getMetaFromState(blockState) != 0) {
        if (random.nextInt(ConfigManager.infernalStemDropChance) == 0) {
          event.getDrops().add(new ItemStack(RegistryManager.infernalStem, 1));
        }
      }
    }
    if (block == Blocks.CHORUS_FLOWER) {
      if (random.nextInt(ConfigManager.dragonsEyeDropChance) == 0) {
        event.getDrops().add(new ItemStack(RegistryManager.dragonsEye, 1));
      }
    }
    if (block.isLeaves(blockState, event.getWorld(), event.getPos())) {
      if (random.nextInt(ConfigManager.berriesDropChance) == 0) {
        event.getDrops().add(new ItemStack(Util.berries.get(random.nextInt(Util.berries.size())), 1));
      }
    }
  }
}
