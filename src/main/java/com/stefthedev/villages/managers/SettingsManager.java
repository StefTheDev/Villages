package com.stefthedev.villages.managers;

import com.stefthedev.villages.data.settings.Setting;
import com.stefthedev.villages.data.settings.SettingType;
import com.stefthedev.villages.utilities.storage.YAML;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

public class SettingsManager {

    private final Plugin plugin;
    private final YAML yaml;
    private final Set<Setting> settings;

    public SettingsManager(Plugin plugin, YAML yaml) {
        this.plugin = plugin;
        this.yaml = yaml;
        this.settings = new HashSet<>();
    }

    public void load() {
        yaml.setup();
        FileConfiguration configuration = yaml.getFileConfiguration();
        ConfigurationSection section = configuration.getConfigurationSection("");
        if(section == null) return;
        section.getKeys(false).forEach(s -> settings.add(new Setting<>(s, SettingType.valueOf(s.replace("-", "_").toUpperCase()), configuration.get(s))));
    }

    public Setting getSetting(SettingType settingType) {
        for (Setting setting : settings) {
            if (setting.getType() == settingType) {
                return setting;
            }
        }
        return null;
    }

    public boolean checkWorld(String name) {
        for(World world : plugin.getServer().getWorlds()) {
            if(world.getName().equals(name)) return true;
        }
        return false;
    }
}

