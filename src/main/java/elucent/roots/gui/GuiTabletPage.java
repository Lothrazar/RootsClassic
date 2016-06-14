package elucent.roots.gui;

import org.lwjgl.opengl.GL11;

import elucent.roots.RegistryManager;
import elucent.roots.Roots;
import elucent.roots.Util;
import elucent.roots.research.EnumRecipeType;
import elucent.roots.research.ResearchBase;
import elucent.roots.research.ResearchGroup;
import elucent.roots.research.ResearchManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiTabletPage extends GuiScreen {
	public double mouseX = 0;
	public double mouseY = 0; 
	public double smoothMouseX = 0;
	public double smoothMouseY = 0; 
	public int layer = 0;
	public double cycle = 0;
	public int currentPage = 0;
	public ResearchBase research = null;
	boolean showRightArrow = false;
	boolean showLeftArrow = true;
	EntityPlayer player = null;
	public GuiTabletPage(ResearchBase r, EntityPlayer player){
		this.player = player;
		research = r;
	}
	
	@Override
	public void keyTyped(char typedChar, int keyCode){
		if (keyCode == 1){
			player.openGui(Roots.instance, 1, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
		}
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton){
		float basePosX = ((float)width/2.0f)-96;
		float basePosY = ((float)height/2.0f)-128;
		if (showLeftArrow){
			if (mouseX >= basePosX+16 && mouseX < basePosX+48 && mouseY >= basePosY+224 && mouseY < basePosY+240){
				this.currentPage --;
			}
		}
		if (showRightArrow){
			if (mouseX >= basePosX+144 && mouseX < basePosX+176 && mouseY >= basePosY+224 && mouseY < basePosY+240){
				this.currentPage ++;
			}
		}
	}
	
	public void drawQuad(VertexBuffer vertexbuffer, float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, int minU, int minV, int maxU, int maxV)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        vertexbuffer.pos((double)(x4 + 0.0F), (double)(y4 + 0.0F), (double)this.zLevel).tex((double)((float)(minU + 0) * f), (double)((float)(minV + maxV) * f1)).endVertex();
        vertexbuffer.pos((double)(x3 + 0.0F), (double)(y3 + 0.0F), (double)this.zLevel).tex((double)((float)(minU + maxU) * f), (double)((float)(minV + maxV) * f1)).endVertex();
        vertexbuffer.pos((double)(x2 + 0.0F), (double)(y2 + 0.0F), (double)this.zLevel).tex((double)((float)(minU + maxU) * f), (double)((float)(minV + 0) * f1)).endVertex();
        vertexbuffer.pos((double)(x1 + 0.0F), (double)(y1 + 0.0F), (double)this.zLevel).tex((double)((float)(minU + 0) * f), (double)((float)(minV + 0) * f1)).endVertex();
    }
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks){
		RenderHelper.disableStandardItemLighting();
		RenderHelper.enableGUIStandardItemLighting();
		if (this.currentPage == 0){
			this.showLeftArrow = false;
		}
		else {
			this.showLeftArrow = true;
		}
		if (this.currentPage == this.research.info.size() - 1){
			this.showRightArrow = false;
		}
		else {
			this.showRightArrow = true;
		}
		cycle += 4.0;
		this.drawDefaultBackground();
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("roots:textures/gui/tabletGui.png"));
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		
		float basePosX = ((float)width/2.0f)-96;
		float basePosY = ((float)height/2.0f)-128;
		EnumRecipeType type = research.info.get(currentPage).recipe;
		if (type == EnumRecipeType.TYPE_NULL){
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("roots:textures/gui/tabletGui.png"));
			this.drawTexturedModalRect(basePosX,basePosY,64,0,192,256);
			fontRendererObj.drawStringWithShadow(research.info.get(currentPage).title, basePosX+96-(this.fontRendererObj.getStringWidth(research.info.get(currentPage).title)/2.0f), basePosY+12, Util.intColor(255, 255, 255));
			for (int i = 0; i < research.info.get(currentPage).info.size(); i ++){
				fontRendererObj.drawStringWithShadow(research.info.get(currentPage).info.get(i),basePosX+16,basePosY+32+i*11,Util.intColor(255, 255, 255));
			}
		}
		if (type == EnumRecipeType.TYPE_CRAFTING){
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("roots:textures/gui/tabletCrafting.png"));
			this.drawTexturedModalRect(basePosX,basePosY,0,0,192,256);
			if (research.info.get(currentPage).craftingRecipe.get(0) != null){
				this.itemRender.renderItemIntoGUI(research.info.get(currentPage).craftingRecipe.get(0), (int)basePosX+32, (int)basePosY+32);
			}
			if (research.info.get(currentPage).craftingRecipe.get(1) != null){
				this.itemRender.renderItemIntoGUI(research.info.get(currentPage).craftingRecipe.get(1), (int)basePosX+56, (int)basePosY+32);
			}
			if (research.info.get(currentPage).craftingRecipe.get(2) != null){
				this.itemRender.renderItemIntoGUI(research.info.get(currentPage).craftingRecipe.get(2), (int)basePosX+80, (int)basePosY+32);
			}
			if (research.info.get(currentPage).craftingRecipe.get(3) != null){
				this.itemRender.renderItemIntoGUI(research.info.get(currentPage).craftingRecipe.get(3), (int)basePosX+32, (int)basePosY+56);
			}
			if (research.info.get(currentPage).craftingRecipe.get(4) != null){
				this.itemRender.renderItemIntoGUI(research.info.get(currentPage).craftingRecipe.get(4), (int)basePosX+56, (int)basePosY+56);
			}
			if (research.info.get(currentPage).craftingRecipe.get(5) != null){
				this.itemRender.renderItemIntoGUI(research.info.get(currentPage).craftingRecipe.get(5), (int)basePosX+80, (int)basePosY+56);
			}
			if (research.info.get(currentPage).craftingRecipe.get(6) != null){
				this.itemRender.renderItemIntoGUI(research.info.get(currentPage).craftingRecipe.get(6), (int)basePosX+32, (int)basePosY+80);
			}
			if (research.info.get(currentPage).craftingRecipe.get(7) != null){
				this.itemRender.renderItemIntoGUI(research.info.get(currentPage).craftingRecipe.get(7), (int)basePosX+56, (int)basePosY+80);
			}
			if (research.info.get(currentPage).craftingRecipe.get(8) != null){
				this.itemRender.renderItemIntoGUI(research.info.get(currentPage).craftingRecipe.get(8), (int)basePosX+80, (int)basePosY+80);
			}
			if (research.info.get(currentPage).craftingRecipe.get(9) != null){
				this.itemRender.renderItemIntoGUI(research.info.get(currentPage).craftingRecipe.get(9), (int)basePosX+144, (int)basePosY+56);
			}
			for (int i = 0; i < research.info.get(currentPage).info.size(); i ++){
				fontRendererObj.drawStringWithShadow(research.info.get(currentPage).info.get(i),basePosX+16,basePosY+96+i*11,Util.intColor(255, 255, 255));
			}
			fontRendererObj.drawStringWithShadow(research.info.get(currentPage).title, basePosX+96-(this.fontRendererObj.getStringWidth(research.info.get(currentPage).title)/2.0f), basePosY+12, Util.intColor(255, 255, 255));
		}
		if (type == EnumRecipeType.TYPE_SMELTING){
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("roots:textures/gui/tabletSmelting.png"));
			this.drawTexturedModalRect(basePosX,basePosY,0,0,192,256);
			if (research.info.get(currentPage).smeltingRecipe.get(0) != null){
				this.itemRender.renderItemIntoGUI(research.info.get(currentPage).smeltingRecipe.get(0), (int)basePosX+56, (int)basePosY+40);
			}
			if (research.info.get(currentPage).smeltingRecipe.get(1) != null){
				this.itemRender.renderItemIntoGUI(research.info.get(currentPage).smeltingRecipe.get(1), (int)basePosX+144, (int)basePosY+56);
			}
			for (int i = 0; i < research.info.get(currentPage).info.size(); i ++){
				fontRendererObj.drawStringWithShadow(research.info.get(currentPage).info.get(i),basePosX+16,basePosY+104+i*11,Util.intColor(255, 255, 255));
			}
			fontRendererObj.drawStringWithShadow(research.info.get(currentPage).title, basePosX+96-(this.fontRendererObj.getStringWidth(research.info.get(currentPage).title)/2.0f), basePosY+12, Util.intColor(255, 255, 255));
		}
		if (type == EnumRecipeType.TYPE_DISPLAY){
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("roots:textures/gui/tabletDisplay.png"));
			this.drawTexturedModalRect(basePosX,basePosY,0,0,192,256);
			if (research.info.get(currentPage).displayItem != null){
				this.itemRender.renderItemIntoGUI(research.info.get(currentPage).displayItem, (int)basePosX+88, (int)basePosY+48);
			}
			for (int i = 0; i < research.info.get(currentPage).info.size(); i ++){
				fontRendererObj.drawStringWithShadow(research.info.get(currentPage).info.get(i),basePosX+16,basePosY+80+i*11,Util.intColor(255, 255, 255));
			}
			fontRendererObj.drawStringWithShadow(research.info.get(currentPage).title, basePosX+96-(this.fontRendererObj.getStringWidth(research.info.get(currentPage).title)/2.0f), basePosY+12, Util.intColor(255, 255, 255));
		}
		if (type == EnumRecipeType.TYPE_ALTAR){
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("roots:textures/gui/tabletAltar.png"));
			this.drawTexturedModalRect(basePosX,basePosY,0,0,192,256);
			
			for (int i = 0; i < research.info.get(currentPage).altarRecipe.blocks.size(); i ++){
				Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("roots:textures/gui/tabletAltar.png"));
				int u = 192;
				int v = 240;
				int xShift = 0;
				int yShift = 0;
				this.drawTexturedModalRect(basePosX+93, basePosY+153, 192, 32, 16, 16);
				if (research.info.get(currentPage).altarRecipe.blocks.get(i) == RegistryManager.standingStoneT1){
					u = 192;
					v = 48;
					xShift = 8*research.info.get(currentPage).altarRecipe.positions.get(i).getX();
					yShift = 8*research.info.get(currentPage).altarRecipe.positions.get(i).getZ();
				}
				if (research.info.get(currentPage).altarRecipe.blocks.get(i) == RegistryManager.standingStoneT2){
					u = 192;
					v = 64;
					xShift = 8*research.info.get(currentPage).altarRecipe.positions.get(i).getX();
					yShift = 8*research.info.get(currentPage).altarRecipe.positions.get(i).getZ();
				}
				this.drawTexturedModalRect(basePosX+93+xShift, basePosY+153+yShift, u, v, 16, 16);
			}
			
			for (int i = 0; i < research.info.get(currentPage).altarRecipe.ingredients.size(); i ++){
				if (research.info.get(currentPage).altarRecipe.ingredients.get(i) != null){
					this.itemRender.renderItemIntoGUI(research.info.get(currentPage).altarRecipe.ingredients.get(i), (int)basePosX+64+24*i, (int)basePosY+56);
				}
			}
			
			for (int i = 0; i < research.info.get(currentPage).altarRecipe.incenses.size(); i ++){
				if (research.info.get(currentPage).altarRecipe.incenses.get(i) != null){
					this.itemRender.renderItemIntoGUI(research.info.get(currentPage).altarRecipe.incenses.get(i), (int)basePosX+76+16*i, (int)basePosY+88);
				}
			}
			fontRendererObj.drawStringWithShadow(research.info.get(currentPage).title, basePosX+96-(this.fontRendererObj.getStringWidth(research.info.get(currentPage).title)/2.0f), basePosY+12, Util.intColor(255, 255, 255));
		}
		if (type == EnumRecipeType.TYPE_MORTAR){
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("roots:textures/gui/tabletMortar.png"));
			this.drawTexturedModalRect(basePosX,basePosY,0,0,192,256);
			for (int i = 0; i < research.info.get(currentPage).mortarRecipe.materials.size(); i ++){
				this.itemRender.renderItemIntoGUI(research.info.get(currentPage).mortarRecipe.materials.get(i), (int)basePosX+24+i*16, (int)basePosY+56);
			}
			this.itemRender.renderItemIntoGUI(new ItemStack(RegistryManager.dustPetal), (int)basePosX+144, (int)basePosY+56);
			for (int i = 0; i < research.info.get(currentPage).info.size(); i ++){
				fontRendererObj.drawStringWithShadow(research.info.get(currentPage).info.get(i),basePosX+16,basePosY+96+i*11,Util.intColor(255, 255, 255));
			}
			fontRendererObj.drawStringWithShadow(research.info.get(currentPage).title, basePosX+96-(this.fontRendererObj.getStringWidth(research.info.get(currentPage).title)/2.0f), basePosY+12, Util.intColor(255, 255, 255));
		}
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("roots:textures/gui/tabletGui.png"));
		if (showLeftArrow){
			if (mouseX >= basePosX+16 && mouseX < basePosX+48 && mouseY >= basePosY+224 && mouseY < basePosY+240){
				this.drawTexturedModalRect(basePosX+16, basePosY+224, 32, 80, 32, 16);
			}
			else {
				this.drawTexturedModalRect(basePosX+16, basePosY+224, 32, 64, 32, 16);
			}
		}
		if (showRightArrow){
			if (mouseX >= basePosX+144 && mouseX < basePosX+176 && mouseY >= basePosY+224 && mouseY < basePosY+240){
				this.drawTexturedModalRect(basePosX+144, basePosY+224, 0, 80, 32, 16);
			}
			else {
				this.drawTexturedModalRect(basePosX+144, basePosY+224, 0, 64, 32, 16);
			}
		}
		
		GlStateManager.enableBlend();
		Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
	    tessellator.draw();
	    GlStateManager.disableBlend();
		RenderHelper.enableStandardItemLighting();
	}
}
