package elucent.rootsclassic.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.LazyValue;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.capability.RootsCapabilityManager;
import elucent.rootsclassic.client.model.SylvanArmorModel;
import elucent.rootsclassic.util.RootsUtil;

public class SylvanArmorItem extends ArmorItem {

  private final LazyValue<BipedModel<?>> model;

  public SylvanArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, Item.Properties builderIn) {
    super(materialIn, slot, builderIn);
    this.model = DistExecutor.unsafeRunForDist(() -> () -> new LazyValue<>(() -> this.provideArmorModelForSlot(slot)),
        () -> () -> null);
  }

  @Override
  public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
    return Const.MODID + ":textures/models/armor/sylvan.png";
  }

  @OnlyIn(Dist.CLIENT)
  @Nullable
  @Override
  public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
    return (A) model.get();
  }

  @OnlyIn(Dist.CLIENT)
  public BipedModel<?> provideArmorModelForSlot(EquipmentSlotType slot) {
    return new SylvanArmorModel(slot);
  }

  @Override
  public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
    RootsUtil.randomlyRepair(world.random, stack);
    if (random.nextInt(40) == 0) {
      player.getCapability(RootsCapabilityManager.MANA_CAPABILITY).ifPresent(cap -> {
        cap.setMana(cap.getMana() + 1.0f);
      });
    }
  }

  @Override
  public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
    return false;
  }

  @Override
  public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    super.appendHoverText(stack, worldIn, tooltip, flagIn);
    tooltip.add(StringTextComponent.EMPTY);
    tooltip.add(new TranslationTextComponent("rootsclassic.attribute.equipped").withStyle(TextFormatting.GRAY));
    tooltip.add(new StringTextComponent(" ").append(new TranslationTextComponent("rootsclassic.attribute.increasedmanaregen")).withStyle(TextFormatting.BLUE));
    tooltip.add(StringTextComponent.EMPTY);
    tooltip.add(new TranslationTextComponent("rootsclassic.attribute.fullset").withStyle(TextFormatting.GRAY));
    tooltip.add(new StringTextComponent(" +1 ").append(new TranslationTextComponent("rootsclassic.attribute.potency")).withStyle(TextFormatting.BLUE));
  }
}
