package org.china2b2t.twilightx.commands;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;

import com.google.common.collect.Lists;

import org.bukkit.plugin.java.JavaPlugin;
import org.china2b2t.twilightx.utils.*;

public class PluginmgrHandler implements CommandExecutor {
    private String prefix = ChatColor.GOLD.toString() + ChatColor.BOLD + "China2B2T" + ChatColor.RESET.toString() + ChatColor.YELLOW + " >> " + ChatColor.WHITE;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (!sender.hasPermission("twilightx.pluginmgr")) {
            sender.sendMessage(prefix + "Hey, you...don't do that >:(");
            return true;
        }
        if (args.length < 2) {
            sender.sendMessage(prefix + "Usage: /pluginmgr <load|reload|unload> <filename (without .jar suffix)>");
            return false;
        }

        String action = args[0].toLowerCase();
        String pluginName = args[1];
        try {
            if (action.equals("unload")) {
                unloadPlugin(pluginName, sender);
            } else if (action.equals("load")) {
                loadPlugin(pluginName, sender);
            } else if (action.equals("reload")) {
                reloadPlugin(pluginName, sender);
            } else {
                sender.sendMessage(prefix + "Invalid action specified.");
            }
        } catch (Exception e) {
            sender.sendMessage(prefix + "Error with " + pluginName + ": " + e.toString());
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) throws IllegalArgumentException {
        List<String> tabs = Lists.newArrayList();
        if (args.length > 1) {
            String action = args[0].toLowerCase();
            if (action.equals("unload")) {
                for (Plugin plugin : Bukkit.getServer().getPluginManager().getPlugins()) {
                    tabs.add(plugin.getName());
                }
            } else if (action.equals("load")) {
                for (File file : new File("plugins").listFiles()) {
                    if (file.isFile() && file.getName().toLowerCase().endsWith(".jar"))
                        tabs.add(file.getName().substring(0, file.getName().length() - 4));
                }
            }
        }
        return tabs;
    }

    private void unloadPlugin(String pluginName, CommandSender sender) throws Exception {
        SimplePluginManager manager = (SimplePluginManager) Bukkit.getServer().getPluginManager();

        List<Plugin> plugins = ReflectionHelper.getPrivateValue(SimplePluginManager.class, manager, "plugins");
        Map<String, Plugin> lookupNames = ReflectionHelper.getPrivateValue(SimplePluginManager.class, manager, "lookupNames");
        SimpleCommandMap commandMap = ReflectionHelper.getPrivateValue(SimplePluginManager.class, manager, "commandMap");
        Map<String, Command> knownCommands = ReflectionHelper.getPrivateValue(SimpleCommandMap.class, commandMap, "knownCommands");

        for (Plugin plugin : manager.getPlugins()) {
            if (!plugin.getDescription().getName().equalsIgnoreCase(pluginName)) continue;

            manager.disablePlugin(plugin);
            plugins.remove(plugin);
            lookupNames.remove(pluginName);

            Iterator<Map.Entry<String, Command>> it = knownCommands.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Command> entry = (Map.Entry) it.next();
                if (!(entry.getValue() instanceof PluginCommand)) continue;
                PluginCommand command = (PluginCommand) entry.getValue();
                if (command.getPlugin() == plugin) {
                    command.unregister(commandMap);
                    it.remove();
                }
            }

            sender.sendMessage(prefix + "Unloaded " + pluginName + " successfully!");
            return;
        }

        sender.sendMessage(prefix + "Can't found loaded plugin: " + pluginName);
    }


    private void loadPlugin(String pluginName, CommandSender sender) {
        PluginManager manager = Bukkit.getServer().getPluginManager();
        File pluginFile = new File("plugins", pluginName + ".jar");

        if (!pluginFile.exists() || !pluginFile.isFile()) {
            sender.sendMessage(prefix + "Error loading " + pluginName + ".jar, no plugin with that name was found.");
            return;
        }

        try {
            Plugin plugin = manager.loadPlugin(pluginFile);
            plugin.onLoad();
            manager.enablePlugin(plugin);

            sender.sendMessage(prefix + "Loaded " + pluginName + " successfully!");
        } catch (Exception ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not load '" + pluginFile.getPath() + "' in folder '" + pluginFile.getParentFile().getPath() + "'", ex);
            sender.sendMessage(prefix + "Error loading " + pluginName + ".jar, for error details please see the console.");
        }
    }

    private void reloadPlugin(String pluginName, CommandSender sender) {
        SimplePluginManager manager = (SimplePluginManager) Bukkit.getServer().getPluginManager();

        List<Plugin> plugins = ReflectionHelper.getPrivateValue(SimplePluginManager.class, manager, "plugins");
        Map<String, Plugin> lookupNames = ReflectionHelper.getPrivateValue(SimplePluginManager.class, manager, "lookupNames");
        SimpleCommandMap commandMap = ReflectionHelper.getPrivateValue(SimplePluginManager.class, manager, "commandMap");
        Map<String, Command> knownCommands = ReflectionHelper.getPrivateValue(SimpleCommandMap.class, commandMap, "knownCommands");

        for (Plugin plugin : manager.getPlugins()) {
            if (!plugin.getDescription().getName().equalsIgnoreCase(pluginName)) continue;

            manager.disablePlugin(plugin);
            plugins.remove(plugin);
            lookupNames.remove(pluginName);

            Iterator<Map.Entry<String, Command>> it = knownCommands.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Command> entry = (Map.Entry) it.next();
                if (!(entry.getValue() instanceof PluginCommand)) continue;
                PluginCommand command = (PluginCommand) entry.getValue();
                if (command.getPlugin() == plugin) {
                    command.unregister(commandMap);
                    it.remove();
                }
            }

            File pluginFile = ReflectionHelper.getPrivateValue(JavaPlugin.class, (JavaPlugin)plugin, "file");

            try {
                plugin = manager.loadPlugin(pluginFile);
                plugin.onLoad();
                manager.enablePlugin(plugin);

                sender.sendMessage(prefix + "Reloaded " + pluginName + " successfully!");
            } catch (Exception ex) {
                Bukkit.getLogger().log(Level.SEVERE, "Could not load '" + pluginFile.getPath() + "' in folder '" + pluginFile.getParentFile().getPath() + "'", ex);
                sender.sendMessage(prefix + "Error loading " + pluginName + ".jar, this plugin must be reloaded by restarting the server.");
            }

            return;
        }

        sender.sendMessage(prefix + "Can't found loaded plugin: " + pluginName);
    }
}