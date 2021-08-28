package elucent.rootsclassic.lootmodifiers;

import com.google.gson.JsonObject;
import java.util.List;
import java.util.Random;
import javax.annotation.Nonnull;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.block.TallGrassBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.config.RootsConfig;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.registry.RootsTags;

public class DropModifier {

  public static final DeferredRegister<GlobalLootModifierSerializer<?>> GLM = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, Const.MODID);
  public static final RegistryObject<BlockDropModifier.Serializer> ROOTSCLASSIC_DROPS = GLM.register("rootsclassic_drops", BlockDropModifier.Serializer::new);

  public static class BlockDropModifier extends LootModifier {

    public BlockDropModifier(ILootCondition[] lootConditions) {
      super(lootConditions);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
      if (context.has(LootParameters.BLOCK_STATE)) {
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

    private static class Serializer extends GlobalLootModifierSerializer<BlockDropModifier> {

      @Override
      public BlockDropModifier read(ResourceLocation location, JsonObject jsonObject, ILootCondition[] lootConditions) {
        return new BlockDropModifier(lootConditions);
      }

      @Override
      public JsonObject write(BlockDropModifier instance) {
        return makeConditions(instance.conditions);
      }
    }
  }
}
