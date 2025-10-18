package io.github.emanuelmcp.pixelCore.domain.repository;

import io.github.emanuelmcp.pixelCore.domain.BackpackEntity;
import java.util.UUID;

public interface BackpackRepository {
  BackpackEntity findByUuid(UUID uuid);
  boolean existsByUuid(UUID uuid);
  void save(UUID uuid, BackpackEntity backpackEntity);
  void update(UUID uuid, BackpackEntity data);
}
