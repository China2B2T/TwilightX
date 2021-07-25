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
import org.china2b2t.twilightx.ignore
import org.china2b2t.twilightx.ignored
import java.lang.Exception

class MsgHandler : CommandExecutor {
    private var time: HashMap<Player, Long> = HashMap()

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        commandLabel: String,
        args: Array<String>
    ): Boolean {
        if (sender is ConsoleCommandSender) {
            sender.sendMessage(ChatColor.RED.toString() + "仅玩家可用！")
        }

        if (args.size <= 1) {
            sender.sendMessage(ChatColor.RED.toString() + "Usage: /msg <player> <message>")
            return true
        }

        if (time.containsKey(sender as Player)) {
            if (System.currentTimeMillis() - time[sender]!! <= 3000) {
                return true
            }
        }

        time[sender] = System.currentTimeMillis()

        try {
            val target = Bukkit.getPlayer(args[0]) as Player

            val message = StringBuilder()
            for (i in 1 until args.size) {
                message.append(args[i])
                message.append(" ")
            }

            val echoMsg = ChatColor.LIGHT_PURPLE.toString() + "发至 " + args[0] + ": " + message.toString()
            val sendMsg = ChatColor.LIGHT_PURPLE.toString() + "来自 " + sender.name + ": " + message.toString()
            val send = TextComponent(sendMsg)
            val echo = TextComponent(echoMsg)

            send.clickEvent = ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + sender.name + " ")
            echo.clickEvent = ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + args[0] + " ")
            Bukkit.getPlayer(sender.name).spigot().sendMessage(echo)

            if (!target.ignored(sender as Player)) {
                target.spigot().sendMessage(send)
            }
        } catch (e: Exception) {
            sender.sendMessage(ChatColor.RED.toString() + "该玩家不在线!")
        }
        return true
    }
}