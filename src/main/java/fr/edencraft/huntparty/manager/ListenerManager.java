package fr.edencraft.huntparty.manager;

import fr.edencraft.huntparty.HuntParty;
import fr.edencraft.huntparty.configuration.ConfigurationManager;
import fr.edencraft.huntparty.listener.HuntListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class ListenerManager {

    private final HuntParty plugin;

    public ListenerManager(HuntParty plugin){
        this.plugin = plugin;
        registerEvents();
    }

    private void registerEvents(){
        ConfigurationManager configurationManager = HuntParty.getConfigurationManager();
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new HuntListener(), plugin);
    }

}
