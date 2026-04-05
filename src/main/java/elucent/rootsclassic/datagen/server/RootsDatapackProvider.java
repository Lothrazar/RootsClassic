package elucent.rootsclassic.datagen.server;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.registry.RootsDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class RootsDatapackProvider extends DatapackBuiltinEntriesProvider {
  public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
    .add(Registries.DAMAGE_TYPE, context -> {
      context.register(RootsDamageTypes.GENERIC, new DamageType(Const.MODID + ".generic", 0.0F));
      context.register(RootsDamageTypes.FIRE, new DamageType(Const.MODID + ".fire", 0.1F, DamageEffects.BURNING));
      context.register(RootsDamageTypes.WITHER, new DamageType(Const.MODID + ".wither", 0.0F));
      context.register(RootsDamageTypes.CACTUS, new DamageType(Const.MODID + ".cactus", 0.1F));
    });

  public RootsDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, Set<String> modIds) {
    super(output, registries, BUILDER, modIds);
  }
}
