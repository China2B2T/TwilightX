package org.china2b2t.twilightx.commands

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.china2b2t.twilightx.GLimitStorage
import org.china2b2t.twilightx.MuteStorage

class GLHandler : CommandExecutor {
    private val prefix =
        ChatColor.GOLD.toString() + ChatColor.BOLD + "China2B2T" + ChatColor.RESET.toString() + ChatColor.YELLOW + " >> " + ChatColor.WHITE

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        commandLabel: String,
        args: Array<String>
    ): Boolean {
        if (!sender?.isOp!!) {
            sender.sendMessage(prefix + "Hey, you...don't do that >:(")
            return true
        }

        if (args?.size != 1) {
            sender.sendMessage(prefix + "Usage: /gl <player>")
        } else {
            try {
                var player = Bukkit.getPlayer(args[0])

                if (player.isOp) {
                    sender.sendMessage(prefix + "What are you doing?")
                    return true
                }

                if (GLimitStorage.isLimited(player)) {
                    sender.sendMessage(prefix + "Already limited ${player.name}")
                    return true
                }

                GLimitStorage.setLimited(player, true)

                sender.sendMessage(prefix + "Limited ${player.name}")
            } catch(e: Exception) {
                sender.sendMessage(prefix + "Cannot find player ${args[0]}")
            }
        }

        return true
    }
}