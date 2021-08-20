package fr.edencraft.huntparty;

import fr.edencraft.huntparty.configuration.ConfigurationManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class HuntParty extends JavaPlugin {

    private static final String VERSION = HuntParty.class.getPackage().getImplementationVersion();

    // MANAGER
    private static ConfigurationManager configurationManager;

    @Override
    public void onEnable() {
        long delay = System.currentTimeMillis();
        configurationManager = new ConfigurationManager(this);
        configurationManager.setupFiles();
    }

    @Override
    public void onDisable() {
        configurationManager.saveFiles();
    }

    public static ConfigurationManager getConfigurationManager() {
        return configurationManager;
    }

    public static String getVERSION() { return VERSION; }
}
