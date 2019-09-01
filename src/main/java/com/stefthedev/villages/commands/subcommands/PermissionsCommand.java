package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.Villages;
import com.stefthedev.villages.menus.PermissionsMenu;
import com.stefthedev.villages.utilities.general.Chat;
import com.stefthedev.villages.utilities.general.Command;
import com.stefthedev.villages.utilities.general.Message;
import com.stefthedev.villages.villages.Village;
import com.stefthedev.villages.villages.VillageManager;
import com.stefthedev.villages.villages.VillageMember;
import com.stefthedev.villages.villages.VillagePermission;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PermissionsCommand extends Command {

    private final Villages villages;
    private final VillageManager villageManager;

    public PermissionsCommand(Villages villages) {
        super("permissions", "permissions [player]");
        this.villages = villages;
        this.villageManager = villages.getVillageManager();
    }

    @Override
    public boolean run(Player player, String[] args) {
        if(args.length == 2) {
            Village village = villageManager.getVillage(player);
            if(village != null) {
                VillageMember villageMember = village.getMember(player.getUniqueId());
                if(villageMember.hasPermission(VillagePermission.EDIT_PERMISSIONS) || village.getOwner().equals(player.getUniqueId())) {
                    OfflinePlayer offlinePlayer = villageManager.offlinePlayer(village, args[1]);
                    if(offlinePlayer != null) {

                        new PermissionsMenu(
                                villages,
                                offlinePlayer,
                                village.getMember(offlinePlayer.getUniqueId())
                        ).build().open(player);
                    } else {
                        player.sendMessage(Chat.format(Message.VILLAGE_MEMBER_NULL.toString().replace("{0}", args[1])));
                    }
                } else {
                    player.sendMessage(Chat.format(Message.NO_PERMISSION.toString().replace("{0}", VillagePermission.EDIT_PERMISSIONS.name())));
                }
            } else {
                player.sendMessage(Chat.format(Message.VILLAGE_NULL.toString()));
            }
        } else {
            player.sendMessage(Chat.format(Message.USAGE.toString().replace("{0}", "/village" + getUsage())));
        }
        return false;
    }
}
