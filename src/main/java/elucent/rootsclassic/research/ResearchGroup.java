package elucent.rootsclassic.research;

import java.util.ArrayList;
import java.util.List;

public class ResearchGroup {

  private String name = "";
  private String properName = "";
  public List<ResearchBase> researches = new ArrayList<>();

  public ResearchGroup(String name, String properName) {
    this.name = name;
    this.properName = properName;
  }

  public ResearchGroup addResearch(ResearchBase research) {
    if (!research.getInfo().isEmpty()) researches.add(research);
    return this;
  }

  public String getName() {
    return name;
  }

  public String getProperName() {
    return properName;
  }
}
