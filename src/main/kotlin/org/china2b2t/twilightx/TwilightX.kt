package org.china2b2t.twilightx

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import org.china2b2t.twilightx.commands.KillHandler
import org.china2b2t.twilightx.commands.MsgHandler
import org.china2b2t.twilightx.commands.PluginmgrHandler
import org.china2b2t.twilightx.commands.TwiloadHandler
import org.china2b2t.twilightx.events.PlayerListener


class TwilightX : JavaPlugin() {
    override fun onLoad() {
        this.logger
            .log(java.util.logging.Level.INFO, org.bukkit.ChatColor.AQUA.toString() + "> TwilightX by China2B2T <")
        this.logger
            .log(java.util.logging.Level.INFO, org.bukkit.ChatColor.AQUA.toString() + "Thanks for using!")
    }

    override fun onEnable() {
        instance = this

        val config: java.io.File = java.io.File(this.dataFolder, "config.yml")
        if (!config.exists()) {
            saveDefaultConfig()
        }

        Companion.config = this.config as YamlConfiguration?

        this.server.pluginManager.registerEvents(PlayerListener(), this)
        this.server.getPluginCommand("kill").executor = KillHandler()
        this.server.getPluginCommand("twiload").executor = TwiloadHandler()
        this.server.getPluginCommand("msg").executor = MsgHandler()
        this.server.getPluginCommand("pluginmgr").executor = PluginmgrHandler()

        this.logger
            .log(java.util.logging.Level.INFO, org.bukkit.ChatColor.GREEN.toString() + "> TwilightX by China2B2T <")
        this.logger
            .log(java.util.logging.Level.INFO, org.bukkit.ChatColor.GREEN.toString() + "Loaded successfully!")
    }

    companion object {
        var instance: JavaPlugin? = null
        var config: YamlConfiguration? = null
        // var data: YamlConfiguration? = null

        fun reload() {
            instance?.reloadConfig()
            config = instance?.config as YamlConfiguration
        }
    }
}