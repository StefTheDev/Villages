package com.stefthedev.villages.menus;

import com.stefthedev.villages.utilities.general.Item;
import com.stefthedev.villages.utilities.general.Message;
import com.stefthedev.villages.utilities.menus.Menu;
import com.stefthedev.villages.utilities.menus.MenuItem;
import com.stefthedev.villages.data.village.Village;
import com.stefthedev.villages.managers.VillageManager;
import com.stefthedev.villages.data.village.VillageMember;
import com.stefthedev.villages.data.village.VillagePermission;
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
        Arrays.asList(VillagePermission.values()).forEach(villagePermission -> addItems(new MenuItem(atomicInteger.addAndGet(1),
                permission(villagePermission, villageMember.hasPermission(villagePermission)),
                event-> {
                    if(villageMember.hasPermission(villagePermission)) {
                        villageMember.remove(villagePermission);
                    } else {
                        villageMember.add(villagePermission);
                    }
                })
        ));
        addItems(new MenuItem(31, back(), inventoryClickEvent ->
        {
            inventoryClickEvent.getWhoClicked().closeInventory();
            new MembersMenu(getPlugin(), villageManager, village, 1).build().open((Player) inventoryClickEvent.getWhoClicked());
        }));
        return this;
    }

    private ItemStack permission(VillagePermission villagePermission, boolean enabled) {
        Item item = new Item();
        if(enabled) {
            item.material(Material.LIME_DYE);
            item.name(Message.MENU_ENABLED_TITLE.toString().replace("{0}", villagePermission.name()));
            item.lore(Message.MENU_ENABLED_LORE.toList());
        } else {
            item.material(Material.GRAY_DYE);
            item.name(Message.MENU_DISABLED_TITLE.toString().replace("{0}", villagePermission.name()));
            item.lore(Message.MENU_DISABLED_LORE.toList());
        }
        return item.build();
    }

    private ItemStack back() {
        return new Item()
                .material(Material.PISTON)
                .name(Message.MENU_BACK_TITLE.toString())
                .lore(Message.MENU_BACK_LORE.toList())
                .build();
    }
}
