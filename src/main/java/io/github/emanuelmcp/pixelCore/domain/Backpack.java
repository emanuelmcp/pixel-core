package io.github.emanuelmcp.pixelCore.domain;

public record Backpack(String itemData) {

  public boolean isInvalid() {
    return itemData == null || itemData.isBlank();
  }
}
