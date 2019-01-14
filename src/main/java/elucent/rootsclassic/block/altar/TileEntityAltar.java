package elucent.rootsclassic.block.altar;

import java.util.ArrayList;
import java.util.Random;
import elucent.rootsclassic.Roots;
import elucent.rootsclassic.block.brazier.TileEntityBrazier;
import elucent.rootsclassic.ritual.RitualBase;
import elucent.rootsclassic.ritual.RitualManager;
import elucent.rootsclassic.tileentity.TEBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class TileEntityAltar extends TEBase implements ITickable {

  private static final int RECIPE_PROGRESS_TIME = 200;
  private ArrayList<ItemStack> inventory = new ArrayList<ItemStack>();
  private ArrayList<ItemStack> incenses = new ArrayList<ItemStack>();
  private Random random = new Random();
  private int ticker = 0;
  private int progress = 0;
  private String ritualName = null;
  private RitualBase ritualCurrent = null;
  private ItemStack resultItem = ItemStack.EMPTY;

  public TileEntityAltar() {
    super();
  }

  @Override
  public void readFromNBT(NBTTagCompound tag) {
    super.readFromNBT(tag);
    setInventory(new ArrayList<ItemStack>());
    if (tag.hasKey("inventory")) {
      NBTTagList list = tag.getTagList("inventory", Constants.NBT.TAG_COMPOUND);
      for (int i = 0; i < list.tagCount(); i++) {
        getInventory().add(new ItemStack(list.getCompoundTagAt(i)));
      }
    }
    setIncenses(new ArrayList<ItemStack>());
    if (tag.hasKey("incenses")) {
      NBTTagList list = tag.getTagList("incenses", Constants.NBT.TAG_COMPOUND);
      for (int i = 0; i < list.tagCount(); i++) {
        getIncenses().add(new ItemStack(list.getCompoundTagAt(i)));
      }
    }
    if (tag.hasKey("ritualName")) {
      setRitualName(tag.getString("ritualName"));
      setRitualCurrent(RitualManager.getRitualFromName(getRitualName()));
    }
    if (tag.hasKey("progress")) {
      setProgress(tag.getInteger("progress"));
    }
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound tag) {
    super.writeToNBT(tag);
    if (getInventory().size() > 0) {
      NBTTagList list = new NBTTagList();
      for (int i = 0; i < getInventory().size(); i++) {
        //        if (getInventory().get(i) != null) {
        list.appendTag(getInventory().get(i).writeToNBT(new NBTTagCompound()));
        //        }
      }
      tag.setTag("inventory", list);
    }
    if (getIncenses().size() > 0) {
      NBTTagList list = new NBTTagList();
      for (int i = 0; i < getIncenses().size(); i++) {
        //  ;
        //        if (getIncenses().get(i) != null) {
        list.appendTag(getIncenses().get(i).writeToNBT(new NBTTagCompound()));
        //        } 
      }
      tag.setTag("incenses", list);
    }
    if (getRitualName() != null) {
      tag.setString("ritualName", getRitualName());
    }
    tag.setInteger("progress", getProgress());
    return tag;
  }

  @Override
  public void breakBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
    for (int i = 0; i < getInventory().size(); i++) {
      if (!world.isRemote) {
        world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, getInventory().get(i)));
      }
    }
    this.invalidate();
  }

  @Override
  public boolean activate(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
    if (heldItem.isEmpty() && !player.isSneaking() && this.getProgress() == 0) {
      //try to withdraw an item
      if (getInventory().size() > 0) {
        if (!world.isRemote) {
          world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, getInventory().remove(getInventory().size() - 1)));
        }
        else {
          getInventory().remove(getInventory().size() - 1);
        }
        markDirty();
        this.getWorld().notifyBlockUpdate(getPos(), state, world.getBlockState(pos), 3);
        return true;
      }
    }
    else if (player.isSneaking() && heldItem.isEmpty() && this.getProgress() == 0) {
      // Try to start a new ritual
      setRitualName(null);
      setRitualCurrent(null);
      RitualBase ritual = RitualManager.findMatchingByIngredients(this);
      if (ritual == null) {
        Roots.statusMessage(player, "roots.error.noritual.ingredients");
        return false;
      }
      if (!ritual.verifyPositionBlocks(world, pos)) {
        Roots.statusMessage(player, "roots.error.noritual.stones");
        return false;
      }
      //does it match everything else? 
      if (ritual.incesceMatches(getWorld(), getPos())) {
        setRitualCurrent(ritual);
        setRitualName(ritual.getName());
        Roots.logger.info("found " + ritual.getName());
        setIncenses(RitualManager.getIncenses(world, getPos()));
        setProgress(RECIPE_PROGRESS_TIME);
        for (TileEntityBrazier brazier : ritual.getRecipeBraziers(world, pos)) {
          brazier.setBurning(true);
          brazier.setHeldItem(ItemStack.EMPTY);
        }
        //        this.emptyNearbyBraziers();
        //          System.out.println(" ritual STARTED " + ritual.name);
        markDirty();
        this.getWorld().notifyBlockUpdate(getPos(), state, world.getBlockState(pos), 3);
        Roots.statusMessage(player, "roots.ritual.started");
        return true;
      }
      else {
        Roots.logger.info("found but empty incense  " + ritual.getName());
        Roots.statusMessage(player, "roots.error.noritual.incense");
        return false;
      }
      //      if (getRitualName() == null) {
      //          // but wait tho, maybe its valid but its not lit on fire yet
      //        Roots.statusMessage(player, "roots.error.noritual.name");
      //
      //        markDirty();
      //        this.getWorld().notifyBlockUpdate(getPos(), state, world.getBlockState(pos), 3);
      //        return true;
      //      }
    }
    else {
      //try to insert an item into the altar 
      if (getInventory().size() < 3 && getProgress() == 0) {
        ItemStack toAdd = new ItemStack(heldItem.getItem(), 1, heldItem.getItemDamage());
        if (heldItem.hasTagCompound()) {
          toAdd.setTagCompound(heldItem.getTagCompound());
        }
        getInventory().add(toAdd);
        heldItem.shrink(1);
        markDirty();
        this.getWorld().notifyBlockUpdate(getPos(), state, world.getBlockState(pos), 3);
        return true;
      }
    }
    return false;
  }

  @Override
  public void update() {
    setTicker(getTicker() + 3);
    if (getTicker() > 360) {
      setTicker(0);
    }
    if (getProgress() > 0 && getRitualCurrent() != null) {
      setProgress(getProgress() - 1);
      if (getWorld().isRemote) {
        if (getRitualCurrent().getPositionsRelative().size() > 0) {
          BlockPos pos = getRitualCurrent().getPositionsRelative().get(random.nextInt(getRitualCurrent().getPositionsRelative().size())).up().add(getPos().getX(), getPos().getY(), getPos().getZ());
          if (random.nextInt(6) == 0) {
            Roots.proxy.spawnParticleMagicAltarLineFX(getWorld(), pos.getX() + 0.5, pos.getY() + 0.125, pos.getZ() + 0.5, getPos().getX() + 0.5, getPos().getY() + 0.875, getPos().getZ() + 0.5, getRitualCurrent().getColor().x, getRitualCurrent().getColor().y, getRitualCurrent().getColor().z);
          }
          else {
            Roots.proxy.spawnParticleMagicAltarLineFX(getWorld(), pos.getX() + 0.5, pos.getY() + 0.125, pos.getZ() + 0.5, getPos().getX() + 0.5, getPos().getY() + 0.875, getPos().getZ() + 0.5, getRitualCurrent().getSecondaryColor().x, getRitualCurrent().getSecondaryColor().y, getRitualCurrent().getSecondaryColor().z);
          }
        }
        if (random.nextInt(4) == 0) {
          Roots.proxy.spawnParticleMagicAltarFX(getWorld(), getPos().getX() + 0.5, getPos().getY() + 0.875, getPos().getZ() + 0.5, 0.125 * Math.sin(Math.toRadians(360.0 * (getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(360.0 * (getProgress() % 100) / 100.0)), getRitualCurrent().getColor().x, getRitualCurrent().getColor().y, getRitualCurrent().getColor().z);
        }
        else {
          Roots.proxy.spawnParticleMagicAltarFX(getWorld(), getPos().getX() + 0.5, getPos().getY() + 0.875, getPos().getZ() + 0.5, 0.125 * Math.sin(Math.toRadians(360.0 * (getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(360.0 * (getProgress() % 100) / 100.0)), getRitualCurrent().getSecondaryColor().x, getRitualCurrent().getSecondaryColor().y, getRitualCurrent().getSecondaryColor().z);
        }
        if (random.nextInt(4) == 0) {
          Roots.proxy.spawnParticleMagicAltarFX(getWorld(), getPos().getX() + 0.5, getPos().getY() + 0.875, getPos().getZ() + 0.5, 0.125 * Math.sin(Math.toRadians(90.0 + 360.0 * (getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(90.0 + 360.0 * (getProgress() % 100) / 100.0)), getRitualCurrent().getColor().x, getRitualCurrent().getColor().y, getRitualCurrent().getColor().z);
        }
        else {
          Roots.proxy.spawnParticleMagicAltarFX(getWorld(), getPos().getX() + 0.5, getPos().getY() + 0.875, getPos().getZ() + 0.5, 0.125 * Math.sin(Math.toRadians(90.0 + 360.0 * (getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(90.0 + 360.0 * (getProgress() % 100) / 100.0)), getRitualCurrent().getSecondaryColor().x, getRitualCurrent().getSecondaryColor().y, getRitualCurrent().getSecondaryColor().z);
        }
        if (random.nextInt(4) == 0) {
          Roots.proxy.spawnParticleMagicAltarFX(getWorld(), getPos().getX() + 0.5, getPos().getY() + 0.875, getPos().getZ() + 0.5, 0.125 * Math.sin(Math.toRadians(180.0 + 360.0 * (getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(180.0 + 360.0 * (getProgress() % 100) / 100.0)), getRitualCurrent().getColor().x, getRitualCurrent().getColor().y, getRitualCurrent().getColor().z);
        }
        else {
          Roots.proxy.spawnParticleMagicAltarFX(getWorld(), getPos().getX() + 0.5, getPos().getY() + 0.875, getPos().getZ() + 0.5, 0.125 * Math.sin(Math.toRadians(180.0 + 360.0 * (getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(180.0 + 360.0 * (getProgress() % 100) / 100.0)), getRitualCurrent().getSecondaryColor().x, getRitualCurrent().getSecondaryColor().y, getRitualCurrent().getSecondaryColor().z);
        }
        if (random.nextInt(4) == 0) {
          Roots.proxy.spawnParticleMagicAltarFX(getWorld(), getPos().getX() + 0.5, getPos().getY() + 0.875, getPos().getZ() + 0.5, 0.125 * Math.sin(Math.toRadians(270.0 + 360.0 * (getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(270.0 + 360.0 * (getProgress() % 100) / 100.0)), getRitualCurrent().getColor().x, getRitualCurrent().getColor().y, getRitualCurrent().getColor().z);
        }
        else {
          Roots.proxy.spawnParticleMagicAltarFX(getWorld(), getPos().getX() + 0.5, getPos().getY() + 0.875, getPos().getZ() + 0.5, 0.125 * Math.sin(Math.toRadians(270.0 + 360.0 * (getProgress() % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(270.0 + 360.0 * (getProgress() % 100) / 100.0)), getRitualCurrent().getSecondaryColor().x, getRitualCurrent().getSecondaryColor().y, getRitualCurrent().getSecondaryColor().z);
        }
      }
      //      if (getProgress() % 40 == 0) {
      //        setIncenses(RitualManager.getIncenses(getWorld(), getPos()));
      //        boolean doesMatch = false;
      //        for (int i = 0; i < RitualManager.rituals.size(); i++) {
      //          if (RitualManager.rituals.get(i).incesceMatches(getWorld(), getPos())) {
      //            doesMatch = true;
      //          }
      //        }
      //        if (!doesMatch) {
      //          setRitualCurrent(null);
      //          setRitualName(null);
      //        }
      //      }
      if (getProgress() == 0 && getRitualCurrent() != null) {
        Roots.logger.info("RITUAL has completed " + getRitualCurrent().getName());
        getRitualCurrent().doEffect(getWorld(), getPos(), getInventory(), getIncenses());
        setRitualName(null);
        setRitualCurrent(null);
        markDirty();
        this.getWorld().notifyBlockUpdate(getPos(), getWorld().getBlockState(getPos()), getWorld().getBlockState(getPos()), 3);
      }
    }
  }

  //  private void emptyNearbyBraziers() {
  //    for (TileEntityBrazier brazier : getRitualCurrent().getRecipeBraziers(world, pos)) {
  //      brazier.setHeldItem(ItemStack.EMPTY);
  //      brazier.setBurning(false);
  //    }
  //  }
  public ArrayList<ItemStack> getInventory() {
    return inventory;
  }

  public void setInventory(ArrayList<ItemStack> inventory) {
    this.inventory = inventory;
  }

  public ArrayList<ItemStack> getIncenses() {
    return incenses;
  }

  public void setIncenses(ArrayList<ItemStack> incenses) {
    this.incenses = incenses;
  }

  public int getTicker() {
    return ticker;
  }

  public void setTicker(int ticker) {
    this.ticker = ticker;
  }

  public int getProgress() {
    return progress;
  }

  public void setProgress(int progress) {
    this.progress = progress;
  }

  public String getRitualName() {
    return ritualName;
  }

  public void setRitualName(String ritualName) {
    this.ritualName = ritualName;
  }

  public RitualBase getRitualCurrent() {
    return ritualCurrent;
  }

  public void setRitualCurrent(RitualBase ritualCurrent) {
    this.ritualCurrent = ritualCurrent;
  }

  public ItemStack getResultItem() {
    return resultItem;
  }

  public void setResultItem(ItemStack resultItem) {
    if (resultItem == null) {
      resultItem = ItemStack.EMPTY;
    }
    this.resultItem = resultItem;
  }
}
