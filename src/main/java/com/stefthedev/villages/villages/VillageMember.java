package com.stefthedev.villages.villages;

import java.util.*;

public class VillageMember {

    private final UUID uuid;
    private final List<VillagePermission> villagePermissions;

    public VillageMember(UUID uuid) {
        this.uuid = uuid;
        this.villagePermissions = new ArrayList<>();
    }

    public void add(VillagePermission villagePermission) {
        this.villagePermissions.add(villagePermission);
    }

    public void remove(VillagePermission villagePermission) {
        this.villagePermissions.remove(villagePermission);
    }

    public boolean hasPermission(VillagePermission villagePermission) {
        return villagePermissions.contains(villagePermission);
    }

    public List<VillagePermission> getPermissions() {
        return Collections.unmodifiableList(villagePermissions);
    }

    public UUID getUniqueId() {
        return uuid;
    }
}
