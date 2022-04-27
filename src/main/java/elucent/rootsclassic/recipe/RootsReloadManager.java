package elucent.rootsclassic.recipe;

import elucent.rootsclassic.component.ComponentManager;
import elucent.rootsclassic.mutation.MutagenManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RootsReloadManager implements IResourceManagerReloadListener {

  @Override
  public void onResourceManagerReload(IResourceManager resourceManager) {
    MutagenManager.reload();
    ComponentManager.reload();
  }

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public void onAddReloadListeners(AddReloadListenerEvent event) {
    event.addListener(this);
  }
}