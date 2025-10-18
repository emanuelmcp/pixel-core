package io.github.emanuelmcp.pixelCore.domain.repository;

import io.github.emanuelmcp.pixelCore.domain.Backpack;
import java.util.UUID;

public interface BackpackRepository {
  Backpack findByUuid(UUID uuid);
  void saveOrUpdate(UUID uuid, Backpack entity);
}
