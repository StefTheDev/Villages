package com.stefthedev.villages.listeners;

import com.stefthedev.villages.Villages;
import com.stefthedev.villages.settings.SettingType;
import com.stefthedev.villages.settings.SettingsManager;
import com.stefthedev.villages.villages.Village;
import com.stefthedev.villages.villages.VillageManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class PlayerListener implements Listener {

    private final VillageManager villageManager;
    private final SettingsManager settingsManager;

    public PlayerListener(Villages villages) {
        this.villageManager = villages.getVillageManager();
        this.settingsManager = villages.getSettingsManager();
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInteract(PlayerInteractEvent event) {
        if((boolean) settingsManager.getSetting(SettingType.BLOCK_BREAK).getElement()) return;
        if((boolean) settingsManager.getSetting(SettingType.BLOCK_PLACE).getElement()) return;

        Village village = villageManager.getVillage(event.getPlayer().getLocation().getChunk());
        if(village != null) {
            if(!village.getMembers().contains(event.getPlayer().getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onChestInteract(PlayerInteractEvent event) {
        if((boolean) settingsManager.getSetting(SettingType.CHEST_ACCESS).getElement()) return;

        Village village = villageManager.getVillage(event.getPlayer().getLocation().getChunk());
        if(village != null) {
            if(!village.getMembers().contains(event.getPlayer().getUniqueId()) && Objects.requireNonNull(event.getClickedBlock()).getType() == Material.CHEST) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if((boolean) settingsManager.getSetting(SettingType.DAMAGE_PLAYERS).getElement()) return;

        if(event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Village village = villageManager.getVillage(event.getEntity().getLocation().getChunk());
            if(village != null) {
                event.setCancelled(true);
            }
        }
    }
}
