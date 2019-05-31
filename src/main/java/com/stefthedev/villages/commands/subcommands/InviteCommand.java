package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.Villages;
import com.stefthedev.villages.utilities.Chat;
import com.stefthedev.villages.utilities.Command;
import com.stefthedev.villages.utilities.Message;
import com.stefthedev.villages.villages.Village;
import com.stefthedev.villages.villages.VillageManager;
import com.stefthedev.villages.villages.VillageRequest;
import org.bukkit.entity.Player;

public class InviteCommand extends Command {

    private final Villages villages;
    private final VillageManager villageManager;

    public InviteCommand(Villages villages) {
        super("invite");
        this.villages = villages;
        this.villageManager = villages.getVillageManager();
    }

    @Override
    public boolean run(Player player, String[] args) {
        if(args.length == 2) {
            Player target = villages.getServer().getPlayer(args[1]);
            if(target == null) {
                player.sendMessage(Chat.format(Message.PLAYER_OFFLINE.toString()));
            } else {
                Village village = villageManager.getVillage(player);
                if(village == null) {
                    player.sendMessage(Chat.format(Message.PLAYER_FALSE.toString()));
                    return true;
                }
                VillageRequest villageRequest = villageManager.getRequest(player);
                if(villageRequest != null) {
                    player.sendMessage(Chat.format(Message.REQUEST_PENDING_TARGET.toString()).replace("{0}", target.getName()));
                    return true;
                }
                if(village.getOwner().equals(player.getUniqueId())) {
                    Village targetVillage = villageManager.getVillage(target);
                    if(targetVillage != null) {
                        player.sendMessage(Chat.format(Message.TARGET_TRUE.toString().replace("{0}", target.getName())));
                    } else {
                        villageRequest = new VillageRequest(village, player.getUniqueId(), target.getUniqueId(), VillageRequest.VillageRequestAction.INVITE);
                        villageRequest.send();
                        villageManager.add(villageRequest);
                    }
                } else {
                    player.sendMessage(Chat.format(Message.OWNER_INVITE.toString()));
                }
            }
        } else {
            player.sendMessage(Chat.format(Message.USAGE.toString().replace("{0}", "invite [player]")));
        }
        return false;
    }
}
