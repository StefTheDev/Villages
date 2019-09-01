package com.stefthedev.villages.listeners;

import com.stefthedev.villages.Villages;
import com.stefthedev.villages.villages.Village;
import com.stefthedev.villages.villages.VillageManager;

import com.stefthedev.villages.villages.VillagePermission;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerListener implements Listener {

    private final VillageManager villageManager;

    public PlayerListener(Villages villages) {
        this.villageManager = villages.getVillageManager();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onFurnaceInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Block block = event.getClickedBlock();
        if(block == null) return;

        Village currentVillage = villageManager.getVillage(block.getChunk());
        if(currentVillage == null) return;

        Village playerVillager = villageManager.getVillage(player);

        Material material = block.getType();
        if(material == Material.FURNACE || material == Material.FURNACE_MINECART || material == Material.BLAST_FURNACE) {
            if (playerVillager == currentVillage) {
                if (!playerVillager.getMember(player.getUniqueId()).hasPermission(VillagePermission.FURNACE_ACCESS)
                        && !playerVillager.getOwner().equals(player.getUniqueId())) {
                    event.setCancelled(true);
                }
            } else {
                event.setCancelled(true);
            }
        }

        if(material == Material.SHULKER_BOX) {
            if (playerVillager == currentVillage) {
                if (!playerVillager.getMember(player.getUniqueId()).hasPermission(VillagePermission.SHULKER_ACCESS)
                        && !playerVillager.getOwner().equals(player.getUniqueId())) {
                    event.setCancelled(true);
                }
            } else {
                event.setCancelled(true);
            }
        }

        if(material == Material.CHEST || material == Material.CHEST_MINECART || material == Material.TRAPPED_CHEST) {
            if (playerVillager == currentVillage) {
                if (!playerVillager.getMember(player.getUniqueId()).hasPermission(VillagePermission.CHEST_ACCESS)
                        && !playerVillager.getOwner().equals(player.getUniqueId())) {
                    event.setCancelled(true);
                }
            } else {
                event.setCancelled(true);
            }
        }

        if(material == Material.HOPPER || material == Material.HOPPER_MINECART) {
            if (playerVillager == currentVillage) {
                if (!playerVillager.getMember(player.getUniqueId()).hasPermission(VillagePermission.BREWING_ACCESS)
                        && !playerVillager.getOwner().equals(player.getUniqueId())) {
                    event.setCancelled(true);
                }
            } else {
                event.setCancelled(true);
            }
        }

        if(material == Material.BREWING_STAND) {
            if (playerVillager == currentVillage) {
                if (!playerVillager.getMember(player.getUniqueId()).hasPermission(VillagePermission.BREWING_ACCESS)
                        && !playerVillager.getOwner().equals(player.getUniqueId())) {
                    event.setCancelled(true);
                }
            } else {
                event.setCancelled(true);
            }
        }

        if(material == Material.DRAGON_EGG) {
            if (playerVillager == currentVillage) {
                if (!playerVillager.getMember(player.getUniqueId()).hasPermission(VillagePermission.DRAGON_EGG_TOUCH)
                        && !playerVillager.getOwner().equals(player.getUniqueId())) {
                    event.setCancelled(true);
                }
            } else {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBucket(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();

        Block block = event.getBlockClicked();

        Village currentVillage = villageManager.getVillage(block.getChunk());
        if(currentVillage == null) return;

        Village playerVillager = villageManager.getVillage(player);

        Material material = event.getBucket();
        if(material == Material.WATER_BUCKET) {
            if (playerVillager == currentVillage) {
                if (!playerVillager.getMember(player.getUniqueId()).hasPermission(VillagePermission.WATER_PLACEMENT)
                        && !playerVillager.getOwner().equals(player.getUniqueId())) {
                    event.setCancelled(true);
                }
            } else {
                event.setCancelled(true);
            }
        }

        if(material == Material.LAVA_BUCKET) {
            if (playerVillager == currentVillage) {
                if (!playerVillager.getMember(player.getUniqueId()).hasPermission(VillagePermission.LAVA_PLACEMENT)
                        && !playerVillager.getOwner().equals(player.getUniqueId())) {
                    event.setCancelled(true);
                }
            } else {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onArmorStandInteract(PlayerArmorStandManipulateEvent event) {
        Player player = event.getPlayer();

        ArmorStand armorStand = event.getRightClicked();
        Village currentVillage = villageManager.getVillage(armorStand.getLocation().getChunk());
        if (currentVillage == null) return;

        Village playerVillager = villageManager.getVillage(player);

        if (playerVillager == currentVillage) {
            if (!playerVillager.getMember(player.getUniqueId()).hasPermission(VillagePermission.ARMOR_STAND_ACCESS)
                    && !playerVillager.getOwner().equals(player.getUniqueId())) {
                event.setCancelled(true);
            }
        } else {
            event.setCancelled(true);
        }
    }
}
