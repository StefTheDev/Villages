package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.Villages;
import com.stefthedev.villages.utilities.Chat;
import com.stefthedev.villages.utilities.Command;
import com.stefthedev.villages.utilities.Message;
import com.stefthedev.villages.villages.Village;
import com.stefthedev.villages.villages.VillageManager;
import org.bukkit.entity.Player;

public class UnClaimCommand extends Command {

    private final VillageManager villageManager;

    public UnClaimCommand(Villages villages) {
        super("unclaim");
        this.villageManager = villages.getVillageManager();
    }

    @Override
    public boolean run(Player player, String[] args) {
        Village village = villageManager.getVillage(player);
        if(village == null) {
            player.sendMessage(Chat.format(Message.PLAYER_FALSE.toString()));
        } else {
            if(!village.getOwner().equals(player.getUniqueId())) {
                player.sendMessage(Chat.format(Message.OWNER_UNCLAIM.toString()));
            } else if(!village.getVillageClaims().contains(villageManager.getClaim(player.getLocation().getChunk()))) {
                player.sendMessage(Chat.format(Message.UNCLAIM_OTHER.toString()));
            } else {
                village.remove(villageManager.getClaim(player.getLocation().getChunk()));
                player.sendMessage(Chat.format(Message.UNCLAIM.toString()));
            }
        }
        return false;
    }
}
