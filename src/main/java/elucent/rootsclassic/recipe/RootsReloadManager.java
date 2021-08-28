package elucent.rootsclassic.recipe;

import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import elucent.rootsclassic.component.ComponentManager;
import elucent.rootsclassic.mutation.MutagenManager;
import elucent.rootsclassic.ritual.RitualManager;

public class RootsReloadManager implements IResourceManagerReloadListener {

  @Override
  public void onResourceManagerReload(IResourceManager resourceManager) {
    MutagenManager.reload();
    ComponentManager.reload();
    RitualManager.reload();
  }

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public void onAddReloadListeners(AddReloadListenerEvent event) {
    event.addListener(this);
  }
}