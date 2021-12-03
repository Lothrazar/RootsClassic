package elucent.rootsclassic.registry;

import elucent.rootsclassic.Const;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;

public class RootsTags {
  public static final Tag.Named<Item> BARK = ItemTags.bind(Const.MODID + ":bark");
  public static final Tag.Named<Item> BERRIES = ItemTags.bind(Const.MODID + ":berries");
}
