package elucent.rootsclassic.recipe;

import elucent.rootsclassic.Const;
import elucent.rootsclassic.mutation.MutagenManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;

public class RootsReloadManager implements ResourceManagerReloadListener {

  @Override
  public void onResourceManagerReload(ResourceManager resourceManager) {
    MutagenManager.reload();
  }

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public void onAddReloadListeners(AddServerReloadListenersEvent event) {
    event.addListener(Const.modLoc("mutagen_manager"), this);
  }
}