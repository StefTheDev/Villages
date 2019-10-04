package com.stefthedev.villages.menus;

import com.stefthedev.villages.data.village.VillageClaim;
import com.stefthedev.villages.utilities.general.Chat;
import com.stefthedev.villages.utilities.general.Item;
import com.stefthedev.villages.utilities.general.Message;
import com.stefthedev.villages.utilities.menus.Menu;
import com.stefthedev.villages.utilities.menus.MenuItem;
import com.stefthedev.villages.data.village.Village;
import com.stefthedev.villages.managers.VillageManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClaimsMenu extends Menu {

    private final VillageManager villageManager;
    private final Village village;
    private int index;

    ClaimsMenu(Plugin plugin, VillageManager villageManager, Village village, int index) {
        super(plugin, village.getName() + " Claims", 36);
        this.villageManager = villageManager;
        this.village = village;
        this.index = index;
    }

    @Override
    public Menu build() {
        int pages = (int) Math.ceil((double) village.getVillageClaims().size() / 27);
        if (index == 1 && pages == 1) {
            addItems(
                    new MenuItem(31, back(), inventoryClickEvent ->
                    {
                        inventoryClickEvent.getWhoClicked().closeInventory();
                        new PanelMenu(getPlugin(), villageManager, village).build().open((Player) inventoryClickEvent.getWhoClicked());
                    })
            );
        } else if (index == pages) {
            addItems(
                    new MenuItem(30, previous(), inventoryClickEvent -> index -= 1),
                    new MenuItem(31, back(), inventoryClickEvent ->
                    {
                        inventoryClickEvent.getWhoClicked().closeInventory();
                        new PanelMenu(getPlugin(), villageManager, village).build().open((Player) inventoryClickEvent.getWhoClicked());
                    })
            );
        } else if (index == 1) {
            addItems(
                    new MenuItem(32, next(), inventoryClickEvent -> index += 1),
                    new MenuItem(31, back(), inventoryClickEvent ->
                    {
                        inventoryClickEvent.getWhoClicked().closeInventory();
                        new PanelMenu(getPlugin(), villageManager, village).build().open((Player) inventoryClickEvent.getWhoClicked());
                    })
            );
        } else {
            addItems(
                    new MenuItem(30, previous(), inventoryClickEvent -> index -= 1),
                    new MenuItem(32, next(), inventoryClickEvent -> index += 1),
                    new MenuItem(31, back(), inventoryClickEvent ->
                    {
                        inventoryClickEvent.getWhoClicked().closeInventory();
                        new PanelMenu(getPlugin(), villageManager, village).build().open((Player) inventoryClickEvent.getWhoClicked());
                    })
            );
        }

        List<VillageClaim> villageClaims = new ArrayList<>(village.getVillageClaims());
        for (int i = 0; i < villageClaims.size(); i++) {
            if (i >= 27) break;
            int number = i + ((index - 1) * 27);
            if (number >= villageClaims.size()) break;
            VillageClaim villageClaim = villageClaims.get(number);
            addItems(new MenuItem(i, claim(villageClaims.get(number)), inventoryClickEvent -> {
                Player player = (Player) inventoryClickEvent.getWhoClicked();
                player.closeInventory();
                World world = Bukkit.getWorld(villageClaim.getWorld());
                if (world == null) return;
                Chunk chunk = world.getChunkAt(villageClaim.getX(), villageClaim.getZ());
                int y = world.getHighestBlockYAt(chunk.getX() << 4, chunk.getZ() << 4);
                player.teleport(new Location(chunk.getWorld(), chunk.getX() << 4, y, chunk.getZ() << 4));
                player.sendMessage(Chat.format(Message.VILLAGE_CLAIM_TELEPORT.toString().replace("{0}", villageClaim.toString())));
            }));
        }
        return this;
    }

    private ItemStack claim(VillageClaim villageClaim) {
        return new Item()
                .material(Material.GRASS_BLOCK)
                .name(Message.MENU_CLAIM_TITLE.toString().replace("{0}", Objects.requireNonNull(villageClaim.toString())))
                .lore(Message.MENU_CLAIM_LORE.toList())
                .build();
    }

    private ItemStack back() {
        return new Item()
                .material(Material.PISTON)
                .name(Message.MENU_BACK_TITLE.toString())
                .lore(Message.MENU_BACK_LORE.toList())
                .build();
    }

    private ItemStack next() {
        return new Item()
                .material(Material.ARROW)
                .name(Message.MENU_NEXT_TITLE.toString())
                .lore(Message.MENU_NEXT_LORE.toList())
                .build();
    }

    private ItemStack previous() {
        return new Item()
                .material(Material.ARROW)
                .name(Message.MENU_PREVIOUS_TITLE.toString())
                .lore(Message.MENU_PREVIOUS_LORE.toList())
                .build();
    }
}
