package io.github.emanuelmcp.pixelCore.infra.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.google.inject.Inject;
import io.github.emanuelmcp.pixelCore.domain.BackpackEntity;
import io.github.emanuelmcp.pixelCore.domain.repository.BackpackRepository;
import io.github.emanuelmcp.pixelCore.infra.utils.InventoryUtils;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.inventory.VirtualInventory;
import xyz.xenondevs.invui.window.Window;

@CommandAlias("backpack")
public class BackpackCommand extends BaseCommand implements Listener {
  private final BackpackRepository backpackRepository;

  @Inject
  public BackpackCommand(BackpackRepository backpackRepository) {
    this.backpackRepository = backpackRepository;
  }

  @Default
  public void onBackpack(Player player) {
    VirtualInventory inv = loadOrCreateBackpack(player);
    Gui gui = createBackpackGui(inv);
    Window window = Window.single()
        .setViewer(player)
        .setTitle("🎒 Tu Mochila")
        .setGui(gui)
        .build();
    window.addCloseHandler(() -> saveBackpack(player, inv));
    window.open();
  }


  private Gui createBackpackGui(VirtualInventory inv) {
    return Gui.normal()
        .setStructure(
            "#########",
            "#########",
            "#########"
        )
        .addIngredient('#', inv)
        .build();
  }

  private void saveBackpack(Player player, VirtualInventory inv) {
    UUID uuid = player.getUniqueId();
    try {
      // Serializar el inventario y convertirlo a Base64
      String base64 = Base64.getEncoder().encodeToString(inv.serialize());
      BackpackEntity entity = backpackRepository.findByUuid(uuid);
      if (entity == null) {
        entity = new BackpackEntity();
        entity.setItemData(base64);
        backpackRepository.save(uuid, entity);
      } else {
        entity.setItemData(base64);
        backpackRepository.update(uuid, entity);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  private VirtualInventory loadOrCreateBackpack(Player player) {
    UUID uuid = player.getUniqueId();
    BackpackEntity entity = backpackRepository.findByUuid(uuid);
    if (entity == null || entity.getItemData() == null || entity.getItemData().isEmpty()) {
      return new VirtualInventory(27);
    }
    try {
      String base64 = entity.getItemData().trim();
      byte[] data = Base64.getDecoder().decode(base64);
      System.out.println("[DEBUG] Backpack bytes: " + data.length + " for " + player.getName());
      if (data.length < 20) {
        System.err.println("⚠️ Corrupt backpack data for " + player.getName());
        return new VirtualInventory(27);
      }
      return Objects.requireNonNullElseGet(VirtualInventory.deserialize(data), () -> new VirtualInventory(27));
    } catch (Exception e) {
      e.printStackTrace();
      return new VirtualInventory(27);
    }
  }


}
