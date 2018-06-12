package elucent.rootsclassic.tileentity;

import java.util.Random;
import elucent.rootsclassic.RegistryManager;
import elucent.rootsclassic.Roots;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.ComponentManager;
import elucent.rootsclassic.item.ItemStaff;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityImbuer extends TEBase implements ITickable {

  public ItemStack stick = null;
  public ItemStack dust = null;
  public int progress = 0;
  public int spin = 0;
  Random random = new Random();

  public TileEntityImbuer() {
    super();
  }

  @Override
  public void readFromNBT(NBTTagCompound tag) {
    super.readFromNBT(tag);
    if (tag.hasKey("stick")) {
      stick = new ItemStack(tag.getCompoundTag("stick"));
    }
    if (tag.hasKey("dust")) {
      dust = new ItemStack(tag.getCompoundTag("dust"));
    }
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound tag) {
    super.writeToNBT(tag);
    if (stick != null) {
      tag.setTag("stick", stick.writeToNBT(new NBTTagCompound()));
    }
    if (dust != null) {
      tag.setTag("dust", dust.writeToNBT(new NBTTagCompound()));
    }
    return tag;
  }

  @Override
  public void breakBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
    if (stick != null) {
      if (!world.isRemote) {
        world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stick));
      }
    }
    if (dust != null) {
      if (!world.isRemote) {
        world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, dust));
      }
    }
    this.invalidate();
  }

  @Override
  public boolean activate(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
    if (progress == 0) {
      if (heldItem == null) {
        if (stick != null) {
          if (!world.isRemote) {
            world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, stick));
          }
          stick = null;
          markDirty();
          this.getWorld().notifyBlockUpdate(getPos(), state, world.getBlockState(pos), 3);
          return true;
        }
        else if (dust != null) {
          if (!world.isRemote) {
            world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, dust));
          }
          dust = null;
          markDirty();
          this.getWorld().notifyBlockUpdate(getPos(), state, world.getBlockState(pos), 3);
          return true;
        }
        return false;
      }
      else if (heldItem.getItem() == Items.STICK) {
        if (stick == null) {
          stick = new ItemStack(Items.STICK, 1);
          heldItem.shrink(1);
          markDirty();
          this.getWorld().notifyBlockUpdate(getPos(), state, world.getBlockState(pos), 3);
          return true;
        }
        return false;
      }
      else if (heldItem.getItem() == RegistryManager.dustPetal) {
        if (dust == null) {
          NBTTagCompound tag = new NBTTagCompound();
          heldItem.writeToNBT(tag);
          dust = new ItemStack(tag);
          heldItem.shrink(1);
          markDirty();
          this.getWorld().notifyBlockUpdate(getPos(), state, world.getBlockState(pos), 3);
          return true;
        }
        return false;
      }
      return false;
    }
    return false;
  }

  @Override
  public void update() {
    if (progress == 0) {
      spin += 4;
    }
    else {
      spin += 12;
    }
    if (dust != null && stick != null) {
      progress++;
    }
    if (progress != 0 && progress % 1 == 0) {
      int chance = random.nextInt(4);
      ComponentBase comp = null;
      if (dust.hasTagCompound()) {
        if (dust.getTagCompound().hasKey("effect")) {
          comp = ComponentManager.getComponentFromName(dust.getTagCompound().getString("effect"));
        }
      }
      if (comp != null) {
        if (chance == 0) {
          if (random.nextBoolean()) {
            Roots.proxy.spawnParticleMagicLineFX(getWorld(), getPos().getX() + 0.125, getPos().getY() + 0.125, getPos().getZ() + 0.125, getPos().getX() + 0.5, getPos().getY() + 0.625, getPos().getZ() + 0.5, comp.primaryColor.x, comp.primaryColor.y, comp.primaryColor.z);
          }
          else {
            Roots.proxy.spawnParticleMagicLineFX(getWorld(), getPos().getX() + 0.125, getPos().getY() + 0.125, getPos().getZ() + 0.125, getPos().getX() + 0.5, getPos().getY() + 0.625, getPos().getZ() + 0.5, comp.secondaryColor.x, comp.secondaryColor.y, comp.secondaryColor.z);
          }
        }
        if (chance == 1) {
          if (random.nextBoolean()) {
            Roots.proxy.spawnParticleMagicLineFX(getWorld(), getPos().getX() + 0.875, getPos().getY() + 0.125, getPos().getZ() + 0.125, getPos().getX() + 0.5, getPos().getY() + 0.625, getPos().getZ() + 0.5, comp.primaryColor.x, comp.primaryColor.y, comp.primaryColor.z);
          }
          else {
            Roots.proxy.spawnParticleMagicLineFX(getWorld(), getPos().getX() + 0.875, getPos().getY() + 0.125, getPos().getZ() + 0.125, getPos().getX() + 0.5, getPos().getY() + 0.625, getPos().getZ() + 0.5, comp.secondaryColor.x, comp.secondaryColor.y, comp.secondaryColor.z);
          }
        }
        if (chance == 2) {
          if (random.nextBoolean()) {
            Roots.proxy.spawnParticleMagicLineFX(getWorld(), getPos().getX() + 0.875, getPos().getY() + 0.125, getPos().getZ() + 0.875, getPos().getX() + 0.5, getPos().getY() + 0.625, getPos().getZ() + 0.5, comp.primaryColor.x, comp.primaryColor.y, comp.primaryColor.z);
          }
          else {
            Roots.proxy.spawnParticleMagicLineFX(getWorld(), getPos().getX() + 0.875, getPos().getY() + 0.125, getPos().getZ() + 0.875, getPos().getX() + 0.5, getPos().getY() + 0.625, getPos().getZ() + 0.5, comp.secondaryColor.x, comp.secondaryColor.y, comp.secondaryColor.z);
          }
        }
        if (chance == 3) {
          if (random.nextBoolean()) {
            Roots.proxy.spawnParticleMagicLineFX(getWorld(), getPos().getX() + 0.125, getPos().getY() + 0.125, getPos().getZ() + 0.875, getPos().getX() + 0.5, getPos().getY() + 0.625, getPos().getZ() + 0.5, comp.primaryColor.x, comp.primaryColor.y, comp.primaryColor.z);
          }
          else {
            Roots.proxy.spawnParticleMagicLineFX(getWorld(), getPos().getX() + 0.125, getPos().getY() + 0.125, getPos().getZ() + 0.875, getPos().getX() + 0.5, getPos().getY() + 0.625, getPos().getZ() + 0.5, comp.secondaryColor.x, comp.secondaryColor.y, comp.secondaryColor.z);
          }
        }
      }
    }
    if (progress > 40) {
      progress = 0;
      if (dust != null && stick != null) {
        if (dust.hasTagCompound()) {
          ItemStack staff = new ItemStack(RegistryManager.staff, 1, 1);
          String effectName = dust.getTagCompound().getString("effect");
          int potency = dust.getTagCompound().getInteger("potency");
          int duration = dust.getTagCompound().getInteger("efficiency");
          int size = dust.getTagCompound().getInteger("size");
          ItemStaff.createData(staff, effectName, potency, duration, size);
          if (!getWorld().isRemote) {
            getWorld().spawnEntity(new EntityItem(getWorld(), getPos().getX() + 0.5, getPos().getY() + 1.0, getPos().getZ() + 0.5, staff));
          }
          stick = null;
          dust = null;
          markDirty();
          this.getWorld().notifyBlockUpdate(getPos(), getWorld().getBlockState(getPos()), getWorld().getBlockState(getPos()), 3);
        }
      }
    }
  }
}
