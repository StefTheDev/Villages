package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.Villages;
import com.stefthedev.villages.utilities.Chat;
import com.stefthedev.villages.utilities.Command;
import com.stefthedev.villages.utilities.Message;
import com.stefthedev.villages.villages.Village;
import com.stefthedev.villages.villages.VillageManager;
import com.stefthedev.villages.villages.VillageRequest;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class KickCommand extends Command {

    private final Villages villages;
    private final VillageManager villageManager;

    public KickCommand(Villages villages) {
        super("kick");
        this.villages = villages;
        this.villageManager = villages.getVillageManager();
    }

    @Override
    public boolean run(Player player, String[] args) {
        if (args.length == 2) {
            Village village = villageManager.getVillage(player);
            if (village == null) {
                player.sendMessage(Chat.format(Message.PLAYER_FALSE.toString()));
                return true;
            } else {
                if (village.getOwner().equals(player.getUniqueId())) {
                    OfflinePlayer offlinePlayer = offlinePlayer(village, args[1]);
                    if(offlinePlayer == null) {
                        player.sendMessage(Chat.format(Message.REQUEST_OFFLINE.toString()).replace("{0}", args[1]));
                    } else {
                        VillageRequest villageRequest = new VillageRequest(village, player.getUniqueId(), offlinePlayer.getUniqueId(), VillageRequest.VillageRequestAction.KICK);
                        villageRequest.send();
                        villageManager.add(villageRequest);
                    }
                } else {
                    player.sendMessage(Chat.format(Message.OWNER_KICK.toString()));
                }
            }
        } else {
            player.sendMessage(Chat.format(Message.USAGE.toString().replace("{0}", "kick [player]")));
        }
        return true;
    }

    private OfflinePlayer offlinePlayer(Village village, String name) {
        for (UUID uuid : village.getMembers()) {
            OfflinePlayer offlinePlayer = villages.getServer().getOfflinePlayer(uuid);
            if(Objects.requireNonNull(offlinePlayer.getName()).equalsIgnoreCase(name)) {
                return offlinePlayer;
            }
        }
        return null;
    }
}
