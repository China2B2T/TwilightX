package org.china2b2t.twilightx.events;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.china2b2t.twilightx.TwilightX;

public class PlayerListener implements Listener {
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().equalsIgnoreCase("help") || e.getMessage().equalsIgnoreCase("minecraft:help")) {
            if (TwilightX.config.isSet("enable-help") && TwilightX.config.getBoolean("enable-help")) {
                e.setCancelled(true);

                List<String> help = TwilightX.config.getStringList("help");
                for (String msg : help) {
                    e.getPlayer().sendMessage(msg.replace('&', ChatColor.COLOR_CHAR));
                }
            }
        }
    }
}
