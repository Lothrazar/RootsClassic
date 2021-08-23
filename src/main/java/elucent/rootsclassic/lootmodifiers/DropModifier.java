package elucent.rootsclassic.lootmodifiers;

import com.google.gson.JsonObject;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.registry.RootsTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.block.TallGrassBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

@EventBusSubscriber(modid = Const.MODID, bus = Bus.MOD)
public class DropModifier {
	@SubscribeEvent
	public static void registerModifiers(RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
		event.getRegistry().register(
				new LeavesDropSerializer().setRegistryName(Const.MODID, "rootsclassic_drops")
		);
	}

	public static class LeavesDropSerializer extends GlobalLootModifierSerializer<LeavesDropModifier> {

		@Override
		public LeavesDropModifier read(ResourceLocation location, JsonObject jsonObject, ILootCondition[] lootConditions) {
			return new LeavesDropModifier(lootConditions);
		}

		@Override
		public JsonObject write(LeavesDropModifier instance) {
			return new JsonObject();
		}
	}

	private static class LeavesDropModifier extends LootModifier {
		protected LeavesDropModifier(ILootCondition[] lootConditions) {
			super(lootConditions);
		}

		@Nonnull
		@Override
		protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
			if(context.has(LootParameters.BLOCK_STATE)) {
				BlockState state = context.get(LootParameters.BLOCK_STATE);
				Block block = state.getBlock();
				Random rand = context.getRandom();

				if (block instanceof TallGrassBlock) {
					if (rand.nextInt(RootsConfig.COMMON.oldRootDropChance.get()) == 0) {
						generatedLoot.add(new ItemStack(RootsRegistry.OLD_ROOT.get(), 1));
					}
				}
				if (block == Blocks.WHEAT || block == Blocks.CARROTS || block == Blocks.POTATOES || block == Blocks.BEETROOTS) {
					if (((CropsBlock) block).isMaxAge(state)) {
						if (rand.nextInt(RootsConfig.COMMON.verdantSprigDropChance.get()) == 0) {
							generatedLoot.add(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1));
						}
					}
				}
				if (block == Blocks.NETHER_WART) {
					if (state.get(NetherWartBlock.AGE) == 3) {
						if (rand.nextInt(RootsConfig.COMMON.infernalStemDropChance.get()) == 0) {
							generatedLoot.add(new ItemStack(RootsRegistry.INFERNAL_BULB.get(), 1));
						}
					}
				}
				if (block == Blocks.CHORUS_FLOWER) {
					if (rand.nextInt(RootsConfig.COMMON.dragonsEyeDropChance.get()) == 0) {
						generatedLoot.add(new ItemStack(RootsRegistry.DRAGONS_EYE.get(), 1));
					}
				}
				if(block instanceof LeavesBlock) {
					if (rand.nextInt(RootsConfig.COMMON.berriesDropChance.get()) == 0) {
						generatedLoot.add(new ItemStack(RootsTags.BERRIES.getRandomElement(rand)));
					}
				}
			}

			return generatedLoot;
		}
	}
}
