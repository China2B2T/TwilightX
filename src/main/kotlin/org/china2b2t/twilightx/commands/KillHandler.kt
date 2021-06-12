package org.china2b2t.twilightx.commands

import org.bukkit.entity.Player
import org.bukkit.Bukkit
import java.lang.StringBuilder
import net.md_5.bungee.api.chat.ClickEvent
import org.bukkit.ChatColor
import org.bukkit.command.*
import org.china2b2t.twilightx.TwilightX
import org.bukkit.entity.Damageable
import kotlin.Throws
import java.lang.IllegalArgumentException
import org.bukkit.plugin.SimplePluginManager
import org.china2b2t.twilightx.utils.ReflectionHelper
import org.bukkit.plugin.java.JavaPlugin

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
             Bukkit.dispatchCommand(sender, "minecraft:kill " + args[0]);
        }

        if (TwilightX.config.isSet("enable-suicide")) {
            if (TwilightX.config.getBoolean("enable-suicide")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "minecraft:kill" + args[0])
            } else {
                sender.sendMessage(prefix + "Suicide disabled!")
            }
        } else {
            sender.sendMessage(prefix + "Suicide disabled!")
        }

        return true
    }
}