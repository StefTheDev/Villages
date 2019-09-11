package com.stefthedev.villages.managers;

import com.stefthedev.villages.data.Village;
import com.stefthedev.villages.data.VillageResource;
import com.stefthedev.villages.utilities.general.Manager;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class ResourceManager extends Manager<VillageResource> {

    private final Map<Integer, VillageResource> villageResourceMap;

    public ResourceManager(Plugin plugin) {
        super("resources", plugin);
        this.villageResourceMap = new HashMap<>();
    }

    //Serialize data via YAML configuration.

    //Checks if villages has the right amount of resources for that level.
    public boolean check(Village village, int level) {
        VillageResource resource = villageResourceMap.get(level);
        if (resource == null) return false;

        for(VillageResource villageResource : village.getVillageResources()) {
            if(match(resource, villageResource)) return true;
        }
        return false;
    }

    private boolean match(VillageResource resource, VillageResource villageResource) {
        return resource.toString().equals(villageResource.toString()) && villageResource.getAmount() >= resource.getAmount();
    }

    public Map<Integer, VillageResource> getVillageResourceMap() {
        return villageResourceMap;
    }
}
