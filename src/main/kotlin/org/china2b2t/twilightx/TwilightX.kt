package org.china2b2t.twilightx

import java.io.File
import java.util.logging.Level

import org.bukkit.ChatColor
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import org.china2b2t.twilightx.commands.KillHandler
import org.china2b2t.twilightx.commands.MsgHandler
import org.china2b2t.twilightx.commands.PluginmgrHandler
import org.china2b2t.twilightx.commands.TwiloadHandler
import org.china2b2t.twilightx.events.PlayerListener

class TwilightX: JavaPlugin() {
    companion object {
        @JvmStatic
        var instance = TwilightX()
        @JvmStatic
        var config = YamlConfiguration()
    }

    override fun onLoad() {
        this.getLogger().log(Level.INFO, ChatColor.AQUA.toString() + "> TwilightX by China2B2T <")
        this.getLogger().log(Level.INFO, ChatColor.AQUA.toString() + "Thanks for using!")

        TwilightX.instance = this
    }

    override fun onEnable() {
        val config = File(this.getDataFolder(), "config.yml")
        if (!config.exists()) {
            this.saveDefaultConfig()
        }
    }
}