package fr.edencraft.huntparty.utils;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class HuntUtils {

    public static boolean playerInAnyHunt(Player player, List<Hunt> hunts) {
        return hunts.stream().anyMatch(hunt -> playerInHunt(player, hunt));
    }

    public static boolean playerInHunt(Player player, Hunt hunt) {
        return hunt.getHuntPlayers().stream().anyMatch(huntPlayer -> huntPlayer.getPlayer().equals(player));
    }

    public static List<Hunt> findHuntByName(String huntName, List<Hunt> hunts) {
        return hunts.stream().filter(hunt -> hunt.getName().equalsIgnoreCase(huntName)).collect(Collectors.toList());
    }
}
