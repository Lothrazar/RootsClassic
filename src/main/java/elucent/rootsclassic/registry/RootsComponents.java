package elucent.rootsclassic.registry;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.datacomponent.SpellData;
import elucent.rootsclassic.datacomponent.SpellDataList;
import elucent.rootsclassic.datacomponent.StaffUses;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class RootsComponents {
	public static final DeferredRegister<DataComponentType<?>> COMPONENT_TYPE = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Const.MODID);

	public static final Supplier<DataComponentType<StaffUses>> STAFF_USES = COMPONENT_TYPE.register("staff_uses", () ->
		DataComponentType.<StaffUses>builder()
			.persistent(StaffUses.CODEC)
			.networkSynchronized(StaffUses.STREAM_CODEC)
			.build());


	public static final Supplier<DataComponentType<Integer>> SELECTED_SPELL = COMPONENT_TYPE.register("selected", () ->
		DataComponentType.<Integer>builder()
			.persistent(ExtraCodecs.NON_NEGATIVE_INT)
			.networkSynchronized(ByteBufCodecs.INT)
			.build());

	public static final Supplier<DataComponentType<SpellData>> SPELL = COMPONENT_TYPE.register("spell", () ->
		DataComponentType.<SpellData>builder()
			.persistent(SpellData.CODEC)
			.networkSynchronized(SpellData.STREAM_CODEC)
			.build());

	public static final Supplier<DataComponentType<SpellDataList>> SPELLS = COMPONENT_TYPE.register("spells", () ->
		DataComponentType.<SpellDataList>builder()
			.persistent(SpellDataList.CODEC)
			.networkSynchronized(SpellDataList.STREAM_CODEC)
			.build());

	public static final Supplier<DataComponentType<Integer>> SPIKES = COMPONENT_TYPE.register("spikes", () ->
		DataComponentType.<Integer>builder()
			.persistent(ExtraCodecs.NON_NEGATIVE_INT)
			.networkSynchronized(ByteBufCodecs.VAR_INT)
			.build());

	public static final Supplier<DataComponentType<Integer>> FORCEFUL = COMPONENT_TYPE.register("forceful", () ->
		DataComponentType.<Integer>builder()
			.persistent(ExtraCodecs.NON_NEGATIVE_INT)
			.networkSynchronized(ByteBufCodecs.VAR_INT)
			.build());

	public static final Supplier<DataComponentType<Integer>> HOLY = COMPONENT_TYPE.register("holy", () ->
		DataComponentType.<Integer>builder()
			.persistent(ExtraCodecs.NON_NEGATIVE_INT)
			.networkSynchronized(ByteBufCodecs.VAR_INT)
			.build());

	public static final Supplier<DataComponentType<Integer>> AQUATIC = COMPONENT_TYPE.register("aquatic", () ->
		DataComponentType.<Integer>builder()
			.persistent(ExtraCodecs.NON_NEGATIVE_INT)
			.networkSynchronized(ByteBufCodecs.VAR_INT)
			.build());

	public static final Supplier<DataComponentType<Integer>> SHADOWSTEP = COMPONENT_TYPE.register("shadowstep", () ->
		DataComponentType.<Integer>builder()
			.persistent(ExtraCodecs.NON_NEGATIVE_INT)
			.networkSynchronized(ByteBufCodecs.VAR_INT)
			.build());

	public static final Supplier<DataComponentType<Integer>> CURRENT_GROUP = COMPONENT_TYPE.register("current_group", () ->
		DataComponentType.<Integer>builder()
			.persistent(ExtraCodecs.NON_NEGATIVE_INT)
			.networkSynchronized(ByteBufCodecs.VAR_INT)
			.build());

}
