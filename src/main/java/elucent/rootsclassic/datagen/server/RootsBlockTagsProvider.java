package elucent.rootsclassic.datagen.server;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.block.AcceleratorStandingStoneBlock;
import elucent.rootsclassic.block.AestheticStandingStoneBlock;
import elucent.rootsclassic.block.AttunedStandingStoneBlock;
import elucent.rootsclassic.block.EntanglerStandingStoneBlock;
import elucent.rootsclassic.block.GrowerStandingStoneBlock;
import elucent.rootsclassic.block.IgniterStandingStoneBlock;
import elucent.rootsclassic.block.MundaneStandingStoneBlock;
import elucent.rootsclassic.block.RepulsorStandingStoneBlock;
import elucent.rootsclassic.block.VacuumStandingStoneBlock;
import elucent.rootsclassic.blockentity.AcceleratorStandingStoneTile;
import elucent.rootsclassic.blockentity.AestheticStandingStoneTile;
import elucent.rootsclassic.blockentity.EntanglerStandingStoneTile;
import elucent.rootsclassic.blockentity.GrowerStandingStoneTile;
import elucent.rootsclassic.blockentity.IgniterStandingStoneTile;
import elucent.rootsclassic.blockentity.RepulsorStandingStoneTile;
import elucent.rootsclassic.blockentity.VacuumStandingStoneTile;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.registry.RootsTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class RootsBlockTagsProvider extends BlockTagsProvider {

	public RootsBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, Const.MODID, existingFileHelper);
	}

	@Override
	public void addTags(HolderLookup.Provider lookupProvider) {
		this.tag(RootsTags.INCORRECT_FOR_LIVING_TOOL).addTag(BlockTags.INCORRECT_FOR_IRON_TOOL);
		this.tag(RootsTags.INCORRECT_FOR_ENGRAVED_TOOL).addTag(BlockTags.INCORRECT_FOR_IRON_TOOL);

		this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
			RootsRegistry.MUNDANE_STANDING_STONE.get(),
			RootsRegistry.ATTUNED_STANDING_STONE.get(),
			RootsRegistry.VACUUM_STANDING_STONE.get(),
			RootsRegistry.REPULSOR_STANDING_STONE.get(),
			RootsRegistry.ACCELERATOR_STANDING_STONE.get(),
			RootsRegistry.AESTHETIC_STANDING_STONE.get(),
			RootsRegistry.ENTANGLER_STANDING_STONE.get(),
			RootsRegistry.IGNITER_STANDING_STONE.get(),
			RootsRegistry.GROWER_STANDING_STONE.get(),
			RootsRegistry.ALTAR.get(),
			RootsRegistry.MORTAR.get()
		);
	}
}
