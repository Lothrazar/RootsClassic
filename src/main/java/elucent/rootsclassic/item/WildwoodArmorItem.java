package elucent.rootsclassic.item;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.client.ClientHandler;
import elucent.rootsclassic.client.model.WildwoodArmorModel;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("deprecation")
public class WildwoodArmorItem extends ArmorItem {

  public WildwoodArmorItem(Holder<ArmorMaterial> materialHolder, ArmorItem.Type type, Item.Properties builderIn) {
    super(materialHolder, type, builderIn);
  }

  @Override
  public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
    return Const.modLoc("textures/models/armor/wildwood.png");
  }

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
		super.inventoryTick(stack, level, entity, slotId, isSelected);
		if (slotId < 4)
			RootsUtil.randomlyRepair(level.random, stack);
	}

	@Override
  public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
    return false;
  }

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
		super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
		tooltipComponents.add(Component.empty());
		tooltipComponents.add(Component.translatable("rootsclassic.attribute.equipped").withStyle(ChatFormatting.GRAY));
		tooltipComponents.add(Component.literal(" ").append(Component.translatable("rootsclassic.attribute.increasedmanaregen")).withStyle(ChatFormatting.BLUE));
  }

  @SuppressWarnings("removal")
  @Override
  public void initializeClient(Consumer<IClientItemExtensions> consumer) {
    consumer.accept(new IClientItemExtensions() {
	    private final LazyLoadedValue<HumanoidModel<?>> model = new LazyLoadedValue<>(() -> this.provideArmorModelForSlot(type));

	    public HumanoidModel<?> provideArmorModelForSlot(ArmorItem.Type type) {
		    return new WildwoodArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(ClientHandler.WILDWOOD_ARMOR), type);
	    }

      @Override
      public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
        return model.get();
      }
    });
  }
}
