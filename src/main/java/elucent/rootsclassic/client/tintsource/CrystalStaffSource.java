package elucent.rootsclassic.client.tintsource;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.ComponentBaseRegistry;
import elucent.rootsclassic.datacomponent.SpellData;
import elucent.rootsclassic.item.CrystalStaffItem;
import elucent.rootsclassic.registry.RootsComponents;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public record CrystalStaffSource(int tintIndex) implements ItemTintSource {
  public static final MapCodec<CrystalStaffSource> MAP_CODEC = RecordCodecBuilder.mapCodec(
    inst -> inst.group(
      Codec.intRange(0, 1).fieldOf("tintIndex").forGetter(CrystalStaffSource::tintIndex)
    ).apply(inst, CrystalStaffSource::new)
  );

  public CrystalStaffSource() {
    this(0);
  }

  @Override
  public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity owner) {
    if (stack.getItem() instanceof CrystalStaffItem && stack.has(RootsComponents.SPELLS)) {
      SpellData selectedSpell = CrystalStaffItem.getSelectedSpell(stack);
      String effect = selectedSpell.effect();
      if (effect != null) {
        Identifier compName = Identifier.tryParse(effect);
        if (compName != null) {
          ComponentBase comp = ComponentBaseRegistry.COMPONENTS.getValue(compName);
          if (comp != null) {
            if (tintIndex == 2) {
              return RootsUtil.intColor((int) comp.primaryColor.x, (int) comp.primaryColor.y, (int) comp.primaryColor.z);
            }
            if (tintIndex == 1) {
              return RootsUtil.intColor((int) comp.secondaryColor.x, (int) comp.secondaryColor.y, (int) comp.secondaryColor.z);
            }
          }
        }
      }
    }
    return RootsUtil.intColor(255, 255, 255);
  }

  @Override
  public MapCodec<? extends ItemTintSource> type() {
    return MAP_CODEC;
  }
}
