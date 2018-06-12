package elucent.rootsclassic.capability;

import elucent.rootsclassic.Const;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RootsCapabilityManager {

  @CapabilityInject(IManaCapability.class)
  public static final Capability<IManaCapability> manaCapability = null;

  public static void preInit() {
    CapabilityManager.INSTANCE.register(IManaCapability.class, new ManaCapabilityStorage(), DefaultManaCapability.class);
  }

  @SubscribeEvent
  public void onAddCapabilities(AttachCapabilitiesEvent<EntityPlayer> e) {
    class ManaCapabilityProvider implements ICapabilityProvider, INBTSerializable, IManaCapability {

      private EntityPlayer player;
      float mana;
      float maxMana;

      ManaCapabilityProvider(boolean isNew) {
        if (isNew) {
          mana = 40;
          maxMana = 40;
        }
      }

      @Override
      public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return manaCapability != null && capability == manaCapability;
      }

      @Override
      public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == manaCapability) {
          return manaCapability.cast(this);
        }
        return null;
      }

      @Override
      public float getMana() {
        return mana;
      }

      @Override
      public float getMaxMana() {
        return maxMana;
      }

      @Override
      public void setMana(float mana) {
        this.mana = mana;
        if (mana < 0) {
          this.mana = 0;
        }
        if (mana > getMaxMana()) {
          this.mana = getMaxMana();
        }
      }

      @Override
      public void setMaxMana(float maxMana) {
        this.maxMana = maxMana;
      }

      @Override
      public NBTBase serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setFloat("maxMana", getMaxMana());
        tag.setFloat("mana", getMana());
        return tag;
      }

      @Override
      public void deserializeNBT(NBTBase nbt) {
        if (nbt instanceof NBTTagCompound) {
          NBTTagCompound tag = (NBTTagCompound) nbt;
          //System.out.println("Loading NBT! Mana=" + tag.getFloat("mana") + "/" + tag.getFloat("maxMana"));
          if (tag.hasKey("maxMana")) {
            setMaxMana(tag.getFloat("maxMana"));
          }
          if (tag.hasKey("mana")) {
            setMana(tag.getFloat("mana"));
          }
        }
      }
    }
    ManaCapabilityProvider provider = new ManaCapabilityProvider(!e.getObject().hasCapability(manaCapability, null));
    e.addCapability(new ResourceLocation(Const.MODID, "manacapability"), provider);

  }
}
