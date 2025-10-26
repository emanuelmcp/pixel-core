package io.github.emanuelmcp.pixelCore.infra.listener;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.emanuelmcp.pixelCore.infra.listener.handler.HandleEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class InventoryCloseListener implements Listener {
    private final List<HandleEvent<InventoryCloseEvent>> handlers;

    @Inject
    public InventoryCloseListener(Set<HandleEvent<InventoryCloseEvent>> handlers) {
        this.handlers = new ArrayList<>(handlers);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        for (HandleEvent<InventoryCloseEvent> handler : handlers) {
            try {
                handler.handle(event);
            } catch (Exception e) {
                Logger.getLogger(getClass().getName())
                        .log(Level.SEVERE, "Error en handler: " + handler.getClass().getSimpleName(), e);
            }
        }
    }
}
