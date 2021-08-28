package elucent.rootsclassic.registry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.entity.EntityAccelerator;
import elucent.rootsclassic.entity.EntityTileAccelerator;
import elucent.rootsclassic.entity.skeleton.PhantomSkeletonEntity;

public class RootsEntities {

  public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Const.MODID);
  public static final RegistryObject<EntityType<PhantomSkeletonEntity>> PHANTOM_SKELETON = ENTITIES.register("phantom_skeleton", () -> register("phantom_skeleton", EntityType.Builder.<PhantomSkeletonEntity> create(PhantomSkeletonEntity::new, EntityClassification.MONSTER)
      .size(0.6F, 1.99F).trackingRange(6)));
  public static final RegistryObject<EntityType<EntityAccelerator>> ENTITY_ACCELERATOR = ENTITIES.register("entity_accelerator", () -> register("entity_accelerator", EntityType.Builder.<EntityAccelerator> create(EntityAccelerator::new, EntityClassification.MISC)
      .size(0.5F, 0.5F).trackingRange(64).updateInterval(20).setShouldReceiveVelocityUpdates(true)));
  public static final RegistryObject<EntityType<EntityTileAccelerator>> TILE_ACCELERATOR = ENTITIES.register("tile_accelerator", () -> register("tile_accelerator", EntityType.Builder.<EntityTileAccelerator> create(EntityTileAccelerator::new, EntityClassification.MISC)
      .size(0.5F, 0.5F).trackingRange(64).updateInterval(20).setShouldReceiveVelocityUpdates(true)));

  public static void registerSpawnPlacement() {
    EntitySpawnPlacementRegistry.register(RootsEntities.PHANTOM_SKELETON.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);
  }

  public static void addSpawns(BiomeLoadingEvent event) {}

  public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
    event.put(RootsEntities.PHANTOM_SKELETON.get(), PhantomSkeletonEntity.registerAttributes().create());
  }

  public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
    return builder.build(id);
  }
}
