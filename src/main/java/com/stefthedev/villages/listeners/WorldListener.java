package com.stefthedev.villages.listeners;

import com.google.gson.reflect.TypeToken;
import com.stefthedev.villages.villages.Village;
import com.stefthedev.villages.villages.VillageManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldSaveEvent;

import java.util.Set;

public class WorldListener implements Listener {

    private final VillageManager villageManager;

    public WorldListener(VillageManager villageManager) {
        this.villageManager = villageManager;
    }

    @EventHandler
    public void onSave(WorldSaveEvent event) {
        for(World world : Bukkit.getWorlds()) {
            if(world == event.getWorld()) {
                villageManager.unload();
                villageManager.load(new TypeToken<Set<Village>>(){}.getType());
                break;
            }
        }
    }
}
