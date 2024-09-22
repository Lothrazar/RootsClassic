package elucent.rootsclassic.registry;

import elucent.rootsclassic.Const;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class RootsTags {

  public static final TagKey<Block> INCORRECT_FOR_LIVING_TOOL = BlockTags.create(Const.modLoc("incorrect_for_living_tool"));
  public static final TagKey<Block> INCORRECT_FOR_ENGRAVED_TOOL = BlockTags.create(Const.modLoc("incorrect_for_engraved_tool"));
  public static final TagKey<Item> BARKS = ItemTags.create(Const.modLoc("barks"));
  public static final TagKey<Item> BERRIES = ItemTags.create(Const.modLoc("berries"));
}
