package elucent.rootsclassic.component;

import java.util.ArrayList;
import java.util.List;
import elucent.rootsclassic.RegistryManager;
import elucent.rootsclassic.Util;
import elucent.rootsclassic.item.ItemDustPetal;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ComponentRecipe {

  private String effectResult = "";
  private List<ItemStack> materials = new ArrayList<ItemStack>();
  private boolean disabled = false;
  private boolean needsMixin = true;

  public ComponentRecipe(String result) {
    this.setEffectResult(result);
  }

  public String getLocalizedName() {
    return I18n.translateToLocalFormatted("roots.component." + this.getEffectResult() + ".name");
  }

  @Override
  public String toString() {
    String s = getLocalizedName() + ": ";
    for (ItemStack mat : getMaterials()) {
      s += mat.getDisplayName() + " ";
    }
    return s;
  }

  public static int getModifierCapacity(List<ItemStack> items) {
    int maxCapacity = -1;
    for (ItemStack it : items) {
      if (it.getItem() == RegistryManager.oldRoot && maxCapacity < 0) {
        maxCapacity = 0;
      }
      if (it.getItem() == RegistryManager.verdantSprig && maxCapacity < 1) {
        maxCapacity = 1;
      }
      if (it.getItem() == RegistryManager.infernalStem && maxCapacity < 2) {
        maxCapacity = 2;
      }
      if (it.getItem() == RegistryManager.dragonsEye && maxCapacity < 3) {
        maxCapacity = 3;
      }
    }
    return maxCapacity;
  }

  public ComponentRecipe addIngredient(ItemStack stack) {
    materials.add(stack);
    return this;
  }

  public static int getModifierCount(List<ItemStack> items) {
    int count = 0;
    for (ItemStack it : items) {
      if (it.getItem() == Items.GLOWSTONE_DUST) {
        count++;
      }
      else if (it.getItem() == Items.REDSTONE) {
        count++;
      }
      else if (it.getItem() == Items.GUNPOWDER) {
        count++;
      }
    }
    return count;
  }

  public boolean matches(List<ItemStack> items) {
    ArrayList<ItemStack> tempItems = new ArrayList<ItemStack>();
    for (ItemStack stackIn : items) {
      if (stackIn.isEmpty() == false && isSupplimentItem(stackIn) == false) {
        tempItems.add(stackIn);
      }
    }
    for (int i = 0; i < materials.size(); i++) {
      if (i >= tempItems.size()) {
        return false;
      }
      ItemStack mat = materials.get(i);
      ItemStack input = tempItems.get(i);
      if (Util.oreDictMatches(mat, input) == false) {
        return false;
      }
    }
    return true;
  }

  /**
   * True if not part of a recipe but just a recipe booster
   * 
   * @param it
   * @return
   */
  private boolean isSupplimentItem(ItemStack it) {
    return it.getItem() == RegistryManager.oldRoot ||
        it.getItem() == RegistryManager.verdantSprig ||
        it.getItem() == RegistryManager.infernalStem ||
        it.getItem() == RegistryManager.dragonsEye ||
        it.getItem() == Items.GLOWSTONE_DUST ||
        it.getItem() == Items.REDSTONE ||
        it.getItem() == Items.GUNPOWDER;
  }

  public String getEffectResult() {
    return effectResult;
  }

  public void setEffectResult(String effectResult) {
    this.effectResult = effectResult;
  }

  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
  }

  public List<ItemStack> getMaterials() {
    return materials;
  }

  public void setMaterials(List<ItemStack> materials) {
    this.materials = materials;
  }

  public ItemStack getRecipeResult(List<ItemStack> inventory) {
    ItemStack drop = new ItemStack(RegistryManager.dustPetal);
    ItemDustPetal.createData(drop, this.getEffectResult(), inventory);
    return drop;
  }

  /**
   * if a Rare Material is required to finish, in addition to recipe list. IE: old root, etc
   * 
   * @return
   */
  public boolean needsMixin() {
    return needsMixin;
  }

  public void setNeedsMixin(boolean mix) {
    needsMixin = mix;
  }
}
