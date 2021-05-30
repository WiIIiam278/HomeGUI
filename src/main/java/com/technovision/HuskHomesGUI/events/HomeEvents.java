package com.technovision.HuskHomesGUI.events;

import com.cryptomorin.xseries.XMaterial;
import com.technovision.HuskHomesGUI.HuskHomesGUI;
import com.technovision.HuskHomesGUI.gui.ChangeIconGUI;
import com.technovision.HuskHomesGUI.gui.HomeGUI;
import de.themoep.minedown.MineDown;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Locale;

public class HomeEvents implements Listener {

    @EventHandler
    public void onGuiActivation(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof HomeGUI) {
            if (event.getClickedInventory() == null) {
                return;
            }
            Player player = (Player) event.getWhoClicked();
            if (event.getCurrentItem() != null) {
                if (event.getCurrentItem().getType() == Material.AIR) {
                    return;
                }
                event.setCancelled(true);
                if (event.getClickedInventory().getType() == InventoryType.PLAYER) {
                    return;
                }
                String playerName = player.getName();
                int slotNum = event.getSlot();
                String name = HomeGUI.allHomes.get(playerName).get(slotNum).getName();
                String homeStyle = HuskHomesGUI.PLUGIN.getConfig().getString("home_style");
                if (homeStyle == null) {
                    homeStyle = "item";
                }

                if (event.isLeftClick()) {
                    // Middle Click
                    player.performCommand("huskhomes:home " + name);
                    player.closeInventory();
                } else if (event.getClick() == ClickType.MIDDLE) {
                    // Middle Click
                    player.performCommand("huskhomes:delhome " + name);
                    if (homeStyle.toLowerCase(Locale.ROOT).equalsIgnoreCase("item")) {
                        HuskHomesGUI.dataReader.removeIcon(player.getUniqueId().toString(), name);
                    }
                    player.closeInventory();
                } else if (event.isRightClick()) {
                    if (homeStyle.toLowerCase(Locale.ROOT).equalsIgnoreCase("color") || homeStyle.toLowerCase(Locale.ROOT).equalsIgnoreCase("colour")) {
                        return;
                    }

                    // Right Click
                    player.closeInventory();
                    ChangeIconGUI iconGUI = new ChangeIconGUI();
                    iconGUI.openInventory(player, HomeGUI.allHomes.get(playerName).get(slotNum));
                }
            }
        } else if (event.getInventory().getHolder() instanceof ChangeIconGUI) {
            if (event.getClickedInventory() == null) {
                return;
            }
            Player player = (Player) event.getWhoClicked();
            if (event.getCurrentItem() != null) {
                if (event.getCurrentItem().getType() == Material.AIR) {
                    return;
                }
                event.setCancelled(true);
                if (event.getClickedInventory().getType() == InventoryType.PLAYER) {
                    return;
                }
                if (event.isLeftClick()) {
                    String itemName = getFriendlyName(event.getCurrentItem().getType());
                    String playerID = event.getWhoClicked().getUniqueId().toString();
                    XMaterial icon = XMaterial.matchXMaterial(event.getCurrentItem().getType());

                    String homeName = ChangeIconGUI.homes.get(playerID).getName();
                    HuskHomesGUI.dataReader.write(playerID, homeName, icon.parseMaterial());

                    String msg = HuskHomesGUI.PLUGIN.getConfig().getString("icon-select-message");
                    msg = msg.replace("{home}", homeName);
                    msg = msg.replace("{icon}", itemName);
                    player.spigot().sendMessage(new MineDown(msg).toComponent());
                    player.closeInventory();
                }
            }
        }
    }

    @EventHandler
    public void onCommandPreProcess(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        if (e.getMessage().equalsIgnoreCase("/home") || e.getMessage().equalsIgnoreCase("/homes") || e.getMessage().equalsIgnoreCase("/homelist")) {
            if (player.hasPermission("huskhomes.home")) {
                e.setCancelled(true);
                Bukkit.getScheduler().runTaskAsynchronously(HuskHomesGUI.PLUGIN, () -> {
                    HomeGUI gui = new HomeGUI(player.getName(), player.getUniqueId());
                    Bukkit.getScheduler().runTask(HuskHomesGUI.PLUGIN, () -> player.openInventory(gui.getInventory()));
                });
            }
        }
    }

    private String format(String s) {
        if (!s.contains("_")) {
            return capitalize(s);
        }
        String[] j = s.split("_");
        StringBuilder c = new StringBuilder();
        for (String f : j) {
            f = capitalize(f);
            c.append(c.toString().equalsIgnoreCase("") ? f : " " + f);
        }
        return c.toString();
    }

    private String capitalize(String text) {
        String firstLetter = text.substring(0, 1).toUpperCase();
        String next = text.substring(1).toLowerCase();
        return firstLetter + next;
    }

    public String getFriendlyName(Material m) {
        return format(m.name());
    }
}
