package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.utilities.general.Chat;
import com.stefthedev.villages.utilities.general.Command;
import com.stefthedev.villages.utilities.general.Message;
import com.stefthedev.villages.villages.Village;
import com.stefthedev.villages.villages.VillageManager;
import com.stefthedev.villages.villages.VillageMember;
import org.bukkit.entity.Player;

public class LeaveCommand extends Command {

    private final VillageManager villageManager;

    public LeaveCommand(VillageManager villageManager) {
        super("leave", "leave");
        this.villageManager = villageManager;
    }

    @Override
    public boolean run(Player player, String[] args) {
        Village village = villageManager.getVillage(player);
        if(village != null) {
            if(!village.getOwner().equals(player.getUniqueId())) {
                player.sendMessage(Chat.format(Message.VILLAGE_LEAVE.toString().replace("{0}", village.getName())));
                VillageMember villageMember = village.getMember(player.getUniqueId());
                village.remove(villageMember);
            } else {
                player.sendMessage(Chat.format(Message.VILLAGE_LEAVE_OWNER.toString()));
            }
        } else {
            player.sendMessage(Chat.format(Message.VILLAGE_NULL.toString()));
        }
        return false;
    }
}
