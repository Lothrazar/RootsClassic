package elucent.rootsclassic.component.components;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ComponentLilac extends ComponentBase {

  public ComponentLilac() {
    super(new ResourceLocation(Const.MODID, "lilac"), Blocks.LILAC, 14);
  }

  @Override
  public void doEffect(Level world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (!world.isClientSide && type == EnumCastType.SPELL && caster instanceof Player) {
      BlockPos pos = RootsUtil.getRayTrace(world, (Player) caster, 4 + 2 * (int) size);
      boolean fullEfficiency = growBlockSafe(world, pos, (int) potency) && growBlockSafe(world, pos.east(), (int) potency) && growBlockSafe(world, pos.west(), (int) potency) && growBlockSafe(world, pos.north(), (int) potency) && growBlockSafe(world, pos.south(), (int) potency);
      //			if (fullEfficiency) { TODO: Re-implement the advancements maybe?
      //				ServerPlayerEntity player = (ServerPlayerEntity)caster;
      //				Advancement advancementIn = player.getServer().getAdvancementManager().getAdvancement(new ResourceLocation(Const.MODID + ":achieve_spell_growth"));
      //				if (advancementIn != null) {
      //					AdvancementProgress advancementprogress = player.getAdvancements().getProgress(advancementIn);
      //					if(!advancementprogress.isDone()) {
      //						for(String s : advancementprogress.getRemaningCriteria()) {
      //							player.getAdvancements().grantCriterion(advancementIn, s);
      //						}
      //					}
      //				}
      //			}
    }
  }

  public boolean growBlockSafe(Level world, BlockPos pos, int potency) {
    BlockState state = world.getBlockState(pos);
    if (state.getBlock() instanceof BonemealableBlock && world.random.nextInt(5 - potency) < 2) {
      ((BonemealableBlock) state.getBlock()).performBonemeal((ServerLevel) world, world.random, pos, state);
      return true;
    }
    if (state.getBlock() == Blocks.NETHER_WART && world.random.nextInt(5 - potency) < 2) {
      int age = state.getValue(NetherWartBlock.AGE).intValue();
      if (age < 3) {
        state = state.setValue(NetherWartBlock.AGE, Integer.valueOf(age + 1));
        world.setBlock(pos, state, 2);
        return true;
      }
    }
    return false;
  }
}
