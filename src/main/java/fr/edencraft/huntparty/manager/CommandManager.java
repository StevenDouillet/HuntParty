package fr.edencraft.huntparty.manager;

import co.aikar.commands.PaperCommandManager;
import fr.edencraft.huntparty.command.AdminCommands;
import fr.edencraft.huntparty.command.PlayerCommands;
import org.bukkit.plugin.Plugin;

public class CommandManager {

    public CommandManager(Plugin plugin) {
        PaperCommandManager manager = new PaperCommandManager(plugin);
        manager.registerCommand(new AdminCommands());
        manager.registerCommand(new PlayerCommands());
    }
}
