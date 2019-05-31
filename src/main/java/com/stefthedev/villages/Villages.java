package com.stefthedev.villages;

import com.stefthedev.villages.commands.VillageCommand;
import com.stefthedev.villages.commands.subcommands.*;
import com.stefthedev.villages.listeners.VillageListener;
import com.stefthedev.villages.utilities.Config;
import com.stefthedev.villages.utilities.Message;
import com.stefthedev.villages.villages.VillageManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Objects;

public class Villages extends JavaPlugin {

    private VillageManager villageManager;

    public void onEnable() {
        Config messageConfig = new Config(this, "messages");
        registerMessages(messageConfig);

        Config villageConfig = new Config(this, "villages");
        villageConfig.setup();

        villageManager = new VillageManager(villageConfig);
        villageManager.load();

        VillageCommand villageCommand = new VillageCommand(this);
        villageCommand.initialise(
                new AcceptCommand(this),
                new ClaimCommand(this),
                new CreateCommand(this),
                new DenyCommand(this),
                new DisbandCommand(this),
                new InfoCommand(this),
                new InviteCommand(this),
                new KickCommand(this),
                new UnClaimCommand(this)
        );

        Objects.requireNonNull(getCommand(villageCommand.toString())).setExecutor(villageCommand);
        registerListeners(new VillageListener(this));
    }

    private void registerListeners(Listener... listeners) {
        Arrays.asList(listeners).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    private void registerMessages(Config config) {
        config.setup();
        Message.setConfiguration(config.getFileConfiguration());
        for(Message message: Message.values()) {
            if (config.getFileConfiguration().getString(message.getPath()) == null) {
                if(message.getList() != null) {
                    config.getFileConfiguration().set(message.getPath(), message.getList());
                } else {
                    config.getFileConfiguration().set(message.getPath(), message.getDef());
                }
            }
        }
        config.save();
    }

    public void onDisable() {
        villageManager.unload();
    }

    public VillageManager getVillageManager() {
        return villageManager;
    }
}
