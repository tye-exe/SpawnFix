package me.tye.spawnfix;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class Util {
public static final JavaPlugin plugin = JavaPlugin.getPlugin(SpawnFix.class);

/**
 * @param configKey The key path.
 * @return The value from the given key path as a string in lowercase.
 */
public static String get(@NotNull String configKey) {
  return String.valueOf(plugin.getConfig().get(configKey)).strip().toLowerCase();
}
}
