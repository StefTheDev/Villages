package com.stefthedev.villages.utilities.general;

import com.stefthedev.villages.utilities.storage.JSON;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Set;

public class Manager<T> {

    private final JSON<T> json;
    private Set<T> set;

    public Manager(String string, Plugin plugin) {
        this.json = new JSON<>(string, plugin);
    }

    public void load(Type type) {
        set = json.read(type);
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

    public Set<T> toSet() {
        return Collections.unmodifiableSet(set);
    }
}
