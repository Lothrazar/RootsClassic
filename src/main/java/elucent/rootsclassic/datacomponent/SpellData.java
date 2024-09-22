package elucent.rootsclassic.datacomponent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;

public record SpellData(int potency, int efficiency, int size, String effect) {
	public static final SpellData EMPTY = new SpellData(0, 0, 0, "");
	public static final Codec<SpellData> CODEC = RecordCodecBuilder.create(inst -> inst.group(
			ExtraCodecs.NON_NEGATIVE_INT.fieldOf("potency").forGetter(SpellData::potency),
			ExtraCodecs.NON_NEGATIVE_INT.fieldOf("efficiency").forGetter(SpellData::efficiency),
			ExtraCodecs.NON_NEGATIVE_INT.fieldOf("size").forGetter(SpellData::size),
			Codec.STRING.fieldOf("effect").forGetter(SpellData::effect)
		)
		.apply(inst, SpellData::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, SpellData> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.INT, SpellData::potency,
		ByteBufCodecs.INT, SpellData::efficiency,
		ByteBufCodecs.INT, SpellData::size,
		ByteBufCodecs.STRING_UTF8, SpellData::effect,
		SpellData::new
	);
}
