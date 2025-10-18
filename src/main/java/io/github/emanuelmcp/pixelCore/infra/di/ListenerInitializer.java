package io.github.emanuelmcp.pixelCore.infra.di;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import io.github.emanuelmcp.pixelCore.infra.listener.PlayerDeathListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@Singleton
public class ListenerInitializer implements Initializer {
  private final JavaPlugin plugin;
  private final PluginManager pluginManager;

  @Inject
  public ListenerInitializer(JavaPlugin plugin, PluginManager pluginManager) {
    this.plugin = plugin;
    this.pluginManager = pluginManager;
  }

  @Override
  public void init(@NotNull Injector injector) {
    pluginManager.registerEvents(injector.getInstance(PlayerDeathListener.class), plugin);
  }
}
