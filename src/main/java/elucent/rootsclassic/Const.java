package elucent.rootsclassic;

import net.minecraft.resources.ResourceLocation;

public class Const {

  public static final String MODID = "rootsclassic";
  public static final ResourceLocation TABLETSMELTING = modLoc("textures/gui/tabletsmelting.png");
  public static final ResourceLocation TABLETCRAFTING = modLoc("textures/gui/tabletcrafting.png");
  public static final ResourceLocation TABLETGUI = modLoc("textures/gui/tabletgui.png");
  public static final ResourceLocation MANABAR = modLoc("textures/gui/manabar.png");
  public static final ResourceLocation TABLETDISPLAY = modLoc("textures/gui/tabletdisplay.png");
  public static final ResourceLocation TABLETALTAR = modLoc("textures/gui/tabletaltar.png");
  public static final ResourceLocation TABLETMORTAR = modLoc("textures/gui/tabletmortar.png");
  public static final String NBT_THORNS = MODID + ":RMOD_thornsDamage";
  public static final String NBT_VULN = MODID + ":RMOD_vuln";
  public static final String NBT_DONT_DROP = MODID + ":RMOD_dropItems";
  public static final String NBT_TRACK_TICKS = "RMOD_trackTicks";
  public static final String NBT_SKIP_TICKS = MODID + ":RMOD_skipTicks";
	
	public static final ResourceLocation modLoc(String path) {
		return ResourceLocation.fromNamespaceAndPath(MODID, path);
	}
}
