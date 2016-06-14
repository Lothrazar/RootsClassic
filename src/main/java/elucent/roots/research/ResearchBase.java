package elucent.roots.research;

import java.util.ArrayList;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;

public class ResearchBase {
	public double posX = 0;
	public double posY = 0;
	public String name = "";
	public String properName = "";
	public ResearchBase preReq = null;
	public ItemStack icon = null;
	public ItemStack researchItem = null;
	public ResearchBase req = null;
	public ArrayList<ResearchPage> info = new ArrayList<ResearchPage>();
	public ResearchBase(String name, String properName, ItemStack icon, ItemStack researchItem){
		this.name = name;
		this.properName = properName;
		this.icon = icon;
		this.researchItem = researchItem;
	}
	
	public ResearchBase addPage(ResearchPage page){
		info.add(page);
		return this;
	}
	
	public ResearchBase setPreReq(ResearchBase req){
		this.preReq = req;
		return this;
	}
}
