package org.china2b2t.twilightx.commands

import com.google.common.collect.Lists
import org.bukkit.entity.Player
import org.bukkit.Bukkit
import java.lang.StringBuilder
import net.md_5.bungee.api.chat.ClickEvent
import org.bukkit.ChatColor
import org.bukkit.command.*
import org.china2b2t.twilightx.TwilightX
import org.bukkit.entity.Damageable
import org.bukkit.plugin.Plugin
import kotlin.Throws
import java.lang.IllegalArgumentException
import org.bukkit.plugin.SimplePluginManager
import org.china2b2t.twilightx.utils.ReflectionHelper
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.lang.Exception
import java.util.logging.Level

class PluginmgrHandler : CommandExecutor {
    private val prefix =
        ChatColor.GOLD.toString() + ChatColor.BOLD + "China2B2T" + ChatColor.RESET.toString() + ChatColor.YELLOW + " >> " + ChatColor.WHITE

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        commandLabel: String,
        args: Array<String>
    ): Boolean {
        if (!sender.hasPermission("twilightx.pluginmgr")) {
            sender.sendMessage(prefix + "Hey, you...don't do that >:(")
            return true
        }
        if (args.size < 2) {
            sender.sendMessage(prefix + "Usage: /pluginmgr <load|reload|unload> <filename (without .jar suffix)>")
            return false
        }
        val action = args[0].toLowerCase()
        val pluginName = args[1]
        try {
            if (action == "unload") {
                unloadPlugin(pluginName, sender)
            } else if (action == "load") {
                loadPlugin(pluginName, sender)
            } else if (action == "reload") {
                reloadPlugin(pluginName, sender)
            } else {
                sender.sendMessage(prefix + "Invalid action specified.")
            }
        } catch (e: Exception) {
            sender.sendMessage(prefix + "Error with " + pluginName + ": " + e.toString())
        }
        return true
    }

    @Throws(IllegalArgumentException::class)
    fun onTabComplete(sender: CommandSender?, command: Command?, alias: String?, args: Array<String>): List<String> {
        val tabs: MutableList<String> = Lists.newArrayList()
        if (args.size > 1) {
            val action = args[0].toLowerCase()
            if (action == "unload") {
                for (plugin in Bukkit.getServer().pluginManager.plugins) {
                    tabs.add(plugin.name)
                }
            } else if (action == "load") {
                for (file in File("plugins").listFiles()) {
                    if (file.isFile && file.name.toLowerCase().endsWith(".jar")) tabs.add(
                        file.name.substring(
                            0,
                            file.name.length - 4
                        )
                    )
                }
            }
        }
        return tabs
    }

    @Throws(Exception::class)
    private fun unloadPlugin(pluginName: String, sender: CommandSender) {
        val manager = Bukkit.getServer().pluginManager as SimplePluginManager
        val plugins = ReflectionHelper.getPrivateValue<MutableList<Plugin>, SimplePluginManager>(
            SimplePluginManager::class.java, manager, "plugins"
        )

        val lookupNames = ReflectionHelper.getPrivateValue<MutableMap<String, Plugin>, SimplePluginManager>(
            SimplePluginManager::class.java, manager, "lookupNames"
        )

        val commandMap = ReflectionHelper.getPrivateValue<SimpleCommandMap, SimplePluginManager>(
            SimplePluginManager::class.java, manager, "commandMap"
        )

        val knownCommands = ReflectionHelper.getPrivateValue<MutableMap<String, Command>, SimpleCommandMap>(
            SimpleCommandMap::class.java, commandMap, "knownCommands"
        )

        for (plugin in manager.plugins) {
            if (!plugin.description.name.equals(pluginName, ignoreCase = true)) continue

            manager.disablePlugin(plugin)
            plugins.remove(plugin)
            lookupNames.remove(pluginName)

            val it: MutableIterator<Map.Entry<String, Command>> = knownCommands.entries.iterator()
            while (it.hasNext()) {
                val (_, value) = it.next()
                if (value !is PluginCommand) continue
                val command = value

                if (command.plugin === plugin) {
                    command.unregister(commandMap)
                    it.remove()
                }
            }

            sender.sendMessage(prefix + "Unloaded " + pluginName + " successfully!")
            return
        }

        sender.sendMessage(prefix + "Can't found loaded plugin: " + pluginName)
    }

    private fun loadPlugin(pluginName: String, sender: CommandSender) {
        val manager = Bukkit.getServer().pluginManager
        val pluginFile = File("plugins", "$pluginName.jar")

        if (!pluginFile.exists() || !pluginFile.isFile) {
            sender.sendMessage(prefix + "Error loading " + pluginName + ".jar, no plugin with that name was found.")
            return
        }

        try {
            val plugin = manager.loadPlugin(pluginFile)

            plugin.onLoad()
            manager.enablePlugin(plugin)
            sender.sendMessage(prefix + "Loaded " + pluginName + " successfully!")
        } catch (ex: Exception) {
            Bukkit.getLogger().log(
                Level.SEVERE,
                "Could not load '" + pluginFile.path + "' in folder '" + pluginFile.parentFile.path + "'",
                ex
            )
            sender.sendMessage(prefix + "Error loading " + pluginName + ".jar, for error details please see the console.")
        }
    }

    private fun reloadPlugin(pluginName: String, sender: CommandSender) {
        val manager = Bukkit.getServer().pluginManager as SimplePluginManager
        val plugins = ReflectionHelper.getPrivateValue<MutableList<Plugin>, SimplePluginManager>(
            SimplePluginManager::class.java, manager, "plugins"
        )

        val lookupNames = ReflectionHelper.getPrivateValue<MutableMap<String, Plugin>, SimplePluginManager>(
            SimplePluginManager::class.java, manager, "lookupNames"
        )

        val commandMap = ReflectionHelper.getPrivateValue<SimpleCommandMap, SimplePluginManager>(
            SimplePluginManager::class.java, manager, "commandMap"
        )

        val knownCommands = ReflectionHelper.getPrivateValue<MutableMap<String, Command>, SimpleCommandMap>(
            SimpleCommandMap::class.java, commandMap, "knownCommands"
        )

        for (plugin in manager.plugins) {
            if (!plugin.description.name.equals(pluginName, ignoreCase = true)) continue

            manager.disablePlugin(plugin)
            plugins.remove(plugin)
            lookupNames.remove(pluginName)

            val it: MutableIterator<Map.Entry<String, Command>> = knownCommands.entries.iterator()

            while (it.hasNext()) {
                val (_, value) = it.next()
                if (value !is PluginCommand) continue
                val command = value

                if (command.plugin === plugin) {
                    command.unregister(commandMap)
                    it.remove()
                }
            }

            val pluginFile =
                ReflectionHelper.getPrivateValue<File, JavaPlugin>(JavaPlugin::class.java, plugin as JavaPlugin, "file")
            try {
                val plug = manager.loadPlugin(pluginFile)

                plug.onLoad()
                manager.enablePlugin(plug)
                sender.sendMessage(prefix + "Reloaded " + pluginName + " successfully!")
            } catch (ex: Exception) {
                Bukkit.getLogger().log(
                    Level.SEVERE,
                    "Could not load '" + pluginFile.path + "' in folder '" + pluginFile.parentFile.path + "'",
                    ex
                )
                sender.sendMessage(prefix + "Error loading " + pluginName + ".jar, this plugin must be reloaded by restarting the server.")
            }

            return
        }

        sender.sendMessage(prefix + "Can't found loaded plugin: " + pluginName)
    }
}