package elucent.rootsclassic.component;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ComponentBase {

  private ResourceLocation name = null;
  private ItemStack itemSource = ItemStack.EMPTY;
  public Vector3d primaryColor = new Vector3d(0, 0, 0);
  public Vector3d secondaryColor = new Vector3d(0, 0, 0);
  private float manaCost = 0;
  private TextFormatting textColor = TextFormatting.WHITE;

  public ComponentBase setPrimaryColor(double r, double g, double b) {
    this.primaryColor = new Vector3d(r, g, b);
    return this;
  }

  public ComponentBase setSecondaryColor(double r, double g, double b) {
    this.secondaryColor = new Vector3d(r, g, b);
    return this;
  }

  public ComponentBase setTextColor(TextFormatting color) {
    this.textColor = color;
    return this;
  }

  public ComponentBase(ResourceLocation name, ItemStack item, int cost) {
    this.name = name;
    this.setManaCost(cost);
    itemSource = item;
  }

  public ComponentBase(ResourceLocation name, Item item, int cost) {
    this(name, new ItemStack(item), cost);
  }

  public ComponentBase(ResourceLocation name, Block block, int cost) {
    this(name, new ItemStack(block), cost);
  }

  public ResourceLocation getName() {
    return name;
  }

  public TranslationTextComponent getEffectName() {
    return new TranslationTextComponent("rootsclassic.component." + name);
  }

  public TextFormatting getTextColor() {
    return textColor;
  }

  public ItemStack getItem() {
    return itemSource;
  }

  public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {}

  public void doEffect(World world, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {}

  public void castingAction(PlayerEntity player, int count, int potency, int efficiency, int size) {}

  public float getManaCost() {
    return manaCost;
  }

  public void setManaCost(float manaCost) {
    this.manaCost = manaCost;
  }
}
