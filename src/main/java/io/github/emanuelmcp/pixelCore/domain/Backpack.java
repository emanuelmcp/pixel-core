package io.github.emanuelmcp.pixelCore.domain;

import java.util.Base64;

public record Backpack(String itemData) {

  public boolean isEmpty() {
    return itemData == null || itemData.isBlank();
  }

  public Backpack setItemData(String itemData) {
    return new Backpack(itemData);
  }

  public byte[] getBytesFromItemData() {
      return Base64.getDecoder().decode(this.itemData().trim());
  }
}
