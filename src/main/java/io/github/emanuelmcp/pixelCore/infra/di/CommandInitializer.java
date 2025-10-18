package io.github.emanuelmcp.pixelCore.infra.di;

import co.aikar.commands.PaperCommandManager;
import com.google.inject.Inject;
import com.google.inject.Injector;
import io.github.emanuelmcp.pixelCore.infra.command.BackpackCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class CommandInitializer implements Initializer{
  private final JavaPlugin plugin;

  @Inject
  public CommandInitializer(JavaPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public void init(@NotNull Injector injector) {
    PaperCommandManager commandManager = new PaperCommandManager(plugin);
    commandManager.registerCommand(injector.getInstance(BackpackCommand.class));
  }
}
