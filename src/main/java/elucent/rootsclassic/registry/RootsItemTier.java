package elucent.rootsclassic.registry;

import elucent.rootsclassic.Const;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class RootsItemTier {
  public static final Tier LIVING = TierSortingRegistry.registerTier(
          new ForgeTier(2, 192, 6.0f, 2.0f, 18,
                  BlockTags.createOptional(new ResourceLocation(Const.MODID, "needs_living_tool")),
                  () -> null),
          new ResourceLocation(Const.MODID, "living"), List.of(Tiers.IRON), List.of(Tiers.DIAMOND));
  public static final Tier ENGRAVED = TierSortingRegistry.registerTier(
          new ForgeTier(2, 1050, 5F, 8.0F, 5,
                  BlockTags.createOptional(new ResourceLocation(Const.MODID, "needs_engraved_tool")),
                  () -> null),
          new ResourceLocation(Const.MODID, "engraved"), List.of(Tiers.IRON), List.of(Tiers.DIAMOND));
}