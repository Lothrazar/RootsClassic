package elucent.rootsclassic.registry;

import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import elucent.rootsclassic.Const;

public class RootsTags {
	public static final ITag.INamedTag<Item> BARK = ItemTags.makeWrapperTag(Const.MODID + ":bark");
	public static final ITag.INamedTag<Item> BERRIES = ItemTags.makeWrapperTag(Const.MODID + ":berries");
}
