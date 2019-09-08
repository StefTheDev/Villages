package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.utilities.general.Chat;
import com.stefthedev.villages.utilities.general.Command;
import com.stefthedev.villages.utilities.general.Message;
import com.stefthedev.villages.villages.Village;
import com.stefthedev.villages.villages.VillageClaim;
import com.stefthedev.villages.villages.VillageManager;

import com.stefthedev.villages.villages.VillageMember;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CreateCommand extends Command {

    private final VillageManager villageManager;

    public CreateCommand(VillageManager villageManager) {
        super("create", "create [name]");
        this.villageManager = villageManager;
    }

    @Override
    public boolean run(Player player, String[] args) {
        if(args.length == 2) {
            Village village = villageManager.getVillage(player);
            if (village == null) {
                village = villageManager.getVillage(args[1]);
                if (village != null) {
                    player.sendMessage(Chat.format(Message.VILLAGE_EXISTS.toString().replace("{0}", village.getName())));
                } else {
                    Location location = player.getLocation();
                    if(location.getWorld() == null) return true;

                    Chunk chunk = player.getLocation().getChunk();
                    village = new Village(args[1], "A peaceful settlement.", player.getUniqueId());
                    village.add(new VillageClaim(chunk.getWorld().getName(), chunk.getX(), chunk.getZ()));
                    village.add(new VillageMember(player.getUniqueId()));
                    village.setLocation(location);

                    villageManager.add(village);
                    player.sendMessage(Chat.format(Message.VILLAGE_CREATE.toString().replace("{0}", village.getName())));
                }
            } else {
                player.sendMessage(Chat.format(Message.VILLAGE_NOT_NULL.toString()));
            }
        } else {
            player.sendMessage(Chat.format(Message.USAGE.toString().replace("{0}", "/village" + getUsage())));
        }
        return false;
    }
}
