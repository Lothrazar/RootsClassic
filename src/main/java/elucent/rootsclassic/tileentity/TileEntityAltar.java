package elucent.rootsclassic.tileentity;

import java.util.ArrayList;
import java.util.Random;
import elucent.rootsclassic.Roots;
import elucent.rootsclassic.ritual.RitualBase;
import elucent.rootsclassic.ritual.RitualManager;
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

  public ArrayList<ItemStack> inventory = new ArrayList<ItemStack>();
  public ArrayList<ItemStack> incenses = new ArrayList<ItemStack>();
  Random random = new Random();
  int ticker = 0;
  int progress = 0;
  String ritualName = null;
  RitualBase ritualCurrent = null;
  ItemStack resultItem = null;

  public TileEntityAltar() {
    super();
  }

  @Override
  public void readFromNBT(NBTTagCompound tag) {
    super.readFromNBT(tag);
    inventory = new ArrayList<ItemStack>();
    if (tag.hasKey("inventory")) {
      NBTTagList list = tag.getTagList("inventory", Constants.NBT.TAG_COMPOUND);
      for (int i = 0; i < list.tagCount(); i++) {
        inventory.add(new ItemStack(list.getCompoundTagAt(i)));
      }
    }
    incenses = new ArrayList<ItemStack>();
    if (tag.hasKey("incenses")) {
      NBTTagList list = tag.getTagList("incenses", Constants.NBT.TAG_COMPOUND);
      for (int i = 0; i < list.tagCount(); i++) {
        incenses.add(new ItemStack(list.getCompoundTagAt(i)));
      }
    }
    if (tag.hasKey("ritualName")) {
      ritualName = tag.getString("ritualName");
      ritualCurrent = RitualManager.getRitualFromName(ritualName);
    }
    if (tag.hasKey("progress")) {
      progress = tag.getInteger("progress");
    }
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound tag) {
    super.writeToNBT(tag);
    if (inventory.size() > 0) {
      NBTTagList list = new NBTTagList();
      for (int i = 0; i < inventory.size(); i++) {
        ;
        if (inventory.get(i) != null) {
          list.appendTag(inventory.get(i).writeToNBT(new NBTTagCompound()));
        }
      }
      tag.setTag("inventory", list);
    }
    if (incenses.size() > 0) {
      NBTTagList list = new NBTTagList();
      for (int i = 0; i < incenses.size(); i++) {
        ;
        if (incenses.get(i) != null) {
          list.appendTag(incenses.get(i).writeToNBT(new NBTTagCompound()));
        }
      }
      tag.setTag("incenses", list);
    }
    if (ritualName != null) {
      tag.setString("ritualName", ritualName);
    }
    tag.setInteger("progress", progress);
    return tag;
  }

  @Override
  public void breakBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
    for (int i = 0; i < inventory.size(); i++) {
      if (!world.isRemote) {
        world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, inventory.get(i)));
      }
    }
    this.invalidate();
  }

  @Override
  public boolean activate(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
    if (heldItem.isEmpty() && !player.isSneaking() && this.progress == 0) {
      if (inventory.size() > 0) {
        if (!world.isRemote) {
          world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, inventory.remove(inventory.size() - 1)));
        }
        else {
          inventory.remove(inventory.size() - 1);
        }
        markDirty();
        this.getWorld().notifyBlockUpdate(getPos(), state, world.getBlockState(pos), 3);
        return true;
      }
    }
    else if (player.isSneaking() && heldItem.isEmpty() && this.progress == 0) {//
      ritualName = null;
      ritualCurrent = null;
      for (RitualBase ritual : RitualManager.rituals) {
        if (ritual.verifyPositionBlocks(world, pos) == false) {
          Roots.logger.info("totems dont match: " + ritual.getName());
          return false;
        }
        else if (ritual.matches(getWorld(), getPos())) {
          ritualCurrent = ritual;
          ritualName = ritual.getName();
          incenses = RitualManager.getIncenses(world, getPos());
          progress = 200;
          //          System.out.println(" ritual STARTED " + ritual.name);
          markDirty();
          this.getWorld().notifyBlockUpdate(getPos(), state, world.getBlockState(pos), 3);
          return true;
        }
      }
      if (ritualName == null) {
          // but wait tho, maybe its valid but its not lit on fire yet
          Roots.statusMessage(player, "roots.error.noritual.name");

        markDirty();
        this.getWorld().notifyBlockUpdate(getPos(), state, world.getBlockState(pos), 3);
        return true;
      }
    }
    else {
      if (inventory.size() < 3 && progress == 0) {
        ItemStack toAdd = new ItemStack(heldItem.getItem(), 1, heldItem.getItemDamage());
        if (heldItem.hasTagCompound()) {
          toAdd.setTagCompound(heldItem.getTagCompound());
        }
        inventory.add(toAdd);
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
    ticker += 3.0;
    if (ticker > 360) {
      ticker = 0;
    }
    if (progress > 0 && ritualCurrent != null) {
      progress--;
      if (getWorld().isRemote) {
        if (ritualCurrent.getPositionsRelative().size() > 0) {
          BlockPos pos = ritualCurrent.getPositionsRelative().get(random.nextInt(ritualCurrent.getPositionsRelative().size())).up().add(getPos().getX(), getPos().getY(), getPos().getZ());
          if (random.nextInt(6) == 0) {
            Roots.proxy.spawnParticleMagicAltarLineFX(getWorld(), pos.getX() + 0.5, pos.getY() + 0.125, pos.getZ() + 0.5, getPos().getX() + 0.5, getPos().getY() + 0.875, getPos().getZ() + 0.5, ritualCurrent.getColor().x, ritualCurrent.getColor().y, ritualCurrent.getColor().z);
          }
          else {
            Roots.proxy.spawnParticleMagicAltarLineFX(getWorld(), pos.getX() + 0.5, pos.getY() + 0.125, pos.getZ() + 0.5, getPos().getX() + 0.5, getPos().getY() + 0.875, getPos().getZ() + 0.5, ritualCurrent.getSecondaryColor().x, ritualCurrent.getSecondaryColor().y, ritualCurrent.getSecondaryColor().z);
          }
        }
        if (random.nextInt(4) == 0) {
          Roots.proxy.spawnParticleMagicAltarFX(getWorld(), getPos().getX() + 0.5, getPos().getY() + 0.875, getPos().getZ() + 0.5, 0.125 * Math.sin(Math.toRadians(360.0 * (progress % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(360.0 * (progress % 100) / 100.0)), ritualCurrent.getColor().x, ritualCurrent.getColor().y, ritualCurrent.getColor().z);
        }
        else {
          Roots.proxy.spawnParticleMagicAltarFX(getWorld(), getPos().getX() + 0.5, getPos().getY() + 0.875, getPos().getZ() + 0.5, 0.125 * Math.sin(Math.toRadians(360.0 * (progress % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(360.0 * (progress % 100) / 100.0)), ritualCurrent.getSecondaryColor().x, ritualCurrent.getSecondaryColor().y, ritualCurrent.getSecondaryColor().z);
        }
        if (random.nextInt(4) == 0) {
          Roots.proxy.spawnParticleMagicAltarFX(getWorld(), getPos().getX() + 0.5, getPos().getY() + 0.875, getPos().getZ() + 0.5, 0.125 * Math.sin(Math.toRadians(90.0 + 360.0 * (progress % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(90.0 + 360.0 * (progress % 100) / 100.0)), ritualCurrent.getColor().x, ritualCurrent.getColor().y, ritualCurrent.getColor().z);
        }
        else {
          Roots.proxy.spawnParticleMagicAltarFX(getWorld(), getPos().getX() + 0.5, getPos().getY() + 0.875, getPos().getZ() + 0.5, 0.125 * Math.sin(Math.toRadians(90.0 + 360.0 * (progress % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(90.0 + 360.0 * (progress % 100) / 100.0)), ritualCurrent.getSecondaryColor().x, ritualCurrent.getSecondaryColor().y, ritualCurrent.getSecondaryColor().z);
        }
        if (random.nextInt(4) == 0) {
          Roots.proxy.spawnParticleMagicAltarFX(getWorld(), getPos().getX() + 0.5, getPos().getY() + 0.875, getPos().getZ() + 0.5, 0.125 * Math.sin(Math.toRadians(180.0 + 360.0 * (progress % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(180.0 + 360.0 * (progress % 100) / 100.0)), ritualCurrent.getColor().x, ritualCurrent.getColor().y, ritualCurrent.getColor().z);
        }
        else {
          Roots.proxy.spawnParticleMagicAltarFX(getWorld(), getPos().getX() + 0.5, getPos().getY() + 0.875, getPos().getZ() + 0.5, 0.125 * Math.sin(Math.toRadians(180.0 + 360.0 * (progress % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(180.0 + 360.0 * (progress % 100) / 100.0)), ritualCurrent.getSecondaryColor().x, ritualCurrent.getSecondaryColor().y, ritualCurrent.getSecondaryColor().z);
        }
        if (random.nextInt(4) == 0) {
          Roots.proxy.spawnParticleMagicAltarFX(getWorld(), getPos().getX() + 0.5, getPos().getY() + 0.875, getPos().getZ() + 0.5, 0.125 * Math.sin(Math.toRadians(270.0 + 360.0 * (progress % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(270.0 + 360.0 * (progress % 100) / 100.0)), ritualCurrent.getColor().x, ritualCurrent.getColor().y, ritualCurrent.getColor().z);
        }
        else {
          Roots.proxy.spawnParticleMagicAltarFX(getWorld(), getPos().getX() + 0.5, getPos().getY() + 0.875, getPos().getZ() + 0.5, 0.125 * Math.sin(Math.toRadians(270.0 + 360.0 * (progress % 100) / 100.0)), 0, 0.125 * Math.cos(Math.toRadians(270.0 + 360.0 * (progress % 100) / 100.0)), ritualCurrent.getSecondaryColor().x, ritualCurrent.getSecondaryColor().y, ritualCurrent.getSecondaryColor().z);
        }
      }
      if (progress % 40 == 0) {
        incenses = RitualManager.getIncenses(getWorld(), getPos());
        boolean doesMatch = false;
        for (int i = 0; i < RitualManager.rituals.size(); i++) {
          if (RitualManager.rituals.get(i).matches(getWorld(), getPos())) {
            doesMatch = true;
          }
        }
        if (!doesMatch) {
          ritualCurrent = null;
          ritualName = null;
        }
      }
      if (progress == 0 && ritualCurrent != null) {
        System.out.println(" ritual do effect ok");
        ritualCurrent.doEffect(getWorld(), getPos(), inventory, incenses);
        ritualName = null;
        ritualCurrent = null;
        markDirty();
        this.getWorld().notifyBlockUpdate(getPos(), getWorld().getBlockState(getPos()), getWorld().getBlockState(getPos()), 3);
      }
    }
  }
}
