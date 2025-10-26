package io.github.emanuelmcp.pixelCore.infra.di.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.emanuelmcp.pixelCore.domain.repository.BackpackRepository;
import io.github.emanuelmcp.pixelCore.infra.dao.repository.JdbiBackpackRepository;
import javax.sql.DataSource;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class DatabaseBinderModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(BackpackRepository.class).to(JdbiBackpackRepository.class);
  }

  @Provides
  @Singleton
  public Jdbi provideJdbi() {
    HikariConfig config = new HikariConfig();
    config.setDriverClassName("org.postgresql.Driver");
    config.setJdbcUrl("jdbc:postgresql://localhost:5432/pixelcore");
    config.setUsername("postgres");
    config.setPassword("root");
    config.setMaximumPoolSize(10);
    config.setMinimumIdle(2);
    config.setIdleTimeout(30000);
    config.setConnectionTimeout(10000);
    config.setLeakDetectionThreshold(60000);
    config.setPoolName("PixelCore-Hikari");

    // Recomendado para evitar que JDBI cierre conexiones
    config.setAutoCommit(true);

    DataSource dataSource = new HikariDataSource(config);
    Jdbi jdbi = Jdbi.create(dataSource);
    jdbi.installPlugin(new SqlObjectPlugin());
    return jdbi;
  }
}
