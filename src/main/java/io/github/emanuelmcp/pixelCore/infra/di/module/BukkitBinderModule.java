package io.github.emanuelmcp.pixelCore.infra.di.module;

import com.google.inject.AbstractModule;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class BukkitBinderModule extends AbstractModule {
  private final JavaPlugin javaPlugin;

  public BukkitBinderModule(final @NotNull JavaPlugin javaPlugin) {
    this.javaPlugin = javaPlugin;
  }

  @Override
  protected void configure(){
    super.bind(JavaPlugin.class).toInstance(javaPlugin);
    super.bind(PluginManager.class).toInstance(javaPlugin.getServer().getPluginManager());
  }
}
