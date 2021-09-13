package elucent.rootsclassic.client.screen;

import net.minecraft.world.item.ItemStack;

public class ScreenSlotInstance {

  private int x;
  private int y;
  private ItemStack stack;

  public ScreenSlotInstance(ItemStack s, int x, int y) {
    setIngredient(s);
    this.setX(x);
    this.setY(y);
  }

  public boolean isMouseover(int mouseX, int mouseY) {
    return getX() < mouseX && mouseX < getX() + getWidth()
        && getY() < mouseY && mouseY < getY() + getHeight();
  }

  private int getHeight() {
    return 18;
  }

  private int getWidth() {
    return 18;
  }

  public ItemStack getStack() {
    return stack;
  }

  public void setIngredient(ItemStack stack) {
    this.stack = stack;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }
}
