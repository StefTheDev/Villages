package com.stefthedev.villages.managers;
import com.stefthedev.villages.data.village.*;
import com.stefthedev.villages.utilities.general.Manager;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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
        for (Village village : toSet()) {
            if (village.getName().equalsIgnoreCase(string)) {
                return village;
            }
        }
        return null;
    }

    public Village getVillage(Chunk chunk) {
        for (Village village : toSet()) {
            for (VillageClaim villageClaim : village.getVillageClaims()) {
                if (chunkMatchesClaim(chunk, villageClaim)) return village;
            }
        }
        return null;
    }

    public Village getVillage(Player player) {
        for (Village village : toSet()) {
            for (VillageMember villageMember : village.getVillageMembers()) {
                if (villageMember.getUniqueId().equals(player.getUniqueId())) return village;
            }
        }
        return null;
    }

    public VillageRequest getRequest(Player player) {
        for (VillageRequest villageRequest : villageRequests) {
            if (villageRequest.getTarget() != null) {
                if (villageRequest.getTarget().equals(player.getUniqueId())) {
                    return villageRequest;
                }
            } else {
                if (villageRequest.getUniqueId().equals(player.getUniqueId())) {
                    return villageRequest;
                }
            }
        }
        return null;
    }
    public VillageClaim getClaim(Village village, Chunk chunk) {
        for (VillageClaim villageClaim : village.getVillageClaims()) {
            if (chunkMatchesClaim(chunk, villageClaim)) {
                return villageClaim;
            }
        }
        return null;
    }

    public OfflinePlayer offlinePlayer(Village village, String name) {
        for (VillageMember villageMember : village.getVillageMembers()) {
            OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(villageMember.getUniqueId());
            if (Objects.requireNonNull(offlinePlayer.getName()).equalsIgnoreCase(name)) {
                return offlinePlayer;
            }
        }
        return null;
    }

    public int getMax(Player player) {
        final AtomicInteger max = new AtomicInteger();

        player.getEffectivePermissions().stream().map(PermissionAttachmentInfo::getPermission).map(String::toLowerCase).filter(value ->
                value.startsWith("village.claims.")).map(value ->
                value.replace("village.claims.", "")).forEach(value -> { max.set(-1);
            try {
                if (Integer.parseInt(value) > max.get()) max.set(Integer.parseInt(value));
            } catch (NumberFormatException ignored) { max.set(0); }
        });

        return max.get();
    }

    public boolean checkPermission(VillagePermission villagePermission, Village village, Player player) {
        return (!village.getMember(player.getUniqueId()).hasPermission(villagePermission) || !village.getOwner().equals(player.getUniqueId())) && !village.hasPermission(villagePermission);
    }

    private boolean chunkMatchesClaim(Chunk chunk, VillageClaim villageClaim) {
        return villageClaim.getX() == chunk.getX() && villageClaim.getZ() == chunk.getZ() && villageClaim.getWorld().equals(chunk.getWorld().getName());
    }

    public Set<VillageRequest> getRequests() {
        return Collections.unmodifiableSet(villageRequests);
    }

}
