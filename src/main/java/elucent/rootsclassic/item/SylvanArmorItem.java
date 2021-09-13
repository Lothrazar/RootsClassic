package elucent.rootsclassic.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.capability.RootsCapabilityManager;
import elucent.rootsclassic.client.model.SylvanArmorModel;
import elucent.rootsclassic.util.RootsUtil;

public class SylvanArmorItem extends ArmorItem {

  private final LazyLoadedValue<HumanoidModel<?>> model;

  public SylvanArmorItem(ArmorMaterial materialIn, EquipmentSlot slot, Item.Properties builderIn) {
    super(materialIn, slot, builderIn);
    this.model = DistExecutor.unsafeRunForDist(() -> () -> new LazyLoadedValue<>(() -> this.provideArmorModelForSlot(slot)),
        () -> () -> null);
  }

  @Override
  public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
    return Const.MODID + ":textures/models/armor/sylvan.png";
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public <A extends HumanoidModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, A _default) {
    return (A) model.get();
  }

  @OnlyIn(Dist.CLIENT)
  public HumanoidModel<?> provideArmorModelForSlot(EquipmentSlot slot) {
    return new SylvanArmorModel(slot);
  }

  @Override
  public void onArmorTick(ItemStack stack, Level world, Player player) {
    RootsUtil.randomlyRepair(world.random, stack);
    if (world.random.nextInt(40) == 0) {
      player.getCapability(RootsCapabilityManager.MANA_CAPABILITY).ifPresent(cap -> {
        cap.setMana(cap.getMana() + 1.0f);
      });
    }
  }

  @Override
  public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
    super.appendHoverText(stack, worldIn, tooltip, flagIn);
    tooltip.add(TextComponent.EMPTY);
    tooltip.add(new TranslatableComponent("rootsclassic.attribute.equipped").withStyle(ChatFormatting.GRAY));
    tooltip.add(new TextComponent(" ").append(new TranslatableComponent("rootsclassic.attribute.increasedmanaregen")).withStyle(ChatFormatting.BLUE));
    tooltip.add(TextComponent.EMPTY);
    tooltip.add(new TranslatableComponent("rootsclassic.attribute.fullset").withStyle(ChatFormatting.GRAY));
    tooltip.add(new TextComponent(" +1 ").append(new TranslatableComponent("rootsclassic.attribute.potency")).withStyle(ChatFormatting.BLUE));
  }
}
