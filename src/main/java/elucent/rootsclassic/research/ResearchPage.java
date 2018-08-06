package elucent.rootsclassic.research;

import java.util.ArrayList;
import elucent.rootsclassic.component.ComponentRecipe;
import elucent.rootsclassic.ritual.RitualBase;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ResearchPage {

  public ArrayList<ItemStack> craftingRecipe = new ArrayList<ItemStack>();
  public ComponentRecipe mortarRecipe = null;
  public RitualBase altarRecipe = null;
  public ArrayList<ItemStack> smeltingRecipe = new ArrayList<ItemStack>();
  public EnumPageType recipe = EnumPageType.TYPE_NULL;
  public ItemStack displayItem = null;
  public ArrayList<String> info = new ArrayList<String>();
  public String title = "";

  public ResearchPage() {}

  public ArrayList<String> makeLines(String s) {
    ArrayList<String> list = new ArrayList<String>();
    ArrayList<String> words = new ArrayList<String>();
    String temp = "";
    int counter = 0;
    for (int i = 0; i < s.length(); i++) {
      temp += s.charAt(i);
      if (s.charAt(i) == ' ') {
        words.add(temp);
        temp = "";
      }
    }
    words.add(temp);
    temp = "";
    for (int i = 0; i < words.size(); i++) {
      counter += Minecraft.getMinecraft().fontRenderer.getStringWidth(words.get(i));
      if (counter > 160) {
        list.add(temp);
        temp = words.get(i);
        counter = Minecraft.getMinecraft().fontRenderer.getStringWidth(words.get(i));
      }
      else {
        temp += words.get(i);
      }
    }
    list.add(temp);
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
