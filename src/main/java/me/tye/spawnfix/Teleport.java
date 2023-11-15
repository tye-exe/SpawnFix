package me.tye.spawnfix;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

import static me.tye.spawnfix.Util.get;
import static me.tye.spawnfix.Util.plugin;

public class Teleport implements Runnable{

public static final HashMap<UUID,BukkitTask> runningTasks = new HashMap<>();

private final Player player;
private final Location location;

private int timesTeleported = 1;

/**
 A runnable object that teleports the given player to the given location the amount of times specified by "teleport.times" in the config.
 * @param player The given player.
 * @param location The given location.
 */
public Teleport(Player player, Location location) {
  this.player = player;
  this.location = location;
}

@Override
public void run() {
  int retryLimit = 10;

  try {
    retryLimit = Integer.parseInt(get("teleport.times"));
  } catch (NumberFormatException e) {
    plugin.getLogger().log(Level.WARNING, "Unable to parse the max amount of teleport times, defaulting to 10.");
  }

  if (timesTeleported > retryLimit) {
    runningTasks.get(player.getUniqueId()).cancel();
  }

  player.teleport(location);
  timesTeleported++;
}

}
