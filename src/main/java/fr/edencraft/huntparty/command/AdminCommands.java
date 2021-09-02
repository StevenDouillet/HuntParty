package fr.edencraft.huntparty.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import fr.edencraft.huntparty.HuntParty;
import fr.edencraft.huntparty.configuration.ConfigurationUtils;
import fr.edencraft.huntparty.lang.MessageFR;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

@CommandAlias("huntparty|hp")
public class AdminCommands extends BaseCommand {

    @Subcommand("create")
    @Description("Create a new hunt")
    @CommandPermission("huntparty.create")
    public static void onCreateHuntCommand(Player player, String huntName) {
        if(ConfigurationUtils.getAllHuntName().stream().anyMatch(hunt -> hunt.equalsIgnoreCase(huntName))){
            player.sendMessage(MessageFR.huntNameAlreadyExist);
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return;
        };
        ConfigurationUtils.createHunt(player, huntName);
        player.sendMessage(MessageFR.huntCreated.replace("{Hunt}", huntName));
    }

    @Subcommand("setup")
    @Description("Setup a treasure for a specific hunt")
    @CommandPermission("huntparty.setup")
    public static void onSetupTreasure(Player player, String huntName) {
        if(!ConfigurationUtils.getAllHuntName().stream().anyMatch(hunt -> hunt.equalsIgnoreCase(huntName))){
            player.sendMessage(MessageFR.huntDoesntExist);
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return;
        };
        ConfigurationUtils.setupTreasureHunt(player, huntName);
    }

    @Subcommand("delete")
    @Description("Delete a hunt")
    @CommandPermission("huntparty.delete")
    public static void onDeleteHuntCommand(Player player, String huntName) {
        if(!ConfigurationUtils.getAllHuntName().stream().anyMatch(hunt -> hunt.equalsIgnoreCase(huntName))){
            player.sendMessage(MessageFR.huntDoesntExist);
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return;
        };
        ConfigurationUtils.deleteHunt(player, huntName);
        player.sendMessage(MessageFR.huntHasBeenDelete.replace("{Hunt}", huntName));
    }

    @Subcommand("list")
    @Description("List all hunts")
    @CommandPermission("huntparty.list")
    public static void onListHuntCommand(Player player) {
        if(HuntParty.hunts.size() <= 0) {
            player.sendMessage(MessageFR.noHuntFound);
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return;
        }

        player.sendMessage(MessageFR.huntListTitle);
        HuntParty.hunts.forEach(hunt -> {
            player.sendMessage(ChatColor.DARK_GRAY + " [" +
                    hunt.getState().getDisplayName() + ChatColor.DARK_GRAY + "] " +
                    ChatColor.WHITE + hunt.getName());
        });
    }
}
