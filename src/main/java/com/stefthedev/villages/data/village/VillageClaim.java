package com.stefthedev.villages.data.village;

public class VillageClaim {

    private final String world;
    private final int x, z;

    public VillageClaim(String world, int x, int z) {
        this.world = world;
        this.x = x;
        this.z = z;
    }

    public String getWorld() {
        return world;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    @Override
    public String toString() {
        return world + ", " + x + ", " + z;
    }
}