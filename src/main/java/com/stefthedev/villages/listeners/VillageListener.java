package com.stefthedev.villages.listeners;

import com.stefthedev.villages.Villages;
import com.stefthedev.villages.utilities.Chat;
import com.stefthedev.villages.utilities.Message;
import com.stefthedev.villages.villages.Village;
import com.stefthedev.villages.villages.VillageManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Objects;

public class VillageListener implements Listener {

    private final VillageManager villageManager;

    public VillageListener(Villages villages) {
        this.villageManager = villages.getVillageManager();
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if(Objects.requireNonNull(event.getTo()).getChunk() != event.getFrom().getChunk()) {
            Village from = villageManager.getVillage(event.getFrom().getChunk());
            Village to = villageManager.getVillage(event.getTo().getChunk());

            if (villageManager.getVillage(event.getFrom().getChunk()) == villageManager.getVillage(event.getTo().getChunk()))
                return;

            if (from == null && to != null) {
                event.getPlayer().sendTitle(
                        Chat.color(Message.TITLE_HEADER.toString().replace("{0}", to.toString())),
                        Chat.color(Message.TITLE_FOOTER.toString().replace("{0}", Objects.requireNonNull(Bukkit.getOfflinePlayer(to.getOwner()).getName()))),
                        10, 30, 10
                );
                return;
            }

            if (to == null && from != null) {
                event.getPlayer().sendTitle(
                        Chat.color(Message.TITLE_WILDERNESS_HEADER.toString()),
                        Chat.color(Message.TITLE_WILDERNESS_FOOTER.toString()),
                        10, 30, 10
                );
            }

            if (to != null && to != from) {
                event.getPlayer().sendTitle(
                        Chat.color(Message.TITLE_HEADER.toString().replace("{0}", to.toString())),
                        Chat.color(Message.TITLE_FOOTER.toString().replace("{0}", Objects.requireNonNull(Bukkit.getOfflinePlayer(to.getOwner()).getName()))),
                        10, 30, 10
                );
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Village village = villageManager.getVillage(event.getBlock().getChunk());
        if(village != null) {
            if(!village.getMembers().contains(event.getPlayer().getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Village village = villageManager.getVillage(event.getBlock().getChunk());
        if(village != null) {
            if(!village.getMembers().contains(event.getPlayer().getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Village village = villageManager.getVillage(event.getPlayer().getLocation().getChunk());
        if(village != null) {
            if(!village.getMembers().contains(event.getPlayer().getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }
}
