package elucent.roots.research;

import java.util.ArrayList;

public class ResearchGroup {
	public String name = "";
	public String properName = "";
	public ArrayList<ResearchBase> researches = new ArrayList<ResearchBase>();
	public ResearchGroup(String name, String properName){
		this.name = name;
		this.properName = properName;
	}
	
	public ResearchGroup addResearch(ResearchBase research){
		researches.add(research);
		return this;
	}
}
