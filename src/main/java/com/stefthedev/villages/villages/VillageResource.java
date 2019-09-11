package com.stefthedev.villages.villages;

public class VillageResource {

    private String material;
    private int amount = 0;

    public VillageResource(String material) {
        this.material = material;
    }

    public void add(int amount) {
        this.amount += amount;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return material;
    }
}
