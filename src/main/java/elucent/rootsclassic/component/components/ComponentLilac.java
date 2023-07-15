package elucent.rootsclassic.component.components;

import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.util.RootsUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ComponentLilac extends ComponentBase {

  public ComponentLilac() {
    super(Blocks.LILAC, 14);
  }

  @Override
  public void doEffect(Level level, Entity casterEntity, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (!level.isClientSide && type == EnumCastType.SPELL && casterEntity instanceof LivingEntity caster) {
      BlockPos pos = RootsUtil.getRayTrace(level, caster, 4 + 2 * (int) size);
      boolean fullEfficiency = growBlockSafe(level, pos, (int) potency) && growBlockSafe(level, pos.east(), (int) potency) &&
          growBlockSafe(level, pos.west(), (int) potency) && growBlockSafe(level, pos.north(), (int) potency) && growBlockSafe(level, pos.south(), (int) potency);
      //  if (fullEfficiency) { 
      // TODO: Re-implement the advancements maybe?
      //				ServerPlayerEntity player = (ServerPlayerEntity)caster;
      //				Advancement advancementIn = player.getServer().getAdvancementManager().getAdvancement(new ResourceLocation(Const.MODID + ":achieve_spell_growth"));
      //				if (advancementIn != null) {
      //					AdvancementProgress advancementprogress = player.getAdvancements().getProgress(advancementIn);
      //					if(!advancementprogress.isDone()) {
      //						for(String s : advancementprogress.getRemaningCriteria()) {
      //							player.getAdvancements().grantCriterion(advancementIn, s);
      //						}
      //					}
      // 			}
    }
  }

  public boolean growBlockSafe(Level levelAccessor, BlockPos pos, int potency) {
    BlockState state = levelAccessor.getBlockState(pos);
    if (state.getBlock() instanceof BonemealableBlock && levelAccessor.random.nextInt(5 - potency) < 2) {
      ((BonemealableBlock) state.getBlock()).performBonemeal((ServerLevel) levelAccessor, levelAccessor.random, pos, state);
      return true;
    }
    if (state.getBlock() == Blocks.NETHER_WART && levelAccessor.random.nextInt(5 - potency) < 2) {
      int age = state.getValue(NetherWartBlock.AGE).intValue();
      if (age < 3) {
        state = state.setValue(NetherWartBlock.AGE, Integer.valueOf(age + 1));
        levelAccessor.setBlock(pos, state, 2);
        return true;
      }
    }
    return false;
  }
}
