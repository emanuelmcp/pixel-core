package io.github.emanuelmcp.pixelCore.domain.repository;

import io.github.emanuelmcp.pixelCore.domain.Backpack;
import java.util.Optional;
import java.util.UUID;

public interface BackpackRepository {
  Optional<Backpack> findByUuid(UUID uuid);
  void saveOrUpdate(UUID uuid, Backpack entity);
}
