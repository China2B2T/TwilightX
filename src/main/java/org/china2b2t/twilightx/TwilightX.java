package org.china2b2t.twilightx;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.china2b2t.twilightx.commands.KillHandler;
import org.china2b2t.twilightx.commands.MsgHandler;
import org.china2b2t.twilightx.commands.TwiloadHandler;
import org.china2b2t.twilightx.events.PlayerListener;

public class TwilightX extends JavaPlugin {
    public static JavaPlugin instance;
    public static YamlConfiguration config;

    @Override
    public void onLoad() {
        this.getLogger().log(Level.INFO, ChatColor.AQUA + "> TwilightX by China2B2T <");
        this.getLogger().log(Level.INFO, ChatColor.AQUA + "Thanks for using!");
    }

    @Override
    public void onEnable() {
        instance = this;

        File config = new File(this.getDataFolder(), "config.yml");
        if (!config.exists()) {
            saveDefaultConfig();
        }

        TwilightX.config = (YamlConfiguration) this.getConfig();

        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        this.getServer().getPluginCommand("kill").setExecutor(new KillHandler());
        this.getServer().getPluginCommand("twiload").setExecutor(new TwiloadHandler());
        this.getServer().getPluginCommand("msg").setExecutor(new MsgHandler());

        this.getLogger().log(Level.INFO, ChatColor.GREEN + "> TwilightX by China2B2T <");
        this.getLogger().log(Level.INFO, ChatColor.GREEN+ "Loaded successfully!");
    }

    public static void reload() {
        instance.reloadConfig();
        config = (YamlConfiguration) instance.getConfig();
    }
}
