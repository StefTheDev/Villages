package com.stefthedev.villages.settings;

import com.stefthedev.villages.utilities.Config;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class SettingsManager {

    private final Config config;
    private final Set<Setting> settings;

    public SettingsManager(Config config) {
        this.config = config;
        this.settings = new HashSet<>();
    }

    public void load() {
        FileConfiguration configuration = config.getFileConfiguration();
        ConfigurationSection section = configuration.getConfigurationSection("");
        if(section == null) return;
        section.getKeys(false).forEach(s -> settings.add(new Setting<>(s, SettingType.valueOf(s.replace("-", "_").toUpperCase()), configuration.get(s))));
    }

    public List<Setting> getSettings(SettingType... settingTypes) {
        List<Setting> settings = new ArrayList<>();
        for(SettingType settingType : settingTypes) {
            for(Setting setting : this.settings) {
                if(setting.getType() == settingType) {
                    settings.add(setting);
                }
            }
        }
        return settings;
    }

    public Setting getSetting(SettingType settingType) {
        for(Setting setting : settings) {
            if(setting.getType() == settingType) {
                return setting;
            }
        }
        return null;
    }
}
