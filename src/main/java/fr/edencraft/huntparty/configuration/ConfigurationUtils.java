package fr.edencraft.huntparty.configuration;

import fr.edencraft.huntparty.HuntParty;
import fr.edencraft.huntparty.lang.MessageFR;
import fr.edencraft.huntparty.utils.Hunt;
import fr.edencraft.huntparty.utils.HuntState;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigurationUtils {

    public static ConfigurationSection getHuntConfigurationSection(String huntID) {
        FileConfiguration cfg = HuntParty.getConfigurationManager().getConfigurationFile("Hunt.yml");
        return cfg.getConfigurationSection("hunts." + huntID);
    }

    public static ConfigurationSection getHuntTreasuresConfigurationSection(String huntID) {
        FileConfiguration cfg = HuntParty.getConfigurationManager().getConfigurationFile("Hunt.yml");
        return cfg.getConfigurationSection("hunts." + huntID + ".treasures");
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
            ConfigurationSection huntIDSection = cfg.getConfigurationSection("hunts." + huntID);
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
        huntIDSection.set("state", HuntState.WAITING.name());
        huntIDSection.createSection("treasures");

        HuntParty.getConfigurationManager().saveFile("Hunt.yml");

        Hunt hunt = new Hunt(String.valueOf(id));
        hunt.buildHunt();
        HuntParty.hunts.add(hunt);
    }

    public static void setupTreasureHunt(Player player, String huntName) {
        String huntId = getHuntID(huntName);
        Location targetLocation = player.getTargetBlock(null, 100).getLocation();
        ConfigurationSection huntTreasureSection = ConfigurationUtils.getHuntTreasuresConfigurationSection(huntId);

        int id = huntTreasureSection.getKeys(false).size();
        while (huntTreasureSection.contains(String.valueOf(id))) {
            id++;
        }
        ConfigurationSection treasureHuntIDSection = huntTreasureSection.createSection(String.valueOf(id));

        treasureHuntIDSection.set("x", targetLocation.getX());
        treasureHuntIDSection.set("y", targetLocation.getY());
        treasureHuntIDSection.set("z", targetLocation.getZ());
        treasureHuntIDSection.set("world", targetLocation.getWorld().getName());

        HuntParty.getConfigurationManager().saveFile("Hunt.yml");
        player.sendMessage(MessageFR.treasureHuntSet.replace("{Hunt}", huntName));
    }

    public static void deleteHunt(Player player, String huntName) {
        String huntId = getHuntID(huntName);
        FileConfiguration cfg = HuntParty.getConfigurationManager().getConfigurationFile("Hunt.yml");
        ConfigurationSection huntSection = cfg.getConfigurationSection("hunts");
        huntSection.set(huntId, null);

        HuntParty.getConfigurationManager().saveFile("Hunt.yml");

        List<Hunt> matchingHunt = HuntParty.hunts.stream().filter(hunt -> hunt.getId().equalsIgnoreCase(huntId))
                .collect(Collectors.toList());
        HuntParty.hunts.remove(matchingHunt.get(0));
    }
}
