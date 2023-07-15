package elucent.rootsclassic.lootmodifiers;

import javax.annotation.Nonnull;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.registry.RootsTags;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
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
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import net.minecraftforge.registries.RegistryObject;

public class DropModifier {

  public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLM = DeferredRegister.create(Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Const.MODID);
  public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ROOTSCLASSIC_DROPS = GLM.register("rootsclassic_drops", BlockDropModifier.CODEC);

  public static class BlockDropModifier extends LootModifier {

    public static final Supplier<Codec<BlockDropModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, BlockDropModifier::new)));

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
          if (RootsConfig.oldRootDropChance.get() > 0 && rand.nextInt(RootsConfig.oldRootDropChance.get()) == 0) {
            generatedLoot.add(new ItemStack(RootsRegistry.OLD_ROOT.get(), 1));
          }
        }
        if (block == Blocks.WHEAT || block == Blocks.CARROTS || block == Blocks.POTATOES || block == Blocks.BEETROOTS) {
          if (((CropBlock) block).isMaxAge(state)) {
            if (RootsConfig.verdantSprigDropChance.get() > 0 && rand.nextInt(RootsConfig.verdantSprigDropChance.get()) == 0) {
              generatedLoot.add(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1));
            }
          }
        }
        if (block == Blocks.NETHER_WART) {
          if (state.getValue(NetherWartBlock.AGE) == 3) {
            if (RootsConfig.infernalStemDropChance.get() > 0 && rand.nextInt(RootsConfig.infernalStemDropChance.get()) == 0) {
              generatedLoot.add(new ItemStack(RootsRegistry.INFERNAL_BULB.get(), 1));
            }
          }
        }
        if (block == Blocks.CHORUS_FLOWER) {
          if (RootsConfig.dragonsEyeDropChance.get() > 0 && rand.nextInt(RootsConfig.dragonsEyeDropChance.get()) == 0) {
            generatedLoot.add(new ItemStack(RootsRegistry.DRAGONS_EYE.get(), 1));
          }
        }
        if (block instanceof LeavesBlock) {
          if (!generatedLoot.stream().anyMatch((stack) -> stack.getItem() instanceof BlockItem && ((BlockItem) stack.getItem()).getBlock() == block)) {
            if (RootsConfig.berriesDropChance.get() > 0 && rand.nextInt(RootsConfig.berriesDropChance.get()) == 0) {
              Item berry = RootsRegistry.ELDERBERRY.get();
              berry = ForgeRegistries.ITEMS.tags().getTag(RootsTags.BERRIES).getRandomElement(rand).orElse(berry);
              generatedLoot.add(new ItemStack(berry));
            }
          }
        }
      }
      return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
      return ROOTSCLASSIC_DROPS.get();
    }
  }
}
