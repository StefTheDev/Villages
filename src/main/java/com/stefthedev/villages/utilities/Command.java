package com.stefthedev.villages.utilities;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class Command implements CommandExecutor {

    private final String name;

    public Command(String name) {
        this.name = name;
    }

    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        if(command.getLabel().equalsIgnoreCase(name)) {
            return run((Player) commandSender, strings);
        }
        return false;
    }

    public abstract boolean run(Player player, String[] args);

    @Override
    public String toString() {
        return name;
    }
}

