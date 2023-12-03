package me.tye.spawnfix;

import me.tye.spawnfix.utils.Config;
import me.tye.spawnfix.utils.Teleport;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.logging.Level;

import static me.tye.spawnfix.utils.Util.*;

public class PlayerRespawn implements Listener {

@EventHandler
public static void playerRespawn(PlayerRespawnEvent e) {
  Player player = e.getPlayer();
  Location spawnLocation = e.getPlayer().getBedSpawnLocation();

  if (Config.onSpawn.getOccurrenceConfig() == Config.Occurrence.NEVER) {
    return;
  }

  //Sets the respawn location to the default spawn location if the player hasn't set a spawn yet.
  if (spawnLocation == null) {
    spawnLocation = getDefaultSpawn();
  }

  BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(plugin, new Teleport(player, spawnLocation), 2, Config.teleport_retryInterval.getIntegerConfig());
  Teleport.runningTasks.put(player.getUniqueId(), bukkitTask);
}
}
