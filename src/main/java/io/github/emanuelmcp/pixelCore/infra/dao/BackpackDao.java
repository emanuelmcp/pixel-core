package io.github.emanuelmcp.pixelCore.infra.dao;

import io.github.emanuelmcp.pixelCore.infra.dao.entity.BackpackEntity;
import java.util.UUID;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@RegisterBeanMapper(BackpackEntity.class)
public interface BackpackDao {
  @SqlQuery("SELECT player_uuid, item_data FROM backpack WHERE player_uuid = :uuid")
  BackpackEntity findByUuid(@Bind("uuid") UUID uuid);

  @SqlUpdate("INSERT INTO backpack (player_uuid, item_data) VALUES (:uuid, :b.itemData) ON CONFLICT DO NOTHING")
  void save(@Bind("uuid") UUID uuid, @BindBean("b") BackpackEntity backpackEntity);

  @SqlUpdate("UPDATE backpack SET item_data = :b.itemData WHERE player_uuid = :uuid")
  void update(@Bind("uuid") UUID uuid, @BindBean("b") BackpackEntity data);
}
