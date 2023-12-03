package me.tye.spawnfix;

import me.tye.spawnfix.utils.Config;
import me.tye.spawnfix.utils.Teleport;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;

import static me.tye.spawnfix.utils.Util.*;

public class PlayerJoin implements Listener {

private static final ArrayList<UUID> joined = new ArrayList<>();

@EventHandler
public static void PlayerSpawn(PlayerJoinEvent e) {
    Player player = e.getPlayer();

    Config.Occurrence login = Config.login.getOccurrenceConfig();

    //if login is never then always return.
    if (login == Config.Occurrence.NEVER) {
        return;
    }

    //if the login is first, only teleport on the first join.
    if (login == Config.Occurrence.FIRST && joined.contains(player.getUniqueId())) {
        return;
    }

    PersistentDataContainer dataContainer = player.getPersistentDataContainer();

    String lastLoginWorldName = dataContainer.get(new NamespacedKey(plugin, "lastloginworld"), PersistentDataType.STRING);
    Double lastLoginX = dataContainer.get(new NamespacedKey(plugin, "lastloginx"), PersistentDataType.DOUBLE);
    Double lastLoginY = dataContainer.get(new NamespacedKey(plugin, "lastloginy"), PersistentDataType.DOUBLE);
    Double lastLoginZ = dataContainer.get(new NamespacedKey(plugin, "lastloginz"), PersistentDataType.DOUBLE);
    Float lastLoginYaw = dataContainer.get(new NamespacedKey(plugin, "lastloginyaw"), PersistentDataType.FLOAT);
    Float lastLoginPitch = dataContainer.get(new NamespacedKey(plugin, "lastloginyaw"), PersistentDataType.FLOAT);

    //Default to the default spawn location
    Location properLocation = getDefaultSpawn();

    //If the last login location can be parsed then the player is teleported to that instead.
    if (lastLoginWorldName != null && lastLoginX != null && lastLoginY != null && lastLoginZ != null && lastLoginYaw != null && lastLoginPitch != null) {
        properLocation = new Location(Bukkit.getWorld(lastLoginWorldName), lastLoginX, lastLoginY, lastLoginZ, lastLoginYaw, lastLoginPitch);
    }

    BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(plugin, new Teleport(player, properLocation), 2, Config.teleport_retryInterval.getIntegerConfig());
    Teleport.runningTasks.put(player.getUniqueId(), bukkitTask);

    joined.add(player.getUniqueId());
}
}
