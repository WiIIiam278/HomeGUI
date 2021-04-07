package com.technovision.HuskHomesGUI.commands;

import com.technovision.HuskHomesGUI.gui.HomeGUI;
import me.william278.huskhomes2.HuskHomes;
import me.william278.huskhomes2.api.HuskHomesAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ViewHomes implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            HuskHomesAPI huskHomesAPI = HuskHomes.getInstance().getAPI();
            if (args.length == 0) {
                if (huskHomesAPI.getHomes(player).size() > 1) {
                    HomeGUI gui = new HomeGUI(player.getName(), player.getUniqueId());
                    player.openInventory(gui.getInventory());
                } else {
                    player.performCommand("huskhomes:home");
                }
            } else {
                player.performCommand("huskhomes:home " + args[0]);
            }
            return true;
        }
        return false;
    }
}
