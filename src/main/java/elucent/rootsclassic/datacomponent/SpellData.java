package elucent.rootsclassic.datacomponent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.ComponentBaseRegistry;
import elucent.rootsclassic.item.StaffItem;
import elucent.rootsclassic.registry.RootsComponents;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public record SpellData(int potency, int efficiency, int size, String effect) implements ItemTintSource {
	public static final SpellData EMPTY = new SpellData(0, 0, 0, "");
	public static final Codec<SpellData> CODEC = RecordCodecBuilder.create(inst -> inst.group(
			ExtraCodecs.NON_NEGATIVE_INT.fieldOf("potency").forGetter(SpellData::potency),
			ExtraCodecs.NON_NEGATIVE_INT.fieldOf("efficiency").forGetter(SpellData::efficiency),
			ExtraCodecs.NON_NEGATIVE_INT.fieldOf("size").forGetter(SpellData::size),
			Codec.STRING.fieldOf("effect").forGetter(SpellData::effect)
		)
		.apply(inst, SpellData::new));
  public static final MapCodec<SpellData> MAP_CODEC = CODEC.fieldOf("spell_data");

	public static final StreamCodec<RegistryFriendlyByteBuf, SpellData> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.INT, SpellData::potency,
		ByteBufCodecs.INT, SpellData::efficiency,
		ByteBufCodecs.INT, SpellData::size,
		ByteBufCodecs.STRING_UTF8, SpellData::effect,
		SpellData::new
	);

  @Override
  public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity owner) {
//    Identifier compName = Identifier.tryParse(effect());
//    if (compName != null) {
//      ComponentBase comp = ComponentBaseRegistry.COMPONENTS.getValue(compName);
//      if (comp != null) {
//        if (tintIndex == 2) {
//          return RootsUtil.intColor((int) comp.primaryColor.x, (int) comp.primaryColor.y, (int) comp.primaryColor.z);
//        }
//        if (tintIndex == 1) {
//          return RootsUtil.intColor((int) comp.secondaryColor.x, (int) comp.secondaryColor.y, (int) comp.secondaryColor.z);
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
