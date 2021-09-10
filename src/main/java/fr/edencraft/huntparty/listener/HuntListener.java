package fr.edencraft.huntparty.listener;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import fr.edencraft.huntparty.event.HuntStartedEvent;
import fr.edencraft.huntparty.event.PlayerJoinHuntEvent;
import fr.edencraft.huntparty.utils.Hunt;
import fr.edencraft.huntparty.utils.HuntPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class HuntListener implements Listener {

    @EventHandler
    public void onPlayerJoinHunt(PlayerJoinHuntEvent event) {
        Hunt hunt = event.getHunt();
        Player player = event.getPlayer();

        HuntPlayer huntPlayer = new HuntPlayer(player);
        huntPlayer.setHunt(hunt);
        hunt.addHuntPLayer(huntPlayer);
    }

    @EventHandler
    public void onHuntStart(HuntStartedEvent event) {
        String skullBase64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThiN2NhM2M3ZDMxNGE2MWFiZWQ4ZmMxOGQ3OTdmYzMwYjZlZmM4NDQ1NDI1YzRlMjUwOTk3ZTUyZTZjYiJ9fX0=";
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();

        PlayerProfile playerProfile = Bukkit.createProfile(UUID.randomUUID(), null);
        playerProfile.setProperty(new ProfileProperty("textures", skullBase64));
        skullMeta.setPlayerProfile(playerProfile);
        head.setItemMeta(skullMeta);

        event.getHunt().getHuntTreasures().forEach(treasure -> {
            treasure.getLocation().getBlock().setType(Material.DIAMOND_BLOCK);

            treasure.getLocation().getBlock().setType(Material.PLAYER_HEAD);
            treasure.getLocation().getBlock().setBlockData((BlockData) head.getData());
        });
    }
}
