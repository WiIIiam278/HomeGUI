package com.technovision.HuskHomesGUI;

import com.technovision.HuskHomesGUI.events.HomeEvents;
import com.technovision.HuskHomesGUI.playerdata.PlayerDataReader;
import org.bukkit.plugin.java.JavaPlugin;

public class HuskHomesGUI extends JavaPlugin {

    public static HuskHomesGUI PLUGIN;
    public static PlayerDataReader dataReader;

    @Override
    public void onEnable() {
        PLUGIN = this;
        dataReader = new PlayerDataReader();
        loadConfig();

        // Register Events
        getServer().getPluginManager().registerEvents(new HomeEvents(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled HuskHomesGUI");
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        getConfig().options().copyHeader(true);
        saveConfig();
    }
}
