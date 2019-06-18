package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.Villages;
import com.stefthedev.villages.utilities.Chat;
import com.stefthedev.villages.utilities.Command;
import com.stefthedev.villages.utilities.Message;
import com.stefthedev.villages.villages.Village;
import com.stefthedev.villages.villages.VillageManager;
import com.stefthedev.villages.villages.VillageRequest;
import org.bukkit.entity.Player;

public class DisbandCommand extends Command {

    private final VillageManager villageManager;

    public DisbandCommand(Villages villages) {
        super("disband");
        this.villageManager = villages.getVillageManager();
    }

    @Override
    public boolean run(Player player, String[] args) {
        Village village = villageManager.getVillage(player);
        if(village == null) {
            player.sendMessage(Chat.format(Message.PLAYER_FALSE.toString()));
        } else {
            if(!village.getOwner().equals(player.getUniqueId())) {
                player.sendMessage(Chat.format(Message.OWNER_DISBAND.toString()));
            } else {
                VillageRequest villageRequest = villageManager.getRequest(player);
                if(villageRequest == null) {
                    villageRequest = new VillageRequest(village, player.getUniqueId(), null, VillageRequest.VillageRequestAction.DISBAND);
                    villageRequest.send();
                    villageManager.add(villageRequest);
                } else {
                    player.sendMessage(Chat.format(Message.REQUEST_PENDING.toString()));
                }
            }
        }
        return false;
    }
}
