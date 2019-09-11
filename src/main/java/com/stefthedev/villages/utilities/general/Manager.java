package com.stefthedev.villages.utilities.general;

import com.stefthedev.villages.utilities.storage.JSON;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Manager<T> {

    private final JSON<T> json;
    private Set<T> set;

    public Manager(String string, Plugin plugin) {
        this.json = new JSON<>(string, plugin);
    }

    public void load(Type type) {
        set = json.read(type);
        if(json.read(type) == null) set = new HashSet<>();
    }

    public void unload() {
        json.write(set);
    }

    public void add(T t) {
        set.add(t);
    }

    public void remove(T t) {
        set.remove(t);
    }

    protected Set<T> toSet() {
        return Collections.unmodifiableSet(set);
    }
}
