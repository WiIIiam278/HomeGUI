package com.technovision.HuskHomesGUI.playerdata;

import com.technovision.HuskHomesGUI.HuskHomesGUI;
import me.william278.huskhomes2.HuskHomes;
import me.william278.huskhomes2.api.HuskHomesAPI;
import me.william278.huskhomes2.teleport.points.Home;

import java.util.ArrayList;
import java.util.List;

public class HuskHomesReader {

    private final ArrayList<HomeIcon> homes;

    public HuskHomesReader(String playerName) {
        HuskHomesAPI huskHomesAPI = HuskHomesAPI.getInstance();
        homes = new ArrayList<>();
        for (Home home : huskHomesAPI.getHomes(playerName)) {
            if (home == null) {
                continue;
            }
            HomeIcon icon = new HomeIcon(home, home.getOwnerUsername(), home.getOwnerUUID(), home.getName(), home.getDescription(), home.isPublic(), home.getCreationTime());
            homes.add(icon);
        }
    }

    public List<HomeIcon> getHomes() {
        return homes;
    }

}
