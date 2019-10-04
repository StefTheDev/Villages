package com.stefthedev.villages.data.settings;

public class Setting<E> {

    private final String string;
    private final SettingType settingType;

    private final E element;

    public Setting(String string, SettingType settingType, E element) {
        this.string = string;
        this.settingType = settingType;
        this.element = element;
    }

    public SettingType getType() {
        return settingType;
    }

    public E getElement() {
        return element;
    }

    @Override
    public String toString() {
        return string;
    }
}