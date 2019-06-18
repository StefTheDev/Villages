package com.stefthedev.villages.listeners;

import com.stefthedev.villages.Villages;
import com.stefthedev.villages.settings.SettingType;
import com.stefthedev.villages.settings.SettingsManager;
import com.stefthedev.villages.villages.Village;
import com.stefthedev.villages.villages.VillageManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityListener implements Listener {

    private final VillageManager villageManager;
    private final SettingsManager settingsManager;

    public EntityListener(Villages villages) {
        this.villageManager = villages.getVillageManager();
        this.settingsManager = villages.getSettingsManager();
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent event) {
        if ((boolean) settingsManager.getSetting(SettingType.EXPLOSIONS).getElement()) return;

        Village village = villageManager.getVillage(event.getEntity().getLocation().getChunk());
        if (village != null) {
            event.setCancelled(true);
        }
    }
}
