package fr.edencraft.huntparty.utils;

import fr.edencraft.huntparty.lang.MessageFR;
import org.bukkit.ChatColor;

public enum HuntState {
    WAITING(MessageFR.huntStateWaiting, ChatColor.GREEN),
    RUNNING(MessageFR.huntStateRunning, ChatColor.YELLOW),
    ENDING(MessageFR.huntStateEnding, ChatColor.RED);

    private final String name;
    private final ChatColor color;

    HuntState(String name, ChatColor color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() { return color; }

    public String getDisplayName() { return color + name; }
}
