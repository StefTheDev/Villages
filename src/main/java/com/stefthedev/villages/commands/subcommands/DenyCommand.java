package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.utilities.general.Chat;
import com.stefthedev.villages.utilities.general.Command;
import com.stefthedev.villages.utilities.general.Message;
import com.stefthedev.villages.villages.VillageManager;
import com.stefthedev.villages.villages.VillageRequest;
import org.bukkit.entity.Player;

public class DenyCommand extends Command {

    private final VillageManager villageManager;

    public DenyCommand(VillageManager villageManager) {
        super("deny", "deny");
        this.villageManager = villageManager;
    }

    @Override
    public boolean run(Player player, String[] args) {
        VillageRequest villageRequest = villageManager.getRequest(player);
        if(villageRequest == null) {
            player.sendMessage(Chat.format(Message.REQUEST_NULL.toString()));
        } else {
            player.sendMessage(Chat.format(Message.REQUEST_DENIED.toString()));
            villageManager.getRequests().remove(villageRequest);
        }
        return false;
    }
}
