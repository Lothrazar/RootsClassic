package elucent.roots.gui;

import org.lwjgl.opengl.GL11;

import elucent.roots.Roots;
import elucent.roots.Util;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.BossInfo.Color;

public class GuiTablet extends GuiScreen {
	public double mouseX = 0;
	public double mouseY = 0; 
	public double smoothMouseX = 0;
	public double smoothMouseY = 0; 
	public int layer = 0;
	public double cycle = 0;
	int currentGroup = 0;
	EntityPlayer player = null;
	public GuiTablet(EntityPlayer p){
		player = p;
		if (p.getHeldItem(EnumHand.MAIN_HAND).hasTagCompound()){
			currentGroup = p.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getInteger("currentGroup");
		}
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton){float basePosX = ((float)width/2.0f)-108;
		ResearchGroup group = null;
		ResearchBase base = null;
		for (int i = 0; i < ResearchManager.globalResearches.get(currentGroup).researches.size(); i ++){
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("roots:textures/gui/tabletGui.png"));
			float yShift = (float) Math.floor(i / 6);
			float xShift = i % 6;
			if (mouseX >= basePosX+32*xShift && mouseX < basePosX+32*xShift+24 && mouseY >= 32+32*yShift && mouseY < 32+40*yShift+24){
				group = ResearchManager.globalResearches.get(currentGroup);
				base = ResearchManager.globalResearches.get(currentGroup).researches.get(i);
			}
		}
		if (group != null && base != null){
			player.getEntityData().setString("RMOD_researchGroup", group.name);
			player.getEntityData().setString("RMOD_researchBase", base.name);
			player.openGui(Roots.instance, 2, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
		}
		if (mouseX >= 32 && mouseX < 64 && mouseY >= height-48 && mouseY < height-32){
			currentGroup --;
			if (currentGroup < 0){
				currentGroup = ResearchManager.globalResearches.size()-1;
			}
		}
		if (mouseX >= width-64 && mouseX < width-32 && mouseY >= height-48 && mouseY < height-32){
			currentGroup ++;
			if (currentGroup == ResearchManager.globalResearches.size()){
				currentGroup = 0;
			}
		}
		if (currentGroup != 0){
			player.getHeldItem(EnumHand.MAIN_HAND).setTagCompound(new NBTTagCompound());
			player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().setInteger("currentGroup", currentGroup);
		}
		else {
			if (player.getHeldItem(EnumHand.MAIN_HAND).hasTagCompound()){
				player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().removeTag("currentGroup");
			}
		}
	}
	
	public void drawQuad(VertexBuffer vertexbuffer, float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, int minU, int minV, int maxU, int maxV){
		float f = 0.00390625F;
        float f1 = 0.00390625F;
        vertexbuffer.pos((double)(x4 + 0.0F), (double)(y4 + 0.0F), (double)this.zLevel).tex((double)((float)(minU + 0) * f), (double)((float)(minV + maxV) * f1)).endVertex();
        vertexbuffer.pos((double)(x3 + 0.0F), (double)(y3 + 0.0F), (double)this.zLevel).tex((double)((float)(minU + maxU) * f), (double)((float)(minV + maxV) * f1)).endVertex();
        vertexbuffer.pos((double)(x2 + 0.0F), (double)(y2 + 0.0F), (double)this.zLevel).tex((double)((float)(minU + maxU) * f), (double)((float)(minV + 0) * f1)).endVertex();
        vertexbuffer.pos((double)(x1 + 0.0F), (double)(y1 + 0.0F), (double)this.zLevel).tex((double)((float)(minU + 0) * f), (double)((float)(minV + 0) * f1)).endVertex();
    }
	
	public float getRadiusX(float degrees, float radius){
		float magnitude = radius + 16.0f*((float)Math.cos(((degrees+(cycle/8.0f))/45.0)*Math.PI));
		return magnitude*(float)Math.cos((degrees/360.0f)*Math.PI);
	}
	
	public float getRadiusY(float degrees, float radius){
		float magnitude = radius + 16.0f*((float)Math.cos(((degrees+(cycle/8.0f))/45.0)*Math.PI));
		return magnitude*(float)Math.sin((degrees/360.0f)*Math.PI);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks){
		RenderHelper.disableStandardItemLighting();
		RenderHelper.enableGUIStandardItemLighting();
		cycle += 8.0;
		this.drawDefaultBackground();
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("roots:textures/gui/tabletGui.png"));
		this.mouseX = mouseX;
		this.mouseY = mouseY;	
		
		float unit = (float)width/32.0f;
		
		GlStateManager.enableBlend();
		Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		for (float i = 0; i < width; i += (float)width/32.0f){
			float height1 = 12.0f*((float)Math.cos(((cycle/360.0)+(i/(width/4.0)))*Math.PI)+1.0f);
			float height2 = 12.0f*((float)Math.cos(((cycle/360.0)+((i+(float)width/32.0)/(width/4.0)))*Math.PI)+1.0f);
			this.drawQuad(vertexbuffer, i, height-(24.0f+height1), i+(float)width/32.0f, height-(24.0f+height2), i+(float)width/32.0f, height, i, height, 16, 96, 16, 64);
		}
	    tessellator.draw();
	    GlStateManager.disableBlend();
		
		float basePosX = ((float)width/2.0f)-108;
		
		for (int i = 0; i < ResearchManager.globalResearches.get(currentGroup).researches.size(); i ++){
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("roots:textures/gui/tabletGui.png"));
			float yShift = (float) Math.floor(i / 6);
			float xShift = i % 6;
			this.drawTexturedModalRect(basePosX+32*xShift, 32+40*yShift, 16, 0, 24, 24);
			if (ResearchManager.globalResearches.get(currentGroup).researches.get(i).icon != null){
				this.itemRender.renderItemIntoGUI(ResearchManager.globalResearches.get(currentGroup).researches.get(i).icon, (int)(basePosX+xShift*32+4), (int)(32+40*yShift+4));
			}
			if (mouseX >= basePosX+32*xShift && mouseX < basePosX+32*xShift+24 && mouseY >= 32+32*yShift && mouseY < 32+40*yShift+24){
				String name = ResearchManager.globalResearches.get(currentGroup).researches.get(i).properName;
				this.fontRendererObj.drawStringWithShadow(name, basePosX+32*xShift+12-(fontRendererObj.getStringWidth(name)/2.0f), 32+40*yShift+25, Util.intColor(255, 255, 255));
			}
		}
		this.fontRendererObj.drawStringWithShadow(ResearchManager.globalResearches.get(currentGroup).properName, width/2.0f-(fontRendererObj.getStringWidth(ResearchManager.globalResearches.get(currentGroup).properName)/2.0f), height-16.0f, Util.intColor(255, 255, 255));
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("roots:textures/gui/tabletGui.png"));
		if (mouseX >= 32 && mouseX < 64 && mouseY >= height-48 && mouseY < height-32){
			this.drawTexturedModalRect(32, height-48, 32, 80, 32, 16);
		}
		else {
			this.drawTexturedModalRect(32, height-48, 32, 64, 32, 16);
		}
		if (mouseX >= width-64 && mouseX < width-32 && mouseY >= height-48 && mouseY < height-32){
			this.drawTexturedModalRect(width-64, height-48, 0, 80, 32, 16);
		}
		else {
			this.drawTexturedModalRect(width-64, height-48, 0, 64, 32, 16);
		}
		RenderHelper.enableStandardItemLighting();
	}
}
