package me.tye.spawnfix;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

public class PlayerJoin implements Listener {

public static final JavaPlugin plugin = JavaPlugin.getPlugin(SpawnFix.class);
private static final ArrayList<UUID> joined = new ArrayList<>();
private static final HashMap<UUID, BukkitTask> runningTasks = new HashMap<>();

@EventHandler
public static void PlayerSpawn(PlayerJoinEvent e) {
    Player player = e.getPlayer();

    if (joined.contains(player.getUniqueId())) {
        return;
    }

    PersistentDataContainer dataContainer = player.getPersistentDataContainer();

    Double lastLoinX = dataContainer.get(new NamespacedKey(plugin, "lastloginx"), PersistentDataType.DOUBLE);
    Double lastLoinY = dataContainer.get(new NamespacedKey(plugin, "lastloginy"), PersistentDataType.DOUBLE);
    Double lastLoinZ = dataContainer.get(new NamespacedKey(plugin, "lastloginz"), PersistentDataType.DOUBLE);

    Location properLocation;

    //if there is an error reading the data or the player hasn't joined before, use the default location.
    if (lastLoinX == null || lastLoinY == null || lastLoinZ == null) {
        try {
            double defaultX = Double.parseDouble(String.valueOf(plugin.getConfig().get("default.x")));
            double defaultY = Double.parseDouble(String.valueOf(plugin.getConfig().get("default.y")));
            double defaultZ = Double.parseDouble(String.valueOf(plugin.getConfig().get("default.z")));

            properLocation = new Location(player.getWorld(), defaultX, defaultY, defaultZ);
        } catch (Exception ex) {
            plugin.getLogger().log(Level.WARNING, "Could not parse entered value for default spawn.");
            return;
        }
    }
    //if the player has joined before, get the last join location.
    else {
        properLocation = new Location(player.getWorld(), lastLoinX, lastLoinY, lastLoinZ);
    }

    int retryInterval = 2;
    try {
        retryInterval = Integer.parseInt(String.valueOf(plugin.getConfig().get("teleport.retryInterval")));
    } catch (NumberFormatException ex) {
        plugin.getLogger().log(Level.WARNING, "Unable to parse the retry interval, defaulting to 2.");
    }

    BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
        private Player player;
        private Location location;

        private int timesTeleported = 1;

        public Runnable init(Player player, Location location) {
            this.player = player;
            this.location = location;
            return this;
        }

        @Override
        public void run() {
            int retryLimit = 10;

            try {
                retryLimit = Integer.parseInt(String.valueOf(plugin.getConfig().get("teleport.times")));
            } catch (NumberFormatException e) {
                plugin.getLogger().log(Level.WARNING, "Unable to parse the max amount of teleport times, defaulting to 10.");
            }

            if (timesTeleported > retryLimit) {
                runningTasks.get(player.getUniqueId()).cancel();
            }

            player.teleport(location);
            timesTeleported++;
        }
    }.init(player, properLocation), 2, retryInterval);

    runningTasks.put(player.getUniqueId(), bukkitTask);

    joined.add(player.getUniqueId());
}
}
