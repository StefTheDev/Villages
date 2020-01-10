package com.stefthedev.villages.resources.commands.subcommands;

import com.stefthedev.villages.Villages;
import com.stefthedev.villages.utilities.general.Chat;
import com.stefthedev.villages.utilities.general.Command;
import com.stefthedev.villages.utilities.general.Message;
import com.stefthedev.villages.data.village.Village;
import com.stefthedev.villages.managers.VillageManager;
import com.stefthedev.villages.data.village.VillageMember;
import com.stefthedev.villages.data.village.VillagePermission;
import org.bukkit.entity.Player;


public class HomeCommand extends Command {

    private final Villages villages;
    private final VillageManager villageManager;

    public HomeCommand(Villages villages) {
        super("home", "home");
        this.villages = villages;
        this.villageManager = villages.getVillageManager();
    }

    @Override
    public boolean run(Player player, String[] args) {
        Village village = villageManager.getVillage(player);
        if(village != null) {
            VillageMember villageMember = village.getMember(player.getUniqueId());
            if(villageMember.hasPermission(VillagePermission.HOME) || village.getOwner().equals(player.getUniqueId()) || village.hasPermission(VillagePermission.HOME)) {
                if(villageMember.hasCooldown()) {
                    player.sendMessage(Chat.format(Message.VILLAGE_COOLDOWN.toString().replace("{0}", String.valueOf((villageMember.getCooldown()/1000) - (System.currentTimeMillis()/1000)))));
                } else {
                    player.teleport(village.getVillageLocation().toLocation(villages));
                    player.sendMessage(Chat.format(Message.VILLAGE_HOME.toString()));
                    villageMember.setCooldown(30);
                }
            } else {
                player.sendMessage(Chat.format(Message.NO_PERMISSION.toString().replace("{0}", VillagePermission.HOME.name())));
            }
        } else {
            player.sendMessage(Chat.format(Message.VILLAGE_NULL.toString()));
        }
        return false;
    }
}
