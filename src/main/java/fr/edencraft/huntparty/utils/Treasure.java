package fr.edencraft.huntparty.utils;

import org.bukkit.Location;

import java.util.List;

public class Treasure {

    private List<String> commands;
    private Location location;

    public Treasure(List<String> commands, Location location) {
        this.commands = commands;
        this.location = location;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
