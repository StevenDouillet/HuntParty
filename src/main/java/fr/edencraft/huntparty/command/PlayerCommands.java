package fr.edencraft.huntparty.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import fr.edencraft.huntparty.HuntParty;
import fr.edencraft.huntparty.lang.MessageFR;
import fr.edencraft.huntparty.utils.Hunt;
import fr.edencraft.huntparty.utils.HuntPlayer;
import fr.edencraft.huntparty.utils.HuntUtils;
import org.bukkit.entity.Player;

import java.util.function.Predicate;

@CommandAlias("huntparty | hp")
public class PlayerCommands extends BaseCommand {

    @Subcommand("join")
    @Description("Join a hunt")
    @CommandPermission("huntparty.join")
    public static void onPlayerJoinHuntCommand(Player player, String huntName) {
        if(HuntUtils.playerInAnyHunt(player, HuntParty.hunts)){
            player.sendMessage(MessageFR.senderAlreadyInHunt);
            return;
        };

        Hunt hunt = HuntUtils.findHuntByName(huntName, HuntParty.hunts).get(0);
    }

    @Subcommand("leave")
    @Description("Leave a hunt")
    @CommandPermission("huntparty.leave")
    public static void onLeaveHuntCommand(Player player, String huntName) {
        //todo
    }
}
