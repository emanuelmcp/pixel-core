package io.github.emanuelmcp.pixelCore.application;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.emanuelmcp.pixelCore.domain.Backpack;
import io.github.emanuelmcp.pixelCore.domain.repository.BackpackRepository;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

@Singleton
public class BackpackService {

    private static final Logger LOGGER = Logger.getLogger(BackpackService.class.getName());
    private static final int DEFAULT_SIZE = 27; // configurable en el futuro

    private final BackpackRepository repository;

    @Inject
    public BackpackService(BackpackRepository repository) {
        this.repository = repository;
    }

    /**
     * Guarda la mochila serializando los ítems de forma segura con la API moderna de Bukkit.
     */
    public void saveBackpack(UUID uuid, ItemStack[] contents) {
        Optional.ofNullable(contents)
                .map(Arrays::asList)
                .map(this::serializeItemsSafe)
                .map(Base64.getEncoder()::encodeToString)
                .ifPresentOrElse(
                        base64 -> repository.saveOrUpdate(
                                uuid,
                                repository.findByUuid(uuid)
                                        .map(bp -> bp.setItemData(base64))
                                        .orElseGet(() -> new Backpack(base64))
                        ),
                        () -> LOGGER.warning("No se pudo guardar la mochila de " + uuid + ": contenido nulo")
                );
    }

    /**
     * Carga una mochila existente o crea una nueva vacía si no existe.
     */
    public ItemStack[] loadOrCreateBackpack(UUID uuid) {
        return repository.findByUuid(uuid)
                .filter(b -> !b.isEmpty())
                .map(this::loadInventoryFromBackpack)
                .orElseGet(this::emptyBackpack);
    }

    /**
     * Deserializa los ítems guardados en la base de datos de forma segura.
     */
    public ItemStack[] loadInventoryFromBackpack(Backpack backpack) {
        return Optional.ofNullable(backpack)
                .filter(b -> !b.isEmpty())
                .map(this::deserializeItemsSafe)
                .filter(items -> !items.isEmpty())
                .map(this::toFixedSizeArray)
                .orElseGet(this::emptyBackpack);
    }

    /* ----------------------- MÉTODOS PRIVADOS AUXILIARES ----------------------- */

    private byte[] serializeItemsSafe(List<ItemStack> items) {
        try {
            return ItemStack.serializeItemsAsBytes(items);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error serializando ítems", e);
            return new byte[0];
        }
    }

    private List<ItemStack> deserializeItemsSafe(Backpack backpack) {
        try {
            return List.of(ItemStack.deserializeItemsFromBytes(backpack.getBytesFromItemData()));
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error al deserializar mochila: " + e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    private ItemStack[] toFixedSizeArray(List<ItemStack> items) {
        ItemStack[] array = new ItemStack[Math.max(DEFAULT_SIZE, items.size())];
        IntStream.range(0, items.size()).forEach(i -> array[i] = items.get(i));
        return array;
    }

    private ItemStack[] emptyBackpack() {
        return new ItemStack[DEFAULT_SIZE];
    }
}
