package org.china2b2t.twilightx.commands

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.china2b2t.twilightx.MuteStorage

class UnmuteHandler: CommandExecutor {
    private val prefix =
        ChatColor.GOLD.toString() + ChatColor.BOLD + "China2B2T" + ChatColor.RESET.toString() + ChatColor.YELLOW + " >> " + ChatColor.WHITE

    override fun onCommand(
        sender: CommandSender?,
        command: Command?,
        label: String?,
        args: Array<out String>?
    ): Boolean {
        if (!sender?.isOp!!) {
            sender.sendMessage(prefix + "Hey, you...don't do that >:(")
            return true
        }

        if (args?.size != 1) {
            sender.sendMessage(prefix + "Usage: /unmute <player>")
            return true
        }

        try {
            val player = Bukkit.getPlayer(args[0])

            if (!MuteStorage.isMuted(player)) {
                sender.sendMessage(prefix + "${args[0]} has not been muted!")
                return true
            }

            MuteStorage.setMuted(player, false)
            sender.sendMessage(prefix + "Unmuted ${args[0]} successfully!")
        } catch(e: Exception) {
            sender.sendMessage(prefix + "Cannot find player ${args[0]}")
        }

        return true
    }
}