package elucent.rootsclassic.registry;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.entity.EntityAccelerator;
import elucent.rootsclassic.entity.EntityTileAccelerator;
import elucent.rootsclassic.entity.skeleton.PhantomSkeletonEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RootsEntities {

  public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Const.MODID);
  public static final DeferredHolder<EntityType<?>, EntityType<PhantomSkeletonEntity>> PHANTOM_SKELETON = ENTITY_TYPES.register("phantom_skeleton", () -> register("phantom_skeleton", EntityType.Builder.<PhantomSkeletonEntity> of(PhantomSkeletonEntity::new, MobCategory.MONSTER)
      .sized(0.6F, 1.99F).clientTrackingRange(6)));
  public static final DeferredHolder<EntityType<?>, EntityType<EntityAccelerator>> ENTITY_ACCELERATOR = ENTITY_TYPES.register("entity_accelerator", () -> register("entity_accelerator", EntityType.Builder.<EntityAccelerator> of(EntityAccelerator::new, MobCategory.MISC)
      .sized(0.5F, 0.5F).clientTrackingRange(64).updateInterval(20).setShouldReceiveVelocityUpdates(true)));
  public static final DeferredHolder<EntityType<?>, EntityType<EntityTileAccelerator>> TILE_ACCELERATOR = ENTITY_TYPES.register("tile_accelerator", () -> register("tile_accelerator", EntityType.Builder.<EntityTileAccelerator> of(EntityTileAccelerator::new, MobCategory.MISC)
      .sized(0.5F, 0.5F).clientTrackingRange(64).updateInterval(20).setShouldReceiveVelocityUpdates(true)));

  @SubscribeEvent
  public static void onSpawnPlacementRegisterEvent(RegisterSpawnPlacementsEvent event) {
    event.register(RootsEntities.PHANTOM_SKELETON.get(), SpawnPlacementTypes.ON_GROUND,
	    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
  }

  public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
    event.put(RootsEntities.PHANTOM_SKELETON.get(), PhantomSkeletonEntity.registerAttributes().build());
  }

  public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
    return builder.build(id);
  }
}
