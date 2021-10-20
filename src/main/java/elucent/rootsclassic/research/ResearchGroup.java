package elucent.rootsclassic.research;

import java.util.ArrayList;

public class ResearchGroup {
  private String name = "";
  private String properName = "";
  public ArrayList<ResearchBase> researches = new ArrayList<>();

  public ResearchGroup(String name, String properName) {
    this.name = name;
    this.properName = properName;
  }

  public ResearchGroup addResearch(ResearchBase research) {
    researches.add(research);
    return this;
  }

  public String getName() {
    return name;
  }

  public String getProperName() {
    return properName;
  }
}
