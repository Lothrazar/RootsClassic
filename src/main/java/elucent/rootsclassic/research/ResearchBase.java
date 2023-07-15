package elucent.rootsclassic.research;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import elucent.rootsclassic.Roots;
import elucent.rootsclassic.recipe.RitualRecipe;
import net.minecraft.world.item.ItemStack;

public class ResearchBase {

  public double posX = 0;
  public double posY = 0;
  private String name = "";
  private final ItemStack icon;
  private final ResearchBase req = null;
  private final List<ResearchPage> info = new ArrayList<>();

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

  public List<ResearchPage> getInfo() {
    return info;
  }

  public ResearchBase addPageOf(Optional<RitualRecipe<?>> optionalRitual) {
    optionalRitual.ifPresentOrElse(ritual -> {
      info.add(new ResearchPage().addAltarRecipe(ritual));
    }, () -> {
      Roots.LOGGER.warn("Missing altar recipe for page {}", name);
    });
    return this;
  }
}
