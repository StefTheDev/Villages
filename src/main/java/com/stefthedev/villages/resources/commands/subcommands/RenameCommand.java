package com.stefthedev.villages.resources.commands.subcommands;

import com.stefthedev.villages.data.village.Village;
import com.stefthedev.villages.data.village.VillageMember;
import com.stefthedev.villages.data.village.VillagePermission;
import com.stefthedev.villages.managers.VillageManager;
import com.stefthedev.villages.utilities.general.Chat;
import com.stefthedev.villages.utilities.general.Command;
import com.stefthedev.villages.utilities.general.Message;
import org.bukkit.entity.Player;

public class RenameCommand extends Command {

    private final VillageManager villageManager;

    public RenameCommand(VillageManager villageManager) {
        super("rename", "rename [name]");
        this.villageManager = villageManager;
    }

    @Override
    public boolean run(Player player, String[] args) {
        Village village = villageManager.getVillage(player);
        if(village != null) {
            VillageMember villageMember = village.getMember(player.getUniqueId());
            if(villageMember.hasPermission(VillagePermission.RENAME_VILLAGE) || village.getOwner().equals(player.getUniqueId()) || village.hasPermission(VillagePermission.RENAME_VILLAGE)) {
                if(args[1].length() > 32) {
                    player.sendMessage(Chat.format(Message.VILLAGE_CREATE_LIMIT.toString()));
                    return true;
                }
                Village other = villageManager.getVillage(args[1]);
                if(other == null) {
                    player.sendMessage(Chat.format(Message.VILLAGE_RENAME.toString().replace("{0}", args[1])));
                    village.setName(args[1]);
                } else {
                    player.sendMessage(Chat.format(Message.VILLAGE_EXISTS.toString().replace("{0}", village.getName())));
                }
            } else {
                player.sendMessage(Chat.format(Message.NO_PERMISSION.toString().replace("{0}", VillagePermission.RENAME_VILLAGE.name() )));
            }
        } else {
            player.sendMessage(Chat.format(Message.VILLAGE_NULL.toString()));
        }
        return false;
    }
}
