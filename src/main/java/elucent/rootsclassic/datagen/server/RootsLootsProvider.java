package elucent.rootsclassic.datagen.server;

import elucent.rootsclassic.block.AttunedStandingStoneBlock;
import elucent.rootsclassic.registry.RootsEntities;
import elucent.rootsclassic.registry.RootsRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class RootsLootsProvider extends LootTableProvider {

	public RootsLootsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(packOutput, Set.of(), List.of(
			new SubProviderEntry(RootsBlockLoot::new, LootContextParamSets.BLOCK),
			new SubProviderEntry(RootsEntityLoot::new, LootContextParamSets.ENTITY)
		), lookupProvider);
	}

	private static class RootsBlockLoot extends BlockLootSubProvider {

		protected RootsBlockLoot(HolderLookup.Provider provider) {
			super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
		}

		@Override
		public void generate() {
			this.dropSelf(RootsRegistry.MORTAR.get());
			this.dropSelf(RootsRegistry.ALTAR.get());
			this.dropSelf(RootsRegistry.BRAZIER.get());
			this.dropSelf(RootsRegistry.IMBUER.get());
			this.dropSelf(RootsRegistry.MUNDANE_STANDING_STONE.get());
			this.dropSelf(RootsRegistry.MIDNIGHT_BLOOM.get());
			this.dropSelf(RootsRegistry.FLARE_ORCHID.get());
			this.dropSelf(RootsRegistry.RADIANT_DAISY.get());
			this.add(RootsRegistry.ATTUNED_STANDING_STONE.get(), this::registerStandingStone);
			this.add(RootsRegistry.VACUUM_STANDING_STONE.get(), this::registerStandingStone);
			this.add(RootsRegistry.REPULSOR_STANDING_STONE.get(), this::registerStandingStone);
			this.add(RootsRegistry.ACCELERATOR_STANDING_STONE.get(), this::registerStandingStone);
			this.add(RootsRegistry.AESTHETIC_STANDING_STONE.get(), this::registerStandingStone);
			this.add(RootsRegistry.ENTANGLER_STANDING_STONE.get(), this::registerStandingStone);
			this.add(RootsRegistry.IGNITER_STANDING_STONE.get(), this::registerStandingStone);
			this.add(RootsRegistry.GROWER_STANDING_STONE.get(), this::registerStandingStone);
			this.add(RootsRegistry.HEALER_STANDING_STONE.get(), this::registerStandingStone);
		}

		public LootTable.Builder registerStandingStone(Block standingStone) {
			return createSinglePropConditionTable(standingStone, AttunedStandingStoneBlock.HALF, DoubleBlockHalf.LOWER);
		}

		@Override
		protected Iterable<Block> getKnownBlocks() {
			return RootsRegistry.BLOCKS.getEntries().stream().map(holder -> (Block)holder.value())::iterator;
		}
	}

	private static class RootsEntityLoot extends EntityLootSubProvider {

		protected RootsEntityLoot(HolderLookup.Provider provider) {
			super(FeatureFlags.REGISTRY.allFlags(), provider);
		}

		@Override
		public void generate() {
			this.add(RootsEntities.PHANTOM_SKELETON.get(), LootTable.lootTable());
		}

		@Override
		protected boolean canHaveLootTable(EntityType<?> entityType) {
			return entityType.getCategory() != MobCategory.MISC;
		}

		@Override
		protected Stream<EntityType<?>> getKnownEntityTypes() {
			return RootsEntities.ENTITY_TYPES.getEntries().stream().map(DeferredHolder::get);
		}
	}

	@Override
	protected void validate(WritableRegistry<LootTable> writableregistry, ValidationContext validationcontext, ProblemReporter.Collector problemreporter$collector) {
		super.validate(writableregistry, validationcontext, problemreporter$collector);
	}
}
