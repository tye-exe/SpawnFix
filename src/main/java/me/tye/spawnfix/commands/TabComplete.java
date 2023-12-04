package me.tye.spawnfix.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TabComplete implements TabCompleter {
@Nullable
@Override
public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
  ArrayList<String> completions = new ArrayList<>();

  if (!commandSender.hasPermission("sf")) {
    return completions;
  }

  String token = "";

  if (args.length == 1) {
    token = args[0];
  }

  StringUtil.copyPartialMatches(token, List.of("set, teleportTo"), completions);

  return completions;
}
}
