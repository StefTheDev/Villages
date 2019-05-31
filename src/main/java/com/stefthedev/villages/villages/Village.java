package com.stefthedev.villages.villages;

import org.bukkit.Chunk;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Village {

    private final String name;
    private final UUID owner;

    private final Set<UUID> members;
    private final Set<VillageClaim> villageClaims;

    public Village(String name, UUID owner) {
        this.name = name;
        this.owner = owner;
        this.members = new HashSet<>();
        villageClaims = new HashSet<>();
    }

    public void add(VillageClaim villageClaim) {
        this.villageClaims.add(villageClaim);
    }

    void add(UUID uuid) {
        this.members.add(uuid);
    }

    public void remove(VillageClaim villageClaim) {
        this.villageClaims.remove(villageClaim);
    }

    void remove(UUID uuid) {
        this.members.remove(uuid);
    }

    VillageClaim get(Chunk chunk) {
        for (VillageClaim villageClaim : villageClaims) {
            if (villageClaim.getX() == chunk.getX() &&
                    villageClaim.getZ() == chunk.getZ() &&
                    villageClaim.getWorld().equalsIgnoreCase(chunk.getWorld().getName())) {
                return villageClaim;
            }
        }
        return null;
    }

    public UUID getOwner() {
        return owner;
    }

    public Set<UUID> getMembers() {
        Set<UUID> memberSet = members;
        memberSet.add(owner);
        return Collections.unmodifiableSet(memberSet);
    }

    public Set<VillageClaim> getVillageClaims() {
        return Collections.unmodifiableSet(villageClaims);
    }

    @Override
    public String toString() {
        return name;
    }
}
