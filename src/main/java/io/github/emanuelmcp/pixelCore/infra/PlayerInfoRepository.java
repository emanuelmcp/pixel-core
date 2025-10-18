package io.github.emanuelmcp.pixelCore.infra;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.emanuelmcp.pixelCore.domain.PlayerInfo;
import java.util.UUID;
import org.jdbi.v3.core.Jdbi;

@Singleton
public class PlayerInfoRepository {
  private final Jdbi jdbi;

  @Inject
  public PlayerInfoRepository(Jdbi jdbi) {
    this.jdbi = jdbi;
  }

  public void save(UUID uuid, String name) {
    jdbi.useExtension(PlayerInfoDao.class, dao -> dao.insertPlayer(uuid, name));
  }

  public PlayerInfo findByUuid(String uuid) {
    return jdbi.withExtension(PlayerInfoDao.class, dao -> dao.findByUuid(uuid));
  }
}
