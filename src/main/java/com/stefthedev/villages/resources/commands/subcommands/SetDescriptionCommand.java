package com.stefthedev.villages.resources.commands.subcommands;

import com.stefthedev.villages.utilities.general.Chat;
import com.stefthedev.villages.utilities.general.Command;
import com.stefthedev.villages.utilities.general.Message;
import com.stefthedev.villages.data.village.Village;
import com.stefthedev.villages.managers.VillageManager;
import com.stefthedev.villages.data.village.VillageMember;
import com.stefthedev.villages.data.village.VillagePermission;
import org.bukkit.entity.Player;

public class SetDescriptionCommand extends Command {

    private final VillageManager villageManager;

    public SetDescriptionCommand(VillageManager villageManager) {
        super("setdescription", "setdescription [description]");
        this.villageManager = villageManager;
    }

    @Override
    public boolean run(Player player, String[] args) {
        Village village = villageManager.getVillage(player);
        if(village != null) {
            VillageMember villageMember = village.getMember(player.getUniqueId());
            if(villageMember.hasPermission(VillagePermission.SET_DESCRIPTION) || village.getOwner().equals(player.getUniqueId()) || village.hasPermission(VillagePermission.SET_DESCRIPTION)) {
                StringBuilder description = new StringBuilder();
                for(String string: args) {
                    if(!string.equalsIgnoreCase(toString())) description.append(string).append(" ");
                }
                if(description.toString().length() <= 32) {
                    village.setDescription(description.toString());
                    player.sendMessage(Chat.format(Message.VILLAGE_SET_DESCRIPTION.toString().replace("{0}", description.toString())));
                } else {
                    player.sendMessage(Chat.format(Message.VILLAGE_DESCRIPTION_LIMIT.toString()));
                }
            } else {
                player.sendMessage(Chat.format(Message.NO_PERMISSION.toString().replace("{0}", VillagePermission.SET_DESCRIPTION.name() )));
            }
        } else {
            player.sendMessage(Chat.format(Message.VILLAGE_NULL.toString()));
        }
        return false;
    }
}
