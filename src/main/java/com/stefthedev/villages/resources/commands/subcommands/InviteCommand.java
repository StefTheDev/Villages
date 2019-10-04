package com.stefthedev.villages.resources.commands.subcommands;

import com.stefthedev.villages.data.village.Village;
import com.stefthedev.villages.data.village.VillageMember;
import com.stefthedev.villages.data.village.VillagePermission;
import com.stefthedev.villages.data.village.VillageRequest;
import com.stefthedev.villages.managers.VillageManager;
import com.stefthedev.villages.utilities.general.Chat;
import com.stefthedev.villages.utilities.general.Command;
import com.stefthedev.villages.utilities.general.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class InviteCommand extends Command {

    private final VillageManager villageManager;

    public InviteCommand(VillageManager villageManager) {
        super("invite", "invite [player]");
        this.villageManager = villageManager;
    }

    @Override
    public boolean run(Player player, String[] args) {
        if (args.length == 2) {
            Village village = villageManager.getVillage(player);
            if (village != null) {
                VillageMember villageMember = village.getMember(player.getUniqueId());
                if (villageMember.hasPermission(VillagePermission.INVITE_MEMBER) || village.getOwner().equals(player.getUniqueId())) {
                    VillageRequest villageRequest = villageManager.getRequest(player);
                    if (villageRequest == null) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target != player) {
                            if (target != null) {
                                Village targetVillage = villageManager.getVillage(target);
                                if (targetVillage == null) {
                                    villageRequest = new VillageRequest(village, player.getUniqueId(), target.getUniqueId(), VillageRequest.VillageRequestAction.INVITE);
                                    villageRequest.send();
                                    villageManager.add(villageRequest);
                                } else {
                                    player.sendMessage(Chat.format(Message.REQUEST_INVITE_TARGET_NOT_NULL.toString().replace("{0}", target.getDisplayName())));
                                }
                            } else {
                                player.sendMessage(Chat.format(Message.PLAYER_OFFLINE.toString().replace("{0}", args[1])));
                            }
                        } else {
                            player.sendMessage(Chat.format(Message.REQUEST_INVITE_SELF.toString()));
                        }
                    } else {
                        player.sendMessage(Chat.format(Message.REQUEST_PENDING.toString()));
                    }
                } else {
                    player.sendMessage(Chat.format(Message.NO_PERMISSION.toString().replace("{0}", VillagePermission.INVITE_MEMBER.name())));
                }
            } else {
                player.sendMessage(Chat.format(Message.VILLAGE_NULL.toString()));
            }
        } else {
            player.sendMessage(Chat.format(Message.USAGE.toString().replace("{0}", "/village" + getUsage())));
        }
        return true;
    }
}
