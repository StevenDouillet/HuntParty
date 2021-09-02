package fr.edencraft.huntparty.event;

import fr.edencraft.huntparty.utils.Hunt;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerJoinHuntEvent extends Event implements Cancellable {

    private final static HandlerList handlerList = new HandlerList();
    private boolean isCancelled;

    private final Player player;
    private final Hunt hunt;

    public PlayerJoinHuntEvent(Player player, Hunt hunt) {
        this.player = player;
        this.hunt = hunt;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    public Player getPlayer() {
        return player;
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
