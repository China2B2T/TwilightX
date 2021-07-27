package org.china2b2t.twilightx.commands

import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import org.china2b2t.twilightx.ignored
import java.lang.Exception
import java.lang.StringBuilder

class ReplyHandler : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        commandLabel: String,
        args: Array<String>
    ): Boolean {
        if (sender is ConsoleCommandSender) {
            sender.sendMessage(ChatColor.RED.toString() + "仅玩家可用！")
        }

        if (MsgHandler.time.containsKey(sender as Player)) {
            if (System.currentTimeMillis() - MsgHandler.time[sender]!! <= 3000) {
                return true
            }
        }

        MsgHandler.time[sender] = System.currentTimeMillis()

        try {
            if (!MsgHandler.recent.containsKey(sender)) {
                sender.sendMessage(ChatColor.RED.toString() + "没有最近的私聊活动")
                return true
            }

            val target = MsgHandler.recent[sender]

            if (target != null) {
                if (!target.isOnline) {
                    sender.sendMessage(ChatColor.RED.toString() + "对方已下线")
                    return true
                }
            } else {
                sender.sendMessage(ChatColor.RED.toString() + "没有最近的私聊活动")
                return true
            }

            MsgHandler.recent[sender] = target
            MsgHandler.recent[target as Player] = sender

            val message = StringBuilder()
            for (i in 0 until args.size) {
                message.append(args[i])
                message.append(" ")
            }

            val echoMsg = ChatColor.LIGHT_PURPLE.toString() + "发至 " + target.name + ": " + message.toString()
            val sendMsg = ChatColor.LIGHT_PURPLE.toString() + "来自 " + sender.name + ": " + message.toString()
            val send = TextComponent(sendMsg)
            val echo = TextComponent(echoMsg)

            send.clickEvent = ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + sender.name + " ")
            echo.clickEvent = ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + target.name + " ")
            Bukkit.getPlayer(sender.name).spigot().sendMessage(echo)

            if (!target.ignored(sender)) {
                target.spigot().sendMessage(send)
            }
        } catch (e: Exception) {
            sender.sendMessage(ChatColor.RED.toString() + "没有最近的私聊活动")
        }
        return true
    }
}