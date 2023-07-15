package elucent.rootsclassic.research;

import java.util.ArrayList;
import java.util.List;
import elucent.rootsclassic.recipe.ComponentRecipe;
import elucent.rootsclassic.recipe.RitualRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;

public class ResearchPage {

  public List<ItemStack> craftingRecipe = new ArrayList<>();
  public ComponentRecipe mortarRecipe = null;
  public RitualRecipe<?> altarRecipe = null;
  public List<ItemStack> smeltingRecipe = new ArrayList<>();
  public EnumPageType recipe = EnumPageType.TYPE_NULL;
  public ItemStack displayItem = null;
  public List<String> info = new ArrayList<>();
  public String title = "";

  public ResearchPage() {}

  public List<String> makeLines(String s) {
    List<String> list = new ArrayList<>();
    List<String> words = new ArrayList<>();
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

  public ResearchPage addAltarRecipe(RitualRecipe<?> ritual) {
    recipe = EnumPageType.TYPE_ALTAR;
    altarRecipe = ritual;
    return this;
  }
}
