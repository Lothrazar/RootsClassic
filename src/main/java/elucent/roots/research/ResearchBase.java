package elucent.roots.research;

import java.util.ArrayList;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;

public class ResearchBase {
	public double posX = 0;
	public double posY = 0;
	public String name = "";
	public ItemStack icon = null;
	public ResearchBase req = null;
	public ArrayList<ResearchPage> info = new ArrayList<ResearchPage>();
	public ResearchBase(String name, ItemStack icon){
		this.name = name;
		this.icon = icon;
	}
	
	public ResearchBase addPage(ResearchPage page){
		info.add(page);
		return this;
	}

	public ResearchGroup addResearch(ResearchBase addPage) {
		// TODO Auto-generated method stub
		return null;
	}
}
