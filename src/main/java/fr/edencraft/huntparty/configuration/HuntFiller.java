package fr.edencraft.huntparty.configuration;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class HuntFiller implements CFGFiller {

    @Override
    public void fill(FileConfiguration fileConfiguration) {
        ConfigurationSection huntSection = fileConfiguration.createSection("hunts");
    }
}
