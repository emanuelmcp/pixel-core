package io.github.emanuelmcp.pixelCore.infra.dao.repository;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.emanuelmcp.pixelCore.domain.Backpack;
import io.github.emanuelmcp.pixelCore.infra.dao.entity.BackpackEntity;
import io.github.emanuelmcp.pixelCore.domain.repository.BackpackRepository;
import io.github.emanuelmcp.pixelCore.infra.dao.BackpackDao;
import java.util.Optional;
import java.util.UUID;
import org.jdbi.v3.core.Jdbi;

@Singleton
public class JdbiBackpackRepository implements BackpackRepository {
  private final Jdbi jdbi;

  @Inject
  public JdbiBackpackRepository(Jdbi jdbi) {
    this.jdbi = jdbi;
  }

  @Override
  public Backpack findByUuid(UUID uuid) {
    return jdbi.withExtension(BackpackDao.class, dao -> {
      BackpackEntity backpackEntity = dao.findByUuid(uuid);
      return new Backpack(backpackEntity.getItemData());
    });
  }

  @Override
  public void saveOrUpdate(UUID uuid, Backpack entity) {
    jdbi.useExtension(BackpackDao.class, dao -> {
      BackpackEntity existing = dao.findByUuid(uuid);
      Optional.ofNullable(existing).ifPresentOrElse(
          e -> dao.update(uuid, new BackpackEntity(entity.itemData())),
          () -> dao.save(uuid, new BackpackEntity(entity.itemData()))
      );
    });
  }
}
