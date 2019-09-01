package com.stefthedev.villages.menus;

import com.stefthedev.villages.utilities.general.Item;
import com.stefthedev.villages.utilities.general.Message;
import com.stefthedev.villages.utilities.menus.Menu;
import com.stefthedev.villages.utilities.menus.MenuItem;
import com.stefthedev.villages.villages.VillageMember;
import com.stefthedev.villages.villages.VillagePermission;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class PermissionsMenu extends Menu {

    private final VillageMember villageMember;

    public PermissionsMenu(Plugin plugin, OfflinePlayer offlinePlayer, VillageMember villageMember) {
        super(plugin, offlinePlayer.getName() + "'s Permissions", 27);
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
}
