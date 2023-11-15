package me.tye.spawnfix;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class SpawnFix extends JavaPlugin {

@Override
public void onEnable() {

    getLogger().log(Level.INFO, "See the readme on github for config help:");
    getLogger().log(Level.INFO, "https://github.com/Mapty231/SpawnFix/blob/master/README.md");

    getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
    getServer().getPluginManager().registerEvents(new PlayerLeave(), this);
    getServer().getPluginManager().registerEvents(new PlayerRespawn(), this);

    saveDefaultConfig();
}

@Override
public void onDisable() {

}
}
