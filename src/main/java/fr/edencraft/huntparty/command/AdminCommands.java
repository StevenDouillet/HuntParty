package fr.edencraft.huntparty.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import fr.edencraft.huntparty.HuntParty;
import fr.edencraft.huntparty.configuration.ConfigurationUtils;
import fr.edencraft.huntparty.event.HuntStartedEvent;
import fr.edencraft.huntparty.inventory.HuntListProvider;
import fr.edencraft.huntparty.lang.MessageFR;
import fr.edencraft.huntparty.utils.Hunt;
import fr.edencraft.huntparty.utils.HuntPlayer;
import fr.edencraft.huntparty.utils.HuntState;
import fr.edencraft.huntparty.utils.HuntUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

@CommandAlias("huntparty|hp")
public class AdminCommands extends BaseCommand {

    private final Plugin plugin;

    public AdminCommands(Plugin plugin) {
        this.plugin = plugin;
    }

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
    @CommandCompletion("@huntpartysetup")
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
    @CommandCompletion("@huntpartydelete")
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
        HuntListProvider.getInventory(player).open(player);
    }

    @Subcommand("treasures")
    @Description("List all treasures of a hunt")
    @CommandPermission("huntparty.treasures")
    @CommandCompletion("@huntpartytreasures")
    public static void onListTreasureHuntCommand(Player player, String huntName) {
        if(!HuntUtils.doesHuntExist(huntName, HuntParty.hunts)) {
            player.sendMessage(MessageFR.huntDoesntExist);
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return;
        }

        Hunt hunt = HuntUtils.findHuntByName(huntName, HuntParty.hunts).get(0);

        player.sendMessage(MessageFR.huntTreasureListTitle.replace("{hunt}", huntName));
        hunt.getHuntTreasures().forEach(treasure -> {
            player.sendMessage(MessageFR.huntTreasureInfo
                    .replace("{x}", String.valueOf(treasure.getLocation().getX()))
                    .replace("{y}", String.valueOf(treasure.getLocation().getY()))
                    .replace("{z}", String.valueOf(treasure.getLocation().getZ()))
                    .replace("{world}", treasure.getLocation().getWorld().getName()));
        });
    }

    @Subcommand("start")
    @Description("Start a hunt")
    @CommandPermission("huntparty.start")
    @CommandCompletion("@huntpartystart")
    public void onStartHuntCommand(CommandSender sender, String huntName) {
        if(!ConfigurationUtils.getAllHuntName().stream().anyMatch(hunt -> hunt.equalsIgnoreCase(huntName))){
            sendInformation(sender, huntName, MessageFR.huntDoesntExist,
                    "Hunt " + huntName + " doesnt exist", Sound.ENTITY_VILLAGER_NO);
            return;
        };

        HuntParty.hunts.forEach(hunt -> {
            if(hunt.getName().equalsIgnoreCase(huntName)) {
                if(!hunt.getState().getCode().equalsIgnoreCase("WAITING")) {
                    sendInformation(sender, huntName, MessageFR.huntAlreadyStarted,
                            "Hunt " + huntName + " already started", Sound.ENTITY_VILLAGER_NO);
                    return;
                }

                HuntStartedEvent event = new HuntStartedEvent(hunt);
                Bukkit.getPluginManager().callEvent(event);

                hunt.setState(HuntState.RUNNING);
                sendInformation(sender, huntName, MessageFR.huntHasStarted,
                        "Hunt " + huntName + " just started", null);
                return;
            }
        });
    }

    @Subcommand("players")
    @Description("Get all players in a hunt")
    @CommandPermission("huntparty.players")
    @CommandCompletion("@huntpartyplayers")
    private static void onGetPlayersCommand(Player player, String huntName) {
        if(!HuntUtils.doesHuntExist(huntName, HuntParty.hunts)) {
            player.sendMessage(MessageFR.huntDoesntExist);
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return;
        }

        Hunt hunt = HuntUtils.findHuntByName(huntName, HuntParty.hunts).get(0);

        AtomicReference<String> message = new AtomicReference<>(
                MessageFR.huntPlayerListTitle.replace("{hunt}", huntName));
        hunt.getHuntPlayers().forEach(huntPlayer -> {
            message.updateAndGet(v -> v + " " + huntPlayer.getPlayer().getName());
        });

        player.sendMessage(message.get());
    }

    @Subcommand("join")
    @Description("Join a hunt")
    @CommandPermission("huntparty.join")
    @CommandCompletion("@huntpartyjoin")
    public static void onPlayerJoinHuntCommand(Player player, String huntName) {
        if(HuntUtils.playerInAnyHunt(player, HuntParty.hunts)){
            player.sendMessage(MessageFR.senderAlreadyInHunt);
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return;
        };

        Hunt hunt = HuntUtils.findHuntByName(huntName, HuntParty.hunts).get(0);
        hunt.addHuntPLayer(new HuntPlayer(player));
        player.sendMessage(MessageFR.playerHasJoinHunt.replace("{hunt}", huntName));
    }

    @Subcommand("leave")
    @Description("Leave a hunt")
    @CommandPermission("huntparty.leave")
    @CommandCompletion("@huntpartyleave")
    public static void onLeaveHuntCommand(Player player, String huntName) {
        if(!HuntUtils.doesHuntExist(huntName, HuntParty.hunts)) {
            player.sendMessage(MessageFR.huntDoesntExist);
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return;
        }

        Hunt hunt = HuntUtils.findHuntByName(huntName, HuntParty.hunts).get(0);

        if(!HuntUtils.playerInHunt(player, hunt)) {
            player.sendMessage(MessageFR.playerNotInThisHunt);
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return;
        }

        Optional<HuntPlayer> huntPlayer = hunt.getHuntPlayers().stream()
                .filter(foundHuntPlayer -> foundHuntPlayer.getPlayer().equals(player)).findFirst();

        huntPlayer.ifPresent(hunt::removeHuntPlayer);
    }

    private void sendInformation(CommandSender sender, String huntName, String playerMessage, String consoleMessage, Sound sound) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage(playerMessage);
            if(sound != null) {
                player.playSound(player.getLocation(), sound, 1, 1);
            }
        } else {
            this.plugin.getLogger().log(Level.INFO, consoleMessage);
        }
    }
}
