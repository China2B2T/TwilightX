package org.china2b2t.twilightx

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import org.china2b2t.twilightx.commands.*
import org.china2b2t.twilightx.events.PlayerListener
import java.io.File
import java.io.IOException


class TwilightX : JavaPlugin() {
    override fun onLoad() {
        this.logger
            .log(java.util.logging.Level.INFO, org.bukkit.ChatColor.AQUA.toString() + "> TwilightX by China2B2T <")
        this.logger
            .log(java.util.logging.Level.INFO, org.bukkit.ChatColor.AQUA.toString() + "Thanks for using!")
    }

    override fun onEnable() {
        instance = this

        val config = File(this.dataFolder, "config.yml")
        if (!config.exists()) {
            saveDefaultConfig()
        }

        val data = File(this.dataFolder, "playerdata.yml")
        if(!data.exists()) {
            saveDefaultConfig("playerdata.yml")
        }

        Companion.config = this.config as YamlConfiguration
        Companion.data = this.load("playerdata.yml")!!

        this.server.pluginManager.registerEvents(PlayerListener(), this)
        this.server.getPluginCommand("kill").executor = KillHandler()
        this.server.getPluginCommand("twiload").executor = TwiloadHandler()
        this.server.getPluginCommand("msg").executor = MsgHandler()
        this.server.getPluginCommand("pluginmgr").executor = PluginmgrHandler()
        this.server.getPluginCommand("mute").executor = MuteHandler()
        this.server.getPluginCommand("unmute").executor = UnmuteHandler()

        this.logger
            .log(java.util.logging.Level.INFO, org.bukkit.ChatColor.GREEN.toString() + "> TwilightX by China2B2T <")
        this.logger
            .log(java.util.logging.Level.INFO, org.bukkit.ChatColor.GREEN.toString() + "Loaded successfully!")
    }

    companion object {
        lateinit var instance: JavaPlugin
        lateinit var config: YamlConfiguration
        lateinit var data: YamlConfiguration

        fun reload() {
            instance?.reloadConfig()
            config = instance?.config as YamlConfiguration
            data = instance?.load("playerdata.yml")!!
        }
    }
}

fun JavaPlugin.load(fileName: String): YamlConfiguration? {
    val file = File(this.dataFolder, fileName)
    if (!file.exists()) {
        try {
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return YamlConfiguration.loadConfiguration(file)
}

fun JavaPlugin.saveDefaultConfig(fileName: String) {
    this.saveResource(fileName, true)
}
