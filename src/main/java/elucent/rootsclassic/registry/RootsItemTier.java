package elucent.rootsclassic.registry;

import java.util.List;
import elucent.rootsclassic.Const;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

public class RootsItemTier {

  public static final Tier LIVING = TierSortingRegistry.registerTier(
      new ForgeTier(2, 192, 6.0f, 2.0f, 18,
          RootsTags.NEEDS_LIVING_TOOL,
          () -> null),
      new ResourceLocation(Const.MODID, "living"), List.of(Tiers.IRON), List.of(Tiers.DIAMOND));
  public static final Tier ENGRAVED = TierSortingRegistry.registerTier(
      new ForgeTier(2, 1050, 5F, 8.0F, 5,
          RootsTags.NEEDS_ENGRAVED_TOOL,
          () -> null),
      new ResourceLocation(Const.MODID, "engraved"), List.of(Tiers.IRON), List.of(Tiers.DIAMOND));
}