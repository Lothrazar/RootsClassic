package elucent.rootsclassic.registry;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.entity.EntityAccelerator;
import elucent.rootsclassic.entity.EntityTileAccelerator;
import elucent.rootsclassic.entity.skeleton.PhantomSkeletonEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent.Operation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RootsEntities {

  public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Const.MODID);
  public static final RegistryObject<EntityType<PhantomSkeletonEntity>> PHANTOM_SKELETON = ENTITY_TYPES.register("phantom_skeleton", () -> register("phantom_skeleton", EntityType.Builder.<PhantomSkeletonEntity> of(PhantomSkeletonEntity::new, MobCategory.MONSTER)
      .sized(0.6F, 1.99F).clientTrackingRange(6)));
  public static final RegistryObject<EntityType<EntityAccelerator>> ENTITY_ACCELERATOR = ENTITY_TYPES.register("entity_accelerator", () -> register("entity_accelerator", EntityType.Builder.<EntityAccelerator> of(EntityAccelerator::new, MobCategory.MISC)
      .sized(0.5F, 0.5F).clientTrackingRange(64).updateInterval(20).setShouldReceiveVelocityUpdates(true)));
  public static final RegistryObject<EntityType<EntityTileAccelerator>> TILE_ACCELERATOR = ENTITY_TYPES.register("tile_accelerator", () -> register("tile_accelerator", EntityType.Builder.<EntityTileAccelerator> of(EntityTileAccelerator::new, MobCategory.MISC)
      .sized(0.5F, 0.5F).clientTrackingRange(64).updateInterval(20).setShouldReceiveVelocityUpdates(true)));

  @SubscribeEvent
  public static void onSpawnPlacementRegisterEvent(SpawnPlacementRegisterEvent event) {
    event.register(RootsEntities.PHANTOM_SKELETON.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, Operation.AND);
  }

  public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
    event.put(RootsEntities.PHANTOM_SKELETON.get(), PhantomSkeletonEntity.registerAttributes().build());
  }

  public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
    return builder.build(id);
  }
}
