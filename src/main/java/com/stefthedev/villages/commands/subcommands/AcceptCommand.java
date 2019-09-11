package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.utilities.general.Chat;
import com.stefthedev.villages.utilities.general.Command;
import com.stefthedev.villages.utilities.general.Message;
import com.stefthedev.villages.managers.VillageManager;
import com.stefthedev.villages.villages.VillageRequest;
import org.bukkit.entity.Player;

public class AcceptCommand extends Command {

    private final VillageManager villageManager;

    public AcceptCommand(VillageManager villageManager) {
        super("accept", "accept");
        this.villageManager = villageManager;
    }

    @Override
    public boolean run(Player player, String[] args) {
        VillageRequest villageRequest = villageManager.getRequest(player);
        if(villageRequest == null) {
            player.sendMessage(Chat.format(Message.REQUEST_NULL.toString()));
        } else {
            villageRequest.complete(villageManager);
            villageManager.remove(villageRequest);
        }
        return false;
    }
}
