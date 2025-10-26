package io.github.emanuelmcp.pixelCore.infra.listener;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import io.github.emanuelmcp.pixelCore.infra.listener.handler.HandleEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

@Singleton
public class PlayerDeathListener implements Listener {
    private final List<HandleEvent<PlayerDeathEvent>> handlers;

    @Inject
    public PlayerDeathListener(Set<HandleEvent<PlayerDeathEvent>> handlers) {
        this.handlers = new ArrayList<>(handlers);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        for (HandleEvent<PlayerDeathEvent> handler : handlers) {
            try {
                handler.handle(event);
            } catch (Exception e) {
                Logger.getLogger(getClass().getName())
                        .log(Level.SEVERE, "Error en handler: " + handler.getClass().getSimpleName(), e);
            }
        }
    }
}
