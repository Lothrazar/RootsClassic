package elucent.rootsclassic.datacomponent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public record SpellDataList(NonNullList<SpellData> spellList) implements ItemTintSource {
	public static final SpellDataList EMPTY = new SpellDataList(NonNullList.withSize(4, SpellData.EMPTY));
	public static final Codec<SpellDataList> CODEC = RecordCodecBuilder.create(inst -> inst.group(
			SpellData.CODEC.listOf().fieldOf("spellList")
				.flatXmap(
					strings -> {
						SpellData[] ingredients = strings.toArray(SpellData[]::new);
						if (ingredients.length == 0) {
							return DataResult.error(() -> "No spell data");
						} else {
							return ingredients.length > 4
								? DataResult.error(() -> "Too much spell data. The maximum amount of spells is 4")
								: DataResult.success(NonNullList.of(SpellData.EMPTY, ingredients));
						}
					},
					DataResult::success
				).forGetter(SpellDataList::spellList)
		)
		.apply(inst, SpellDataList::new));
  public static final MapCodec<SpellDataList> MAP_CODEC = CODEC.fieldOf("spell_data_list");

	public static final StreamCodec<RegistryFriendlyByteBuf, SpellDataList> STREAM_CODEC = StreamCodec.of(
		SpellDataList::toNetwork, SpellDataList::fromNetwork
	);

	private static SpellDataList fromNetwork(RegistryFriendlyByteBuf byteBuf) {
		int i = byteBuf.readVarInt();
		NonNullList<SpellData> spellList = NonNullList.withSize(i, SpellData.EMPTY);
		spellList.replaceAll(data -> SpellData.STREAM_CODEC.decode(byteBuf));

		return new SpellDataList(spellList);
	}

	private static void toNetwork(RegistryFriendlyByteBuf byteBuf, SpellDataList data) {
		byteBuf.writeVarInt(data.spellList().size());
		for (int i = 0; i < data.spellList().size(); i++) {
			SpellData.STREAM_CODEC.encode(byteBuf, data.spellList().get(i));
		}
	}

  @Override
  public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity owner) {
//    SpellData selectedSpell = CrystalStaffItem.getSelectedSpell(stack);
//    String effect = selectedSpell.effect();
//    if (effect != null) {
//      Identifier compName = Identifier.tryParse(effect);
//      if (compName != null) {
//        ComponentBase comp = ComponentBaseRegistry.COMPONENTS.getValue(compName);
//        if (comp != null) {
//          if (tintIndex == 2) {
//            return RootsUtil.intColor((int) comp.primaryColor.x, (int) comp.primaryColor.y, (int) comp.primaryColor.z);
//          }
//          if (tintIndex == 1) {
//            return RootsUtil.intColor((int) comp.secondaryColor.x, (int) comp.secondaryColor.y, (int) comp.secondaryColor.z);
//          }
//        }
//      }
//    }
    return RootsUtil.intColor(255, 255, 255);
  }

  @Override
  public MapCodec<? extends ItemTintSource> type() {
    return MAP_CODEC;
  }
}
