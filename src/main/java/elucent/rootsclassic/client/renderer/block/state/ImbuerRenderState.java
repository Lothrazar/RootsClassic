package elucent.rootsclassic.client.renderer.block.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

public class ImbuerRenderState extends BlockEntityRenderState {
  public ItemStackRenderState stickRenderState = new ItemStackRenderState();
  public ItemStackRenderState powderRenderState = new ItemStackRenderState();
  public int spin;
}
