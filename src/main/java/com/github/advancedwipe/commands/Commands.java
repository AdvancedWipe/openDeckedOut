package com.github.advancedwipe.commands;

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.Command;
import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.paper.PaperCommandManager;
import com.github.advancedwipe.OpenDeckedOut;
import com.github.advancedwipe.game.DeckedOut;
import com.github.advancedwipe.game.DeckedOutManager;
import com.github.advancedwipe.player.PlayerManager;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.Level;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class Commands {

  private final OpenDeckedOut plugin;
  private DeckedOut workspace;
  private final PaperCommandManager<CommandSender> cmdManager;
  private final DeckedOutManager deckedOutManager;

  public Commands(final OpenDeckedOut plugin) {
    this.plugin = plugin;
    this.deckedOutManager = plugin.getDeckedOutManager();
    this.cmdManager = createCommandManager(plugin);
  }

  public void register() {
    final Command.Builder<CommandSender> deckedout = this.cmdManager.commandBuilder("deckedout");

    this.cmdManager.command(deckedout.literal("version",
            ArgumentDescription.of("Display version of plugin currently in use"))
        .permission("opendeckedout.command.version")
        .handler(this::version));

    this.cmdManager.command(deckedout.literal("add",
            ArgumentDescription.of("Adds a new arena to your workspace"))
        .argument(StringArgument.single("name"))
        .permission("opendeckedout.command.add")
        .handler(this::add));

    this.cmdManager.command(deckedout.literal("workspace",
            ArgumentDescription.of("Show arena in workspace"))
        .permission("opendeckedout.command.workspace")
        .handler(this::workspace));

    this.cmdManager.command(deckedout.literal("pos1",
            ArgumentDescription.of("Define position 1 of arena"))
        .permission("opendeckedout.command.pos1")
        .handler(this::pos1));

    this.cmdManager.command(deckedout.literal("pos2",
            ArgumentDescription.of("Define position 2 of arena"))
        .permission("opendeckedout.command.pos2")
        .handler(this::pos2));

    this.cmdManager.command(deckedout.literal("spawn",
            ArgumentDescription.of("Define spawn of player in arena"))
        .permission("opendeckedout.command.spawn")
        .handler(this::spawn));

    this.cmdManager.command(deckedout.literal("save",
            ArgumentDescription.of("Save arena"))
        .permission("opendeckedout.command.save")
        .handler(this::save));

    this.cmdManager.command(deckedout.literal("join",
            ArgumentDescription.of("Join an arena"))
        .argument(StringArgument.single("name"))
        .permission("opendeckedout.command.join")
        .handler(this::join));
  }

  private void join(CommandContext<CommandSender> context) {
    final Player player = (Player) context.getSender();

    if (plugin.getPlayerManager().isPlayerInGame(player)) {
      player.sendMessage(
          "You are already in a game, can not join another one! Leave your current game to join a new one.");
    }

    plugin.getDeckedOutManager().getGame(context.get("name"))
        .ifPresentOrElse(game -> game.joinToGame(
                plugin.getPlayerManager().getPlayerOrCreate(player)),
            () -> player.sendMessage("Game not found"));
  }

  private void spawn(CommandContext<CommandSender> context) {
    final Player player = (Player) context.getSender();

    var location = player.getLocation();
    workspace.setSpawn(location);

    player.sendMessage(String.format("Spawn set to '%s'", location));
  }

  private void save(CommandContext<CommandSender> context) {
    final Player player = (Player) context.getSender();

    this.workspace.saveToConfig();

    if (workspace.getFile() != null) {
      String message = String.format("Successfully saved arena to file %s",
          workspace.getFile().toString());
      OpenDeckedOut.LOGGER.log(Level.INFO, message);
      player.sendMessage(message);
    }
  }

  private void pos1(CommandContext<CommandSender> context) {
    final Player player = (Player) context.getSender();
    var location = player.getLocation();

    if (workspace.getWorld() == null) {
      workspace.setWorld(location.getWorld());
    }
    if (!workspace.getWorld().equals(location.getWorld())) {
      player.sendMessage("Error: World of position1 needs to be in same world as position2");
      return;
    }

    this.workspace.setPos1(location);

    player.sendMessage(String.format("Pos1 set to '%s'", location));
  }

  private void pos2(CommandContext<CommandSender> context) {
    final Player player = (Player) context.getSender();

    var location = player.getLocation();
    this.workspace.setPos2(location);

    player.sendMessage(String.format("Pos2 set to '%s'", location));
  }

  private void workspace(CommandContext<CommandSender> context) {
    final Player player = (Player) context.getSender();
    plugin.getDeckedOutManager();
    player.sendMessage(String.format("Arena '%s' is currently in workspace", workspace.getName()));
  }

  private void add(CommandContext<CommandSender> context) {
    final String name = context.get("name");
    final Player player = (Player) context.getSender();
    if (this.deckedOutManager.getGame(name) != null) {
      player.sendMessage("This game already exists");
    }

    this.workspace = new DeckedOut(name);

    player.sendMessage(String.format("Add arena '%s' to workspace", name));
  }

  private void version(final CommandContext<CommandSender> context) {
    context.getSender().sendRichMessage("<bold><green>open<white>DeckedOut");
    context.getSender()
        .sendRichMessage("<gray><italic>  v" + this.plugin.getDescription().getVersion());
  }

  private static PaperCommandManager<CommandSender> createCommandManager(
      final OpenDeckedOut plugin) {
    final PaperCommandManager<CommandSender> manager;

    try {
      manager = PaperCommandManager.createNative(plugin,
          CommandExecutionCoordinator.simpleCoordinator());
    } catch (final Exception e) {
      throw new RuntimeException(e);
    }

    return manager;
  }

}