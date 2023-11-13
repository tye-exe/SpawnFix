package me.tye.spawnfix;

import org.bukkit.plugin.java.JavaPlugin;

public final class SpawnFix extends JavaPlugin {

@Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerLeave(), this);

        saveDefaultConfig();
    }

    @Override
    public void onDisable() {

    }
}
