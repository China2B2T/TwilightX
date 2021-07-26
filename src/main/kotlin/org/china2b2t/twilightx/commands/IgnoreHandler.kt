package org.china2b2t.twilightx.commands

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.OfflinePlayer
import org.bukkit.command.*
import org.china2b2t.twilightx.ignore
import org.china2b2t.twilightx.ignored
import org.china2b2t.twilightx.unignore
import java.lang.Exception

class IgnoreHandler : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        commandLabel: String,
        args: Array<String>
    ): Boolean {
        if (sender is ConsoleCommandSender) {
            sender.sendMessage(ChatColor.RED.toString() + "Player only!")
            return true
        }

        if (args.size != 1) {
            sender.sendMessage(ChatColor.RED.toString() + "Usage: /ignore <Player>")
            return true
        }

        sender as OfflinePlayer

        try {
            val tag = Bukkit.getPlayer(args[0])

            if (!sender.ignored(tag)) {
                sender.ignore(tag)
                sender.sendMessage(ChatColor.GREEN.toString() + "成功屏蔽来自${tag.name}的私信")
            } else {
                sender.unignore(tag)
                sender.sendMessage(ChatColor.GREEN.toString() + "取消了对来自${tag.name}的私信的屏蔽")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            sender.sendMessage("Player not found!")
            return true
        }
        return true
    }
}