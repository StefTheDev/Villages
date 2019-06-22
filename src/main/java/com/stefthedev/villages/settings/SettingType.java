package com.stefthedev.villages.settings;

public enum SettingType {
    BLOCK_BREAK("block-break", false),
    BLOCK_PLACE("block-place", false),
    CHEST_ACCESS("chest-access", false),
    CHUNK_LIMIT("chunk-limit", 10),
    DAMAGE_PLAYERS("damage-players", false),
    EXPLOSIONS("explosions", false),
    TITLE_MESSAGES("title-messages", true),
    WORLDGUARD_CHECK("worldguard-check", true);

    private String path;
    private Object object;

    SettingType(String path, Object object) {
        this.path = path;
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    @Override
    public String toString() {
        return path;
    }
}
