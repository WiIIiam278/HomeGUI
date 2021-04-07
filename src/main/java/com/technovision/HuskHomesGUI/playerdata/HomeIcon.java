package com.technovision.HuskHomesGUI.playerdata;

import me.william278.huskhomes2.teleport.points.Home;
import me.william278.huskhomes2.teleport.points.TeleportationPoint;
import org.bukkit.Material;

public class HomeIcon extends Home {

    private Material icon;

    public HomeIcon(TeleportationPoint teleportationPoint, String ownerUsername, String ownerUUID, String name, String description, boolean isPublic, Material icon) {
        super(teleportationPoint, ownerUsername, ownerUUID, name, description, isPublic);
        this.icon = icon;
    }

    public Material getIcon() {return icon;}

    public void setIcon(Material newIcon) {
        icon = newIcon;
    }
}
