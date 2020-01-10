package com.stefthedev.villages.resources.commands.subcommands;

import com.stefthedev.villages.Villages;
import com.stefthedev.villages.data.village.VillageClaim;
import com.stefthedev.villages.data.village.VillagePermission;
import com.stefthedev.villages.hooks.WorldGuardHook;
import com.stefthedev.villages.utilities.general.Chat;
import com.stefthedev.villages.utilities.general.Command;
import com.stefthedev.villages.utilities.general.Message;
import com.stefthedev.villages.data.village.Village;
import com.stefthedev.villages.managers.VillageManager;

import com.stefthedev.villages.data.village.VillageMember;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class CreateCommand extends Command {

    private final Villages villages;
    private final VillageManager villageManager;

    public CreateCommand(Villages villages) {
        super("create", "create [name]");
        this.villages = villages;
        this.villageManager = villages.getVillageManager();
    }

    @Override
    public boolean run(Player player, String[] args) {
        if (args.length == 2) {
            Village village = villageManager.getVillage(player);
            if (village == null) {
                if(args[1].length() > 32) {
                    player.sendMessage(Chat.format(Message.VILLAGE_CREATE_LIMIT.toString()));
                    return true;
                }
                village = villageManager.getVillage(args[1]);
                if (village != null) {
                    player.sendMessage(Chat.format(Message.VILLAGE_EXISTS.toString().replace("{0}", village.getName())));
                } else {
                    Chunk chunk = player.getLocation().getChunk();
                    Village tempVillage = villageManager.getVillage(chunk);
                    if (tempVillage != null) {
                        player.sendMessage(Chat.format(Message.VILLAGE_CREATE_OTHER.toString().replace("{0}", tempVillage.getName())));
                    } else {
                        if(villages.isWorldGuard()) {
                            if(new WorldGuardHook().isRegion(player)) {
                                player.sendMessage(Chat.format(Message.WORLDGUARD_CREATE.toString()));
                                return true;
                            }
                        }
                        village = new Village(args[1], "A peaceful settlement.", player.getUniqueId());
                        village.add(new VillageClaim(chunk.getWorld().getName(), chunk.getX(), chunk.getZ()));
                        village.add(new VillageMember(player.getUniqueId()));

                        village.add(VillagePermission.CHEST_ACCESS);
                        village.add(VillagePermission.WATER_PLACEMENT);
                        village.add(VillagePermission.LAVA_PLACEMENT);
                        village.add(VillagePermission.BLOCK_BREAK);
                        village.add(VillagePermission.BLOCK_PLACE);
                        village.add(VillagePermission.FURNACE_ACCESS);
                        village.add(VillagePermission.SHULKER_ACCESS);
                        village.add(VillagePermission.HOME);

                        village.setLocation(player.getLocation());

                        villageManager.add(village);
                        player.sendMessage(Chat.format(Message.VILLAGE_CREATE.toString().replace("{0}", village.getName())));
                    }
                }
            } else {
                player.sendMessage(Chat.format(Message.VILLAGE_NOT_NULL.toString()));
            }
        } else {
            player.sendMessage(Chat.format(Message.USAGE.toString().replace("{0}", "/village " + getUsage())));
        }
        return false;
    }
}
