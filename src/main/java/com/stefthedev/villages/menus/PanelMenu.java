package com.stefthedev.villages.menus;

import com.stefthedev.villages.managers.VillageManager;
import com.stefthedev.villages.utilities.general.Chat;
import com.stefthedev.villages.utilities.general.Item;
import com.stefthedev.villages.utilities.general.Message;
import com.stefthedev.villages.utilities.menus.Menu;
import com.stefthedev.villages.utilities.menus.MenuItem;
import com.stefthedev.villages.villages.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PanelMenu extends Menu {

    private final VillageManager villageManager;
    private final Village village;

    public PanelMenu(Plugin plugin, VillageManager villageManager, Village village) {
        super(plugin, village.getName() + " Panel", 27);
        this.villageManager = villageManager;
        this.village = village;
    }

    @Override
    public Menu build() {

        addItems(
                new MenuItem(11, members(), inventoryClickEvent -> new MembersMenu(getPlugin(), villageManager, village, 1).build().open((Player) inventoryClickEvent.getWhoClicked())),
                new MenuItem(12, claims(), inventoryClickEvent -> new ClaimsMenu(getPlugin(), villageManager, village, 1).build().open((Player) inventoryClickEvent.getWhoClicked())),
                new MenuItem(13, information(), null),
                new MenuItem(14, home(), inventoryClickEvent -> {
                    Player player = (Player) inventoryClickEvent.getWhoClicked();
                    player.closeInventory();
                    VillageMember villageMember = village.getMember(player.getUniqueId());
                    if(villageMember.hasPermission(VillagePermission.HOME) || village.getOwner().equals(player.getUniqueId())) {
                        player.teleport(village.getVillageLocation().toLocation(getPlugin()));
                        player.sendMessage(Chat.format(Message.VILLAGE_HOME.toString()));
                    } else {
                        player.sendMessage(Chat.format(Message.NO_PERMISSION.toString().replace("{0}", VillagePermission.HOME.name() )));
                    }
                }),
                new MenuItem(15, disband(), inventoryClickEvent -> {
                    Player player = (Player) inventoryClickEvent.getWhoClicked();
                    player.closeInventory();
                    VillageMember villageMember = village.getMember(player.getUniqueId());
                    if (villageMember.hasPermission(VillagePermission.DISBAND) || village.getOwner().equals(player.getUniqueId())) {
                        VillageRequest villageRequest = villageManager.getRequest(player);
                        if (villageRequest == null) {
                            villageRequest = new VillageRequest(village, player.getUniqueId(), null, VillageRequest.VillageRequestAction.DISBAND);
                            villageRequest.send();
                            villageManager.add(villageRequest);
                        } else {
                            player.sendMessage(Chat.format(Message.REQUEST_PENDING.toString()));
                        }
                    } else {
                        player.sendMessage(Chat.format(Message.NO_PERMISSION.toString().replace("{0}", VillagePermission.DISBAND.name())));
                    }
                })
        );
        return this;
    }

    private ItemStack members() {
        return new Item()
                .material(Material.EMERALD)
                .name(Message.MENU_MEMBERS_TITLE.toString())
                .lore(Message.MENU_MEMBERS_LORE.toList())
                .build();
    }

    private ItemStack claims() {
        return new Item()
                .material(Material.CHEST_MINECART)
                .name(Message.MENU_CLAIMS_TITLE.toString())
                .lore(Message.MENU_CLAIMS_LORE.toList())
                .build();
    }

    private ItemStack information() {

        List<String> lore = Message.MENU_INFORMATION_LORE.toList();
        List<String> updated = new ArrayList<>();

        for(String string : lore) {
            string = string.replace("{owner}", Objects.requireNonNull(Bukkit.getOfflinePlayer(village.getOwner()).getName()));
            string = string.replace("{claims}", String.valueOf(village.getVillageClaims().size()));
            string = string.replace("{members}", String.valueOf(village.getVillageMembers().size()));
            string = string.replace("{description}", village.getDescription());
            string = string.replace("{level}", String.valueOf(village.getLevel()));
            updated.add(string);
        }

        return new Item()
                .material(Material.WRITABLE_BOOK)
                .name(Message.MENU_INFORMATION_TITLE.toString())
                .lore(updated)
                .build();
    }

    private ItemStack home() {
        return new Item()
                .material(Material.NETHER_STAR)
                .name(Message.MENU_HOME_TITLE.toString())
                .lore(Message.MENU_HOME_LORE.toList())
                .build();
    }

    private ItemStack disband() {
        return new Item()
                .material(Material.BARRIER)
                .name(Message.MENU_DISBAND_TITLE.toString())
                .lore(Message.MENU_DISBAND_LORE.toList())
                .build();
    }
}
