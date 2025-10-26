package io.github.emanuelmcp.pixelCore.infra.di.module;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import io.github.emanuelmcp.pixelCore.infra.listener.PlayerDeathListener;
import io.github.emanuelmcp.pixelCore.infra.listener.handler.BackpackDeathHandler;
import io.github.emanuelmcp.pixelCore.infra.listener.handler.HandleEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class CoRListenerModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<HandleEvent<PlayerDeathEvent>> deathEventBinder =
                Multibinder.newSetBinder(binder(), new TypeLiteral<>() {
                });
        deathEventBinder.addBinding().to(BackpackDeathHandler.class);
        bind(PlayerDeathListener.class).asEagerSingleton();
    }
}
