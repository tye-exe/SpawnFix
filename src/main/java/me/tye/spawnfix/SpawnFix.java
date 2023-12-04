package me.tye.spawnfix;

import me.tye.spawnfix.commands.SpawnFixCommand;
import me.tye.spawnfix.commands.TabComplete;
import me.tye.spawnfix.utils.Config;
import me.tye.spawnfix.utils.Lang;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;

import static me.tye.spawnfix.utils.Util.*;

public final class SpawnFix extends JavaPlugin {

@Override
public void onEnable() {
    createRequiredFiles();

    Config.init();
    Lang.init();

    Config.load();
    Lang.load();

    getLogger().log(Level.INFO, Lang.startUp_readMe.getResponse());
    getLogger().log(Level.INFO, Lang.startUp_link.getResponse());

    //Listeners
    getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
    getServer().getPluginManager().registerEvents(new PlayerLeave(), this);
    getServer().getPluginManager().registerEvents(new PlayerRespawn(), this);

    //Commands
    Objects.requireNonNull(getCommand("sf")).setExecutor(new SpawnFixCommand());
    Objects.requireNonNull(getCommand("sf")).setTabCompleter(new TabComplete());
}

@Override
public void onDisable() {

}

private void createRequiredFiles() {
    try {
        makeRequiredFile(dataFolder, null, false);
    } catch (IOException e) {
        throw new RuntimeException("\"" + dataFolder.getAbsolutePath() + "\" Couldn't be created. Please manually create this folder.", e);
    }
    try {
        makeRequiredFile(new File(dataFolder+File.separator+"config.yml"), plugin.getResource("config.yml"), true);
    } catch (IOException e) {
        throw new RuntimeException("\"" + new File(dataFolder+File.separator+"config.yml").getAbsolutePath() + "\" Couldn't be created. Please manually create this file.", e);
    }
    try {
        makeRequiredFile(langFolder, null, false);
    } catch (IOException e) {
        throw new RuntimeException("\"" + langFolder.getAbsolutePath() + "\" Couldn't be created. Please manually create this folder.", e);
    }
    try {
        makeRequiredFile(new File(langFolder+File.separator+"eng.yml"), plugin.getResource("lang/eng.yml"), true);
    } catch (IOException e) {
        throw new RuntimeException("\"" + new File(langFolder+File.separator+"eng.yml").getAbsolutePath() + "\" Couldn't be created. Please manually create this file.", e);
    }
}
}
