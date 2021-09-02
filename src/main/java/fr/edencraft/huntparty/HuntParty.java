package fr.edencraft.huntparty;

import fr.edencraft.huntparty.configuration.ConfigurationManager;
import fr.edencraft.huntparty.configuration.ConfigurationUtils;
import fr.edencraft.huntparty.manager.CommandManager;
import fr.edencraft.huntparty.utils.Hunt;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public final class HuntParty extends JavaPlugin {

    // MANAGER
    private static ConfigurationManager configurationManager;

    public static List<Hunt> hunts = new ArrayList<>();

    @Override
    public void onEnable() {
        long delay = System.currentTimeMillis();
        configurationManager = new ConfigurationManager(this);
        configurationManager.setupFiles();
        new CommandManager(this);
        getLogger().log(Level.INFO, "Initialization of all Hunt ...");
        initAllHunt();
        getLogger().log(Level.INFO, "End of Hunt initialization. {x} hunt have been init"
                .replace("{x}", String.valueOf(hunts.size())));
        getLogger().log(Level.INFO, "Plugin enabled took " + (System.currentTimeMillis() - delay) + " ms");
    }

    @Override
    public void onDisable() {
        // todo : remove player datas
        configurationManager.saveFiles();
    }

    public static ConfigurationManager getConfigurationManager() {
        return configurationManager;
    }

    private void initAllHunt() {
        ConfigurationUtils.getAllHuntID().forEach(huntID -> {
            Hunt hunt = new Hunt(huntID);
            hunt.buildHunt();
            hunts.add(hunt);
        });
    }
}
