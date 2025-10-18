package io.github.emanuelmcp.pixelCore.infra;

import io.github.emanuelmcp.pixelCore.domain.PlayerInfo;
import java.util.UUID;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.customizer.Bind;

@RegisterBeanMapper(PlayerInfo.class)
public interface PlayerInfoDao {
  @SqlUpdate("INSERT INTO player_info (uuid, nickname) VALUES (:uuid, :nickname) ON CONFLICT (uuid) DO NOTHING")
  void insertPlayer(@Bind("uuid") UUID uuid, @Bind("nickname") String nickname);

  @SqlQuery("SELECT * FROM player_info WHERE uuid = :uuid")
  PlayerInfo findByUuid(@Bind("uuid") String uuid);
}
