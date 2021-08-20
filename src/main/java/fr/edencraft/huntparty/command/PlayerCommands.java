package fr.edencraft.huntparty.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.entity.Player;

@CommandAlias("huntparty | hp")
public class PlayerCommands extends BaseCommand {

    @Subcommand("join")
    @Description("Join a hunt")
    @CommandPermission("huntparty.join")
    public static void onJoinHunt(Player player, String huntName) {
        //todo
    }

    @Subcommand("leave")
    @Description("Leave a hunt")
    @CommandPermission("huntparty.leave")
    public static void onLeaveHunt(Player player, String huntName) {
        //todo
    }
}
