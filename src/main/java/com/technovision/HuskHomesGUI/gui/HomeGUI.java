package com.technovision.HuskHomesGUI.gui;

import com.technovision.HuskHomesGUI.HuskHomesGUI;
import com.technovision.HuskHomesGUI.playerdata.HomeIcon;
import com.technovision.HuskHomesGUI.playerdata.HuskHomesReader;
import de.themoep.minedown.MineDown;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.awt.*;
import java.util.*;
import java.util.List;

public class HomeGUI implements InventoryHolder {

    public static Map<String, List<HomeIcon>> allHomes = new HashMap<>();

    private final Inventory inv;
    private final List<HomeIcon> homes;

    public HomeGUI(String playerName, UUID playerUUID) {
        HuskHomesReader reader = new HuskHomesReader(playerName);
        homes = reader.getHomes();
        String title = TextComponent.toLegacyText(new MineDown(HuskHomesGUI.PLUGIN.getConfig().getString("gui-main-header")).toComponent());
        inv = Bukkit.createInventory(this, calculateSize(), title);
        allHomes.put(playerName, homes);
        String homeStyle = HuskHomesGUI.PLUGIN.getConfig().getString("home_style");
        if (homeStyle == null) {
            homeStyle = "item";
        }
        if (homeStyle.toLowerCase(Locale.ROOT).equalsIgnoreCase("color") || homeStyle.toLowerCase(Locale.ROOT).equalsIgnoreCase("colour")) {
            initCrossServerItems();
        } else {
            HuskHomesGUI.dataReader.create(playerUUID.toString());
            initItems(playerUUID.toString());
        }
    }

    private int calculateSize() {
        int size = homes.size();
        if (size == 0) {
            return 9;
        }
        if (size >= 54) {
            return 54;
        }
        if (size % 9 == 0) {
            return size;
        }
        int count = 0;
        for (int i = size % 9; i > 0; i--) {
            count++;
        }
        return (size - count) + 9;
    }

    private void initCrossServerItems() {
        String name;
        Color color;
        for (HomeIcon home : homes) {
            name = home.getName().substring(0, 1).toUpperCase() + home.getName().substring(1);
            color = home.getColor();
            ItemStack leatherItem = new ItemStack(Material.LEATHER_BOOTS);
            ItemMeta meta = leatherItem.getItemMeta();
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS);
            leatherItem.setItemMeta(meta);
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) meta;
            leatherArmorMeta.setColor(org.bukkit.Color.fromRGB(color.getRed(), color.getGreen(), color.getBlue()));
            leatherItem.setItemMeta(leatherArmorMeta);
            String nameColor = "&" + home.getColorHex() + "&&l";
            name = TextComponent.toLegacyText(new MineDown(nameColor + name).toComponent());
            List<String> lore = HuskHomesGUI.PLUGIN.getConfig().getStringList("color-home-lore");
            String location = "&f " + (int) home.getX() + "&7,&f " + (int) home.getY() + "&7,&f " + (int) home.getZ();
            for (int i = 0; i < lore.size(); i++) {
                String newLine = lore.get(i).replace("{location}", location);
                newLine = newLine.replace("{world}", home.getWorldName());
                newLine = newLine.replace("{server}", home.getServer());
                if (newLine.contains("{privacy}")) {
                    if (home.isPublic()) {
                        newLine = newLine.replace("{privacy}", "&2Public");
                    } else {
                        newLine = newLine.replace("{privacy}", "&cPrivate");
                    }
                }
                lore.set(i, TextComponent.toLegacyText(new MineDown(newLine).toComponent()));
            }
            inv.addItem(createGuiItem(leatherItem, name, lore));
        }
    }

    private void initItems(String playerID) {
        String name;
        for (HomeIcon home : homes) {
            name = home.getName().substring(0, 1).toUpperCase() + home.getName().substring(1);
            ItemStack item = HuskHomesGUI.dataReader.getItem(playerID, home.getName());
            String nameColor = HuskHomesGUI.PLUGIN.getConfig().getString("item-home-color");
            name = TextComponent.toLegacyText(new MineDown(nameColor + name).toComponent());
            List<String> lore = HuskHomesGUI.PLUGIN.getConfig().getStringList("item-home-lore");
            String location = "&f " + (int) home.getX() + "&7,&f " + (int) home.getY() + "&7,&f " + (int) home.getZ();
            for (int i = 0; i < lore.size(); i++) {
                String newLine = lore.get(i).replace("{location}", location);
                newLine = newLine.replace("{world}", home.getWorldName());
                newLine = newLine.replace("{server}", home.getServer());
                if (newLine.contains("{privacy}")) {
                    if (home.isPublic()) {
                        newLine = newLine.replace("{privacy}", "&2Public");
                    } else {
                        newLine = newLine.replace("{privacy}", "&cPrivate");
                    }
                }
                lore.set(i, TextComponent.toLegacyText(new MineDown(newLine).toComponent()));
            }
            inv.addItem(createGuiItem(item, name, lore));
        }
    }

    private ItemStack createGuiItem(final ItemStack item, final String name, final List<String> lore) {
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
