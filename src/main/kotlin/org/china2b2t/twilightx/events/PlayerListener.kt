package org.china2b2t.twilightx.events

import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.*
import org.china2b2t.twilightx.GLimitStorage.Companion.isLimited
import org.china2b2t.twilightx.MuteStorage.Companion.isMuted
import org.china2b2t.twilightx.TwilightX

class PlayerListener : Listener {
    @EventHandler
    fun onChat(e: AsyncPlayerChatEvent) {
        if (isMuted(e.player)) {
            if (TwilightX.config.isSet("chat-coloring") && TwilightX.config.getBoolean("chat-coloring")) {
                // getEnumConstants(ChatColor.class)

                if (e.player.isOp()) {
                    val cl = TwilightX.config.getMapList("chat-colors.admin")
                    cl.forEach { i ->
                        if (i["char"] == e.message[0]) {
                            e.message = ChatColor.getByChar(i["color"].toString()).toString() + e.message
                            return
                        }
                    }
                } else if (e.player.hasPermission("china2b2t.donor")) {
                    val cl = TwilightX.config.getMapList("chat-colors.donor")
                    cl.forEach { i ->
                        if (i["char"] == e.message[0]) {
                            e.message = ChatColor.getByChar(i["color"].toString()).toString() + e.message
                            return
                        }
                    }
                } else {
                    val cl = TwilightX.config.getMapList("chat-colors.default")
                    cl.forEach { i ->
                        if (i["char"] == e.message[0]) {
                            e.message = ChatColor.getByChar(i["color"].toString()).toString() + e.message
                            return
                        }
                    }
                }
            }

            e.isCancelled = true

            // Fake a echo
            e.player.sendMessage("<" + e.player.name + "> " + e.message)
            return
        }
        if (TwilightX.config.isSet("chat-coloring") && TwilightX.config.getBoolean("chat-coloring")) {
            if (e.player.isOp()) {
                val cl = TwilightX.config.getMapList("chat-colors.admin")
                cl.forEach { i ->
                    if (i["char"] == e.message[0]) {
                        e.message = ChatColor.getByChar(i["color"].toString()).toString() + e.message
                        return
                    }
                }
            } else if (e.player.hasPermission("china2b2t.donor")) {
                val cl = TwilightX.config.getMapList("chat-colors.donor")
                cl.forEach { i ->
                    if (i["char"] == e.message[0]) {
                        e.message = ChatColor.getByChar(i["color"].toString()).toString() + e.message
                        return
                    }
                }
            } else {
                val cl = TwilightX.config.getMapList("chat-colors.default")
                cl.forEach { i ->
                    if (i["char"] == e.message[0]) {
                        e.message = ChatColor.getByChar(i["color"].toString()).toString() + e.message
                        return
                    }
                }
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

    @EventHandler
    fun onSprint(e: PlayerToggleSprintEvent) {
        if (e.isSprinting && !e.isCancelled && isLimited(e.player)) {
            e.isCancelled = true
        }
    }

    @EventHandler
    fun onSwitchWorld(e: PlayerChangedWorldEvent) {
        if (isLimited(e.player)) {
            e.player.damage(5.0)
        }
    }
}