package io.github.emanuelmcp.pixelCore.infra.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.google.inject.Inject;
import io.github.emanuelmcp.pixelCore.application.BackpackService;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("unused")
@CommandAlias("backpack")
public class BackpackCommand extends BaseCommand implements Listener {
    private static final Component BACKPACK_TITLE = Component.text("Mochila");
  private final BackpackService backpackService;

  @Inject
  public BackpackCommand(BackpackService backpackService) {
    this.backpackService = backpackService;
  }

  @Default
  public void onBackpack(Player player) {
      ItemStack[] contents = backpackService.loadOrCreateBackpack(player.getUniqueId());
      Inventory backpackInv = Bukkit.createInventory(
              player,
              27,
              BACKPACK_TITLE
      );
      backpackInv.setContents(contents);
      player.openInventory(backpackInv);
  }
}
