package me.tye.spawnfix.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.UUID;

import static me.tye.spawnfix.utils.Util.plugin;

public class Teleport implements Runnable{

public static final HashMap<UUID,BukkitTask> runningTasks = new HashMap<>();

private final Player player;
private final Location location;

private int timesTeleported = 1;
private final int retryLimit = Config.teleport_times.getIntegerConfig();

/**
 A runnable object that teleports the given player to the given location the amount of times specified by "teleport.times" in the config.
 * @param player The given player.
 * @param location The given location.
 */
public Teleport(@NonNull Player player, @NonNull Location location) {
  this.player = player;
  this.location = location;
}

@Override
public void run() {
  if (timesTeleported > retryLimit) {
    runningTasks.get(player.getUniqueId()).cancel();
    return;
  }

  if (location == null ) {
    plugin.getLogger().warning("Unable to get location to correct the spawn to.");
    return;
  }

  if (player == null) {
    plugin.getLogger().warning("Unable to get player to the spawn of.");
    return;
  }

  player.teleport(location);
  timesTeleported++;
}

}
