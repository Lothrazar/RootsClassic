package elucent.rootsclassic.component.components;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import elucent.rootsclassic.Const;
import elucent.rootsclassic.component.ComponentBase;
import elucent.rootsclassic.component.EnumCastType;
import elucent.rootsclassic.util.RootsUtil;

public class ComponentLilac extends ComponentBase {

  public ComponentLilac() {
    super(new ResourceLocation(Const.MODID, "lilac"), Blocks.LILAC, 14);
  }

  @Override
  public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
    if (!world.isClientSide && type == EnumCastType.SPELL && caster instanceof PlayerEntity) {
      BlockPos pos = RootsUtil.getRayTrace(world, (PlayerEntity) caster, 4 + 2 * (int) size);
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

  public boolean growBlockSafe(World world, BlockPos pos, int potency) {
    BlockState state = world.getBlockState(pos);
    if (state.getBlock() instanceof IGrowable && world.random.nextInt(5 - potency) < 2) {
      ((IGrowable) state.getBlock()).performBonemeal((ServerWorld) world, world.random, pos, state);
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
