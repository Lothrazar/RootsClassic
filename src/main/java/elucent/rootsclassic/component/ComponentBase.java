package elucent.rootsclassic.component;

import net.minecraft.block.Block;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ComponentBase {

  private String name = "";
  private ItemStack itemSource = null;
  public Vec3d primaryColor = new Vec3d(0, 0, 0);
  public Vec3d secondaryColor = new Vec3d(0, 0, 0);
  private float manaCost = 0;
  private TextFormatting textColor = TextFormatting.WHITE;

  public ComponentBase setPrimaryColor(double r, double g, double b) {
    this.primaryColor = new Vec3d(r, g, b);
    return this;
  }

  public ComponentBase setSecondaryColor(double r, double g, double b) {
    this.secondaryColor = new Vec3d(r, g, b);
    return this;
  }

  public ComponentBase setTextColor(TextFormatting color) {
    this.textColor = color;
    return this;
  }

  public ComponentBase(String name, ItemStack item, int cost) {
    this.name = name;
    this.setManaCost(cost);
    itemSource = item;
  }

  public ComponentBase(String name, Item item, int cost) {
    this(name, new ItemStack(item), cost);
  }

  public ComponentBase(String name, Block item, int cost) {
    this(name, new ItemStack(item), cost);
  }

  public ComponentBase(String name, Item item, int meta, int cost) {
    this(name, new ItemStack(item, 1, meta), cost);
  }

  public ComponentBase(String name, Block item, int meta, int cost) {
    this(name, new ItemStack(item, 1, meta), cost);
  }

  public String getName() {
    return name;
  }

  public String getEffectName() {
    return I18n.translateToLocalFormatted("roots.component." + name + ".name");
  }

  public TextFormatting getTextColor() {
    return textColor;
  }

  public ItemStack getItem() {
    return itemSource;
  }

  public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {}

  public void doEffect(World world, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {}

  public void castingAction(EntityPlayer player, int count, int potency, int efficiency, int size) {}

  public float getManaCost() {
    return manaCost;
  }

  public void setManaCost(float manaCost) {
    this.manaCost = manaCost;
  }
}
