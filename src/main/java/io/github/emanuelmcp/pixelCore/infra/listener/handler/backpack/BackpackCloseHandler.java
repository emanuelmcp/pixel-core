package io.github.emanuelmcp.pixelCore.infra.listener.handler.backpack;

import com.google.inject.Inject;
import io.github.emanuelmcp.pixelCore.application.BackpackService;
import io.github.emanuelmcp.pixelCore.infra.listener.handler.HandleEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class BackpackCloseHandler implements HandleEvent<InventoryCloseEvent> {
    private static final Component BACKPACK_TITLE = Component.text("Mochila");
    private final BackpackService backpackService;

    @Inject
    public BackpackCloseHandler(BackpackService backpackService) {
        this.backpackService = backpackService;
    }

    @Override
    public void handle(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;
        if (!event.getView().title().equals(BACKPACK_TITLE)) return;

        ItemStack[] contents = event.getInventory().getContents();
        UUID uuid = player.getUniqueId();
        backpackService.saveBackpack(uuid, contents);
    }
}
