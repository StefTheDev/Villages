package com.stefthedev.villages.villages;

import com.stefthedev.villages.utilities.Config;
import org.bukkit.Chunk;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.*;

public class VillageManager {

    private final Config config;

    private final Set<Village> villages;
    private final Set<VillageRequest> villageRequests;

    public VillageManager(Config config) {
        this.config = config;
        this.villages = new HashSet<>();
        this.villageRequests = new HashSet<>();
    }

    public void load() {
        Configuration configuration = config.getFileConfiguration();
        ConfigurationSection section = configuration.getConfigurationSection("");
        if(section == null) return;
        section.getKeys(false).forEach(s -> {
            Village village = new Village(s, UUID.fromString(Objects.requireNonNull(configuration.getString(s + ".owner"))));
            configuration.getStringList(s + ".claims").forEach(claim -> village.add(parse(claim)));
            configuration.getStringList(s + ".members").forEach(member-> village.add(UUID.fromString(member)));

            villages.add(village);
        });
    }

    public void unload() {
        Configuration configuration = config.getFileConfiguration();

        Map<String, Object> configValues = configuration.getValues(false);
        for (Map.Entry<String, Object> entry : configValues.entrySet()) {
            configuration.set(entry.getKey(), null);
        }

        villages.forEach(village -> {
            configuration.set(village.toString() + ".owner", village.getOwner().toString());

            List<String> claims = new ArrayList<>();
            village.getVillageClaims().forEach(villageClaim -> claims.add(villageClaim.toString()));
            configuration.set(village.toString() + ".claims", claims);

            List<String> members = new ArrayList<>();
            village.getMembers().forEach(member -> members.add(member.toString()));
            configuration.set(village.toString() + ".members", members);
        });
        config.save();
    }

    public VillageClaim getClaim(Chunk chunk) {
        for(Village village : villages) {
            for(VillageClaim villageClaim : village.getVillageClaims()) {
                if(village.get(chunk) == villageClaim) {
                    return villageClaim;
                }
            }
        }
        return null;
    }

    public void add(Village village) {
        this.villages.add(village);
    }

    public void add(VillageRequest villageRequest ) {
        this.villageRequests.add(villageRequest);
    }

    void remove(Village village) {
        this.villages.remove(village);
    }

    public void remove(VillageRequest villageRequest) {
        this.villageRequests.remove(villageRequest);
    }

    public Village getVillage(String string) {
        for(Village village : villages) {
            if(village.toString().equalsIgnoreCase(string)) {
                return village;
            }
        }
        return null;
    }

    public Village getVillage(Chunk chunk) {
        for (Village village : villages) {
            if (village.get(chunk) != null) {
                return village;
            }
        }
        return null;
    }

    public Village getVillage(Player player) {
        for(Village village : villages) {
            if(village.getMembers().contains(player.getUniqueId())) {
                return village;
            }
        }
        return null;
    }

    public VillageRequest getRequest(Player player) {
        for(VillageRequest villageRequest : villageRequests) {
            if(villageRequest.getTarget() != null) {
                if(villageRequest.getTarget().equals(player.getUniqueId())) {
                    return villageRequest;
                }
            }
            if(villageRequest.getUuid().equals(player.getUniqueId())) {
                return villageRequest;
            }
        }
        return null;
    }

    private VillageClaim parse(String string) {
        String[] strings = string.split(":");

        String world = strings[0];
        double x = Double.parseDouble(strings[1]);
        double z = Double.parseDouble(strings[2]);

        return new VillageClaim(world, x, z);
    }

    public Set<Village> getVillages() {
        return Collections.unmodifiableSet(villages);
    }
}
