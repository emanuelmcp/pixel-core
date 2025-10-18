package io.github.emanuelmcp.pixelCore.infra.utils;

import java.util.Base64;
import java.util.List;
import java.util.Objects;
import org.bukkit.inventory.ItemStack;

public class InventoryUtils {
  public static String toBase64(ItemStack[] items) {
    if (Objects.isNull(items)) return "";
    List<ItemStack> nonNullItems =
        java.util.Arrays.stream(items)
            .filter(java.util.Objects::nonNull)
            .toList();
    byte[] bytes = ItemStack.serializeItemsAsBytes(nonNullItems);
    return Base64.getEncoder().encodeToString(bytes);
  }

  public static String toBase64Bytes(byte[] bytes) {
    if (bytes == null || bytes.length == 0) return "";
    return Base64.getEncoder().encodeToString(bytes);
  }

  public static byte[] fromBase64Bytes(String base64) {
    if (base64 == null || base64.isEmpty()) return new byte[0];
    return Base64.getDecoder().decode(base64);
  }
}
