package com.stefthedev.villages.commands;

import com.stefthedev.villages.Villages;
import com.stefthedev.villages.commands.subcommands.HelpCommand;
import com.stefthedev.villages.utilities.general.Chat;
import com.stefthedev.villages.utilities.general.Command;
import com.stefthedev.villages.utilities.general.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import javax.swing.plaf.nimbus.AbstractRegionPainter;
import java.util.*;

public class VillageCommand extends Command implements TabCompleter {

    private final Villages villages;
    private Set<Command> commands;

    public VillageCommand(Villages villages) {
        super("village", "");
        this.villages = villages;
        this.commands = new HashSet<>();
    }

    public boolean run(Player player, String[] args) {
        if(args.length > 0) {
            for(Command command : commands) {
                if(args[0].equalsIgnoreCase(command.toString())) {
                    if(player.hasPermission(toString() + "." + command.toString())) {
                        command.run(player, args);
                    } else {
                        player.sendMessage(Chat.format(Message.NO_PERMISSION.toString()));
                    }
                    break;
                }
            }
        } else {
            return new HelpCommand(this).run(player, args);
        }
        return false;
    }

    public void initialise(Command... commands) {
        this.commands.addAll(Arrays.asList(commands));
    }

    public Set<Command> getCommands() {
        return Collections.unmodifiableSet(commands);
    }

    public Villages getVillages() {
        return villages;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        if(strings.length == 1) {
            commands.forEach(subCommand -> list.add(subCommand.toString()));
        }
        return list;
    }
}
