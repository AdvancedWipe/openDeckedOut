package com.github.advancedwipe.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AdminCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            // Execute logic for subcommand1
            player.sendMessage("You executed /mycommand subcommand1");
            player.sendMessage("Do some stuff");
        } else {
            sender.sendMessage("This command can only be executed by players.");
        }
        return true;
    }
}
