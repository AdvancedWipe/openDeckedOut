package com.github.advancedwipe.commands;

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.Command;
import cloud.commandframework.Command.Builder;
import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.paper.PaperCommandManager;
import com.github.advancedwipe.OpenDeckedOut;
import com.github.advancedwipe.game.Dungeon;
import com.github.advancedwipe.game.DungeonManager;
import com.github.advancedwipe.utils.Utils;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.Level;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.StructureType;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.structure.Structure;
import org.bukkit.structure.StructureManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Holds all available commands of the plugin.
 *
 * <p>This class is responsible for registering all the different in game commands that are
 * available to the player in openDeckedOut
 */
public final class Commands {

  private final OpenDeckedOut plugin;
  private Map<String, Dungeon> workspace;
  private final PaperCommandManager<CommandSender> cmdManager;
  private final DungeonManager dungeonManager;

  public Commands(final @NotNull OpenDeckedOut plugin) {
    this.plugin = plugin;
    workspace = new HashMap<>();
    this.dungeonManager = plugin.getDeckedOutManager();
    this.cmdManager = createCommandManager(plugin);
  }

  public void register() {
    final Command.Builder<CommandSender> deckedout = this.cmdManager.commandBuilder("deckedout");

    registerRegularCommands(deckedout);
    registerAdminCommands(deckedout);

    // In theory an admin command but has no argument
    this.cmdManager.command(deckedout.literal("workspaces",
            ArgumentDescription.of("Show dungeons in edit mode"))
        .permission("opendeckedout.command.admin.workspaces")
        .handler(this::workspace));
  }

  private void registerRegularCommands(Builder<CommandSender> deckedout) {
    this.cmdManager.command(deckedout.literal("version",
            ArgumentDescription.of("Display version of plugin currently in use"))
        .permission("opendeckedout.command.version")
        .handler(this::version));

    this.cmdManager.command(deckedout.literal("join",
            ArgumentDescription.of("Join an arena"))
        .argument(StringArgument.single("name"))
        .permission("opendeckedout.command.join")
        .handler(this::join));

    this.cmdManager.command(deckedout.literal("leave",
            ArgumentDescription.of("Leave your current dungeon"))
        .permission("opendeckedout.command.leave")
        .handler(this::leave));
  }

  private void registerAdminCommands(Builder<CommandSender> deckedout) {
    var admin = deckedout.literal("admin", ArgumentDescription.of("Admin commands"))
        .argument(StringArgument.<CommandSender>builder("dungeonname")
            .withSuggestionsProvider(((context, string) -> Stream.concat(
                OpenDeckedOut.getInstance().getDeckedOutManager().getGameNames().stream(),
                workspace.keySet().stream()).distinct().collect(Collectors.toList()))));

    this.cmdManager.command(admin.literal("add",
            ArgumentDescription.of("Adds a new arena to your workspace"))
        .permission("opendeckedout.command.add")
        .handler(this::add));

    var spawn = admin.literal("spawn");
    registerSpawnCommands(spawn);

    this.cmdManager.command(admin.literal("editable",
            ArgumentDescription.of(
                "Make dungeon editable, use /deckedout "
                    + "admin workspaces to list all dungeons in edit mode"))
        .permission("opendeckedout.command.admin.editable")
        .handler(this::makeEditable));

    this.cmdManager.command(admin.literal("pos1",
            ArgumentDescription.of("Define position 1 of arena"))
        .permission("opendeckedout.command.pos1")
        .handler(this::pos1));

    this.cmdManager.command(admin.literal("pos2",
            ArgumentDescription.of("Define position 2 of arena"))
        .permission("opendeckedout.command.pos2")
        .handler(this::pos2));

    this.cmdManager.command(admin.literal("save",
            ArgumentDescription.of("Save arena"))
        .permission("opendeckedout.command.save")
        .handler(this::save));

    this.cmdManager.command(admin.literal("barrier",
            ArgumentDescription.of("Save barrier position and related structure"))
        .argument(StringArgument.builder("namespace"))
        .argument(StringArgument.builder("key"))
        .argument(StringArgument.builder("includeEntities"))
        .permission("opendeckedout.command.barrier")
        .handler(this::barrier));

  }

  private void barrier(CommandContext<CommandSender> context) {
    final String namespace = context.get("namespace");
    final String key = context.get("key");
    final boolean includeEntities = Boolean.getBoolean(context.get("key").toString());
    final Player player = (Player) context.getSender();

    StructureManager manager = OpenDeckedOut.getInstance().getServer().getStructureManager();
    File structureFile = manager.getStructureFile(new NamespacedKey(namespace, key));
    Structure structure;

    try {
      structure = manager.loadStructure(structureFile);
    } catch (IOException e) {
      OpenDeckedOut.LOGGER.log(Level.WARN, e.getMessage());
      return;
    }

    structure.place(player.getLocation(), includeEntities, StructureRotation.NONE, Mirror.NONE, -1,
        1f, new Random());
  }

  private void makeEditable(CommandContext<CommandSender> context) {
    System.out.println("Needs implementation");

  }

  private void registerSpawnCommands(Builder<CommandSender> spawn) {
    this.cmdManager.command(spawn.literal("SENSOR",
            ArgumentDescription.of("Define spawn of player sensor in dungeon"))
        .permission("opendeckedout.command.admin.spawn.sensor")
        .handler(this::placeSensor));

    this.cmdManager.command(spawn.literal("PLAYER",
            ArgumentDescription.of("Define spawn of player in dungeon"))
        .permission("opendeckedout.command.admin.spawn.player")
        .handler(this::addPlayerSpawn));
    this.cmdManager.command(spawn.literal("RAVAGER",
            ArgumentDescription.of("Set entity spawn position"))
        .permission("opendeckedout.command.admin.spawn.entity")
        .handler(this::addRavagerSpawn));

    var item = spawn.literal("item");
    this.cmdManager.command(item.literal("COIN")
        .permission("opendeckedout.command.admin.spawn.item.coin")
        .handler(this::addCoinSpawner));


  }

  private void placeSensor(CommandContext<CommandSender> context) {
    final Player player = (Player) context.getSender();
    var location = player.getLocation();
    Dungeon dungeon = getDungeonFromWorkspace(context, player);
    if (dungeon == null) {
      player.sendMessage("Dungeon name not available");
      return;
    }

    dungeon.addSensor(location);
    player.sendMessage(String.format("Sensor placed at '%s'", Utils.locationToXYZ(location)));
  }

  private void addCoinSpawner(CommandContext<CommandSender> context) {
    final Player player = (Player) context.getSender();
    var location = player.getLocation();
    Dungeon dungeon = getDungeonFromWorkspace(context, player);
    if (dungeon == null) {
      player.sendMessage("Dungeon name not available");
      return;
    }

    dungeon.addCoinSpawn(location);

    player.sendMessage(String.format("Coin spawner set to '%s'", Utils.locationToXYZ(location)));
  }

  private void addRavagerSpawn(CommandContext<CommandSender> context) {
    final Player player = (Player) context.getSender();
    var location = player.getLocation();
    Dungeon dungeon = getDungeonFromWorkspace(context, player);
    if (dungeon == null) {
      player.sendMessage("Dungeon name not available");
      return;
    }

    dungeon.addRavagerSpawn(location);

    player.sendMessage(String.format("Ravager spawn set to '%s'", Utils.locationToXYZ(location)));
  }

  private void leave(CommandContext<CommandSender> context) {
    final Player player = (Player) context.getSender();

    if (!plugin.getPlayerManager().isPlayerInGame(player)) {
      player.sendMessage("You are not in a dungeon, no need to leave it");
      return;
    }

    if (plugin.getPlayerManager().isPlayerInGame(player)) {
      plugin.getPlayerManager().getPlayerOrCreate(player).changeGame(null);
    }
  }

  private void join(CommandContext<CommandSender> context) {
    final Player player = (Player) context.getSender();

    if (plugin.getPlayerManager().isPlayerInGame(player)) {
      player.sendMessage(
          "You are already in a game, can not join another one! "
              + "Leave your current game to join a new one.");
    }

    plugin.getDeckedOutManager().getGame(context.get("name"))
        .ifPresentOrElse(game -> game.joinToGame(
                plugin.getPlayerManager().getPlayerOrCreate(player)),
            () -> player.sendMessage("Game not found"));
  }

  private void addPlayerSpawn(CommandContext<CommandSender> context) {
    final Player player = (Player) context.getSender();
    Dungeon dungeon = getDungeonFromWorkspace(context, player);
    if (dungeon == null) {
      player.sendMessage("Dungeon name not available");
      return;
    }

    var location = player.getLocation();
    dungeon.setSpawn(location);

    player.sendMessage(String.format("Set player spawn to '%s'", Utils.locationToXYZ(location)));
  }

  private void save(CommandContext<CommandSender> context) {
    final Player player = (Player) context.getSender();
    Dungeon dungeon = getDungeonFromWorkspace(context, player);

    if (dungeon == null) {
      player.sendMessage("Dungeon name not available");
      return;
    }

    dungeon.saveToConfig();

    if (dungeon.getFile() != null) {
      String message = String.format("Successfully saved arena to file %s",
          dungeon.getFile().toString());
      OpenDeckedOut.LOGGER.log(Level.INFO, message);
      player.sendMessage(message);
    }
  }

  private void pos1(CommandContext<CommandSender> context) {
    final Player player = (Player) context.getSender();
    Location location = player.getLocation();
    Dungeon dungeon = getDungeonFromWorkspace(context, player);

    if (dungeon == null) {
      player.sendMessage("Dungeon name not available");
      return;
    }

    if (dungeon.getWorld() == null) {
      dungeon.setWorld(location.getWorld());
    }
    if (!dungeon.getWorld().equals(location.getWorld())) {
      player.sendMessage("Error: World of pos1 needs to be in same world as pos2");
      return;
    }

    dungeon.setPos1(location);

    player.sendMessage(String.format("Set pos1 to '%s'", Utils.locationToXYZ(location)));
  }

  private void pos2(CommandContext<CommandSender> context) {
    final Player player = (Player) context.getSender();
    var location = player.getLocation();
    Dungeon dungeon = getDungeonFromWorkspace(context, player);

    if (dungeon == null) {
      player.sendMessage("Dungeon name not available");
      return;
    }

    dungeon.setPos2(location);

    player.sendMessage(String.format("Set pos2 to '%s'", Utils.locationToXYZ(location)));
  }

  private void workspace(CommandContext<CommandSender> context) {
    final Player player = (Player) context.getSender();
    plugin.getDeckedOutManager();
    player.sendMessage("Needs implementation");
  }

  private void add(CommandContext<CommandSender> context) {
    final String dungeonName = context.get("dungeonname");
    final Player player = (Player) context.getSender();

    if (this.dungeonManager.getGame(dungeonName).isPresent()) {
      player.sendMessage("This dungeon already exists");
    }

    this.workspace.put(dungeonName, new Dungeon(dungeonName));

    player.sendMessage(String.format("Add dungeon '%s' to workspace", dungeonName));
  }

  private void version(final CommandContext<CommandSender> context) {
    context.getSender().sendRichMessage("<bold><green>open<white>DeckedOut");
    context.getSender()
        .sendRichMessage("<gray><italic>  v" + this.plugin.getPluginMeta().getVersion());
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

  @Nullable
  private Dungeon getDungeonFromWorkspace(CommandContext<CommandSender> context, Player player) {
    return workspace.values().stream()
        .filter(game -> game.getName().equals(context.get("dungeonname")))
        .findFirst()
        .orElse(null);
  }

}
