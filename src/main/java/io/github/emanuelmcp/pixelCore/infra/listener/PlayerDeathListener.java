package io.github.emanuelmcp.pixelCore.infra.listener;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.emanuelmcp.pixelCore.application.BackpackService;
import io.github.emanuelmcp.pixelCore.domain.Backpack;
import io.github.emanuelmcp.pixelCore.domain.repository.BackpackRepository;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import xyz.xenondevs.invui.inventory.VirtualInventory;

@Singleton
public class PlayerDeathListener implements Listener {

  private static final Logger LOGGER = Logger.getLogger(PlayerDeathListener.class.getName());

  private final BackpackRepository backpackRepository;
  private final BackpackService backpackService;

  @Inject
  public PlayerDeathListener(BackpackRepository backpackRepository, BackpackService backpackService) {
    this.backpackRepository = backpackRepository;
    this.backpackService = backpackService;
  }

  @EventHandler
  public void onPlayerDeath(PlayerDeathEvent event) {
    dropBackpackItems(event.getEntity());
  }

  private void dropBackpackItems(Player player) {
    UUID playerId = player.getUniqueId();
    backpackRepository.findByUuid(playerId)
        .filter(backpack -> !backpack.isInvalid())
        .ifPresent(backpack -> handleBackpackDrop(player, backpack));
  }

  private void handleBackpackDrop(Player player, Backpack backpack) {
    try {
      VirtualInventory inventory = backpackService.loadInventoryFromBackpack(backpack);
      dropInventoryItems(player.getWorld(), player.getLocation(), inventory);
      backpackService.saveBackpack(player.getUniqueId(), inventory);
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Error al dropear la mochila de " + player.getName(), e);
    }
  }

  private void dropInventoryItems(World world, Location location, VirtualInventory inventory) {
    for (ItemStack item : inventory.getItems()) {
      if (item != null && item.getType().isItem()) {
        world.dropItemNaturally(location, item.clone());
      }
    }
  }
}
