package org.china2b2t.twilightx.commands

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.china2b2t.twilightx.MuteStorage

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
                try {
                    var player = Bukkit.getPlayer(args[0])

                    if (MuteStorage.isMuted(player)) {
                        sender.sendMessage(prefix + "Already muted ${player.name}")
                        return true
                    }

                    MuteStorage.setMuted(player, true)

                    sender.sendMessage(prefix + "Muted ${player.name}")
                } catch(e: Exception) {
                    sender.sendMessage(prefix + "Cannot find player ${args[0]}")
                }
            }
        }
        return true
    }
}