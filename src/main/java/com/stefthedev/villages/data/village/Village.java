package com.stefthedev.villages.data.village;

import org.bukkit.Location;

import java.util.*;

public class Village {

    private String name, description;
    private boolean peaceful;
    private int level;
    private UUID owner;

    private final Set<VillageMember> villageMembers;
    private final Set<VillageClaim> villageClaims;
    private final Set<VillagePermission> villagePermissions;

    private VillageLocation villageLocation;

    public Village(String name, String description, UUID owner) {
        this.name = name;
        this.description = description;
        this.owner = owner;

        this.villageMembers = new HashSet<>();
        this.villageClaims = new HashSet<>();
        this.villagePermissions = new HashSet<>();
    }

    public void add(int level) {
        this.level += level;
    }

    public void add(VillageMember villageMember) {
        villageMembers.add(villageMember);
    }

    public void add(VillageClaim villageClaim) {
        villageClaims.add(villageClaim);
    }

    public void add(VillagePermission villagePermission) {
        villagePermissions.add(villagePermission);
    }

    public void remove(VillageMember villageMember) {
        villageMembers.remove(villageMember);
    }

    public void remove(VillageClaim villageClaim) {
        villageClaims.remove(villageClaim);
    }

    public void remove(VillagePermission villagePermission) {
        villagePermissions.remove(villagePermission);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public void setPeaceful(boolean peaceful) {
        this.peaceful = peaceful;
    }

    public void setLocation(Location location) {
        this.villageLocation = new VillageLocation(
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch(),
                Objects.requireNonNull(location.getWorld()).getName()
        );
    }

    public VillageMember getMember(UUID uuid) {
        for(VillageMember villageMember : villageMembers) {
            if(villageMember.getUniqueId().equals(uuid)) {
                return villageMember;
            }
        }
        return null;
    }

    public boolean hasPermission(VillagePermission villagePermission) {
        return villagePermissions.contains(villagePermission);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPeaceful() {
        return peaceful;
    }

    public int getLevel() {
        return level;
    }

    public UUID getOwner() {
        return owner;
    }

    public Set<VillageMember> getVillageMembers() {
        return Collections.unmodifiableSet(villageMembers);
    }

    public Set<VillageClaim> getVillageClaims() {
        return Collections.unmodifiableSet(villageClaims);
    }

    public VillageLocation getVillageLocation() {
        return villageLocation;
    }
}
