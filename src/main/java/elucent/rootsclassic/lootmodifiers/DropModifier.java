package elucent.rootsclassic.lootmodifiers;

import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.registry.RootsTags;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class DropModifier {

  public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLM = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Const.MODID);
  public static final Supplier<MapCodec<? extends IGlobalLootModifier>> ROOTSCLASSIC_DROPS = GLM.register("rootsclassic_drops", BlockDropModifier.CODEC);

  public static class BlockDropModifier extends LootModifier {

    public static final Supplier<MapCodec<BlockDropModifier>> CODEC = Suppliers.memoize(() ->
	    RecordCodecBuilder.mapCodec(inst -> codecStart(inst).apply(inst, BlockDropModifier::new)));

    public BlockDropModifier(LootItemCondition[] lootConditions) {
      super(lootConditions);
    }

    @Nonnull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
      if (context.hasParam(LootContextParams.BLOCK_STATE)) {
        BlockState state = context.getParamOrNull(LootContextParams.BLOCK_STATE);
        Block block = state.getBlock();
        RandomSource rand = context.getRandom();
        if (block instanceof TallGrassBlock) {
          if (RootsConfig.COMMON.oldRootDropChance.get() > 0 && rand.nextInt(RootsConfig.COMMON.oldRootDropChance.get()) == 0) {
            generatedLoot.add(new ItemStack(RootsRegistry.OLD_ROOT.get(), 1));
          }
        }
        if (block == Blocks.WHEAT || block == Blocks.CARROTS || block == Blocks.POTATOES || block == Blocks.BEETROOTS) {
          if (((CropBlock) block).isMaxAge(state)) {
            if (RootsConfig.COMMON.verdantSprigDropChance.get() > 0 && rand.nextInt(RootsConfig.COMMON.verdantSprigDropChance.get()) == 0) {
              generatedLoot.add(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1));
            }
          }
        }
        if (block == Blocks.NETHER_WART) {
          if (state.getValue(NetherWartBlock.AGE) == 3) {
            if (RootsConfig.COMMON.infernalStemDropChance.get() > 0 && rand.nextInt(RootsConfig.COMMON.infernalStemDropChance.get()) == 0) {
              generatedLoot.add(new ItemStack(RootsRegistry.INFERNAL_BULB.get(), 1));
            }
          }
        }
        if (block == Blocks.CHORUS_FLOWER) {
          if (RootsConfig.COMMON.dragonsEyeDropChance.get() > 0 && rand.nextInt(RootsConfig.COMMON.dragonsEyeDropChance.get()) == 0) {
            generatedLoot.add(new ItemStack(RootsRegistry.DRAGONS_EYE.get(), 1));
          }
        }
        if (block instanceof LeavesBlock) {
          if (generatedLoot.stream().noneMatch((stack) -> stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() == block)) {
            if (RootsConfig.COMMON.berriesDropChance.get() > 0 && rand.nextInt(RootsConfig.COMMON.berriesDropChance.get()) == 0) {
              generatedLoot.add(new ItemStack(BuiltInRegistries.ITEM.getTag(RootsTags.BERRIES)
	              .flatMap(tag -> tag.getRandomElement(rand)).orElse(RootsRegistry.ELDERBERRY)));
            }
          }
        }
      }
      return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
      return ROOTSCLASSIC_DROPS.get();
    }
  }
}
