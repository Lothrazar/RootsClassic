package elucent.roots.capability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RootsCapabilityManager {
    @CapabilityInject(IManaCapability.class)
    public static final Capability<IManaCapability> manaCapability = null;
    
    public static void preInit(){
    	CapabilityManager.INSTANCE.register(IManaCapability.class, new ManaCapabilityStorage(), DefaultManaCapability.class);
    }
	
	@SubscribeEvent
	public void onAddCapabilities(AttachCapabilitiesEvent.Entity e){
		class ManaCapabilityProvider implements ICapabilityProvider, IManaCapability {
            private EntityPlayer player;
            
            float mana = 40;
            float maxMana = 40;

            ManaCapabilityProvider(EntityPlayer player){
                this.player = player;
            }
            
            @Override
            public boolean hasCapability(Capability<?> capability, EnumFacing facing){
                return manaCapability != null && capability == manaCapability;
            }
            
            @Override
            public <T> T getCapability(Capability<T> capability, EnumFacing facing){
                return manaCapability.cast(this);
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
				if (mana < 0){
					this.mana = 0;
				}
				if (mana > getMaxMana()){
					this.mana = getMaxMana();
				}
			}

			@Override
			public void setMaxMana(float maxMana) {
				this.maxMana = maxMana;
			}
        }
		
		if (e.getEntity() instanceof EntityPlayer){
			ManaCapabilityProvider provider = new ManaCapabilityProvider((EntityPlayer)e.getEntity());
			provider.setMana(40.0f);
			provider.setMaxMana(40.0f);
			e.addCapability(new ResourceLocation("roots:manaCapability"), provider);
		}
	}
}
