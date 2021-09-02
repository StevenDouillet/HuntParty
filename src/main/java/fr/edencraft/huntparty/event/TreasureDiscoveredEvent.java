package fr.edencraft.huntparty.event;

import fr.edencraft.huntparty.HuntParty;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class TreasureDiscoveredEvent extends Event implements Cancellable {

    private final static HandlerList handlerList = new HandlerList();
    private boolean isCancelled;

    private final Player playerFinder;
    private final HuntParty instance;

    public TreasureDiscoveredEvent(Player playerFinder, HuntParty instance) {
        this.playerFinder = playerFinder;
        this.instance = instance;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    public Player getPlayerFinder() {
        return playerFinder;
    }

    public HuntParty getInstance() {
        return instance;
    }
}
