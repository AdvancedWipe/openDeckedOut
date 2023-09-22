package com.github.advancedwipe.commands;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MainCommand implements CommandExecutor {

  private final Map<String, CommandExecutor> subCommands = new HashMap<>();

  public MainCommand() {
    // Register sub-commands
    subCommands.put("admin", new AdminCommand());
    subCommands.put("help", new HelpCommand());
    // Add more sub-commands as needed
  }

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
      @NotNull String label, @NotNull String[] args) {
    if (!(sender instanceof Player player)) {
      return true;
    }

    if (args.length < 1) {
      player.sendMessage("Usage: /opendeckedout <arg>");
      player.sendMessage("More information: /opendeckedout help");
      return true;
    }

    String subCommandName = args[0].toLowerCase();
    CommandExecutor subCommandExecutor = subCommands.get(subCommandName);

    return subCommandExecutor != null && subCommandExecutor.onCommand(player, command, label, args);
  }
}
