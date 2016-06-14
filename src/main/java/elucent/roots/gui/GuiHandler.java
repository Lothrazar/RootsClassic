package elucent.roots.gui;

import elucent.roots.research.ResearchBase;
import elucent.roots.research.ResearchGroup;
import elucent.roots.research.ResearchManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == 1){
			return new GuiTablet(player);
		}
		if (ID == 2){
			ResearchBase research = ResearchManager.getResearch(player.getEntityData().getString("RMOD_researchGroup"),player.getEntityData().getString("RMOD_researchBase"));
			player.getEntityData().removeTag("RMOD_researchGroup");
			player.getEntityData().removeTag("RMOD_researchBase");
			if (research != null){
				return new GuiTabletPage(research,player);
			}
		}
		return null;
	}
}
