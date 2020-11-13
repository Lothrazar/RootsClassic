package elucent.rootsclassic.block.mortar;

import java.util.ArrayList;
import java.util.List;
import elucent.rootsclassic.RegistryManager;
import elucent.rootsclassic.component.ComponentManager;
import elucent.rootsclassic.component.ComponentRecipe;
import elucent.rootsclassic.tileentity.TEBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class TileEntityMortar extends TEBase {

  private static final int MAX_INVO_SIZE = 8;
  private static final String NBT_MODIFIERS = "modifiers";
  public List<ItemStack> inventory = new ArrayList<ItemStack>();

  public TileEntityMortar() {
    super();
  }

  @Override
  public void readFromNBT(NBTTagCompound tag) {
    super.readFromNBT(tag);
    inventory = new ArrayList<ItemStack>();
    if (tag.hasKey(NBT_MODIFIERS)) {
      NBTTagList list = tag.getTagList(NBT_MODIFIERS, Constants.NBT.TAG_COMPOUND);
      for (int i = 0; i < list.tagCount(); i++) {
        inventory.add(new ItemStack(list.getCompoundTagAt(i)));
      }
    }
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound tag) {
    super.writeToNBT(tag);
    if (inventory.size() > 0) {
      NBTTagList list = new NBTTagList();
      for (int i = 0; i < inventory.size(); i++) {
        list.appendTag(inventory.get(i).writeToNBT(new NBTTagCompound()));
      }
      tag.setTag(NBT_MODIFIERS, list);
    }
    return tag;
  }

  @Override
  public void breakBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
    dropAllItems(world, pos);
    this.invalidate();
  }

  private void dropAllItems(World world, BlockPos pos) {
    for (ItemStack stack : inventory) {
      if (!world.isRemote && !stack.isEmpty()) {
        world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack));
      }
    }
  }

  @Override
  public boolean activate(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
    if (heldItem.isEmpty() && hand == EnumHand.MAIN_HAND) {
      return tryDropSingleItem(world, pos, state);
    }
    else if (heldItem.getItem() == RegistryManager.pestle) {
      return tryActivateRecipe(player, state);
    }
    else {
      return tryInsertItem(world, pos, state, heldItem);
    }
  }

  private boolean tryInsertItem(World world, BlockPos pos, IBlockState state, ItemStack heldItem) {
    if (inventory.size() < MAX_INVO_SIZE) {
      if (heldItem.getItem() == Items.GLOWSTONE_DUST || heldItem.getItem() == Items.REDSTONE || heldItem.getItem() == Items.GUNPOWDER) {
        int maxCapacity = ComponentRecipe.getModifierCapacity(inventory);
        int modifierCount = ComponentRecipe.getModifierCount(inventory);
        if (modifierCount < maxCapacity) {
          inventory.add(new ItemStack(heldItem.getItem(), 1, heldItem.getMetadata()));
          heldItem.shrink(1);
          markDirty();
          this.getWorld().notifyBlockUpdate(getPos(), state, world.getBlockState(pos), 3);
          return true;
        }
      }
      else {
        ItemStack oneItem = new ItemStack(heldItem.getItem(), 1, heldItem.getItemDamage());
        if (heldItem.hasTagCompound()) {
          oneItem.setTagCompound(heldItem.getTagCompound());
        }
        inventory.add(oneItem);
        heldItem.shrink(1);
        markDirty();
        this.getWorld().notifyBlockUpdate(getPos(), state, world.getBlockState(pos), 3);
        return true;
      }
    }
    return false;
  }

  private boolean tryDropSingleItem(World world, BlockPos pos, IBlockState state) {
    if (inventory.size() > 0) {
      if (!world.isRemote) {
        world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, inventory.remove(inventory.size() - 1)));
      }
      else {
        inventory.remove(inventory.size() - 1);
      }
      markDirty();
      this.getWorld().notifyBlockUpdate(getPos(), state, world.getBlockState(pos), 3);
      return true;
    }
    return false;
  }

  private boolean tryActivateRecipe(EntityPlayer player, IBlockState state) {
    ComponentRecipe recipe = ComponentManager.getRecipeFromInput(inventory);
    if (recipe == null) {
      player.sendStatusMessage(new TextComponentString(I18n.translateToLocalFormatted("roots.mortar.invalid")), true);
      return false;
    }
    else if (recipe.isDisabled()) {
      player.sendStatusMessage(new TextComponentString(I18n.translateToLocalFormatted("roots.mortar.disabled")), true);
      return false;
    }
    else if (recipe.needsMixin() && ComponentRecipe.getModifierCapacity(inventory) < 0) {
      player.sendStatusMessage(new TextComponentString(I18n.translateToLocalFormatted("roots.mortar.mixin")), true);
      return false;
    }
    if (!world.isRemote) {
      world.spawnEntity(new EntityItem(world, getPos().getX() + 0.5, getPos().getY() + 0.5, getPos().getZ() + 0.5, recipe.getRecipeResult(inventory)));
    }
    inventory.clear();
    markDirty();
    world.notifyBlockUpdate(getPos(), state, world.getBlockState(pos), 3);
    return true;
  }
}
