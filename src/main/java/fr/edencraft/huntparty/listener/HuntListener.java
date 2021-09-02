package fr.edencraft.huntparty.listener;

import fr.edencraft.huntparty.event.PlayerJoinHuntEvent;
import fr.edencraft.huntparty.utils.Hunt;
import fr.edencraft.huntparty.utils.HuntPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class HuntListener implements Listener {

    @EventHandler
    public void onPlayerJoinHunt(PlayerJoinHuntEvent event) {
        Hunt hunt = event.getHunt();
        Player player = event.getPlayer();

        HuntPlayer huntPlayer = new HuntPlayer(player);
        huntPlayer.setHunt(hunt);
        hunt.addHuntPLayer(huntPlayer);
    }
}
