package com.stefthedev.villages.menus;

import com.stefthedev.villages.utilities.general.Item;
import com.stefthedev.villages.utilities.general.Message;
import com.stefthedev.villages.utilities.menus.Menu;
import com.stefthedev.villages.utilities.menus.MenuItem;
import com.stefthedev.villages.data.village.Village;
import com.stefthedev.villages.managers.VillageManager;
import com.stefthedev.villages.data.village.VillageMember;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MembersMenu extends Menu {

    private final VillageManager villageManager;
    private final Village village;
    private int index;

    MembersMenu(Plugin plugin, VillageManager villageManager, Village village, int index) {
        super(plugin, village.getName() + " Members", 36);
        this.villageManager = villageManager;
        this.village = village;
        this.index = index;
    }

    @Override
    public Menu build() {
        int pages = (int) Math.ceil((double) village.getVillageMembers().size() / 27);
        if(index == 1 && pages == 1) {
            addItems(
                    new MenuItem(31, back(), inventoryClickEvent ->
                    {
                        inventoryClickEvent.getWhoClicked().closeInventory();
                        new PanelMenu(getPlugin(), villageManager, village).build().open((Player) inventoryClickEvent.getWhoClicked());
                    })
            );
        } else if(index == pages) {
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

        List<VillageMember> villageMembers = new ArrayList<>(village.getVillageMembers());
        for (int i = 0; i < villageMembers.size(); i++) {
            if (i >= 27) break;
            int number = i + ((index - 1) * 27);
            if (number >= villageMembers.size()) break;
            VillageMember villageMember = villageMembers.get(number);
            addItems(new MenuItem(i, member(villageMembers.get(number).getUniqueId()), inventoryClickEvent -> {
                inventoryClickEvent.getWhoClicked().closeInventory();
                new PermissionsMenu(getPlugin(), village, villageManager, villageMember).build().open((Player) inventoryClickEvent.getWhoClicked());
            }));
        }
        return this;
    }

    private ItemStack member(UUID uuid) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        return new Item()
                .name(Message.MENU_MEMBER_TITLE.toString().replace("{0}", Objects.requireNonNull(offlinePlayer.getName())))
                .lore(Message.MENU_MEMBER_LORE.toList())
                .buildPlayer(offlinePlayer);
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
