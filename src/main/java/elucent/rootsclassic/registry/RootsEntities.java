package elucent.rootsclassic.registry;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.entity.EntityAccelerator;
import elucent.rootsclassic.entity.EntityTileAccelerator;
import elucent.rootsclassic.entity.skeleton.PhantomSkeletonEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class RootsEntities {

  public static final DeferredRegister.Entities ENTITY_TYPES = DeferredRegister.createEntities(Const.MODID);

  public static final Supplier<EntityType<PhantomSkeletonEntity>> PHANTOM_SKELETON = ENTITY_TYPES.registerEntityType("phantom_skeleton",
    PhantomSkeletonEntity::new,
    MobCategory.MONSTER,
    builder -> builder
      .sized(0.6F, 1.99F)
      .ridingOffset(-0.7F)
      .eyeHeight(0.45F)
      .clientTrackingRange(6)
      .notInPeaceful()
      .noLootTable()
  );
  public static final Supplier<EntityType<EntityAccelerator>> ENTITY_ACCELERATOR = ENTITY_TYPES.registerEntityType("entity_accelerator",
    EntityAccelerator::new,
    MobCategory.MISC,
    builder -> builder
      .sized(0.5F, 0.5F)
      .clientTrackingRange(64)
      .updateInterval(20)
      .setShouldReceiveVelocityUpdates(true)
      .noLootTable()
  );
  public static final Supplier<EntityType<EntityTileAccelerator>> TILE_ACCELERATOR = ENTITY_TYPES.registerEntityType("tile_accelerator",
    EntityTileAccelerator::new,
    MobCategory.MISC,
    builder -> builder
      .sized(0.5F, 0.5F)
      .clientTrackingRange(64)
      .updateInterval(20)
      .setShouldReceiveVelocityUpdates(true)
      .noLootTable()
  );

  public static void onSpawnPlacementRegisterEvent(RegisterSpawnPlacementsEvent event) {
    event.register(RootsEntities.PHANTOM_SKELETON.get(), SpawnPlacementTypes.ON_GROUND,
	    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.AND);
  }

  public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
    event.put(RootsEntities.PHANTOM_SKELETON.get(), PhantomSkeletonEntity.registerAttributes().build());
  }
}
