package elucent.rootsclassic.registry;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.client.particles.factory.MagicParticleType;
import elucent.rootsclassic.client.particles.factory.MagicParticleTypeData;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Const.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleRegistry {

  public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Const.MODID);
  public static final RegistryObject<ParticleType<MagicParticleTypeData>> MAGIC_TYPE = PARTICLE_TYPES.register("magic", MagicParticleType::new);
  public static final RegistryObject<ParticleType<MagicParticleTypeData>> MAGIC_AURA_TYPE = PARTICLE_TYPES.register("magic_aura", MagicParticleType::new);
  public static final RegistryObject<ParticleType<MagicParticleTypeData>> MAGIC_ALTAR_TYPE = PARTICLE_TYPES.register("magic_altar", MagicParticleType::new);
  public static final RegistryObject<ParticleType<MagicParticleTypeData>> MAGIC_ALTAR_LINE_TYPE = PARTICLE_TYPES.register("magic_altar_line", MagicParticleType::new);
  public static final RegistryObject<ParticleType<MagicParticleTypeData>> MAGIC_LINE_TYPE = PARTICLE_TYPES.register("magic_line", MagicParticleType::new);
}
