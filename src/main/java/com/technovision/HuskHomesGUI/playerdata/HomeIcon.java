package com.technovision.HuskHomesGUI.playerdata;

import me.william278.huskhomes2.teleport.points.Home;
import me.william278.huskhomes2.teleport.points.TeleportationPoint;

import java.awt.*;
import java.util.Random;

public class HomeIcon extends Home {

    // Converts a string into an integer value, used in getting home color
    private static long getStringValue(String string) {
        long value = 0;
        for (String c : string.split("")) {
            value++;
            int characterInt = c.charAt(0);
            value = value * (long) characterInt;
        }
        return value;
    }

    // Returns the calculated randomly-seeded-by-name color of a home, in format #xxxxxx
    public String getColorHex() {
        Random random = new Random(getStringValue(getName()));
        int randomHex = random.nextInt(0xffffff + 1);
        return String.format("#%06x", randomHex);
    }

    // Returns the color object from the home's calculated color
    public Color getColor() {
        return Color.decode(getColorHex());
    }

    public HomeIcon(TeleportationPoint teleportationPoint, String ownerUsername, String ownerUUID, String name, String description, boolean isPublic) {
        super(teleportationPoint, ownerUsername, ownerUUID, name, description, isPublic);
    }
}
