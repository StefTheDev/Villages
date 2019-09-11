package com.stefthedev.villages.menus;

import com.stefthedev.villages.utilities.general.Item;
import com.stefthedev.villages.utilities.general.Message;
import com.stefthedev.villages.utilities.menus.Menu;
import com.stefthedev.villages.utilities.menus.MenuItem;
import com.stefthedev.villages.data.Village;
import com.stefthedev.villages.managers.VillageManager;
import com.stefthedev.villages.data.VillageMember;
import com.stefthedev.villages.data.VillagePermission;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class PermissionsMenu extends Menu {

    private final Village village;
    private final VillageManager villageManager;
    private final VillageMember villageMember;

    PermissionsMenu(Plugin plugin, Village village, VillageManager villageManager, VillageMember villageMember) {
        super(plugin, Bukkit.getOfflinePlayer(villageMember.getUniqueId()).getName() + "'s Permissions", 36);
        this.village = village;
        this.villageManager = villageManager;
        this.villageMember = villageMember;
    }

    @Override
    public Menu build() {
        AtomicInteger atomicInteger = new AtomicInteger(-1);
        Arrays.asList(VillagePermission.values()).forEach(villagePermission -> {
            if (villageMember.hasPermission(villagePermission)) {
                addItems(new MenuItem(atomicInteger.addAndGet(1), enabled(villagePermission), event-> villageMember.remove(villagePermission)));
            } else {
                addItems(new MenuItem(atomicInteger.addAndGet(1), disabled(villagePermission), event-> villageMember.add(villagePermission)));
            }
        });
        addItems(new MenuItem(31, back(), inventoryClickEvent ->
        {
            inventoryClickEvent.getWhoClicked().closeInventory();
            new MembersMenu(getPlugin(), villageManager, village, 1).build().open((Player) inventoryClickEvent.getWhoClicked());
        }));
        return this;
    }

    private ItemStack disabled(VillagePermission villagePermission) {
        return new Item()
                .material(Material.GRAY_DYE)
                .name(Message.MENU_DISABLED_TITLE.toString().replace("{0}", villagePermission.name()))
                .lore(Message.MENU_DISABLED_LORE.toList())
                .build();
    }

    private ItemStack enabled(VillagePermission villagePermission) {
        return new Item()
                .material(Material.LIME_DYE)
                .name(Message.MENU_ENABLED_TITLE.toString().replace("{0}", villagePermission.name()))
                .lore(Message.MENU_ENABLED_LORE.toList())
                .build();
    }

    private ItemStack back() {
        return new Item()
                .material(Material.PISTON)
                .name(Message.MENU_BACK_TITLE.toString())
                .lore(Message.MENU_BACK_LORE.toList())
                .build();
    }
}
