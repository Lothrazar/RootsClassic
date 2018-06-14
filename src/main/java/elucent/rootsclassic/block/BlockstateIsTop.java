package elucent.rootsclassic.block;

import net.minecraft.util.IStringSerializable;

public enum BlockstateIsTop implements IStringSerializable {
  enumBottom(0, "bottom"), enumTop(1, "top");

  private int ID;
  private String name;

  private BlockstateIsTop(int ID, String name) {
    this.ID = ID;
    this.name = name;
  }

  public static BlockstateIsTop fromMeta(int meta) {
    switch (meta) {
      default:
        return enumBottom;
      case 0:
        return enumBottom;
      case 1:
        return enumTop;
    }
  }

  @Override
  public String getName() {
    return name;
  }

  public int getID() {
    return ID;
  }
}