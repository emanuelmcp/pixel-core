package io.github.emanuelmcp.pixelCore.infra.di.module;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import io.github.emanuelmcp.pixelCore.infra.listener.InventoryCloseListener;
import io.github.emanuelmcp.pixelCore.infra.listener.PlayerDeathListener;
import io.github.emanuelmcp.pixelCore.infra.listener.handler.backpack.BackpackCloseHandler;
import io.github.emanuelmcp.pixelCore.infra.listener.handler.backpack.BackpackDeathHandler;
import io.github.emanuelmcp.pixelCore.infra.listener.handler.HandleEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class CoRListenerModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<HandleEvent<PlayerDeathEvent>> deathEventBinder =
                Multibinder.newSetBinder(binder(), new TypeLiteral<>() {
                });
        deathEventBinder.addBinding().to(BackpackDeathHandler.class);

        Multibinder<HandleEvent<InventoryCloseEvent>> inventoryCloseEventBinder = Multibinder.newSetBinder(binder(), new TypeLiteral<>() {
        });
        inventoryCloseEventBinder.addBinding().to(BackpackCloseHandler.class);

        bind(PlayerDeathListener.class).asEagerSingleton();
        bind(InventoryCloseListener.class).asEagerSingleton();
    }
}
