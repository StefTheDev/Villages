package com.stefthedev.villages.resources.commands.subcommands;

import com.stefthedev.villages.Villages;
import com.stefthedev.villages.data.settings.SettingType;
import com.stefthedev.villages.data.village.Village;
import com.stefthedev.villages.data.village.VillageClaim;
import com.stefthedev.villages.data.village.VillageMember;
import com.stefthedev.villages.data.village.VillagePermission;
import com.stefthedev.villages.hooks.WorldGuardHook;
import com.stefthedev.villages.managers.SettingsManager;
import com.stefthedev.villages.managers.VillageManager;
import com.stefthedev.villages.utilities.general.Chat;
import com.stefthedev.villages.utilities.general.Command;
import com.stefthedev.villages.utilities.general.Message;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class ClaimCommand extends Command {

    private final Villages villages;
    private final VillageManager villageManager;
    private final SettingsManager settingsManager;

    public ClaimCommand(Villages villages) {
        super("claim", "claim");
        this.villages = villages;
        this.villageManager = villages.getVillageManager();
        this.settingsManager = villages.getSettingsManager();
    }

    @Override
    public boolean run(Player player, String[] args) {
        Village village = villageManager.getVillage(player);
        if (village != null) {
            VillageMember villageMember = village.getMember(player.getUniqueId());
            if (villageMember.hasPermission(VillagePermission.CLAIM_LAND) || village.getOwner().equals(player.getUniqueId()) || village.hasPermission(VillagePermission.CLAIM_LAND)) {
                Chunk chunk = player.getLocation().getChunk();
                Village tempVillage = villageManager.getVillage(chunk);
                if (tempVillage == null) {
                    int claimLimit = villageManager.getMax(player);
                    int defaultClaimLimit = (int) settingsManager.getSetting(SettingType.DEFAULT_CLAIM_LIMIT).getElement();
                    if(village.getVillageClaims().size() < claimLimit || village.getVillageClaims().size() < claimLimit + defaultClaimLimit) {
                        if(villages.isWorldGuard()) {
                            if(new WorldGuardHook().isRegion(player)) {
                                player.sendMessage(Chat.format(Message.WORLDGUARD_CLAIM.toString()));
                                return true;
                            }
                        }
                        VillageClaim villageClaim = new VillageClaim(chunk.getWorld().getName(), chunk.getX(), chunk.getZ());
                        village.add(villageClaim);
                        player.sendMessage(Chat.format(Message.VILLAGE_CLAIM.toString().replace("{0}", villageClaim.toString())));
                    } else {
                        player.sendMessage(Chat.format(Message.VILLAGE_MAX_CLAIMS.toString().replace("{0}", String.valueOf(claimLimit + defaultClaimLimit))));
                    }
                } else if (tempVillage == village) {
                    player.sendMessage(Chat.format(Message.VILLAGE_CLAIM_OWNED.toString()));
                } else {
                    player.sendMessage(Chat.format(Message.VILLAGE_CLAIM_OTHER.toString()));
                }
            } else {
                player.sendMessage(Chat.format(Message.NO_PERMISSION.toString().replace("{0}", VillagePermission.CLAIM_LAND.name())));
            }
        } else {
            player.sendMessage(Chat.format(Message.VILLAGE_NULL.toString()));
        }
        return true;
    }
}
