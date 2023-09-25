package com.github.advancedwipe.game;

import com.github.advancedwipe.OpenDeckedOut;
import com.github.advancedwipe.player.DungeonPlayer;
import com.github.advancedwipe.utils.Utils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.apache.logging.log4j.Level;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

public class Dungeon implements Game {

  private File file;
  private final UUID uuid;
  private String name;
  private BukkitTask task;
  private World world;
  private Location pos1;
  private Location pos2;

  private GameStatus status = GameStatus.DISABLED;
  private boolean preparing = false;
  private int countdown;
  private Location spawn;
  private List<DungeonPlayer> players = new ArrayList<>();

  public Dungeon(String name) {
    this.name = name;
    this.uuid = java.util.UUID.randomUUID();
  }

  public Dungeon(UUID uuid) {
    this.uuid = uuid;
  }

  public static Dungeon loadGame(File file) {
    final ConfigurationLoader<? extends ConfigurationNode> arenaLoader;
    final ConfigurationNode configMap;
    arenaLoader = YamlConfigurationLoader.builder().file(file).build();

    try {
      configMap = arenaLoader.load();
    } catch (ConfigurateException e) {
      e.printStackTrace();
      return null;
    }

    try {
      var uid = configMap.node("uuid");
      UUID uuid;
      uuid = uid.get(UUID.class);

      final Dungeon game = new Dungeon(uuid);
      game.file = file;
      game.name = configMap.node("name").getString();

      String worldName = configMap.node("world").getString();
      if (worldName == null) {
        OpenDeckedOut.LOGGER.log(Level.WARN, "Arena file has no world specified! Aborting...");
        return null;
      }
      World world = OpenDeckedOut.getInstance().getServer().getWorld(worldName);
      if (world == null) {
        OpenDeckedOut.LOGGER.log(Level.WARN,
            "The world defined in config file does not exist! Aborting loading this arena file.");
        return null;
      }
      game.world = world;

      game.spawn = Utils.readStringToLocation(game.world,
          Objects.requireNonNull(configMap.node("spawn").getString()));
      game.pos1 = Utils.readStringToLocation(game.world,
          Objects.requireNonNull(configMap.node("pos1").getString()));
      game.pos2 = Utils.readStringToLocation(game.world,
          Objects.requireNonNull(configMap.node("pos2").getString()));

      game.start();
      OpenDeckedOut.LOGGER.log(Level.INFO, String.format("Arena '%s' loaded!", game.name));

      return game;
    } catch (Throwable throwable) {
      throwable.printStackTrace();
      return null;
    }
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public UUID getUuid() {
    return uuid;
  }

  @Override
  public GameStatus getStatus() {
    return null;
  }

  @Override
  public void start() {
    if (status == GameStatus.DISABLED) {
      preparing = true;
      countdown = -1;

      status = GameStatus.WAITING;
      preparing = false;
    }
  }
  public void run() {
    if (status == GameStatus.DISABLED) {
      cancelTask();
      return;
    }

    if (status == GameStatus.WAITING) {
      System.out.println("HEY We are waiting");
    }

    players.forEach(player -> player.getPlayer().sendMessage("You are in the game which is running!"));
  }

  @Override
  public void stop() {

  }

  public World getWorld() {
    return world;
  }

  public void setWorld(World world) {
    this.world = world;
  }

  @Override
  public Location getPos1() {
    return null;
  }

  @Override
  public void setPos1(Location location) {
    this.pos1 = location;
  }

  @Override
  public Location getPos2() {
    return null;
  }

  @Override
  public void setPos2(Location location) {
    this.pos2 = location;

  }

  @Override
  public int getGameTime() {
    return 0;
  }

  @Override
  public int countConnectedPlayers() {
    return 0;
  }

  public File getFile() {
    return file;
  }

  public void saveToConfig() {
    File directory = new File(OpenDeckedOut.getInstance().getDataFolder(), "arenas");

    if (!directory.exists()) {
      if (!directory.mkdirs()) {
        OpenDeckedOut.LOGGER.warn("Failed to create folder 'arenas', can not save arenas to file!");
        return;
      }
    }
    if (file == null) {
      do {
        file = new File(directory, uuid + ".yml");
      } while (file.exists());
    }

    final ConfigurationLoader<? extends CommentedConfigurationNode> loader;
    loader = YamlConfigurationLoader.builder().nodeStyle(NodeStyle.BLOCK).file(file).build();

    var configMap = loader.createNode();
    try {
      setValuesToLoader(configMap);
    } catch (SerializationException exception) {
      exception.printStackTrace();
    }

    try {
      loader.save(configMap);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void setValuesToLoader(ConfigurationNode configMap) throws SerializationException {
    configMap.node("uuid").set(uuid);
    configMap.node("name").set(name);
    configMap.node("world").set(world.getName());
    configMap.node("spawn").set(Utils.writeLocationToString(spawn));
    configMap.node("pos1").set(Utils.writeLocationToString(pos1));
    configMap.node("pos2").set(Utils.writeLocationToString(pos2));
  }

  public void setSpawn(Location location) {
    this.spawn = location;
  }

  public Location getSpawn() {
    return this.spawn;
  }

  public void joinToGame(DungeonPlayer dungeonPlayer) {
    if (status == GameStatus.DISABLED) {
      return;
    }

    if (preparing) {
      // schedule player to join game
    }

    dungeonPlayer.changeGame(this);
  }

  public void runTask() {
    cancelTask();
    task = new DungeonRunnable(this).runTaskTimer(OpenDeckedOut.getInstance(), 0, 20);
  }

  private void cancelTask() {
    if (task != null) {
      task.cancel();
      task = null;
    }
  }

  public void internalJoinPlayer(DungeonPlayer dungeonPlayer) {
    Player player = dungeonPlayer.getPlayer();
    if (status == GameStatus.WAITING) {

      boolean isEmpty = players.isEmpty();
      if (!players.contains(dungeonPlayer)) {
        players.add(dungeonPlayer);
      }

      player.teleport(getSpawn());

      if (isEmpty) {
        runTask();
      }
    }

  }

  public void internalLeavePlayer(DungeonPlayer dungeonPlayer) {
    if (status == GameStatus.DISABLED) {
      return;
    }

    players.remove(dungeonPlayer);

    if (players.isEmpty()) {
      cancelTask();
      rebuild();
    }
  }

  private void rebuild() {
    status = GameStatus.WAITING;
    countdown = -1;
  }
}
