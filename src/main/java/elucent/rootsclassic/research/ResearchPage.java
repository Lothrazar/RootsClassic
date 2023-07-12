package elucent.rootsclassic.research;

import elucent.rootsclassic.recipe.ComponentRecipe;
import elucent.rootsclassic.ritual.RitualBase;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class ResearchPage {

  public ArrayList<ItemStack> craftingRecipe = new ArrayList<>();
  public ComponentRecipe mortarRecipe = null;
  public RitualBase altarRecipe = null;
  public ArrayList<ItemStack> smeltingRecipe = new ArrayList<>();
  public EnumPageType recipe = EnumPageType.TYPE_NULL;
  public ItemStack displayItem = null;
  public ArrayList<String> info = new ArrayList<>();
  public String title = "";

  public ResearchPage() {}

  public ArrayList<String> makeLines(String s) {
    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> words = new ArrayList<>();
    StringBuilder temp = new StringBuilder();
    int counter = 0;
    for (int i = 0; i < s.length(); i++) {
      temp.append(s.charAt(i));
      if (s.charAt(i) == ' ') {
        words.add(temp.toString());
        temp = new StringBuilder();
      }
    }
    words.add(temp.toString());
    temp = new StringBuilder();
    for (String word : words) {
      counter += Minecraft.getInstance().font.width(word);
      if (counter > 160) {
        list.add(temp.toString());
        temp = new StringBuilder(word);
        counter = Minecraft.getInstance().font.width(word);
      }
      else {
        temp.append(word);
      }
    }
    list.add(temp.toString());
    return list;
  }

  public ResearchPage addDisplayItem(ItemStack stack) {
    recipe = EnumPageType.TYPE_DISPLAY;
    displayItem = stack;
    return this;
  }

  public ResearchPage addSmeltingRecipe(ItemStack input, ItemStack result) {
    recipe = EnumPageType.TYPE_SMELTING;
    smeltingRecipe.add(input);
    smeltingRecipe.add(result);
    return this;
  }

  public ResearchPage addMortarRecipe(ComponentRecipe component) {
    recipe = EnumPageType.TYPE_MORTAR;
    mortarRecipe = component;
    return this;
  }

  public ResearchPage addAltarRecipe(RitualBase ritual) {
    recipe = EnumPageType.TYPE_ALTAR;
    altarRecipe = ritual;
    return this;
  }
}
