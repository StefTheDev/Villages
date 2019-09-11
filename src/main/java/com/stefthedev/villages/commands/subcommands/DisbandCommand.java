package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.managers.VillageManager;
import com.stefthedev.villages.utilities.general.Chat;
import com.stefthedev.villages.utilities.general.Command;
import com.stefthedev.villages.utilities.general.Message;
import com.stefthedev.villages.villages.*;
import org.bukkit.entity.Player;

public class DisbandCommand extends Command {

    private final VillageManager villageManager;

    public DisbandCommand(VillageManager villageManager) {
        super("disband", "disband");
        this.villageManager = villageManager;
    }

    @Override
    public boolean run(Player player, String[] args) {
        Village village = villageManager.getVillage(player);
        if(village != null) {
            VillageMember villageMember = village.getMember(player.getUniqueId());
            if(villageMember.hasPermission(VillagePermission.DISBAND) || village.getOwner().equals(player.getUniqueId())) {
                VillageRequest villageRequest = villageManager.getRequest(player);
                if(villageRequest == null) {
                    villageRequest = new VillageRequest(village, player.getUniqueId(), null, VillageRequest.VillageRequestAction.DISBAND);
                    villageRequest.send();
                    villageManager.add(villageRequest);
                } else {
                    player.sendMessage(Chat.format(Message.REQUEST_PENDING.toString()));
                }
            } else {
                player.sendMessage(Chat.format(Message.NO_PERMISSION.toString().replace("{0}", VillagePermission.DISBAND.name())));
            }
        } else {
            player.sendMessage(Chat.format(Message.VILLAGE_NULL.toString()));
        }
        return false;
    }
}
