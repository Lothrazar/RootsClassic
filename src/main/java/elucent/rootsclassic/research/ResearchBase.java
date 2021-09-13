package elucent.rootsclassic.research;

import java.util.ArrayList;
import net.minecraft.world.item.ItemStack;

public class ResearchBase {

  public double posX = 0;
  public double posY = 0;
  private String name = "";
  private ItemStack icon;
  private ResearchBase req = null;
  private ArrayList<ResearchPage> info = new ArrayList<>();

  public ResearchBase(String name, ItemStack icon) {
    this.name = name;
    this.icon = icon;
  }

  public ResearchBase addPage(ResearchPage page) {
    info.add(page);
    return this;
  }

  public String getName() {
    return name;
  }

  public ItemStack getIcon() {
    return icon;
  }

  public ResearchBase getReq() {
    return req;
  }

  public ArrayList<ResearchPage> getInfo() {
    return info;
  }
}
