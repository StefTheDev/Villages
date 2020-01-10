package com.stefthedev.villages.resources.listeners;

import com.stefthedev.villages.Villages;
import com.stefthedev.villages.data.village.Village;
import com.stefthedev.villages.managers.VillageManager;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
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
        Village village = villageManager.getVillage(event.getLocation().getChunk());
        if (village == null) return;
        if (village.isPeaceful()) event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        Village entityVillage = null;
        Village otherVillage = null;

        if (event.getEntity() instanceof Player) {
            entityVillage = villageManager.getVillage(((Player) event.getEntity()).getPlayer());
        }

        if (event.getDamager() instanceof Player) {
            otherVillage = villageManager.getVillage(((Player) event.getDamager()).getPlayer());
        }

        if(entityVillage != null && otherVillage != null) {

            if(entityVillage.isPeaceful() && otherVillage.isPeaceful()) {
                event.setCancelled(true);
            }

            if(entityVillage.isPeaceful() && !otherVillage.isPeaceful()) {
                event.setCancelled(true);
            }

            if(!entityVillage.isPeaceful() && otherVillage.isPeaceful()) {
                event.setCancelled(true);
            }
        }
    }
}
