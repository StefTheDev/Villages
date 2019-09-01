package com.stefthedev.villages.utilities.menus;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Menu implements InventoryHolder, Listener {

    private final Inventory inventory;
    private final List<MenuItem> itemList;

    public Menu(Plugin plugin, String name, int size) {
        this.inventory = plugin.getServer().createInventory( this, size, name);
        this.itemList = new ArrayList<>();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void open(Player player) {
        player.closeInventory();
        player.openInventory(inventory);
    }

    protected void addItems(MenuItem... menuItems) {
        Arrays.asList(menuItems).forEach(menuItem -> {
            inventory.setItem(menuItem.getSlot(), menuItem.getItemStack());
            itemList.add(menuItem);
        });
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getClickedInventory() == null) return;
        if(event.getClickedInventory().equals(getInventory())) {
            event.setCancelled(true);
            if(event.getCurrentItem() == null) return;
            if(event.getCurrentItem().getItemMeta() == null) return;
            String name = event.getCurrentItem().getItemMeta().getDisplayName();
            if(itemList.isEmpty()) return;
            for(MenuItem menuItem : itemList) {
                if(menuItem.getItemStack() == null) return;
                if(menuItem.getItemStack().getItemMeta() == null) return;
                if (menuItem.getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(name)) {
                    if(menuItem.getInventoryClickEvent() == null) return;
                    menuItem.getInventoryClickEvent().accept(event);
                    inventory.clear();
                    build();
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if(event.getInventory().equals(getInventory())) {
            HandlerList.unregisterAll(this);
        }
    }

    public abstract Menu build();

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
