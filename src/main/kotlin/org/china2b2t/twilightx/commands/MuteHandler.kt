package org.china2b2t.twilightx.commands

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class MuteHandler :CommandExecutor {
    private val prefix =
        ChatColor.GOLD.toString() + ChatColor.BOLD + "China2B2T" + ChatColor.RESET.toString() + ChatColor.YELLOW + " >> " + ChatColor.WHITE

    override fun onCommand(
        sender: CommandSender?,
        command: Command?,
        commandLabel: String?,
        args: Array<out String>?
    ): Boolean {
        if (!sender?.isOp!!) {
            sender.sendMessage(prefix + "Hey, you...don't do that >:(")
        } else {
            if (args?.size != 1) {
                sender.sendMessage(prefix + "Usage: /mute <player>")
            } else {
                TODO("Add a configuration system and fill this")
            }
        }
        return true
    }
}