package com.stefthedev.villages.utilities.menus;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class MenuItem {

    private final int slot;
    private final ItemStack itemStack;
    private final Consumer<InventoryClickEvent> inventoryClickEvent;

    public MenuItem(int slot, ItemStack itemStack, Consumer<InventoryClickEvent> inventoryClickEvent) {
        this.slot = slot;
        this.itemStack = itemStack;
        this.inventoryClickEvent = inventoryClickEvent;
    }

    int getSlot() {
        return slot;
    }

    ItemStack getItemStack() {
        return itemStack;
    }

    Consumer<InventoryClickEvent> getInventoryClickEvent() {
        return inventoryClickEvent;
    }
}
