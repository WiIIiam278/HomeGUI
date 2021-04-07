package com.technovision.homegui.events;

import com.cryptomorin.xseries.XMaterial;
import com.technovision.homegui.Homegui;
import com.technovision.homegui.gui.ChangeIconGUI;
import com.technovision.homegui.gui.HomeGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class HomeEvents implements Listener {

    @EventHandler
    public void onGuiActivation(InventoryClickEvent event){
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
                String playerID = player.getUniqueId().toString();
                int slotNum = event.getSlot();
                String name = HomeGUI.allHomes.get(playerID).get(slotNum).getName();

                if (event.isLeftClick()) {
                    // Middle Click
                    player.performCommand("huskhomes:home " + name);
                    player.closeInventory();
                } else if (event.getClick() == ClickType.MIDDLE) {
                    // Middle Click
                    player.performCommand("huskhomes:delhome " + name);
                    Homegui.dataReader.removeIcon(playerID, name);
                    player.closeInventory();
                } else if (event.isRightClick()) {
                    // Right Click
                    player.closeInventory();
                    ChangeIconGUI iconGUI = new ChangeIconGUI();
                    iconGUI.openInventory(player, HomeGUI.allHomes.get(playerID).get(slotNum));
                }
            }
        } else if (event.getInventory().getHolder() instanceof ChangeIconGUI) {
            if (event.getClickedInventory() == null) { return; }
            Player player = (Player) event.getWhoClicked();
            if (event.getCurrentItem() != null) {
                if (event.getCurrentItem().getType() == Material.AIR) { return; }
                event.setCancelled(true);
                if (event.getClickedInventory().getType() == InventoryType.PLAYER) { return; }
                if (event.isLeftClick()) {
                    String itemName = getFriendlyName(event.getCurrentItem().getType());
                    String playerID = event.getWhoClicked().getUniqueId().toString();
                    XMaterial icon = XMaterial.matchXMaterial(event.getCurrentItem().getType());

                    String homeName = ChangeIconGUI.homes.get(playerID).getName();
                    Homegui.dataReader.write(playerID, homeName, icon.parseMaterial());

                    String msg = Homegui.PLUGIN.getConfig().getString("icon-select-message").replace("&", "§");
                    msg = msg.replace("{home}", homeName);
                    msg = msg.replace("{icon}", itemName);
                    player.sendMessage(msg);
                    player.closeInventory();
                }
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
