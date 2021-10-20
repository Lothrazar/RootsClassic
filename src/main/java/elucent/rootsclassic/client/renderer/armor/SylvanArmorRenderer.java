package elucent.rootsclassic.client.renderer.armor;

import elucent.rootsclassic.client.ClientHandler;
import elucent.rootsclassic.client.model.SylvanArmorModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.fml.DistExecutor;

public class SylvanArmorRenderer implements IItemRenderProperties {
	private final LazyLoadedValue<HumanoidModel<?>> model;

	public SylvanArmorRenderer(EquipmentSlot slot) {
		this.model = DistExecutor.unsafeRunForDist(() -> () -> new LazyLoadedValue<>(() -> this.provideArmorModelForSlot(slot)),
				() -> () -> null);
	}
	@Override
	public <A extends HumanoidModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, A _default) {
		return (A) provideArmorModelForSlot(armorSlot);
	}

	public HumanoidModel<?> provideArmorModelForSlot(EquipmentSlot armorSlot) {
		if(Minecraft.getInstance().getEntityModels() != null) {
			return new SylvanArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(ClientHandler.SYLVAN_ARMOR), armorSlot);
		}

		return null;
	}
}
