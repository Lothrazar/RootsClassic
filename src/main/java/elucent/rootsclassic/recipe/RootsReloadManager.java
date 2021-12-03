package elucent.rootsclassic.recipe;

import elucent.rootsclassic.component.ComponentManager;
import elucent.rootsclassic.mutation.MutagenManager;
import elucent.rootsclassic.ritual.RitualManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RootsReloadManager implements ResourceManagerReloadListener {
  @Override
  public void onResourceManagerReload(ResourceManager resourceManager) {
    MutagenManager.reload();
    ComponentManager.reload();
    RitualManager.reload();
  }

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public void onAddReloadListeners(AddReloadListenerEvent event) {
    event.addListener(this);
  }
}