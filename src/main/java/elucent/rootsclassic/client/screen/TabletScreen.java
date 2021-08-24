package elucent.rootsclassic.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.client.ClientInfo;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.research.ResearchBase;
import elucent.rootsclassic.research.ResearchGroup;
import elucent.rootsclassic.research.ResearchManager;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.StringTextComponent;

public class TabletScreen extends Screen {
	private int currentGroup = 0;
	private final PlayerEntity player;

	public TabletScreen(PlayerEntity player) {
		super(StringTextComponent.EMPTY);
		this.player = player;
		if (player.getHeldItem(Hand.MAIN_HAND).hasTag()) {
			currentGroup = player.getHeldItem(Hand.MAIN_HAND).getTag().getInt("currentGroup");
		}
	}

	public static void openScreen(PlayerEntity player) {
		Minecraft.getInstance().displayGuiScreen(new TabletScreen(player));
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		float basePosX = (width / 2.0f) - 108;
		ResearchGroup group = null;
		ResearchBase base = null;
		for (int i = 0; i < ResearchManager.globalResearches.get(currentGroup).researches.size(); i++) {
			minecraft.getTextureManager().bindTexture(Const.tabletGui);
			float yShift = (float) Math.floor(i / 6);
			float xShift = i % 6;
			if (mouseX >= basePosX + 32 * xShift && mouseX < basePosX + 32 * xShift + 24 && mouseY >= 32 + 40 * yShift && mouseY < 32 + 40 * yShift + 24) {
				group = ResearchManager.globalResearches.get(currentGroup);
				base = ResearchManager.globalResearches.get(currentGroup).researches.get(i);
			}
		}
		if (group != null && base != null) {
			CompoundNBT persistentData = player.getPersistentData();
			persistentData.putString("RMOD_researchGroup", group.getName());
			persistentData.putString("RMOD_researchBase", base.getName());

			ResearchBase tempResearch = ResearchManager.getResearch(persistentData.getString("RMOD_researchGroup"), persistentData.getString("RMOD_researchBase"));
			ResearchGroup tempGroup = ResearchManager.getResearchGroup(persistentData.getString("RMOD_researchGroup"));
			persistentData.remove("RMOD_researchGroup");
			persistentData.remove("RMOD_researchBase");
			if (tempResearch != null && tempGroup != null) {
				minecraft.getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
				this.minecraft.displayGuiScreen(new TabletPageScreen(tempGroup, tempResearch, player));
			}
		}
		if (mouseX >= 32 && mouseX < 64 && mouseY >= height - 48 && mouseY < height - 32) {
			currentGroup--;
			if (currentGroup < 0) {
				minecraft.getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
				currentGroup = ResearchManager.globalResearches.size() - 1;
			}
		}
		if (mouseX >= width - 64 && mouseX < width - 32 && mouseY >= height - 48 && mouseY < height - 32) {
			currentGroup++;
			if (currentGroup == ResearchManager.globalResearches.size()) {
				minecraft.getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
				currentGroup = 0;
			}
		}

		ItemStack heldStack = player.getHeldItem(Hand.MAIN_HAND);
		if (currentGroup != 0) {
			CompoundNBT tag = heldStack.getTag() != null ? heldStack.getTag() : new CompoundNBT();
			tag.putInt("currentGroup", currentGroup);
			heldStack.setTag(tag);
		} else {
			if (heldStack.hasTag()) {
				heldStack.getTag().remove("currentGroup");
			}
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}

	public void drawQuad(BufferBuilder vertexbuffer, float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, int minU, int minV, int maxU, int maxV) {
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		vertexbuffer.pos(x4 + 0.0F, y4 + 0.0F, this.itemRenderer.zLevel).tex((minU + 0) * f, (minV + maxV) * f1).endVertex();
		vertexbuffer.pos(x3 + 0.0F, y3 + 0.0F, this.itemRenderer.zLevel).tex((minU + maxU) * f, (minV + maxV) * f1).endVertex();
		vertexbuffer.pos(x2 + 0.0F, y2 + 0.0F, this.itemRenderer.zLevel).tex((minU + maxU) * f, (minV + 0) * f1).endVertex();
		vertexbuffer.pos(x1 + 0.0F, y1 + 0.0F, this.itemRenderer.zLevel).tex((minU + 0) * f, (minV + 0) * f1).endVertex();
	}

//	public float getRadiusX(float degrees, float radius) {
//		float magnitude = radius + 16.0f * ((float) Math.cos(((degrees + (cycle / 8.0f)) / 45.0) * Math.PI));
//		return magnitude * (float) Math.cos((degrees / 360.0f) * Math.PI);
//	}
//
//	public float getRadiusY(float degrees, float radius) {
//		float magnitude = radius + 16.0f * ((float) Math.cos(((degrees + (cycle / 8.0f)) / 45.0) * Math.PI));
//		return magnitude * (float) Math.sin((degrees / 360.0f) * Math.PI);
//	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		this.player.stopActiveHand();
		RenderSystem.pushMatrix();
		RenderSystem.color4f(1, 1, 1, 1);
		minecraft.getTextureManager().bindTexture(Const.tabletGui);
		float unit = width / 32.0f;
		if (RootsConfig.CLIENT.showTabletWave.get()) {
			RenderSystem.enableBlend();
			RenderSystem.color4f(1, 1, 1, 1);
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferBuilder = tessellator.getBuffer();
			bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
			for (float i = 0; i < width; i += unit) {
				float height1 = 12.0f * ((float) Math.cos(((ClientInfo.ticksInGame / 36.0) + (i / (width / 4.0))) * Math.PI) + 1.0f);
				float height2 = 12.0f * ((float) Math.cos(((ClientInfo.ticksInGame / 36.0) + ((i + unit) / (width / 4.0))) * Math.PI) + 1.0f);
				this.drawQuad(bufferBuilder, i, height - (24.0f + height1), i + unit, height - (24.0f + height2), i + unit, height, i, height, 16, 96, 16, 64);
			}
			tessellator.draw();
			RenderSystem.disableBlend();
		}
		RenderSystem.color4f(1, 1, 1, 1);
		int basePosX = (int)((width / 2.0f) - 108);
		String researchName = "rootsclassic.research." + ResearchManager.globalResearches.get(currentGroup).getName();
		for (int i = 0; i < ResearchManager.globalResearches.get(currentGroup).researches.size(); i++) {
			minecraft.getTextureManager().bindTexture(Const.tabletGui);
			int yShift = (int)(float) Math.floor(i / 6);
			int xShift = (int)(i % 6);
			this.blit(matrixStack, basePosX + 32 * xShift, 32 + 40 * yShift, 16, 0, 24, 24);
			if (ResearchManager.globalResearches.get(currentGroup).researches.get(i).getIcon() != null) {
				this.itemRenderer.renderItemIntoGUI(ResearchManager.globalResearches.get(currentGroup).researches.get(i).getIcon(), (int) (basePosX + xShift * 32 + 4), (int) (32 + 40 * yShift + 4));
			}
			if (mouseX >= basePosX + 32 * xShift && mouseX < basePosX + 32 * xShift + 24 && mouseY >= 32 + 40 * yShift && mouseY < 32 + 40 * yShift + 24) {
				String name = I18n.format(researchName + "." + ResearchManager.globalResearches.get(currentGroup).researches.get(i).getName());
				this.font.drawStringWithShadow(matrixStack, name, basePosX + 32 * xShift + 12 - (font.getStringWidth(name) / 2.0f), 32 + 40 * yShift + 25, RootsUtil.intColor(255, 255, 255));
			}
		}
		String formattedName = I18n.format(researchName);
		this.font.drawStringWithShadow(matrixStack, formattedName, width / 2.0f - (font.getStringWidth(formattedName) / 2.0f), height - 16.0f, RootsUtil.intColor(255, 255, 255));
		minecraft.getTextureManager().bindTexture(Const.tabletGui);
		if (mouseX >= 32 && mouseX < 64 && mouseY >= height - 48 && mouseY < height - 32) {
			this.blit(matrixStack, 32, height - 48, 32, 80, 32, 16);
		} else {
			this.blit(matrixStack, 32, height - 48, 32, 64, 32, 16);
		}
		if (mouseX >= width - 64 && mouseX < width - 32 && mouseY >= height - 48 && mouseY < height - 32) {
			this.blit(matrixStack, width - 64, height - 48, 0, 80, 32, 16);
		} else {
			this.blit(matrixStack, width - 64, height - 48, 0, 64, 32, 16);
		}
		RenderSystem.popMatrix();
	}
}
