package elucent.rootsclassic.datacomponent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record SpellDataList(NonNullList<SpellData> spellList) {
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
}
