package elucent.rootsclassic.registry;

import net.minecraft.client.Minecraft;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.client.particles.MagicAltarLineParticleData;
import elucent.rootsclassic.client.particles.MagicAltarParticleData;
import elucent.rootsclassic.client.particles.MagicAuraParticleData;
import elucent.rootsclassic.client.particles.MagicLineParticleData;
import elucent.rootsclassic.client.particles.MagicParticleData;
import elucent.rootsclassic.client.particles.factory.MagicParticleType;
import elucent.rootsclassic.client.particles.factory.MagicParticleTypeData;

@Mod.EventBusSubscriber(modid = Const.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleRegistry {

  public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Const.MODID);
  public static final RegistryObject<ParticleType<MagicParticleTypeData>> MAGIC_TYPE = PARTICLE_TYPES.register("magic", MagicParticleType::new);
  public static final RegistryObject<ParticleType<MagicParticleTypeData>> MAGIC_AURA_TYPE = PARTICLE_TYPES.register("magic_aura", MagicParticleType::new);
  public static final RegistryObject<ParticleType<MagicParticleTypeData>> MAGIC_ALTAR_TYPE = PARTICLE_TYPES.register("magic_altar", MagicParticleType::new);
  public static final RegistryObject<ParticleType<MagicParticleTypeData>> MAGIC_ALTAR_LINE_TYPE = PARTICLE_TYPES.register("magic_altar_line", MagicParticleType::new);
  public static final RegistryObject<ParticleType<MagicParticleTypeData>> MAGIC_LINE_TYPE = PARTICLE_TYPES.register("magic_line", MagicParticleType::new);

  @SubscribeEvent
  public static void registerFactories(ParticleFactoryRegisterEvent event) {
    Minecraft.getInstance().particleEngine.register(MAGIC_TYPE.get(), MagicParticleData::new);
    Minecraft.getInstance().particleEngine.register(MAGIC_AURA_TYPE.get(), MagicAuraParticleData::new);
    Minecraft.getInstance().particleEngine.register(MAGIC_ALTAR_TYPE.get(), MagicAltarParticleData::new);
    Minecraft.getInstance().particleEngine.register(MAGIC_ALTAR_LINE_TYPE.get(), MagicAltarLineParticleData::new);
    Minecraft.getInstance().particleEngine.register(MAGIC_LINE_TYPE.get(), MagicLineParticleData::new);
  }
}
