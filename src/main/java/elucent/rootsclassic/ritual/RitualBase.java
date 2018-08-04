package elucent.rootsclassic.ritual;

import java.util.ArrayList;
import java.util.List;
import elucent.rootsclassic.RegistryManager;
import elucent.rootsclassic.Roots;
import elucent.rootsclassic.Util;
import elucent.rootsclassic.tileentity.TileEntityAltar;
import elucent.rootsclassic.tileentity.TileEntityBrazier;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class RitualBase {

  private ArrayList<Block> blocks = new ArrayList<Block>();
  private ArrayList<BlockPos> positionsRelative = new ArrayList<BlockPos>();
  private ArrayList<ItemStack> incenses = new ArrayList<ItemStack>();
  private ArrayList<ItemStack> ingredients = new ArrayList<ItemStack>();
  private Vec3d color = new Vec3d(255, 255, 255);
  private Vec3d secondaryColor = new Vec3d(255, 255, 255);
  private String name = "";

  public RitualBase(String parName, double r, double g, double b) {
    setName(parName);
    setColor(new Vec3d(r, g, b));
    setSecondaryColor(new Vec3d(r, g, b));
  }

  public RitualBase(String parName, double r, double g, double b, double r2, double g2, double b2) {
    setName(parName);
    setColor(new Vec3d(r, g, b));
    setSecondaryColor(new Vec3d(r2, g2, b2));
  }

  public RitualBase addBlock(Block b, int x, int y, int z) {
    getBlocks().add(b);
    getPositionsRelative().add(new BlockPos(x, y, z));
    return this;
  }

  public RitualBase addIngredient(ItemStack i) {
    getIngredients().add(i);
    return this;
  }

  public RitualBase addIncense(ItemStack i) {
    getIncenses().add(i);
    return this;
  }

  public abstract void doEffect(World world, BlockPos pos, List<ItemStack> inventory, List<ItemStack> incenses);

  public boolean verifyPositionBlocks(World world, BlockPos pos) {
    if (getPositionsRelative().size() > 0) {
      for (int i = 0; i < getPositionsRelative().size(); i++) {
        BlockPos loopPos = getPositionsRelative().get(i);
        Block loopBlock = getBlocks().get(i);
        BlockPos loopPosOffset = pos.add(loopPos.getX(), loopPos.getY(), loopPos.getZ());
        if (world.getBlockState(loopPosOffset).getBlock() != loopBlock) {
          Roots.logger.info("Missing block " + loopBlock + " at position " + loopPosOffset);
          return false;
        }
      }
    }
    return true;
  }

  public boolean matches(World world, BlockPos pos) {
    //    if (!verifyPositionBlocks(world, pos)) {
    //      return false;
    //    }
    ArrayList<ItemStack> test = new ArrayList<ItemStack>();
    for (int i = -4; i < 5; i++) {
      for (int j = -4; j < 5; j++) {
        if (world.getBlockState(pos.add(i, 0, j)).getBlock() == RegistryManager.brazier) {
          if (world.getTileEntity(pos.add(i, 0, j)) != null) {
            TileEntityBrazier teb = (TileEntityBrazier) world.getTileEntity(pos.add(i, 0, j));
            if (teb.isBurning()) {
              Roots.logger.info("found brazier item " + teb.getHeldItem());
              test.add(teb.getHeldItem());
            }
            else {
              Roots.logger.info("A brazier is not burning");
            }
          }
        }
      }
    }
    return Util.itemListsMatch(getIncenses(), test) && Util.itemListsMatchWithSize(getIngredients(), ((TileEntityAltar) world.getTileEntity(pos)).inventory);
  }

  public ArrayList<ItemStack> getIngredients() {
    return ingredients;
  }

  public void setIngredients(ArrayList<ItemStack> ingredients) {
    this.ingredients = ingredients;
  }

  public ArrayList<ItemStack> getIncenses() {
    return incenses;
  }

  public void setIncenses(ArrayList<ItemStack> incenses) {
    this.incenses = incenses;
  }

  public ArrayList<BlockPos> getPositionsRelative() {
    return positionsRelative;
  }

  public void setPositionsRelative(ArrayList<BlockPos> positionsRelative) {
    this.positionsRelative = positionsRelative;
  }

  public ArrayList<Block> getBlocks() {
    return blocks;
  }

  public void setBlocks(ArrayList<Block> blocks) {
    this.blocks = blocks;
  }

  public Vec3d getColor() {
    return color;
  }

  public void setColor(Vec3d color) {
    this.color = color;
  }

  public Vec3d getSecondaryColor() {
    return secondaryColor;
  }

  public void setSecondaryColor(Vec3d secondaryColor) {
    this.secondaryColor = secondaryColor;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
