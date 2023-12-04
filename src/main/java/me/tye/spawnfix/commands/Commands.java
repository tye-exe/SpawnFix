package me.tye.spawnfix.commands;

import me.tye.spawnfix.utils.Config;
import me.tye.spawnfix.utils.Key;
import me.tye.spawnfix.utils.Lang;
import me.tye.spawnfix.utils.Util;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.logging.Level;

import static me.tye.spawnfix.utils.Util.*;

public class Commands implements CommandExecutor {
@Override
public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
  if (!commandSender.hasPermission("sf")) return true;

  if (args.length != 1) return true;

  switch (args[0]) {

  //Sets the new spawn to the players position.
  case "setSpawn" -> {
    if (!(commandSender instanceof Player)) return true;

    Player player = (Player) commandSender;
    Location currentLocation = player.getLocation();

    String worldName = currentLocation.getWorld().getName();
    double x = currentLocation.getX();
    double y = currentLocation.getY();
    double z = currentLocation.getZ();
    float yaw = currentLocation.getYaw();
    float pitch = currentLocation.getPitch();

    try {
      writeYamlData("default.worldName", worldName, configFile);
      writeYamlData("default.x", String.valueOf(x), configFile);
      writeYamlData("default.y", String.valueOf(y), configFile);
      writeYamlData("default.z", String.valueOf(z), configFile);
      writeYamlData("default.yaw", String.valueOf(yaw), configFile);
      writeYamlData("default.pitch", String.valueOf(pitch), configFile);
    } catch (IOException e) {
      player.sendMessage(Lang.commands_unableToSet.getResponse(Key.filePath.replaceWith(configFile.getAbsolutePath())));
      log.log(Level.WARNING, "", e);
      return true;
    }

    //reloads the config values
    Config.load();
    player.sendMessage(Lang.commands_setSpawn.getResponse());
  }

  //Teleports the player to the set spawn.
  case "tp" -> {
    if (!(commandSender instanceof Player)) return true;
    Player player = (Player) commandSender;

    player.teleport(Util.getDefaultSpawn());

    player.sendMessage(Lang.commands_teleported.getResponse());
  }

  //Reloads the config values for SpawnFix.
  case "reload" -> {
    Config.load();
    Lang.load();

    commandSender.sendMessage(Lang.commands_reload.getResponse());
  }

  default -> {
    commandSender.sendMessage(Lang.commands_help_help.getResponse());
    commandSender.sendMessage(Lang.commands_help_setSpawn.getResponse());
    commandSender.sendMessage(Lang.commands_help_tp.getResponse());
    commandSender.sendMessage(Lang.commands_help_reload.getResponse());
  }

  }

  return true;
}
}
