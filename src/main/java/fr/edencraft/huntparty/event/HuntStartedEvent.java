package fr.edencraft.huntparty.event;

import fr.edencraft.huntparty.utils.Hunt;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class HuntStartedEvent extends Event implements Cancellable {

    private final static HandlerList handlerList = new HandlerList();
    private boolean isCancelled;

    private final Hunt hunt;

    public HuntStartedEvent(Hunt hunt) {
        this.hunt = hunt;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Hunt getHunt() {
        return hunt;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }
}
