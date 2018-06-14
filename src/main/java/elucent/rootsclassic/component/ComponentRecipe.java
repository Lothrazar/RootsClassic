package elucent.rootsclassic.component;

import java.util.ArrayList;
import java.util.List;
import elucent.rootsclassic.RegistryManager;
import elucent.rootsclassic.Util;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ComponentRecipe {

  private String effectResult = "";
  private ArrayList<ItemStack> materials = new ArrayList<ItemStack>();
  private boolean disabled = false;

  public ComponentRecipe(String result) {
    this.setEffectResult(result);
  }

  public String getLocalizedName() {
    return I18n.format("roots.component." + this.getEffectResult() + ".name");
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
    tempItems.addAll(items);
    for (int i = 0; i < tempItems.size(); i++) {
      ItemStack it = tempItems.get(i);
      if (it.getItem() == RegistryManager.oldRoot ||
          it.getItem() == RegistryManager.verdantSprig ||
          it.getItem() == RegistryManager.infernalStem ||
          it.getItem() == RegistryManager.dragonsEye ||
          it.getItem() == Items.GLOWSTONE_DUST ||
          it.getItem() == Items.REDSTONE ||
          it.getItem() == Items.GUNPOWDER) {
        tempItems.remove(i);
        i--;
      }
    }
    for (int i = 0; i < getMaterials().size(); i++) {
      boolean endIteration = false;
      for (int j = 0; j < tempItems.size() && !endIteration; j++) {
        if (Util.oreDictMatches(getMaterials().get(i), tempItems.get(j))) {
          tempItems.remove(j);
          endIteration = true;
        }
      }
    }
    return tempItems.size() == 0;
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

  public ArrayList<ItemStack> getMaterials() {
    return materials;
  }

  public void setMaterials(ArrayList<ItemStack> materials) {
    this.materials = materials;
  }
}
