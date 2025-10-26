package io.github.emanuelmcp.pixelCore;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import io.github.emanuelmcp.pixelCore.infra.di.CommandInitializer;
import io.github.emanuelmcp.pixelCore.infra.di.ListenerInitializer;
import io.github.emanuelmcp.pixelCore.infra.di.module.BukkitBinderModule;
import io.github.emanuelmcp.pixelCore.infra.di.module.CoRListenerModule;
import io.github.emanuelmcp.pixelCore.infra.di.module.DatabaseBinderModule;
import org.bukkit.plugin.java.JavaPlugin;

public final class PixelCore extends JavaPlugin {

  @Inject
  ListenerInitializer listenerInitializer;
  @Inject
  CommandInitializer commandInitializer;

  @Override
  public void onEnable() {
    Injector injector = Guice.createInjector(
            new BukkitBinderModule(this),
            new DatabaseBinderModule(),
            new CoRListenerModule()
    );
    listenerInitializer.init(injector);
    commandInitializer.init(injector);
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }
}
