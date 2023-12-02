package me.tye.spawnfix;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.Objects;
import java.util.logging.Level;

import static me.tye.spawnfix.Util.get;
import static me.tye.spawnfix.Util.plugin;

public class PlayerRespawn implements Listener {

@EventHandler
public static void playerRespawn(PlayerRespawnEvent e) {
  Player player = e.getPlayer();
  Location spawnLocation = e.getPlayer().getBedSpawnLocation();

  if (!Boolean.parseBoolean(get("onSpawn"))) {
    return;
  }

  int retryInterval = 2;
  try {
    retryInterval = Integer.parseInt(get("teleport.retryInterval"));
  } catch (NumberFormatException ex) {
    plugin.getLogger().log(Level.WARNING, "Unable to parse the retry interval, defaulting to 2.");
  }

  //Sets the respawn location to the default spawn location if the player hasn't set a spawn yet.
  if (spawnLocation == null) {
    try {
      double defaultX = Double.parseDouble(get("default.x"));
      double defaultY = Double.parseDouble(get("default.y"));
      double defaultZ = Double.parseDouble(get("default.z"));

      spawnLocation = new Location(Bukkit.getWorld(get("default.worldName")), defaultX, defaultY, defaultZ);
    } catch (Exception ex) {
      plugin.getLogger().severe("Unable to get default spawn. Aborting respawn correction.");
      return;
    }
  }

  BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(plugin, new Teleport(player, spawnLocation), 2, retryInterval);
  Teleport.runningTasks.put(player.getUniqueId(), bukkitTask);
}
}
