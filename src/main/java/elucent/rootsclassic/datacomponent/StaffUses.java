package elucent.rootsclassic.datacomponent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;

public record StaffUses(int uses, int maxUses) {
	public static final Codec<StaffUses> CODEC = RecordCodecBuilder.create(inst -> inst.group(
			ExtraCodecs.NON_NEGATIVE_INT.fieldOf("uses").forGetter(StaffUses::uses),
			ExtraCodecs.NON_NEGATIVE_INT.fieldOf("maxUses").forGetter(StaffUses::maxUses))
		.apply(inst, StaffUses::new));

	public static final StreamCodec<RegistryFriendlyByteBuf, StaffUses> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.INT, StaffUses::uses,
		ByteBufCodecs.INT, StaffUses::maxUses,
		StaffUses::new
	);
}
