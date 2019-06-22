package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.Villages;
import com.stefthedev.villages.hooks.WorldGuardHook;
import com.stefthedev.villages.utilities.Chat;
import com.stefthedev.villages.utilities.Command;
import com.stefthedev.villages.utilities.Message;
import com.stefthedev.villages.villages.Village;
import com.stefthedev.villages.villages.VillageClaim;
import com.stefthedev.villages.villages.VillageManager;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class CreateCommand extends Command {

    private final VillageManager villageManager;

    public CreateCommand(Villages villages) {
        super("create");
        villageManager = villages.getVillageManager();
    }

    @Override
    public boolean run(Player player, String[] args) {
        if (args.length == 2) {
            Village village = villageManager.getVillage(args[1]);
            if (villageManager.getVillage(player) != null) {
                player.sendMessage(Chat.format(Message.PLAYER_TRUE.toString()));
                return true;
            }
            if (village == null) {
                if (villageManager.getClaim(player.getLocation().getChunk()) != null) {
                    player.sendMessage(Chat.format(Message.CREATE_CLAIMED.toString()));
                    return true;
                }

                try {
                    if(new WorldGuardHook().isRegion(player)) {
                        player.sendMessage(Chat.format(Message.WORLDGUARD_CREATE.toString()));
                        return true;
                    }
                } catch (NoClassDefFoundError ignored) {

                }

                village = new Village(args[1], player.getUniqueId());
                Chunk chunk = player.getLocation().getChunk();
                village.add(new VillageClaim(player.getWorld().getName(), chunk.getX(), chunk.getZ()));
                villageManager.add(village);
                Bukkit.getServer().broadcastMessage(Chat.format(Message.CREATE.toString()
                        .replace("{0}", player.getName())
                        .replace("{1}", village.toString())
                ));

            } else {
                player.sendMessage(Chat.format(Message.CREATE_EXISTS.toString()
                        .replace("{0}", village.toString())
                ));
            }
        } else {
            player.sendMessage(Chat.format(Message.USAGE.toString()
                    .replace("{0}", "create [name]")
            ));
        }
        return false;
    }
}
