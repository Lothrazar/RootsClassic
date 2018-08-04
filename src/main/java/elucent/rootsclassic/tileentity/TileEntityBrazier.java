package elucent.rootsclassic.tileentity;

import java.util.Random;
import elucent.rootsclassic.Roots;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityBrazier extends TEBase implements ITickable {

  private static final int TOTAL_BURN_TIME = 2400;
  private ItemStack heldItem = ItemStack.EMPTY;
  private Random random = new Random();
  private int ticker = 0;
  private boolean burning = false;
  private int progress = 0;

  public TileEntityBrazier() {
    super();
  }

  @Override
  public void readFromNBT(NBTTagCompound tag) {
    super.readFromNBT(tag);
    if (tag.hasKey("heldItem")) {
      setHeldItem(new ItemStack(tag.getCompoundTag("heldItem")));
    }
    if (tag.hasKey("burning")) {
      setBurning(tag.getBoolean("burning"));
    }
    if (tag.hasKey("progress")) {
      progress = tag.getInteger("progress");
    }
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound tag) {
    super.writeToNBT(tag);
    if (getHeldItem() != null) {
      tag.setTag("heldItem", getHeldItem().writeToNBT(new NBTTagCompound()));
    }
    tag.setBoolean("burning", isBurning());
    tag.setInteger("progress", progress);
    return tag;
  }

  @Override
  public void breakBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
    if (getHeldItem() != null && !isBurning()) {
      if (!world.isRemote) {
        world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, getHeldItem()));
      }
    }
    this.invalidate();
  }

  private void dropContaining(IBlockState state) {
    if (!world.isRemote) {
      world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, getHeldItem()));
    }
    setHeldItem(ItemStack.EMPTY);
  }

  private void notifyUpdate(IBlockState state) {
    this.markDirty();
    this.getWorld().notifyBlockUpdate(getPos(), state, world.getBlockState(pos), 3);
  }

  @Override
  public boolean activate(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack playerItem, EnumFacing side, float hitX, float hitY, float hitZ) {
    if (playerItem.isEmpty()) {

      if (!getHeldItem().isEmpty() && !isBurning()) {
        if (player.isSneaking()) {
          Roots.statusMessage(player, getHeldItem().getDisplayName());
        }
        else {

          dropContaining(state);
          notifyUpdate(state);
          Roots.statusMessage(player, "brazier.burning.empty");
        }
        return true;
      }
      else if (isBurning()) {
        if (player.isSneaking()) {
          Roots.statusMessage(player, "brazier.burning.off");
          stopBurning();
          //          heldItem = ItemStack.EMPTY;
          notifyUpdate(state);
          return true;
        }
      }

    }
    else if (playerItem.getItem() == Items.FLINT_AND_STEEL) {
      if (!getHeldItem().isEmpty()) {
        startBurning();
        Roots.statusMessage(player, "brazier.burning.on");

        notifyUpdate(state);
        return true;
      }
    }
    else {
      if (getHeldItem().isEmpty()) {
        setHeldItem(new ItemStack(playerItem.getItem(), 1, playerItem.getItemDamage()));
        if (playerItem.hasTagCompound()) {
          getHeldItem().setTagCompound(playerItem.getTagCompound());
        }
        playerItem.shrink(1);
        Roots.statusMessage(player, "brazier.burning.added");
        notifyUpdate(state);
        return true;
      }
    }
    return false;
  }

  private void startBurning() {
    setBurning(true);
    progress = TOTAL_BURN_TIME;
  }

  private void stopBurning() {
    setBurning(false);
    progress = 0;
  }

  @Override
  public void update() {
    setTicker(getTicker() + (isBurning() == true ? 12 : 3));
    if (progress > 0) {
      World world = getWorld();
      progress--;
      if (progress % 2 == 0) {
        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, getPos().getX() + 0.5, getPos().getY() + 0.5, getPos().getZ() + 0.5, 0, random.nextDouble() * 0.0625 + 0.0625, 0, 0);
      }
      if (progress % 20 == 0) {
        world.spawnParticle(EnumParticleTypes.FLAME, getPos().getX() + 0.5, getPos().getY() + 0.5, getPos().getZ() + 0.5, 0, 0, 0, 0);
      }
      if (progress <= 0) {
        setBurning(false);
        //        heldItem = ItemStack.EMPTY;
        markDirty();
        world.notifyBlockUpdate(getPos(), world.getBlockState(getPos()), world.getBlockState(getPos()), 3);
      }
    }
    if (getTicker() > 360) {
      setTicker(0);
    }
  }

  public boolean isBurning() {
    return burning;
  }

  public void setBurning(boolean burning) {
    this.burning = burning;
  }

  public int getTicker() {
    return ticker;
  }

  public void setTicker(int ticker) {
    this.ticker = ticker;
  }

  public ItemStack getHeldItem() {
    return heldItem;
  }

  public void setHeldItem(ItemStack heldItem) {
    this.heldItem = heldItem;
  }
}
