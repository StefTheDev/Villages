package com.stefthedev.villages.utilities.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

public class JSON<T> {

    private final Gson gson;
    private final File file;

    public JSON(String file, Plugin plugin) {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.file =  new File(plugin.getDataFolder().getPath() + File.separator + file + ".json");
    }

    public void write(Set<T> ts) {
        if (!this.file.exists()) this.file.getParentFile().mkdirs();

        try (FileWriter fileWriter = new FileWriter(file)) {

            fileWriter.write(gson.toJson(ts));
            fileWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<T> read(Type type) {
        if (!this.file.exists()) this.file.getParentFile().mkdirs();

        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new HashSet<>();
        }
        return gson.fromJson(new JsonReader(fileReader), type);
    }
}
