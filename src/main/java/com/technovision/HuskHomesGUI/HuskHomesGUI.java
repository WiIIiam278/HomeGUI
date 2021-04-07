package com.technovision.HuskHomesGUI;

import com.technovision.HuskHomesGUI.commands.HomeCommand;
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

        //Events
        getServer().getPluginManager().registerEvents(new HomeEvents(), this);

        //Commands
        getCommand(HomeCommand.HOME).setExecutor(new HomeCommand());
        getCommand(HomeCommand.H).setExecutor(new HomeCommand());
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage( "[HuskHomesGUI] Plugin has been disabled.");
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        getConfig().options().copyHeader(true);
        saveConfig();
    }
}
