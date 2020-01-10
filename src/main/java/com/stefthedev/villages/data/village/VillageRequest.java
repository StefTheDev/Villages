package com.stefthedev.villages.data.village;

import com.stefthedev.villages.managers.VillageManager;
import com.stefthedev.villages.utilities.general.Chat;
import com.stefthedev.villages.utilities.general.Message;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class VillageRequest {

    public enum VillageRequestAction {
        DISBAND,
        INVITE,
        KICK
    }

    private final Village village;
    private final UUID uuid, target;
    private final VillageRequestAction villageRequestAction;

    public VillageRequest(Village village, UUID uuid, UUID target, VillageRequestAction villageRequestAction) {
        this.village = village;
        this.uuid = uuid;
        this.target = target;
        this.villageRequestAction = villageRequestAction;
    }

    public void send() {
        Player player = Bukkit.getPlayer(uuid);
        if(player == null)return;

        TextComponent accept = new TextComponent(Chat.color(Message.REQUEST_ACCEPT.toString()) + " ");
        accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Chat.color(Message.TOOLTIP.toString())).create()));
        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/village accept"));

        TextComponent deny = new TextComponent(Chat.color(Message.REQUEST_DENY.toString()));
        deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Chat.color(Message.TOOLTIP.toString())).create()));
        deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/village deny"));

        switch (villageRequestAction) {
            case DISBAND: {
                player.sendMessage(Chat.format(Message.REQUEST_DISBAND.toString()));
                player.spigot().sendMessage(accept, deny);
            } break;
            case INVITE: {
                Player target = Bukkit.getPlayer(this.target);
                if(target == null) return;
                player.sendMessage(Chat.format(Message.REQUEST_INVITE.toString().replace("{0}", target.getName())));
                target.sendMessage(Chat.format(Message.REQUEST_INVITE_TARGET.toString().replace("{0}", village.getName())));
                target.spigot().sendMessage(accept, deny);
            } break;
            case KICK: {
                Player target = Bukkit.getPlayer(this.uuid);
                if(target != null) {
                    player.sendMessage(Chat.format(Message.REQUEST_KICK.toString().replace("{0}", target.getName())));
                    player.spigot().sendMessage(accept, deny);
                }
            } break;
        }
    }

    public void complete(VillageManager villageManager) {
        switch (villageRequestAction) {
            case DISBAND: {
                Bukkit.broadcastMessage(Chat.format(Message.DISBAND.toString().replace("{0}", village.getName())));
                villageManager.remove(village);
            } break;
            case INVITE: {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(target);
                Player player = offlinePlayer.getPlayer();
                if(player != null) {
                    player.sendMessage(Chat.format(Message.REQUEST_JOIN_TARGET.toString().replace("{0}", village.getName())));
                    VillageMember villageMember = new VillageMember(player.getUniqueId());
                    villageMember.add(VillagePermission.BLOCK_BREAK);
                    villageMember.add(VillagePermission.BLOCK_PLACE);
                    villageMember.add(VillagePermission.WATER_PLACEMENT);
                    villageMember.add(VillagePermission.FURNACE_ACCESS);
                    villageMember.add(VillagePermission.HOME);
                    village.add(villageMember);
                }
            } break;
            case KICK: {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(target);
                Player player = offlinePlayer.getPlayer();
                if(player != null) {
                    village.remove(village.getMember(player.getUniqueId()));
                    player.sendMessage(Chat.format(Message.REQUEST_KICK_TARGET.toString().replace("{0}", village.getName())));
                }
            } break;
        }
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public UUID getTarget() {
        return target;
    }
}
