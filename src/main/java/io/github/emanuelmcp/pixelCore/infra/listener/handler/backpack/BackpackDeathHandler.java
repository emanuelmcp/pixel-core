package io.github.emanuelmcp.pixelCore.infra.listener.handler.backpack;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.emanuelmcp.pixelCore.application.BackpackService;
import io.github.emanuelmcp.pixelCore.domain.Backpack;
import io.github.emanuelmcp.pixelCore.domain.repository.BackpackRepository;
import io.github.emanuelmcp.pixelCore.infra.listener.handler.HandleEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class BackpackDeathHandler implements HandleEvent<PlayerDeathEvent> {
    private final BackpackRepository backpackRepository;
    private final BackpackService backpackService;
    private static final Logger LOGGER = Logger.getLogger(BackpackDeathHandler.class.getName());

    @Inject
    public BackpackDeathHandler(BackpackRepository backpackRepository, BackpackService backpackService) {
        this.backpackRepository = backpackRepository;
        this.backpackService = backpackService;
    }

    @Override
    public void handle(PlayerDeathEvent event) {
        Player player = event.getEntity();
        backpackRepository.findByUuid(player.getUniqueId())
                .filter(backpack -> !backpack.isEmpty())
                .ifPresent(backpack -> {
                    try {
                        dropBackpackItems(player, backpack);
                    } catch (Exception e) {
                        LOGGER.log(Level.SEVERE, "Error al procesar mochila de " + player.getName(), e);
                    }
                });
    }

    private void dropBackpackItems(Player player, Backpack backpack) throws Exception {
        ItemStack[] items = backpackService.loadInventoryFromBackpack(backpack);
        if (items == null || items.length == 0) {
            LOGGER.fine("Mochila vacía para " + player.getName());
            return;
        }
        boolean droppedAny = false;
        for (ItemStack item : items) {
            if (item != null && item.getType().isItem()) {
                player.getWorld().dropItemNaturally(player.getLocation(), item.clone());
                droppedAny = true;
            }
        }
        if (droppedAny) {
            ItemStack[] empty = new ItemStack[items.length];
            backpackService.saveBackpack(player.getUniqueId(), empty);
        }
    }
}
