package elucent.rootsclassic.component;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class ComponentBase {

  private ResourceLocation name = null;
  private ItemStack itemSource = ItemStack.EMPTY;
  public Vec3 primaryColor = new Vec3(0, 0, 0);
  public Vec3 secondaryColor = new Vec3(0, 0, 0);
  private float manaCost = 0;
  private ChatFormatting textColor = ChatFormatting.WHITE;

  public ComponentBase setPrimaryColor(double r, double g, double b) {
    this.primaryColor = new Vec3(r, g, b);
    return this;
  }

  public ComponentBase setSecondaryColor(double r, double g, double b) {
    this.secondaryColor = new Vec3(r, g, b);
    return this;
  }

  public ComponentBase setTextColor(ChatFormatting color) {
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

  public TranslatableComponent getEffectName() {
    return new TranslatableComponent("rootsclassic.component." + name);
  }

  public ChatFormatting getTextColor() {
    return textColor;
  }

  public ItemStack getItem() {
    return itemSource;
  }

  public void doEffect(Level world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {}

  public void doEffect(Level world, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {}

  public void castingAction(Player player, int count, int potency, int efficiency, int size) {}

  public float getManaCost() {
    return manaCost;
  }

  public void setManaCost(float manaCost) {
    this.manaCost = manaCost;
  }
}
