package com.stefthedev.villages.listeners;

import com.stefthedev.villages.Villages;
import com.stefthedev.villages.villages.Village;
import com.stefthedev.villages.managers.VillageManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityListener implements Listener {

    private final VillageManager villageManager;

    public EntityListener(Villages villages) {
        this.villageManager = villages.getVillageManager();
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent event) {
        Village village = villageManager.getVillage(event.getEntity().getLocation().getChunk());
        if (village != null) event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player) {
            Village village = villageManager.getVillage(event.getEntity().getLocation().getChunk());
            if (village != null) event.setCancelled(true);
        }
    }
}
