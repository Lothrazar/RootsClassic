package elucent.rootsclassic.client.renderer.block.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

import java.util.Collections;
import java.util.List;

public class MortarRenderState extends BlockEntityRenderState {
  public List<ItemStackRenderState> items = Collections.emptyList();
}
