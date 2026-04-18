package elucent.rootsclassic.blockentity;

import elucent.rootsclassic.Roots;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.phys.BlockHitResult;

public abstract class BEBase extends BlockEntity {

  public static final String NBT_PROGRESS = "progress";
  public static final String NBT_BURNING = "burning";

  public BEBase(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
    super(tileEntityTypeIn, pos, state);
  }

  @Override
  public ClientboundBlockEntityDataPacket getUpdatePacket() {
    return ClientboundBlockEntityDataPacket.create(this);
  }

  @Override
  public void onDataPacket(Connection net, ValueInput valueInput) {
    super.onDataPacket(net, valueInput);

    BlockState state = level.getBlockState(getBlockPos());
    level.sendBlockUpdated(getBlockPos(), state, state, 3);
  }


  @Override
  public CompoundTag getUpdateTag(HolderLookup.Provider lookupProvider) {
    CompoundTag tag = new CompoundTag();
    try (ProblemReporter.ScopedCollector problemreporter$scopedcollector = new ProblemReporter.ScopedCollector(Roots.LOGGER)) {
      TagValueOutput output = TagValueOutput.createWithContext(problemreporter$scopedcollector, lookupProvider);
      this.saveAdditional(output);
      tag.merge(output.buildResult());
    }
    return tag;
  }

  @Override
  public CompoundTag getPersistentData() {
    CompoundTag tag = new CompoundTag();
    try (ProblemReporter.ScopedCollector problemreporter$scopedcollector = new ProblemReporter.ScopedCollector(Roots.LOGGER)) {
      HolderLookup.Provider lookupProvider = this.level != null ? this.level.registryAccess() : VanillaRegistries.createLookup();
      TagValueOutput output = TagValueOutput.createWithContext(problemreporter$scopedcollector, lookupProvider);
      this.saveAdditional(output);
      tag.merge(output.buildResult());
    }
    return tag;
  }

  public InteractionResult activate(Level levelAccessor, BlockPos pos, BlockState state, Player player, InteractionHand hand, ItemStack heldItem, BlockHitResult hit) {
    return InteractionResult.PASS;
  }
}
