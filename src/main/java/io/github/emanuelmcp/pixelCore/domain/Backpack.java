package io.github.emanuelmcp.pixelCore.domain;

public record Backpack(String itemData) {

  public boolean isInvalid() {
    return itemData == null || itemData.isBlank();
  }

  public Backpack setItemData(String itemData) {
    return new Backpack(itemData);
  }
}
