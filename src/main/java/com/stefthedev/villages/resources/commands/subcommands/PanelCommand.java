package com.stefthedev.villages.resources.commands.subcommands;

import com.stefthedev.villages.Villages;
import com.stefthedev.villages.menus.PanelMenu;
import com.stefthedev.villages.utilities.general.Chat;
import com.stefthedev.villages.utilities.general.Command;
import com.stefthedev.villages.utilities.general.Message;
import com.stefthedev.villages.data.village.Village;
import com.stefthedev.villages.managers.VillageManager;
import com.stefthedev.villages.data.village.VillageMember;
import com.stefthedev.villages.data.village.VillagePermission;
import org.bukkit.entity.Player;

public class PanelCommand extends Command {

    private final Villages villages;
    private final VillageManager villageManager;

    public PanelCommand(Villages villages) {
        super("panel", "panel");
        this.villages = villages;
        this.villageManager = villages.getVillageManager();
    }

    @Override
    public boolean run(Player player, String[] args) {
        Village village = villageManager.getVillage(player);
        if(village != null) {
            VillageMember villageMember = village.getMember(player.getUniqueId());
            if(villageMember.hasPermission(VillagePermission.PANEL) || village.getOwner().equals(player.getUniqueId()) || village.hasPermission(VillagePermission.PANEL)) {
                new PanelMenu(villages, villageManager, village).build().open(player);
            } else {
                player.sendMessage(Chat.format(Message.NO_PERMISSION.toString().replace("{0}", VillagePermission.PANEL.name() )));
            }
        } else {
            player.sendMessage(Chat.format(Message.VILLAGE_NULL.toString()));
        }
        return false;
    }
}
