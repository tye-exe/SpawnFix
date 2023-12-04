package me.tye.spawnfix;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataType;

import static me.tye.spawnfix.utils.Util.plugin;

public class PlayerLeave implements Listener {

@EventHandler
public static void PlayerLeaveEvent(PlayerQuitEvent e) {
  Player player = e.getPlayer();
  Location logoutLocation = e.getPlayer().getLocation();

  String worldName = logoutLocation.getWorld().getName();
  double x = logoutLocation.getX();
  double y = logoutLocation.getY();
  double z = logoutLocation.getZ();
  float yaw = logoutLocation.getYaw();
  float pitch = logoutLocation.getPitch();

  player.getPersistentDataContainer().set(new NamespacedKey(plugin, "lastloginworld"), PersistentDataType.STRING, worldName);
  player.getPersistentDataContainer().set(new NamespacedKey(plugin, "lastloginx"), PersistentDataType.DOUBLE, x);
  player.getPersistentDataContainer().set(new NamespacedKey(plugin, "lastloginy"), PersistentDataType.DOUBLE, y);
  player.getPersistentDataContainer().set(new NamespacedKey(plugin, "lastloginz"), PersistentDataType.DOUBLE, z);
  player.getPersistentDataContainer().set(new NamespacedKey(plugin, "lastloginyaw"), PersistentDataType.FLOAT, yaw);
  player.getPersistentDataContainer().set(new NamespacedKey(plugin, "lastloginpitch"), PersistentDataType.FLOAT, pitch);

}

}
