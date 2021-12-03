package elucent.rootsclassic.lootmodifiers;

import com.google.gson.JsonObject;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.lootmodifiers.DropModifier.BlockDropModifier.Serializer;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.registry.RootsTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
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
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class DropModifier {

  public static final DeferredRegister<GlobalLootModifierSerializer<?>> GLM = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, Const.MODID);
  public static final RegistryObject<Serializer> ROOTSCLASSIC_DROPS = GLM.register("rootsclassic_drops", BlockDropModifier.Serializer::new);

  public static class BlockDropModifier extends LootModifier {

    public BlockDropModifier(LootItemCondition[] lootConditions) {
      super(lootConditions);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
      if (context.hasParam(LootContextParams.BLOCK_STATE)) {
        BlockState state = context.getParamOrNull(LootContextParams.BLOCK_STATE);
        Block block = state.getBlock();
        Random rand = context.getRandom();
        if (block instanceof TallGrassBlock) {
          if (rand.nextInt(RootsConfig.COMMON.oldRootDropChance.get()) == 0) {
            generatedLoot.add(new ItemStack(RootsRegistry.OLD_ROOT.get(), 1));
          }
        }
        if (block == Blocks.WHEAT || block == Blocks.CARROTS || block == Blocks.POTATOES || block == Blocks.BEETROOTS) {
          if (((CropBlock) block).isMaxAge(state)) {
            if (rand.nextInt(RootsConfig.COMMON.verdantSprigDropChance.get()) == 0) {
              generatedLoot.add(new ItemStack(RootsRegistry.VERDANT_SPRIG.get(), 1));
            }
          }
        }
        if (block == Blocks.NETHER_WART) {
          if (state.getValue(NetherWartBlock.AGE) == 3) {
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
        if (block instanceof LeavesBlock) {
          if (!generatedLoot.stream().anyMatch((stack) -> stack.getItem() instanceof BlockItem && ((BlockItem) stack.getItem()).getBlock() == block)) {
            if (rand.nextInt(RootsConfig.COMMON.berriesDropChance.get()) == 0) {
              generatedLoot.add(new ItemStack(RootsTags.BERRIES.getRandomElement(rand)));
            }
          }
        }
      }
      return generatedLoot;
    }

    protected static class Serializer extends GlobalLootModifierSerializer<BlockDropModifier> {

      @Override
      public BlockDropModifier read(ResourceLocation location, JsonObject jsonObject, LootItemCondition[] lootConditions) {
        return new BlockDropModifier(lootConditions);
      }

      @Override
      public JsonObject write(BlockDropModifier instance) {
        return makeConditions(instance.conditions);
      }
    }
  }
}
