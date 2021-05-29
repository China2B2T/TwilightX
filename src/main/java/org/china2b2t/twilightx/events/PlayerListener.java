package org.china2b2t.twilightx.events;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.china2b2t.twilightx.TwilightX;

public class PlayerListener implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (TwilightX.config.isSet("chat-coloring") && TwilightX.config.getBoolean("chat-coloring")) {
            if (e.getMessage().charAt(0) == '>') {
                e.setMessage(ChatColor.GREEN + e.getMessage());
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (TwilightX.config.isSet("disable-join-quit-msg") && TwilightX.config.getBoolean("disable-join-quit-msg")) {
            e.setJoinMessage(null);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (TwilightX.config.isSet("disable-join-quit-msg") && TwilightX.config.getBoolean("disable-join-quit-msg")) {
            e.setQuitMessage(null);
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        String cmd = "";
        
        if (e.getMessage().indexOf(" ") == -1) {
            cmd = e.getMessage().substring(1);
        } else {
            cmd = e.getMessage().substring(0, e.getMessage().indexOf(" ")).substring(1);
        }

        if (cmd.equalsIgnoreCase("help") || cmd.equalsIgnoreCase("minecraft:help") || cmd.equalsIgnoreCase("bukkit:help")) {
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
