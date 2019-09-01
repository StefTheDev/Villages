package com.stefthedev.villages;

import com.google.gson.reflect.TypeToken;
import com.stefthedev.villages.commands.VillageCommand;
import com.stefthedev.villages.commands.subcommands.*;
import com.stefthedev.villages.hooks.PlaceholderAPIHook;
import com.stefthedev.villages.listeners.EntityListener;
import com.stefthedev.villages.listeners.PlayerListener;
import com.stefthedev.villages.listeners.VillageListener;
import com.stefthedev.villages.listeners.WorldListener;
import com.stefthedev.villages.utilities.general.Message;
import com.stefthedev.villages.utilities.storage.YAML;
import com.stefthedev.villages.villages.Village;
import com.stefthedev.villages.villages.VillageManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

public class Villages extends JavaPlugin {

    private VillageManager villageManager;

    public void onEnable() {

        YAML messageYaml = new YAML(this, "messages");
        registerMessages(messageYaml);

        villageManager = new VillageManager(this);
        villageManager.load(new TypeToken<Set<Village>>(){}.getType());

        VillageCommand villageCommand = new VillageCommand(this);
        villageCommand.initialise(
                new AcceptCommand(villageManager),
                new ClaimCommand(villageManager),
                new CreateCommand(villageManager),
                new DenyCommand(villageManager),
                new DisbandCommand(villageManager),
                new HelpCommand(villageCommand),
                new HomeCommand(this),
                new InviteCommand(villageManager),
                new KickCommand(villageManager),
                new LeaveCommand(villageManager),
                new PermissionsCommand(this),
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
        for(Message message: Message.values()) {
            if (yaml.getFileConfiguration().getString(message.getPath()) == null) {
                if(message.getList() != null) {
                    yaml.getFileConfiguration().set(message.getPath(), message.getList());
                } else {
                    yaml.getFileConfiguration().set(message.getPath(), message.getDef());
                }
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
        }
    }

    public void onDisable() {
        villageManager.unload();
    }

    public VillageManager getVillageManager() {
        return villageManager;
    }
}
