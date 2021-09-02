package fr.edencraft.huntparty.utils;

import fr.edencraft.huntparty.HuntParty;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Hunt {

    private final String id;
    private String name;
    private boolean timed;
    private int time;
    private HuntState state;
    private boolean isBuild;
    private final List<HuntPlayer> huntPlayers = new ArrayList<>();

    public Hunt(String id, String name, boolean timed, int time) {
        this.id = id;
        this.name = name;
        this.timed = timed;
        this.time = time;
        this.state = HuntState.WAITING;
        this.isBuild = false;
    }

    public Hunt(String id) {
        this.id = id;
    }

    public void buildHunt() {
        FileConfiguration cfg = HuntParty.getConfigurationManager().getConfigurationFile("Hunt.yml");
        ConfigurationSection huntSection = cfg.getConfigurationSection("hunts." + this.id);
        this.name = huntSection.getString("name");
        this.timed = huntSection.getBoolean("timed");
        this.time = huntSection.getInt("time");

        switch (Objects.requireNonNull(huntSection.getString("state"))) {
            case "WAITING":
                this.state = HuntState.WAITING;
                break;
            case "RUNNING":
                this.state = HuntState.RUNNING;
                break;
            case "ENDING":
                this.state = HuntState.ENDING;
                break;
        }
        this.isBuild = true;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTimed() {
        return timed;
    }

    public void setTimed(boolean timed) {
        this.timed = timed;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public HuntState getState() {
        return state;
    }

    public void setState(HuntState state) {
        this.state = state;
    }

    public boolean isBuild() {
        return isBuild;
    }

    public void setBuild(boolean build) {
        isBuild = build;
    }

    public List<HuntPlayer> getHuntPlayers() {
        return huntPlayers;
    }

    public void addHuntPLayer(HuntPlayer huntPlayer) {
        this.huntPlayers.add(huntPlayer);
    }

    public void removeHuntPlayer(HuntPlayer huntPlayer) {
        this.huntPlayers.remove(huntPlayer);
    }
}
