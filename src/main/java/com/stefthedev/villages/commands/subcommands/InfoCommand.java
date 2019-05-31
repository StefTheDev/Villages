package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.Villages;
import com.stefthedev.villages.utilities.Chat;
import com.stefthedev.villages.utilities.Command;
import com.stefthedev.villages.utilities.Message;
import com.stefthedev.villages.villages.Village;
import com.stefthedev.villages.villages.VillageManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;

public class InfoCommand extends Command {

    private final VillageManager villageManager;

    public InfoCommand(Villages villages) {
        super("info");
        villageManager = villages.getVillageManager();
    }

    @Override
    public boolean run(Player player, String[] args) {
        if(args.length > 1) {
            Village village = villageManager.getVillage(args[1]);
            if(village == null) {
                player.sendMessage(Chat.format(Message.NULL.toString().replace("{0}", args[1])));
            } else {
                info(village, player);
            }
        } else {
            Village village = villageManager.getVillage(player);
            if(village == null) {
                player.sendMessage(Chat.format(Message.PLAYER_FALSE.toString()));
            } else {
                info(village, player);
            }
        }
        return false;
    }

    private void info(Village village, Player player) {
        Message.INFO.toList().forEach(s -> {
            s = s.replace("{0}", village.toString());
            s = s.replace("{1}", Objects.requireNonNull(Bukkit.getOfflinePlayer(village.getOwner()).getName()));
            s = s.replace("{2}", String.valueOf(village.getMembers().size()));
            s = s.replace("{3}", String.valueOf(village.getVillageClaims().size()));
            player.sendMessage(Chat.color(s));
        });
    }
}
