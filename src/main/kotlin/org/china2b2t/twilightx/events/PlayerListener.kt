package org.china2b2t.twilightx.events

import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.china2b2t.twilightx.MuteStorage.Companion.isMuted
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.china2b2t.twilightx.MuteStorage
import org.china2b2t.twilightx.TwilightX
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.player.PlayerCommandPreprocessEvent

class PlayerListener : Listener {
    @EventHandler
    fun onChat(e: AsyncPlayerChatEvent) {
        if (isMuted(e.player)) {
            e.isCancelled = true
            var message = e.message
            if (TwilightX.config.isSet("chat-coloring") && TwilightX.config.getBoolean("chat-coloring")) {
                if (message[0] == '>') {
                    message = ChatColor.GREEN.toString() + message
                }
            }

            // Fake a echo
            e.player.sendMessage("<" + e.player.name + "> " + message)
            return
        }
        if (TwilightX.config.isSet("chat-coloring") && TwilightX.config.getBoolean("chat-coloring")) {
            if (e.message[0] == '>') {
                e.message = ChatColor.GREEN.toString() + e.message
            }
        }
    }

    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        if (TwilightX.config.isSet("disable-join-quit-msg") && TwilightX.config.getBoolean("disable-join-quit-msg")) {
            e.joinMessage = null
        }
    }

    @EventHandler
    fun onQuit(e: PlayerQuitEvent) {
        if (TwilightX.config.isSet("deop-when-quit") && TwilightX.config.getBoolean("deop-when-quit")) {
            if (e.player.isOp) {
                e.player.isOp = false
            }
        }
        if (TwilightX.config.isSet("disable-join-quit-msg") && TwilightX.config.getBoolean("disable-join-quit-msg")) {
            e.quitMessage = null
        }
    }

    @EventHandler
    fun onCommand(e: PlayerCommandPreprocessEvent) {
        var cmd = ""
        cmd = if (e.message.indexOf(" ") == -1) {
            e.message.substring(1)
        } else {
            e.message.substring(0, e.message.indexOf(" ")).substring(1)
        }
        if (cmd.equals("help", ignoreCase = true) ||
            cmd.equals("minecraft:help", ignoreCase = true) ||
            cmd.equals("bukkit:help", ignoreCase = true)
        ) {
            if (TwilightX.config.isSet("enable-help") && TwilightX.config.getBoolean("enable-help")) {
                e.isCancelled = true
                val help = TwilightX.config.getStringList("help")
                for (msg in help) {
                    e.player.sendMessage(msg.replace('&', ChatColor.COLOR_CHAR))
                }
            }
        }
    }
}