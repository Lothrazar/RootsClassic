package elucent.rootsclassic.registry;

import net.minecraft.world.item.Item;
import net.minecraft.tags.Tag;
import net.minecraft.tags.ItemTags;
import elucent.rootsclassic.Const;

public class RootsTags {
  public static final Tag.Named<Item> BARK = ItemTags.bind(Const.MODID + ":bark");
  public static final Tag.Named<Item> BERRIES = ItemTags.bind(Const.MODID + ":berries");
}
