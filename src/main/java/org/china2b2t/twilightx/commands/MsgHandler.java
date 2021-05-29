package org.china2b2t.twilightx.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MsgHandler implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (args.length <= 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /msg <player> <message>");
            return true;
        }

        try {
            Player target = (Player) Bukkit.getOfflinePlayer(args[0]);
            if (!target.isOnline()) {
                sender.sendMessage(ChatColor.RED + "You are sending a message to a offline player!");
            } else {
                StringBuilder message = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    message.append(args[i]);
                }

                target.sendMessage(ChatColor.LIGHT_PURPLE + "From " + sender.getName() + ": " + message.toString());
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "To " + args[0] + ": " + message.toString());
            }
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "Player not found!");
        }

        return true;
    }
}
