package fr.edencraft.huntparty.configuration;

import fr.edencraft.huntparty.HuntParty;
import fr.edencraft.huntparty.lang.MessageFR;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationUtils {

    public static ConfigurationSection getHuntConfigurationSection(String huntID) {
        FileConfiguration cfg = HuntParty.getConfigurationManager().getConfigurationFile("Hunt.yml");
        return cfg.getConfigurationSection("hunts." + huntID);
    }

    public static List<String> getAllHuntID() {
        FileConfiguration cfg = HuntParty.getConfigurationManager().getConfigurationFile("Hunt.yml");
        ConfigurationSection huntSection = cfg.getConfigurationSection("hunts");
        return new ArrayList<String>(huntSection.getKeys(false));
    }

    public static List<String> getAllHuntName() {
        FileConfiguration cfg = HuntParty.getConfigurationManager().getConfigurationFile("Hunt.yml");
        List<String> huntNames = new ArrayList<>();
        getAllHuntID().forEach(huntID -> {
            ConfigurationSection huntIDSection = cfg.getConfigurationSection("hunt." + huntID);
            if (huntIDSection.getString("name") != null) {
                huntNames.add(huntIDSection.getString("name"));
            }
        });
        return huntNames;
    }

    @Nullable
    public static String getHuntID(String huntName) {
        FileConfiguration cfg = HuntParty.getConfigurationManager().getConfigurationFile("Hunt.yml");
        ConfigurationSection huntSection = cfg.getConfigurationSection("hunts");
        for (String huntID : huntSection.getKeys(false)) {
            ConfigurationSection huntIDSection = huntSection.getConfigurationSection(huntID);
            if (huntIDSection.getString("name").equalsIgnoreCase(huntName)) {
                return huntID;
            }
        }
        return null;
    }

    public static void createHunt(Player player, String huntName) {
        FileConfiguration cfg = HuntParty.getConfigurationManager().getConfigurationFile("Hunt.yml");
        ConfigurationSection huntSection = cfg.getConfigurationSection("hunts");

        int id = huntSection.getKeys(false).size();
        while (huntSection.contains(String.valueOf(id))) {
            id++;
        }

        ConfigurationSection huntIDSection = huntSection.createSection(String.valueOf(id));
        huntIDSection.set("name", huntName);
        huntIDSection.set("timed", true);
        huntIDSection.set("time", 3600);
        huntIDSection.createSection("treasures");

        HuntParty.getConfigurationManager().saveFile("Hunt.yml");
        player.sendMessage(MessageFR.huntCreated.replace("{Hunt}", huntName));
    }

    public static void setupTreasureHunt(Player player, String huntName) {
        String huntId = getHuntID(huntName);
        if(huntId == null){
            player.sendMessage(MessageFR.huntDoesntExist);
            return;
        }

        Location targetLocation = player.getTargetBlock(null, 100).getLocation();
        ConfigurationSection huntIDSection = ConfigurationUtils.getHuntConfigurationSection(huntId);

        int id = huntIDSection.getKeys(false).size();
        while (huntIDSection.contains(String.valueOf(id))) {
            id++;
        }
        ConfigurationSection treasureHuntIDSection = huntIDSection.createSection(String.valueOf(id));

        treasureHuntIDSection.set("x", targetLocation.getX());
        treasureHuntIDSection.set("y", targetLocation.getY());
        treasureHuntIDSection.set("z", targetLocation.getZ());
        treasureHuntIDSection.set("world", targetLocation.getWorld().getName());

        HuntParty.getConfigurationManager().saveFile("Hunt.yml");
        player.sendMessage(MessageFR.treasureHuntSet.replace("{Hunt}", huntName));
    }

    public static void deleteHunt(Player player, String huntName) {
        String huntId = getHuntID(huntName);
        if(huntId == null){
            player.sendMessage(MessageFR.huntDoesntExist);
            return;
        }

        FileConfiguration cfg = HuntParty.getConfigurationManager().getConfigurationFile("Hunt.yml");
        ConfigurationSection huntSection = cfg.getConfigurationSection("hunts");
        huntSection.set(huntId, null);

        player.sendMessage(MessageFR.huntHasBeenDelete.replace("{Hunt}", huntName));
    }
}
