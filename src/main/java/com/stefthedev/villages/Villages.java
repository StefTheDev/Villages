package com.stefthedev.villages;

import com.google.gson.reflect.TypeToken;
import com.stefthedev.villages.managers.SettingsManager;
import com.stefthedev.villages.resources.commands.VillageCommand;
import com.stefthedev.villages.resources.commands.subcommands.*;
import com.stefthedev.villages.hooks.PlaceholderAPIHook;
import com.stefthedev.villages.resources.listeners.EntityListener;
import com.stefthedev.villages.resources.listeners.PlayerListener;
import com.stefthedev.villages.resources.listeners.VillageListener;
import com.stefthedev.villages.resources.listeners.WorldListener;
import com.stefthedev.villages.utilities.general.Message;
import com.stefthedev.villages.utilities.storage.YAML;
import com.stefthedev.villages.data.village.Village;
import com.stefthedev.villages.managers.VillageManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Villages extends JavaPlugin {

    private VillageManager villageManager;
    private SettingsManager settingsManager;

    private boolean worldGuard = false;

    public void onEnable() {
        getLogger().info("Loading Village Data.");
        villageManager = new VillageManager(this);
        villageManager.load(new TypeToken<Set<Village>>(){}.getType());

        getLogger().info("Loading Settings.");
        settingsManager = new SettingsManager(this, new YAML(this, "settings"));
        settingsManager.load();

        registerMessages(new YAML(this, "messages"));
        VillageCommand villageCommand = new VillageCommand(this);
        villageCommand.initialise(
                new AcceptCommand(villageManager),
                new ClaimCommand(this),
                new CreateCommand(this),
                new DenyCommand(villageManager),
                new DisbandCommand(villageManager),
                new HelpCommand(villageCommand),
                new HomeCommand(this),
                new InviteCommand(villageManager),
                new KickCommand(villageManager),
                new LeaveCommand(villageManager),
                new PanelCommand(this),
                new RenameCommand(villageManager),
                new SetDescriptionCommand(villageManager),
                new SetHomeCommand(villageManager),
                new SetOwnerCommand( villageManager),
                new UnClaimCommand(villageManager)
        );

        getLogger().info("Registered " + villageCommand.getCommands().size() + " sub-command(s).");

        Objects.requireNonNull(getCommand(villageCommand.toString())).setExecutor(villageCommand);

        getLogger().info("Registered " + registerListeners(
                new EntityListener(this),
                new PlayerListener(this),
                new VillageListener(this),
                new WorldListener(villageManager)
        ) + " listener(s).");

        getLogger().info("Registered " + registerHooks() + " hook(s).");
    }

    private int registerListeners(Listener... listeners) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Arrays.asList(listeners).forEach(listener -> {
            atomicInteger.getAndAdd(1);
            getServer().getPluginManager().registerEvents(listener, this);
        });
        return atomicInteger.get();
    }

    private void registerMessages(YAML yaml) {
        yaml.setup();
        Message.setConfiguration(yaml.getFileConfiguration());

        int index = 0;
        for (Message message : Message.values()) {
            if (message.getList() != null) {
                yaml.getFileConfiguration().set(message.getPath(), message.getList());
            } else {
                index += 1;
                yaml.getFileConfiguration().set(message.getPath(), message.getDef());
            }
        }
        yaml.save();
        getLogger().info("Registered " + index + " message(s).");
    }


    private int registerHooks() {
        int index = 0;
        if(getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getLogger().info("Successfully hooked into PlaceholderAPI.");
            new PlaceholderAPIHook(this).register();
            index += 1;
        }

        if(getServer().getPluginManager().getPlugin("WorldGuard") != null) {
            getLogger().info("Successfully hooked into WorldGuard.");
            worldGuard = true;
            index += 1;
        }
        return index;
    }

    public void onDisable() {
        villageManager.unload();
    }

    public VillageManager getVillageManager() {
        return villageManager;
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public boolean isWorldGuard() {
        return worldGuard;
    }

}
