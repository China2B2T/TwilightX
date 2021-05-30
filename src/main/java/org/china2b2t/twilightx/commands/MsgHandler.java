package org.china2b2t.twilightx.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.china2b2t.twilightx.utils.TextBuilder;

public class MsgHandler implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (args.length <= 1) {
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
                    message.append(" ");
                }

                TextBuilder send = new TextBuilder(ChatColor.LIGHT_PURPLE + "From " + sender.getName() + ": " + message.toString())
                    .setClickEvent(TextBuilder.ClickEventType.SUGGEST_TEXT, "/msg" + sender.getName())
                    .buildText();
                TextBuilder echo = new TextBuilder(ChatColor.LIGHT_PURPLE + "To " + args[0] + ": " + message.toString())
                    .setClickEvent(TextBuilder.ClickEventType.SUGGEST_TEXT, "/msg" + args[0])
                    .buildText();
                echo.sendMessage((Player) Bukkit.getOfflinePlayer(sender.getName()));
                send.sendMessage(target);
            }
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "Player not found!");
        }

        return true;
    }
}
