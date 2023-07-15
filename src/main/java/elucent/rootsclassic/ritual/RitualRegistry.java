package elucent.rootsclassic.ritual;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.block.altar.AltarBlockEntity;
import elucent.rootsclassic.block.brazier.BrazierBlockEntity;
import elucent.rootsclassic.recipe.RitualRecipe;
import elucent.rootsclassic.registry.RootsRecipes;
import elucent.rootsclassic.registry.RootsRegistry;
import elucent.rootsclassic.ritual.rituals.RitualBanishRain;
import elucent.rootsclassic.ritual.rituals.RitualCauseRain;
import elucent.rootsclassic.ritual.rituals.RitualCrafting;
import elucent.rootsclassic.ritual.rituals.RitualEngravedSword;
import elucent.rootsclassic.ritual.rituals.RitualFlare;
import elucent.rootsclassic.ritual.rituals.RitualGrow;
import elucent.rootsclassic.ritual.rituals.RitualImbuer;
import elucent.rootsclassic.ritual.rituals.RitualLifeDrain;
import elucent.rootsclassic.ritual.rituals.RitualMassBreed;
import elucent.rootsclassic.ritual.rituals.RitualSacrifice;
import elucent.rootsclassic.ritual.rituals.RitualSummoning;
import elucent.rootsclassic.ritual.rituals.RitualTimeShift;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("rawtypes")
public class RitualRegistry {

  public static final DeferredRegister<RitualEffect> RITUALS = DeferredRegister.create(RitualBaseRegistry.registryLocation, Const.MODID);
  public static final RegistryObject<RitualEffect> CRAFTING = RITUALS.register("crafting", RitualCrafting::new);
  public static final RegistryObject<RitualEffect> CAUSE_RAIN = RITUALS.register("cause_rain", RitualCauseRain::new);
  public static final RegistryObject<RitualEffect> BANISH_RAIN = RITUALS.register("banish_rain", RitualBanishRain::new);
  public static final RegistryObject<RitualEffect> MASS_BREEDING = RITUALS.register("mass_breeding", RitualMassBreed::new);
  public static final RegistryObject<RitualEffect> LIFE_DRAIN = RITUALS.register("life_drain", RitualLifeDrain::new);
  public static final RegistryObject<RitualEffect> IMBUER = RITUALS.register("imbuer", RitualImbuer::new);
  public static final RegistryObject<RitualEffect> SUMMONING = RITUALS.register("summoning", RitualSummoning::new);
  public static final RegistryObject<RitualEffect> SACRIFICE = RITUALS.register("sacrifice", RitualSacrifice::new);
  public static final RegistryObject<RitualEffect> FLARE = RITUALS.register("flare", RitualFlare::new);
  public static final RegistryObject<RitualEffect> GROW = RITUALS.register("grow", RitualGrow::new);
  public static final RegistryObject<RitualEffect> ENGRAVED_CRAFTING = RITUALS.register("engraved_crafting", RitualEngravedSword::new);
  public static final RegistryObject<RitualEffect> TIME_SHIFT = RITUALS.register("time_shift", RitualTimeShift::new);

  public static Optional<RitualRecipe<?>> findMatchingByIngredients(AltarBlockEntity altar) {
    List<ItemStack> altarInv = new ArrayList<>();
    for (int i = 0; i < altar.inventory.getSlots(); i++) {
      var stack = altar.inventory.getStackInSlot(i);
      if (!stack.isEmpty()) altarInv.add(stack);
    }
    return altar.getLevel().getRecipeManager().getAllRecipesFor(RootsRecipes.RITUAL_RECIPE_TYPE.get()).stream()
        .filter(it -> RootsUtil.matchesIngredients(altarInv, it.getIngredients()))
        .findFirst();
  }

  public static List<ItemStack> getIncenses(Level level, BlockPos pos) {
    ArrayList<ItemStack> test = new ArrayList<>();
    for (int i = -4; i < 5; i++) {
      for (int j = -4; j < 5; j++) {
        if (level.getBlockState(pos.offset(i, 0, j)).getBlock() == RootsRegistry.BRAZIER.get()) {
          if (level.getBlockEntity(pos.offset(i, 0, j)) instanceof BrazierBlockEntity brazierBlockEntity) {
            if (brazierBlockEntity.isBurning()) {
              test.add(brazierBlockEntity.getHeldItem());
            }
          }
        }
      }
    }
    return test;
  }

  public static Optional<RitualRecipe<?>> recipeByName(RecipeManager recipeManager, ResourceLocation id) {
    return recipeManager.getAllRecipesFor(RootsRecipes.RITUAL_RECIPE_TYPE.get()).stream()
        .filter(it -> it.getId().equals(id))
        .findFirst();
  }
}
