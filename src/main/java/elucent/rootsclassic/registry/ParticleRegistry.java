package elucent.rootsclassic.registry;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.client.particles.factory.MagicParticleType;
import elucent.rootsclassic.client.particles.factory.MagicParticleTypeData;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ParticleRegistry {

  public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Const.MODID);
  public static final Supplier<ParticleType<MagicParticleTypeData>> MAGIC_TYPE = PARTICLE_TYPES.register("magic", MagicParticleType::new);
  public static final Supplier<ParticleType<MagicParticleTypeData>> MAGIC_AURA_TYPE = PARTICLE_TYPES.register("magic_aura", MagicParticleType::new);
  public static final Supplier<ParticleType<MagicParticleTypeData>> MAGIC_ALTAR_TYPE = PARTICLE_TYPES.register("magic_altar", MagicParticleType::new);
  public static final Supplier<ParticleType<MagicParticleTypeData>> MAGIC_ALTAR_LINE_TYPE = PARTICLE_TYPES.register("magic_altar_line", MagicParticleType::new);
  public static final Supplier<ParticleType<MagicParticleTypeData>> MAGIC_LINE_TYPE = PARTICLE_TYPES.register("magic_line", MagicParticleType::new);
}
