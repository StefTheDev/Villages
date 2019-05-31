package com.stefthedev.villages.commands.subcommands;

import com.stefthedev.villages.Villages;
import com.stefthedev.villages.utilities.Chat;
import com.stefthedev.villages.utilities.Command;
import com.stefthedev.villages.utilities.Message;
import com.stefthedev.villages.villages.Village;
import com.stefthedev.villages.villages.VillageClaim;
import com.stefthedev.villages.villages.VillageManager;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Objects;

public class ClaimCommand extends Command {

    private final VillageManager villageManager;

    public ClaimCommand(Villages villages) {
        super("claim");
        villageManager = villages.getVillageManager();
    }

    @Override
    public boolean run(Player player, String[] args) {
        Village village = villageManager.getVillage(player);
        if(village == null) {
            player.sendMessage(Chat.format(Message.PLAYER_FALSE.toString()));
        } else {
            if(!village.getOwner().equals(player.getUniqueId())) {
                player.sendMessage(Chat.format(Message.OWNER_CLAIM.toString()));
            } else if(village.getVillageClaims().contains(villageManager.getClaim(player.getLocation().getChunk()))) {
                player.sendMessage(Chat.format(Message.CLAIMED.toString()));
            } else if(villageManager.getClaim(player.getLocation().getChunk()) != null) {
                player.sendMessage(Chat.format(Message.CLAIMED_OTHER.toString()));
            } else {
                Chunk chunk = player.getLocation().getChunk();
                World world = chunk.getWorld();
                VillageClaim villageClaim = new VillageClaim(Objects.requireNonNull(world).getName(), chunk.getX(), chunk.getZ());
                village.add(villageClaim);

                player.sendMessage(Chat.format(Message.CLAIM.toString()));
            }
        }
        return false;
    }
}
