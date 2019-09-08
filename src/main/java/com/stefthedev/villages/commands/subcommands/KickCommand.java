package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.utilities.general.Chat;
import com.stefthedev.villages.utilities.general.Command;
import com.stefthedev.villages.utilities.general.Message;
import com.stefthedev.villages.villages.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class KickCommand extends Command {

    private final VillageManager villageManager;

    public KickCommand(VillageManager villageManager) {
        super("kick", "kick [player]");
        this.villageManager = villageManager;
    }

    @Override
    public boolean run(Player player, String[] args) {
        if(args.length == 2) {
            Village village = villageManager.getVillage(player);
            if(village != null) {
                VillageMember villageMember = village.getMember(player.getUniqueId());
                if(villageMember.hasPermission(VillagePermission.KICK_MEMBER) || village.getOwner().equals(player.getUniqueId())) {
                    OfflinePlayer offlinePlayer = villageManager.offlinePlayer(village, args[1]);
                    if(offlinePlayer != null) {
                        if(offlinePlayer.getUniqueId() != player.getUniqueId()) {
                            VillageRequest villageRequest = villageManager.getRequest(player);
                            if (villageRequest == null) {
                                villageRequest = new VillageRequest(village, player.getUniqueId(), offlinePlayer.getUniqueId(), VillageRequest.VillageRequestAction.KICK);
                                villageRequest.send();
                                villageManager.add(villageRequest);
                            } else {
                                player.sendMessage(Chat.format(Message.REQUEST_PENDING.toString()));
                            }
                        } else {
                            player.sendMessage(Chat.format(Message.REQUEST_KICK_SELF.toString()));
                        }
                    } else {
                        player.sendMessage(Chat.format(Message.VILLAGE_MEMBER_NULL.toString().replace("{0}", args[1])));
                    }
                } else {
                    player.sendMessage(Chat.format(Message.NO_PERMISSION.toString().replace("{0}", VillagePermission.KICK_MEMBER.name())));
                }
            } else {
                player.sendMessage(Chat.format(Message.VILLAGE_NULL.toString()));
            }
        } else {
            player.sendMessage(Chat.format(Message.USAGE.toString().replace("{0}", "/village" + getUsage())));
        }
        return true;
    }
}
