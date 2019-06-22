package com.stefthedev.villages.hooks;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.RegionQuery;

import org.bukkit.entity.Player;

public class WorldGuardHook {

    public boolean isRegion(Player player) {
        Location location = BukkitAdapter.adapt(player).getLocation();
        RegionQuery query = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
        ApplicableRegionSet applicableRegionSet = query.getApplicableRegions(location);
        return !applicableRegionSet.getRegions().isEmpty();
    }
}
