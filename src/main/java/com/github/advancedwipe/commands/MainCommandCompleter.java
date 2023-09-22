package com.github.advancedwipe.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MainCommandCompleter implements TabCompleter {

  @Override
  public @Nullable List<String> onTabComplete(@NotNull CommandSender sender,
      @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if (sender instanceof Player) {
      if (args.length == 1) {
        // Provide tab-completion options for the first argument
        List<String> completions = new ArrayList<>();
        completions.add("admin");
        completions.add("help");

        return completions;
      }
    }
    return null;
  }
}
