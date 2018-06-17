package elucent.rootsclassic.item;

import com.google.common.collect.Sets;
import elucent.rootsclassic.RegistryManager;
import elucent.rootsclassic.Util;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;

public class ItemLivingAxe extends ItemTool {

  public ItemLivingAxe() {
    super(RegistryManager.livingMaterial, Sets.newHashSet(new Block[] { Blocks.PLANKS }));

    setHarvestLevel("axe", this.toolMaterial.getHarvestLevel());
    this.damageVsEntity = this.toolMaterial.getDamageVsEntity() + 6.0f;
    this.attackSpeed = -3.1f;
  }

  @Override
  public float getStrVsBlock(ItemStack stack, IBlockState state) {
    Material material = state.getMaterial();
    return material != Material.WOOD && material != Material.PLANTS && material != Material.VINE ? super.getStrVsBlock(stack, state) : this.efficiencyOnProperMaterial;
  }

  @Override
  public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
    Util.randomlyRepair(world.rand, stack);
  }

}
