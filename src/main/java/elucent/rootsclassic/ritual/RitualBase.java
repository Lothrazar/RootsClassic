package elucent.rootsclassic.ritual;

import java.util.ArrayList;
import java.util.List;
import elucent.rootsclassic.RegistryManager;
import elucent.rootsclassic.Roots;
import elucent.rootsclassic.Util;
import elucent.rootsclassic.block.brazier.TileEntityBrazier;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class RitualBase {

  private static final int RADIUS = 4;
  private ArrayList<Block> blocks = new ArrayList<Block>();
  private ArrayList<BlockPos> positionsRelative = new ArrayList<BlockPos>();
  private List<ItemStack> incenses = new ArrayList<ItemStack>();
  private List<ItemStack> ingredients = new ArrayList<ItemStack>();
  private Vec3d color = new Vec3d(255, 255, 255);
  private Vec3d secondaryColor = new Vec3d(255, 255, 255);
  private String name = "";
  private int level;

  public RitualBase(String parName, int level, double r, double g, double b) {
    if (RitualManager.getRitualFromName(name) != null) {
      throw new IllegalArgumentException("No duplicate names for rituals");
    }
    this.name = parName;
    setPrimaryColor(new Vec3d(r, g, b));
    setSecondaryColor(new Vec3d(r, g, b));
    setLevel(level);

  }

  private void setLevel(int level) {
    this.level = level;
    if (level == 0) {
      // no pillars needed EX RitualCauseRain
    }
    else if (level == 2) {
      this.addBlock(RegistryManager.standingStoneT1, -3, 0, -3);
      this.addBlock(RegistryManager.standingStoneT1, -3, 0, 3);
      this.addBlock(RegistryManager.standingStoneT1, 3, 0, -3);
      this.addBlock(RegistryManager.standingStoneT1, 3, 0, 3);
      this.addBlock(RegistryManager.standingStoneT1, 3, 0, 0);
      this.addBlock(RegistryManager.standingStoneT1, -3, 0, 0);
      this.addBlock(RegistryManager.standingStoneT1, 0, 0, 3);
      this.addBlock(RegistryManager.standingStoneT1, 0, 0, -3);
      this.addBlock(RegistryManager.standingStoneT2, 5, 1, 0);
      this.addBlock(RegistryManager.standingStoneT2, -5, 1, 0);
      this.addBlock(RegistryManager.standingStoneT2, 0, 1, 5);
      this.addBlock(RegistryManager.standingStoneT2, 0, 1, -5);
    }
    else if (level == 1) {
      this.addBlock(RegistryManager.standingStoneT1, -3, 0, -3);
      this.addBlock(RegistryManager.standingStoneT1, -3, 0, 3);
      this.addBlock(RegistryManager.standingStoneT1, 3, 0, -3);
      this.addBlock(RegistryManager.standingStoneT1, 3, 0, 3);
      this.addBlock(RegistryManager.standingStoneT1, 3, 0, 0);
      this.addBlock(RegistryManager.standingStoneT1, -3, 0, 0);
      this.addBlock(RegistryManager.standingStoneT1, 0, 0, 3);
      this.addBlock(RegistryManager.standingStoneT1, 0, 0, -3);
    }
    else throw new IllegalArgumentException("Level must be 0, 1 or 2");
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

  public boolean doIngredientsMatch(RitualBase ritual) {
    return Util.itemListsMatch(this.getIngredients(), ritual.getIngredients());
  }

  public abstract void doEffect(World world, BlockPos pos, List<ItemStack> inventory, List<ItemStack> incenses);

  public boolean verifyPositionBlocks(World world, BlockPos pos) {
    if (getPositionsRelative().size() > 0) {
      for (int i = 0; i < getPositionsRelative().size(); i++) {
        BlockPos loopPos = getPositionsRelative().get(i);
        Block loopBlock = getBlocks().get(i);
        BlockPos loopPosOffset = pos.add(loopPos.getX(), loopPos.getY(), loopPos.getZ());
        if (world.getBlockState(loopPosOffset).getBlock() != loopBlock) {
          Roots.logger.info(this.level + " level recipe has Missing block " + loopBlock + " at position " + loopPosOffset);
          return false;
        }
      }
    }
    return true;
  }

  public List<TileEntityBrazier> getRecipeBraziers(World world, BlockPos pos) {
    List<TileEntityBrazier> links = new ArrayList<>();
    TileEntity tileHere;
    for (int i = -1 * RADIUS; i <= RADIUS; i++) {
      for (int j = -1 * RADIUS; j <= RADIUS; j++) {
        if (world.getBlockState(pos.add(i, 0, j)).getBlock() == RegistryManager.brazier) {
          tileHere = world.getTileEntity(pos.add(i, 0, j));
          if (tileHere != null && tileHere instanceof TileEntityBrazier) {
            links.add((TileEntityBrazier) tileHere);
          }
        }
      }
    }
    return links;
  }

  public boolean incesceMatches(World world, BlockPos pos) {
    ArrayList<ItemStack> incenceFromNearby = new ArrayList<ItemStack>();
    List<TileEntityBrazier> braziers = getRecipeBraziers(world, pos);
    for (TileEntityBrazier brazier : braziers) {
      if (!brazier.getHeldItem().isEmpty()) {
        //              Roots.logger.info("found brazier item " + brazier.getHeldItem());
        incenceFromNearby.add(brazier.getHeldItem());
      }
    }
    return Util.itemListsMatch(getIncenses(), incenceFromNearby);
  }

  //  public String getLocalizedName() {
  //    //TODO: use this somewhere?
  //    return I18n.format("roots.ritual." + getName() + ".name");
  //  }
  @Override
  public String toString() {
    //this.getName() + System.lineSeparator() +
    String s = "[A] ";
    for (ItemStack mat : this.getIngredients()) {
      s += mat.getDisplayName() + "; ";
    }
    s += System.lineSeparator() + "[I] ";
    for (ItemStack mat : this.getIncenses()) {
      s += mat.getDisplayName() + "; ";
    }
    return s;
  }

  public List<ItemStack> getIngredients() {
    return ingredients;
  }

  public void setIngredients(List<ItemStack> ingredients) {
    if (ingredients.size() == 0 || ingredients.size() > 3) {
      throw new IllegalArgumentException("Invalid ritual ingredients, must be in range [1,3]");
    }
    this.ingredients = ingredients;
  }

  public List<ItemStack> getIncenses() {
    return incenses;
  }

  public void setIncenses(List<ItemStack> incenses) {
    if (incenses.size() == 0 || incenses.size() > 4) {
      throw new IllegalArgumentException("Invalid ritual incense, must be in range [1,4]");
    }
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

  public void setPrimaryColor(Vec3d color) {
    this.color = color;
  }

  public Vec3d getSecondaryColor() {
    return secondaryColor;
  }

  public RitualBase setSecondaryColor(Vec3d secondaryColor) {
    this.secondaryColor = secondaryColor;
    return this;
  }

  public String getName() {
    return name;
  }


}
