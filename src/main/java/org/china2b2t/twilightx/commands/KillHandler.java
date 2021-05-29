package org.china2b2t.twilightx.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Damageable;
import org.china2b2t.twilightx.TwilightX;

public class KillHandler implements CommandExecutor {
    private String prefix = ChatColor.GOLD.toString() + ChatColor.BOLD + "China2B2T" + ChatColor.RESET.toString() + ChatColor.YELLOW + " >> " + ChatColor.WHITE;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        // if (sender.isOp()) {
        //     Bukkit.dispatchCommand(sender, "/minecraft:kill " + args[0]);
        // }

        if (TwilightX.config.isSet("enable-suicide")) {
            if (TwilightX.config.getBoolean("enable-suicide")) {
                ((Damageable)sender).damage(32767.0);
            } else {
                sender.sendMessage(prefix + "Suicide disabled!");
            }
        } else {
            sender.sendMessage(prefix + "Suicide disabled!");
        }

        return true;
    }
}
