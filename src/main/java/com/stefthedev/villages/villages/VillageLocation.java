package com.stefthedev.villages.villages;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

public class VillageLocation {

    private double x, y, z;
    private float yaw, pitch;
    private String world;

    VillageLocation(double x, double y, double z, float yaw, float pitch, String world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.world = world;
    }

    public Location toLocation(Plugin plugin) {
        if(plugin.getServer().getWorld(world) == null) return null;
        Location location = new Location(plugin.getServer().getWorld(world), x, y, z);
        location.setYaw(yaw);
        location.setPitch(pitch);
        return location;
    }
}
