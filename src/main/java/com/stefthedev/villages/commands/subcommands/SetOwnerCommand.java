package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.utilities.general.Chat;
import com.stefthedev.villages.utilities.general.Command;
import com.stefthedev.villages.utilities.general.Message;
import com.stefthedev.villages.villages.Village;
import com.stefthedev.villages.managers.VillageManager;
import com.stefthedev.villages.villages.VillageMember;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Objects;

public class SetOwnerCommand extends Command {

    private final VillageManager villageManager;

    public SetOwnerCommand(VillageManager villageManager) {
        super("setowner", "setowner [player]");
        this.villageManager = villageManager;
    }

    @Override
    public boolean run(Player player, String[] args) {
        if(args.length == 2) {
            Village village = villageManager.getVillage(player);
            if (village != null) {
                VillageMember villageMember = village.getMember(player.getUniqueId());
                if(village.getOwner().equals(villageMember.getUniqueId())) {
                    OfflinePlayer offlinePlayer = villageManager.offlinePlayer(village, args[1]);
                    if (offlinePlayer != null) {
                        if(!village.getOwner().equals(offlinePlayer.getUniqueId())) {
                            village.setOwner(offlinePlayer.getUniqueId());
                            player.sendMessage(Chat.format(Message.VILLAGE_SET_OWNER.toString().replace("{0}", Objects.requireNonNull(offlinePlayer.getName()))));
                        } else {
                            player.sendMessage(Chat.format(Message.VILLAGE_ALREADY_OWNER.toString()));
                        }
                    } else {
                        player.sendMessage(Chat.format(Message.VILLAGE_MEMBER_NULL.toString().replace("{0}", args[1])));
                    }
                } else {
                    player.sendMessage(Chat.format(Message.VILLAGE_OWNER.toString()));
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
