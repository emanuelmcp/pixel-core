package io.github.emanuelmcp.pixelCore.infra.dao.repository;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.emanuelmcp.pixelCore.domain.BackpackEntity;
import io.github.emanuelmcp.pixelCore.domain.repository.BackpackRepository;
import io.github.emanuelmcp.pixelCore.infra.dao.BackpackDao;
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
  public BackpackEntity findByUuid(UUID uuid) {
    return jdbi.withExtension(BackpackDao.class, dao -> dao.findByUuid(uuid));
  }

  @Override
  public boolean existsByUuid(UUID uuid) {
    return jdbi.withExtension(BackpackDao.class, dao -> dao.existsByUuid(uuid));
  }

  @Override
  public void save(UUID uuid, BackpackEntity backpackEntity) {
    jdbi.useExtension(BackpackDao.class, dao -> dao.save(uuid, backpackEntity));
  }

  @Override
  public void update(UUID uuid, BackpackEntity data) {
    jdbi.useExtension(BackpackDao.class, dao -> dao.update(uuid, data));
  }
}
