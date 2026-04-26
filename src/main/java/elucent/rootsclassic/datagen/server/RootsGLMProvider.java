package elucent.rootsclassic.datagen.server;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.lootmodifiers.DropModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.CanItemPerformAbility;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;

import java.util.concurrent.CompletableFuture;

public class RootsGLMProvider extends GlobalLootModifierProvider {

	public RootsGLMProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(packOutput, lookupProvider, Const.MODID);
	}

	@Override
	protected void start() {
		this.add("rootsclassic_drops", new DropModifier.BlockDropModifier(
			new LootItemCondition[]{
				InvertedLootItemCondition.invert(
          CanItemPerformAbility.canItemPerformAbility(ItemAbilities.SHEARS_DIG)
        ).build()
			}, IGlobalLootModifier.DEFAULT_PRIORITY));
	}
}
