package fr.edencraft.huntparty.utils;

import fr.edencraft.huntparty.HuntParty;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class CommandCompletionUtils {

    public static List<String> playersName(){
        List<String> playerNames = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(player -> playerNames.add(player.getName()));
        return playerNames;
    }

    public static List<String> getAllHuntName(){
        List<String> huntNames = new ArrayList<>();
        HuntParty.hunts.forEach(hunt -> huntNames.add(hunt.getName()));
        return huntNames;
    }

    public static List<String> getAllHuntNotStartedName(){
        List<String> huntNames = new ArrayList<>();
        HuntParty.hunts.stream().filter(hunt -> hunt.getState().equals(HuntState.WAITING))
                .forEach(hunt -> huntNames.add(hunt.getName()));
        return huntNames;
    }
}
