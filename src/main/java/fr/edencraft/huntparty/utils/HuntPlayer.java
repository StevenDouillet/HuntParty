package fr.edencraft.huntparty.utils;

import org.bukkit.entity.Player;

public class HuntPlayer {

    private Player player;
    private Hunt hunt;

    public HuntPlayer(Player player, Hunt hunt) {
        this.player = player;
        this.hunt = hunt;
    }

    public HuntPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Hunt getHunt() {
        return hunt;
    }

    public void setHunt(Hunt hunt) {
        this.hunt = hunt;
    }
}
