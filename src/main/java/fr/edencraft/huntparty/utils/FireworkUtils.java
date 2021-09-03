package fr.edencraft.huntparty.utils;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkUtils {

    private void launchFireWork(Location location) {
        location.add(0, 1, 0);
        Firework fw = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();
        FireworkEffect.Builder builder = FireworkEffect.builder();
        fwm.addEffect(builder.flicker(true).withColor(Color.FUCHSIA).build());
        fwm.addEffect(builder.trail(true).build());
        fwm.addEffect(builder.withFade(Color.YELLOW).build());
        fwm.addEffect(builder.with(FireworkEffect.Type.BURST).build());
        fwm.setPower(30);
        fw.setFireworkMeta(fwm);
        fw.detonate();
    }
}
