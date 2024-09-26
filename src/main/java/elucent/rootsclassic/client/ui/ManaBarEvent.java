package elucent.rootsclassic.client.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.attachment.IMana;
import elucent.rootsclassic.attachment.ManaAttachment;
import elucent.rootsclassic.attachment.RootsAttachments;
import elucent.rootsclassic.client.ClientInfo;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.item.IManaRelatedItem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

public class ManaBarEvent {

  public static void onRegisterLayer(RegisterGuiLayersEvent event) {
    event.registerAbove(VanillaGuiLayers.FOOD_LEVEL, Const.MANA_LAYER,
      ManaBarEvent::onDrawManaBar
    );
  }

  private static void onDrawManaBar(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
    final Minecraft mc = Minecraft.getInstance();
    Player player = mc.player;
    boolean showBar = player.getMainHandItem().getItem() instanceof IManaRelatedItem || player.getOffhandItem().getItem() instanceof IManaRelatedItem;
    //it used to hide mana bar increative but i hated that, i assume ppl did, felt broken.
    //TODO could be a config to bring back (hide mana in creative)
    //		if (player.abilities.isCreativeMode) {
    //		  	showBar = false;
    //		}
    ManaAttachment capability = player.getData(RootsAttachments.MANA);
    if (showBar && capability.getMaxMana() > 0) {
      drawManaBar(guiGraphics, mc, capability);
    }
  }

  private static void drawManaBar(GuiGraphics guiGraphics, Minecraft mc, IMana capability) {
    final int manaBarOffset = RootsConfig.CLIENT.manaBarOffset.get();
    final int w = mc.getWindow().getGuiScaledWidth();
    final int h = mc.getWindow().getGuiScaledHeight();
    int manaNumber = Math.round(capability.getMana());
    int maxManaNumber = Math.round(capability.getMaxMana());
    RenderSystem.enableBlend();
    int offsetX = 0;

    // Draw the mana container backdrop
    for (int i = 0; i < maxManaNumber; i += 4) {
      guiGraphics.blitSprite(Const.MANA_CONTAINER, (w / 2) + 10 + offsetX, h - manaBarOffset, 9, 9);
      offsetX += 8;
    }

    offsetX = 0;

    // Draw the mana levels
    while (manaNumber > 0) {
      if (manaNumber >= 4) {
        guiGraphics.blitSprite(Const.MANA_FULL, (w / 2) + 10 + offsetX, h - manaBarOffset, 9, 9);
        manaNumber -= 4;
      } else if (manaNumber == 3) {
        guiGraphics.blitSprite(Const.MANA_ALMOST_FULL, (w / 2) + 10 + offsetX, h - manaBarOffset, 9, 9);
        manaNumber = 0;
      } else if (manaNumber == 2) {
        guiGraphics.blitSprite(Const.MANA_HALF, (w / 2) + 10 + offsetX, h - manaBarOffset, 9, 9);
        manaNumber = 0;
      } else if (manaNumber == 1) {
        guiGraphics.blitSprite(Const.MANA_ALMOST_EMPTY, (w / 2) + 10 + offsetX, h - manaBarOffset, 9, 9);
        manaNumber = 0;
      }
      offsetX += 8;
    }

    RenderSystem.disableBlend();
  }

  public static void clientTickEnd(ClientTickEvent.Post event) {
    ClientInfo.ticksInGame++;
  }
}
