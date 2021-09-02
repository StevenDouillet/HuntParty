package fr.edencraft.huntparty.manager;

import co.aikar.commands.PaperCommandManager;
import fr.edencraft.huntparty.command.AdminCommands;
import fr.edencraft.huntparty.utils.CommandCompletionUtils;
import org.bukkit.plugin.Plugin;

public class CommandManager {

    public CommandManager(Plugin plugin) {
        PaperCommandManager manager = new PaperCommandManager(plugin);
        manager.registerCommand(new AdminCommands(plugin));
        manager.getCommandCompletions().registerAsyncCompletion(
                "huntpartydelete",
                context -> CommandCompletionUtils.getAllHuntName()
        );
        manager.getCommandCompletions().registerAsyncCompletion(
                "huntpartysetup",
                context -> CommandCompletionUtils.getAllHuntName()
        );
        manager.getCommandCompletions().registerAsyncCompletion(
                "huntpartystart",
                context -> CommandCompletionUtils.getAllHuntNotStartedName()
        );
        manager.getCommandCompletions().registerAsyncCompletion(
                "huntpartyleave",
                context -> CommandCompletionUtils.getAllHuntName()
        );
        manager.getCommandCompletions().registerAsyncCompletion(
                "huntpartyjoin",
                context -> CommandCompletionUtils.getAllHuntName()
        );
        manager.getCommandCompletions().registerAsyncCompletion(
                "huntpartyplayers",
                context -> CommandCompletionUtils.getAllHuntName()
        );
    }
}
