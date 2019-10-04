package com.stefthedev.villages.resources.listeners;

import com.stefthedev.villages.Villages;
import com.stefthedev.villages.utilities.general.Chat;
import com.stefthedev.villages.utilities.general.Message;
import com.stefthedev.villages.data.village.Village;
import com.stefthedev.villages.managers.VillageManager;
import com.stefthedev.villages.data.village.VillagePermission;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
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
                        Chat.color(Message.TITLE_HEADER.toString().replace("{0}", to.getName())),
                        Chat.color("&7" + to.getDescription()),
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
                        Chat.color(Message.TITLE_HEADER.toString().replace("{0}", to.getName())),
                        Chat.color("&7" + to.getDescription()),
                        10, 30, 10
                );
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        Village currentVillage = villageManager.getVillage(block.getChunk());
        if (currentVillage == null) return;

        Village playerVillager = villageManager.getVillage(player);

        if (playerVillager == currentVillage) {
            if (!playerVillager.getMember(player.getUniqueId()).hasPermission(VillagePermission.BLOCK_BREAK) &&
                    !playerVillager.getOwner().equals(player.getUniqueId())) event.setCancelled(true);
        } else {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        Village currentVillage = villageManager.getVillage(block.getChunk());
        if (currentVillage == null) return;

        Village playerVillager = villageManager.getVillage(player);

        if (playerVillager == currentVillage) {
            if (!playerVillager.getMember(player.getUniqueId()).hasPermission(VillagePermission.BLOCK_PLACE) &&
                    !playerVillager.getOwner().equals(player.getUniqueId())) event.setCancelled(true);
        } else {
            event.setCancelled(true);
        }
    }
}
