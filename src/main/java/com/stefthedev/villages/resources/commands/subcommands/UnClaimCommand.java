package com.stefthedev.villages.resources.commands.subcommands;

import com.stefthedev.villages.data.*;
import com.stefthedev.villages.managers.VillageManager;
import com.stefthedev.villages.utilities.general.Chat;
import com.stefthedev.villages.utilities.general.Command;
import com.stefthedev.villages.utilities.general.Message;
import org.bukkit.entity.Player;

public class UnClaimCommand extends Command {

    private final VillageManager villageManager;

    public UnClaimCommand(VillageManager villageManager) {
        super("unclaim", "unclaim");
        this.villageManager = villageManager;
    }

    @Override
    public boolean run(Player player, String[] args) {
        Village village = villageManager.getVillage(player);
        if(village != null) {
            VillageMember villageMember = village.getMember(player.getUniqueId());
            if(villageMember.hasPermission(VillagePermission.UNCLAIM_LAND) || village.getOwner().equals(player.getUniqueId())) {
                Village tempVillage = villageManager.getVillage(player.getLocation().getChunk());
                if(tempVillage == village) {
                    if(village.getVillageClaims().size() > 1) {
                        VillageClaim villageClaim = villageManager.getClaim(village, player.getLocation().getChunk());
                        village.remove(villageClaim);
                        player.sendMessage(Chat.format(Message.VILLAGE_UNCLAIM.toString()));
                    } else {
                        player.sendMessage(Chat.format(Message.VILLAGE_UNCLAIM_ONE.toString()));
                    }
                } else {
                    player.sendMessage(Chat.format(Message.VILLAGE_UNCLAIM_OTHER.toString()));
                }
            }
        } else {
            player.sendMessage(Chat.format(Message.VILLAGE_NULL.toString()));
        }
        return false;
    }
}
