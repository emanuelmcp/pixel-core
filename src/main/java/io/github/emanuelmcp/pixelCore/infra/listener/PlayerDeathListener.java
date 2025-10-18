package io.github.emanuelmcp.pixelCore.infra.listener;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.emanuelmcp.pixelCore.app.BackpackService;
import io.github.emanuelmcp.pixelCore.domain.Backpack;
import io.github.emanuelmcp.pixelCore.domain.repository.BackpackRepository;
import java.util.Optional;
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
import xyz.xenondevs.invui.inventory.event.UpdateReason;

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
  public void onPlayerQuit(PlayerDeathEvent event) {
    Player player = event.getEntity();
    dropBackpackItems(player);
  }

  public void dropBackpackItems(Player player) {
    UUID uuid = player.getUniqueId();
    try {
      Optional<Backpack> optional = backpackRepository.findByUuid(uuid);
      if (optional.isEmpty() || optional.get().isInvalid()) {
        return;
      }
      VirtualInventory inventory = backpackService.loadInventoryFromBackpack(optional.get());
      World world = player.getWorld();
      Location deathLocation = player.getLocation();
      for (int i = 0; i < inventory.getSize(); i++) {
        ItemStack item = inventory.getItem(i);
        if (item != null && item.getType().isItem()) {
          world.dropItemNaturally(deathLocation, item.clone());
          inventory.setItem(UpdateReason.SUPPRESSED, i, null);
        }
      }
      backpackService.saveBackpack(uuid, inventory);
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Error al dropear la mochila de " + player.getName(), e);
    }
  }
}
