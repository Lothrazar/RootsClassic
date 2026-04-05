package elucent.rootsclassic;

import net.minecraft.resources.Identifier;

public class Const {

  public static final String MODID = "rootsclassic";
  public static final Identifier TABLETSMELTING = modLoc("textures/gui/tabletsmelting.png");
  public static final Identifier TABLETCRAFTING = modLoc("textures/gui/tabletcrafting.png");
  public static final Identifier TABLETGUI = modLoc("textures/gui/tabletgui.png");
  public static final Identifier MANA_CONTAINER = modLoc("hud/mana/container");
  public static final Identifier MANA_FULL = modLoc("hud/mana/full");
  public static final Identifier MANA_ALMOST_FULL = modLoc("hud/mana/almost_full");
  public static final Identifier MANA_HALF = modLoc("hud/mana/half");
  public static final Identifier MANA_ALMOST_EMPTY = modLoc("hud/mana/almost_empty");
  public static final Identifier TABLETDISPLAY = modLoc("textures/gui/tabletdisplay.png");
  public static final Identifier TABLETALTAR = modLoc("textures/gui/tabletaltar.png");
  public static final Identifier TABLETMORTAR = modLoc("textures/gui/tabletmortar.png");
  public static final Identifier MANA_LAYER = modLoc("mana_layer");
  public static final String NBT_THORNS = MODID + ":RMOD_thornsDamage";
  public static final String NBT_VULN = MODID + ":RMOD_vuln";
  public static final String NBT_DONT_DROP = MODID + ":RMOD_dropItems";
  public static final String NBT_TRACK_TICKS = "RMOD_trackTicks";
  public static final String NBT_SKIP_TICKS = MODID + ":RMOD_skipTicks";

  public static final Identifier modLoc(String path) {
    return Identifier.fromNamespaceAndPath(MODID, path);
  }
}
