package io.github.emanuelmcp.pixelCore.app;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.emanuelmcp.pixelCore.domain.Backpack;
import io.github.emanuelmcp.pixelCore.domain.repository.BackpackRepository;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.xenondevs.invui.inventory.VirtualInventory;

@Singleton
public class BackpackService {
  private static final Logger LOGGER = Logger.getLogger(BackpackService.class.getName());
  private static final int DEFAULT_SIZE = 27;

  private final BackpackRepository repository;

  @Inject
  public BackpackService(BackpackRepository repository) {
    this.repository = repository;
  }

  public void saveBackpack(UUID uuid, VirtualInventory inventory) {
    try {
      String base64 = Base64.getEncoder().encodeToString(inventory.serialize());
      Backpack entity = Optional.ofNullable(repository.findByUuid(uuid))
          .orElseGet(() -> new Backpack(base64));
      //entity.setItemData(base64);
      repository.saveOrUpdate(uuid, entity);
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Error al guardar la mochila de " + uuid, e);
    }
  }

  public VirtualInventory loadOrCreateBackpack(UUID uuid) {
    return Optional.ofNullable(repository.findByUuid(uuid))
        .filter(b -> !b.isInvalid())
        .map(this::loadInventoryFromBackpack)
        .orElseGet(() -> new VirtualInventory(DEFAULT_SIZE));
  }

  private VirtualInventory loadInventoryFromBackpack(Backpack backpack) {
    try {
      byte[] data = Base64.getDecoder().decode(backpack.itemData().trim());
      if (data.length < 20) {
        return new VirtualInventory(DEFAULT_SIZE);
      }
      return Optional.ofNullable(VirtualInventory.deserialize(data))
          .orElseGet(() -> new VirtualInventory(DEFAULT_SIZE));
    } catch (Exception e) {
      LOGGER.log(Level.WARNING, "Error al cargar mochila " + e);
      return new VirtualInventory(DEFAULT_SIZE);
    }
  }
}
