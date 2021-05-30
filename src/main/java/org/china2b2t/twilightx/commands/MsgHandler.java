package org.china2b2t.twilightx.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent.Action;

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

                String echoMsg = ChatColor.LIGHT_PURPLE + "To " + args[0] + ": " + message.toString();
                String sendMsg = ChatColor.LIGHT_PURPLE + "From " + sender.getName() + ": " + message.toString();
                TextComponent send = new TextComponent(sendMsg);
                TextComponent echo = new TextComponent(echoMsg);
                send.setClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, "/msg " + sender.getName()));
                echo.setClickEvent(new ClickEvent(Action.SUGGEST_COMMAND, "/msg" + args[0]));
                ((Player) Bukkit.getOfflinePlayer(sender.getName())).spigot().sendMessage(echo);
                target.spigot().sendMessage(send);
            }
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "Player not found!");
        }

        return true;
    }
}
