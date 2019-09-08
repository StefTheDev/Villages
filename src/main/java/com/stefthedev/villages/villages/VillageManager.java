package com.stefthedev.villages.villages;
import com.stefthedev.villages.utilities.general.Manager;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class VillageManager extends Manager<Village> {

    private final Plugin plugin;
    private final Set<VillageRequest> villageRequests;

    public VillageManager(Plugin plugin) {
        super("villages", plugin);
        this.plugin = plugin;
        this.villageRequests = new HashSet<>();
    }

    public void add(VillageRequest villageRequest) {
        villageRequests.add(villageRequest);
    }

    public void remove(VillageRequest villageRequest) {
        villageRequests.remove(villageRequest);
    }

    public Village getVillage(String string) {
        for(Village village : toSet()) {
            if(village.getName().equalsIgnoreCase(string)) {
                return village;
            }
        }
        return null;
    }

    public Village getVillage(Chunk chunk) {
        for(Village village : toSet()) {
            for(VillageClaim villageClaim : village.getVillageClaims()) {
                if(chunkMatchesClaim(chunk, villageClaim)) return village;
            }
        }
        return null;
    }

    public Village getVillage(Player player) {
        for(Village village : toSet()) {
            for(VillageMember villageMember : village.getVillageMembers()) {
                if(villageMember.getUniqueId().equals(player.getUniqueId())) return village;
            }
        }
        return null;
    }

    public VillageRequest getRequest(Player player) {
        for(VillageRequest villageRequest: villageRequests) {
            if(villageRequest.getUuid().equals(player.getUniqueId())) {
                return villageRequest;
            }
        }
        return null;
    }

    public VillageClaim getClaim(Village village, Chunk chunk) {
        for(VillageClaim villageClaim : village.getVillageClaims()) {
            if(chunkMatchesClaim(chunk, villageClaim)) {
                return villageClaim;
            }
        }
        return null;
    }

    public OfflinePlayer offlinePlayer(Village village, String name) {
        for (VillageMember villageMember : village.getVillageMembers()) {
            OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(villageMember.getUniqueId());
            if(Objects.requireNonNull(offlinePlayer.getName()).equalsIgnoreCase(name)) {
                return offlinePlayer;
            }
        }
        return null;
    }

    private boolean chunkMatchesClaim(Chunk chunk, VillageClaim villageClaim) {
        return villageClaim.getX() == chunk.getX() && villageClaim.getZ() == chunk.getZ() && villageClaim.getWorld().equals(chunk.getWorld().getName());
    }

    public Set<VillageRequest> getRequests() {
        return Collections.unmodifiableSet(villageRequests);
    }
}
