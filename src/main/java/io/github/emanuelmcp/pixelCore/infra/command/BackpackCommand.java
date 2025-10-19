package io.github.emanuelmcp.pixelCore.infra.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.google.inject.Inject;
import io.github.emanuelmcp.pixelCore.application.BackpackService;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.inventory.VirtualInventory;
import xyz.xenondevs.invui.window.Window;

@CommandAlias("backpack")
public class BackpackCommand extends BaseCommand implements Listener {
  private static final String BACKPACK_TITLE = "🎒 Tu Mochila";
  private final BackpackService backpackService;

  @Inject
  public BackpackCommand(BackpackService backpackService) {
    this.backpackService = backpackService;
  }

  @Default
  public void onBackpack(Player player) {
    VirtualInventory inventory = backpackService.loadOrCreateBackpack(player.getUniqueId());
    Window window = createWindow(player, inventory);
    window.addCloseHandler(() -> backpackService.saveBackpack(player.getUniqueId(), inventory));
    window.open();
  }

  private Window createWindow(Player player, VirtualInventory inv) {
    Gui gui = Gui.normal()
        .setStructure(
            "#########",
            "#########",
            "#########"
        )
        .addIngredient('#', inv)
        .build();
    return Window.single()
        .setViewer(player)
        .setTitle(BACKPACK_TITLE)
        .setGui(gui)
        .build();
  }
}
