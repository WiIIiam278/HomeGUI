package com.technovision.HuskHomesGUI.playerdata;

import com.cryptomorin.xseries.XMaterial;
import me.william278.huskhomes2.HuskHomes;
import me.william278.huskhomes2.api.HuskHomesAPI;
import me.william278.huskhomes2.teleport.points.Home;

import java.util.List;

public class HuskHomesReader {

    private List<HomeIcon> homes;

    public HuskHomesReader(String playerName) {
        HuskHomesAPI huskHomesAPI = HuskHomes.getInstance().getAPI();
        for (Home home : huskHomesAPI.getHomes(playerName)) {
            homes.add(new HomeIcon(home, home.getOwnerUsername(), home.getOwnerUUID().toString(), home.getName(), home.getDescription(), home.isPublic(), XMaterial.GRASS_BLOCK.parseMaterial()));
        }
    }

    public List<HomeIcon> getHomes() {
        return homes;
    }

}
