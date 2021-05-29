package org.china2b2t.twilightx.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.china2b2t.twilightx.TwilightX;

public class TwiloadHandler implements CommandExecutor {
    private String prefix = ChatColor.GOLD.toString() + ChatColor.BOLD + "China2B2T" + ChatColor.RESET.toString() + ChatColor.YELLOW + " >> " + ChatColor.WHITE;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (sender.hasPermission("twilightx.admin")) {
            TwilightX.reload();
            sender.sendMessage(prefix + "Reloaded successfully!");
            return true;
        }

        sender.sendMessage("Hey, you...don't do that >:(");
        return true;
    }
}
