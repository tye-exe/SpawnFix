package me.tye.spawnfix.commands;

import me.tye.spawnfix.utils.Util;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnFixCommand implements CommandExecutor {
@Override
public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
  if (!commandSender.hasPermission("sf")) return true;

  if (args.length != 1) return true;

  if (args[0].equals("setSpawn")) {
    if (!(commandSender instanceof Player)) return true;
    Player player = (Player) commandSender;

    Location currentLocation = player.getLocation();

    String worldName = currentLocation.getWorld().getName();
    double x = currentLocation.getX();
    double y = currentLocation.getY();
    double z = currentLocation.getZ();
    float yaw = currentLocation.getYaw();
    float pitch = currentLocation.getPitch();

    //TODO: write data to file
  }

  if (args[0].equals("teleportTo")) {
    if (!(commandSender instanceof Player)) return true;
    Player player = (Player) commandSender;

    player.teleport(Util.getDefaultSpawn());
  }

  return true;
}
}
