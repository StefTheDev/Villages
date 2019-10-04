package com.stefthedev.villages;

import com.google.gson.reflect.TypeToken;
import com.stefthedev.villages.data.settings.Setting;
import com.stefthedev.villages.data.settings.SettingType;
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
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class Villages extends JavaPlugin {

    private VillageManager villageManager;
    private SettingsManager settingsManager;

    private boolean worldGuard = false;

    public void onEnable() {
        registerMessages(new YAML(this, "messages"));

        villageManager = new VillageManager(this);
        villageManager.load(new TypeToken<Set<Village>>(){}.getType());

        settingsManager = new SettingsManager(this, new YAML(this, "settings"));
        settingsManager.load();

        for(World world : getServer().getWorlds()) {
            getLogger().info(world.getName());
        }

        VillageCommand villageCommand = new VillageCommand(this);
        villageCommand.initialise(
                new AcceptCommand(villageManager),
                new ClaimCommand(this),
                new CreateCommand(villageManager),
                new DenyCommand(villageManager),
                new DisbandCommand(villageManager),
                new HelpCommand(villageCommand),
                new HomeCommand(this),
                new InviteCommand(villageManager),
                new KickCommand(villageManager),
                new LeaveCommand(villageManager),
                new PanelCommand(this),
                new SetDescriptionCommand(villageManager),
                new SetHomeCommand(villageManager),
                new SetOwnerCommand( villageManager),
                new UnClaimCommand(villageManager)
        );

        Objects.requireNonNull(getCommand(villageCommand.toString())).setExecutor(villageCommand);
        registerListeners(
                new EntityListener(this),
                new PlayerListener(this),
                new VillageListener(this),
                new WorldListener(villageManager)
        );

        registerHooks();
    }

    private void registerListeners(Listener... listeners) {
        Arrays.asList(listeners).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    private void registerMessages(YAML yaml) {
        yaml.setup();
        Message.setConfiguration(yaml.getFileConfiguration());
        for (Message message : Message.values()) {
            if (message.getList() != null) {
                yaml.getFileConfiguration().set(message.getPath(), message.getList());
            } else {
                yaml.getFileConfiguration().set(message.getPath(), message.getDef());
            }
        }
        yaml.save();
    }


    private void registerHooks() {
        if(getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getLogger().info("Successfully hooked into PlaceholderAPI.");
            new PlaceholderAPIHook(this).register();
        }

        if(getServer().getPluginManager().getPlugin("WorldGuard") != null) {
            getLogger().info("Successfully hooked into WorldGuard.");
            worldGuard = true;
        }
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
