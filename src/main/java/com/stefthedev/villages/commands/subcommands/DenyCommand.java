package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.Villages;
import com.stefthedev.villages.utilities.Chat;
import com.stefthedev.villages.utilities.Command;
import com.stefthedev.villages.utilities.Message;
import com.stefthedev.villages.villages.VillageManager;
import com.stefthedev.villages.villages.VillageRequest;
import org.bukkit.entity.Player;

public class DenyCommand extends Command {

    private final VillageManager villageManager;

    public DenyCommand(Villages villages) {
        super("deny");
        villageManager = villages.getVillageManager();
    }

    @Override
    public boolean run(Player player, String[] args) {
        VillageRequest villageRequest = villageManager.getRequest(player);
        if(villageRequest == null) {
            player.sendMessage(Chat.format(Message.REQUEST_NULL.toString()));
        } else {
            player.sendMessage(Chat.format(Message.REQUEST_DENIED.toString()));
            villageManager.remove(villageRequest);
        }
        return false;
    }
}
