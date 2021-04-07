package com.technovision.HuskHomesGUI;

import com.technovision.HuskHomesGUI.events.HomeEvents;
import com.technovision.HuskHomesGUI.playerdata.PlayerDataReader;
import org.bstats.bukkit.MetricsLite;
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

        // bStats initialisation
        new MetricsLite(this, 10969);
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
