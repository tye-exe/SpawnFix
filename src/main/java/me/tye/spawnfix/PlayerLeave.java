package me.tye.spawnfix;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataType;

import static me.tye.spawnfix.Util.plugin;

public class PlayerLeave implements Listener {

@EventHandler
public static void PlayerLeaveEvent(PlayerQuitEvent e) {
  Player player = e.getPlayer();
  Location logoutLocation = e.getPlayer().getLocation();

  double x = logoutLocation.getX();
  double y = logoutLocation.getY();
  double z = logoutLocation.getZ();

  player.getPersistentDataContainer().set(new NamespacedKey(plugin, "lastloginx"), PersistentDataType.DOUBLE, x);
  player.getPersistentDataContainer().set(new NamespacedKey(plugin, "lastloginy"), PersistentDataType.DOUBLE, y);
  player.getPersistentDataContainer().set(new NamespacedKey(plugin, "lastloginz"), PersistentDataType.DOUBLE, z);

}

}
