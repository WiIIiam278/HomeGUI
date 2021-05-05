package com.technovision.HuskHomesGUI.gui;

import com.cryptomorin.xseries.XMaterial;
import com.technovision.HuskHomesGUI.HuskHomesGUI;
import com.technovision.HuskHomesGUI.playerdata.HomeIcon;
import de.themoep.minedown.MineDown;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ChangeIconGUI implements InventoryHolder, Listener {

    public static List<String> activeGui = new ArrayList<>();
    public static Map<String, HomeIcon> homes = new HashMap<>();
    private final Inventory inv;

    public ChangeIconGUI() {
        String title = TextComponent.toLegacyText(new MineDown(HuskHomesGUI.PLUGIN.getConfig().getString("gui-icon-header")).toComponent());
        inv = Bukkit.createInventory(this, 54, title);
        initItems();
    }

    private ItemStack addItemLore(ItemStack item) {
        final ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setLore(Collections.singletonList(TextComponent.toLegacyText(new MineDown(HuskHomesGUI.PLUGIN.getConfig().getString("icon-select-lore-message")).toComponent())));
            item.setItemMeta(meta);
        }
        return item;
    }

    public void openInventory(Player player, HomeIcon home) {
        player.openInventory(inv);
        activeGui.add(player.getName());
        homes.put(player.getUniqueId().toString(), home);
    }

    private void initItems() {
        List<String> icons = HuskHomesGUI.PLUGIN.getConfig().getStringList("icons");
        for (String icon : icons) {
            try {
                ItemStack item = XMaterial.valueOf(icon).parseItem();
                if (item != null) {
                    inv.addItem(addItemLore(item));
                }
            } catch (IllegalArgumentException e) {
                HuskHomesGUI.PLUGIN.getLogger().config("A material ID specified in config.yml was missing or invalid!");
            }
        }
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
