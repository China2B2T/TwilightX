package org.china2b2t.twilightx.commands

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.china2b2t.twilightx.TwilightX

class KillHandler : CommandExecutor {
    private val prefix =
        ChatColor.GOLD.toString() + ChatColor.BOLD + "China2B2T" + ChatColor.RESET.toString() + ChatColor.YELLOW + " >> " + ChatColor.WHITE

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        commandLabel: String,
        args: Array<String>
    ): Boolean {
        if (sender.isOp) {
             Bukkit.dispatchCommand(sender, "minecraft:kill " + (if (args.isEmpty()) "" else args[0]))
            return true
        }

        if (TwilightX.config.isSet("enable-suicide")) {
            if (TwilightX.config.getBoolean("enable-suicide")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "minecraft:kill " + sender.name)
            } else {
                sender.sendMessage(prefix + "Suicide disabled!")
            }
        } else {
            sender.sendMessage(prefix + "Suicide disabled!")
        }

        return true
    }
}