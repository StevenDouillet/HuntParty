package fr.edencraft.huntparty.utils;

import fr.edencraft.huntparty.lang.MessageFR;
import org.bukkit.ChatColor;

public enum HuntState {
    WAITING(MessageFR.huntStateWaiting, "WAITING", ChatColor.GREEN),
    RUNNING(MessageFR.huntStateRunning, "RUNNING", ChatColor.YELLOW),
    ENDING(MessageFR.huntStateEnding, "ENDING", ChatColor.RED);

    private final String name;
    private final String code;
    private final ChatColor color;

    HuntState(String name, String code, ChatColor color) {
        this.name = name;
        this.code = code;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getCode() { return code; }

    public ChatColor getColor() { return color; }

    public String getDisplayName() { return color + name; }
}
