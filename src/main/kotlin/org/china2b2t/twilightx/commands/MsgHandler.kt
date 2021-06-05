package org.china2b2t.twilightx.commands

import org.bukkit.entity.Player
import org.bukkit.Bukkit
import java.lang.StringBuilder
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.ChatColor
import org.bukkit.command.*
import org.china2b2t.twilightx.TwilightX
import org.bukkit.entity.Damageable
import kotlin.Throws
import java.lang.IllegalArgumentException
import org.bukkit.plugin.SimplePluginManager
import org.china2b2t.twilightx.utils.ReflectionHelper
import org.bukkit.plugin.java.JavaPlugin
import java.lang.Exception

class MsgHandler : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        commandLabel: String,
        args: Array<String>
    ): Boolean {
        if (args.size <= 1) {
            sender.sendMessage(ChatColor.RED.toString() + "Usage: /msg <player> <message>")
            return true
        }
        try {
            val target = Bukkit.getOfflinePlayer(args[0]) as Player
            if (!target.isOnline) {
                sender.sendMessage(ChatColor.RED.toString() + "You are sending a message to a offline player!")
            } else {
                val message = StringBuilder()
                for (i in 1 until args.size) {
                    message.append(args[i])
                    message.append(" ")
                }
                val echoMsg = ChatColor.LIGHT_PURPLE.toString() + "To " + args[0] + ": " + message.toString()
                val sendMsg = ChatColor.LIGHT_PURPLE.toString() + "From " + sender.name + ": " + message.toString()
                val send = TextComponent(sendMsg)
                val echo = TextComponent(echoMsg)
                send.clickEvent = ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + sender.name)
                echo.clickEvent = ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + args[0])
                (Bukkit.getOfflinePlayer(sender.name) as Player).spigot().sendMessage(echo)
                target.spigot().sendMessage(send)
            }
        } catch (e: Exception) {
            sender.sendMessage(ChatColor.RED.toString() + "Player not found!")
        }
        return true
    }
}