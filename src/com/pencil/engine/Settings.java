package com.pencil.engine;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Set;

public class Settings {

    private static final Settings configuration = new Settings("config");
    private static final Settings analytics = new Settings("analytics");
    private static final Settings players = new Settings("players");

    private File file;
    private FileConfiguration config;

    public static Settings getConfig() {
        return configuration;
    }

    public static Settings getAnalytics() {
        return analytics;
    }

    public static Settings getPlayers() {
        return players;
    }

    private Settings(String fileName) {
        if (!Pencil.getPlugin().getDataFolder().exists()) {
            Pencil.getPlugin().getDataFolder().mkdir();
        }

        this.file = new File(Pencil.getPlugin().getDataFolder(), fileName + ".yml");
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public FileConfiguration getAsConfig() {
        return this.config;
    }

    public <T> T get(String path) {
        return (T)this.config.get(path);
    }

    public Set<String> getKeys() {
        return this.config.getKeys(false);
    }

    public void set(String path, Object value) {
        this.config.set(path, value);
        save();
    }

    public boolean contains(String path) {
        return this.config.contains(path);
    }

    public ConfigurationSection createSection(String path) {
        ConfigurationSection section = this.config.createSection(path);
        save();
        return section;
    }

    public void save() {
        try {
            this.config.save(this.file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
