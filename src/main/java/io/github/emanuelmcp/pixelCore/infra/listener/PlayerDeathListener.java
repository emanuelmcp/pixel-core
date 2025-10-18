package io.github.emanuelmcp.pixelCore.infra.listener;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.emanuelmcp.pixelCore.domain.repository.BackpackRepository;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

@Singleton
public class PlayerDeathListener implements Listener {

  private final BackpackRepository backpackRepository;

  @Inject
  public PlayerDeathListener(BackpackRepository backpackRepository) {
    this.backpackRepository = backpackRepository;
  }

  @EventHandler
  public void onPlayerQuit(PlayerDeathEvent event) {

  }
}
