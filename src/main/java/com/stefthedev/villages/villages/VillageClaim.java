package com.stefthedev.villages.villages;

public class VillageClaim {

    private final String world;
    private final double x, z;

    public VillageClaim(String world, double x, double z) {
        this.world = world;
        this.x = x;
        this.z = z;
    }

    String getWorld() {
        return world;
    }

    double getX() {
        return x;
    }

    double getZ() {
        return z;
    }

    @Override
    public String toString() {
        return world + ":" + x + ":" + z;
    }
}
