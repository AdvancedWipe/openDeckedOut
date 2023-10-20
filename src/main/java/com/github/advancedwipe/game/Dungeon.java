package com.github.advancedwipe.game;

import com.github.advancedwipe.OpenDeckedOut;
import com.github.advancedwipe.cards.CardManager;
import com.github.advancedwipe.game.artifact.Artifact;
import com.github.advancedwipe.player.DungeonPlayer;
import com.github.advancedwipe.sound.Heartbeat;
import com.github.advancedwipe.utils.DungeonUtils;
import com.github.advancedwipe.utils.SoundUtils;
import com.github.advancedwipe.utils.Utils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.logging.log4j.Level;
import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Note.Tone;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

public class Dungeon extends Game {

  private final Random random = new Random();
  private BukkitTask task;
  private final int minRandom = 1;
  private final int maxRandom = 100;
  private int tick;
  private final int maxTick = 30;
  private CardManager cardManager;
  private List<DungeonPlayer> players = new ArrayList<>();
  private List<Entity> ravagers = new ArrayList<>();
  private List<Location> ravagerSpawns = new ArrayList<>();
  private List<Location> coinSpawners = new ArrayList<>();
  private List<PlayerSensor> sensors = new ArrayList<>();
  private List<Artifact> artifacts = new ArrayList<>();
  private List<Location> berrys = new ArrayList<>();
  private Heartbeat heartbeat;

  public Dungeon(String name) {
    super(name);
  }

  public Dungeon(UUID uuid) {
    super(uuid);
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

      game.ravagerSpawns = Utils.readStringListToLocationList(game.world,
          Objects.requireNonNull(configMap.node("ravagerSpawns").getList(String.class)));
      game.coinSpawners = Utils.readStringListToLocationList(game.world,
          Objects.requireNonNull(configMap.node("coinSpawners").getList(String.class)));
      game.sensors = Utils.readStringToSensorList(game.world,
          Objects.requireNonNull(configMap.node("playerSensors").getList(String.class)));
      game.artifacts = Utils.readStringListToArtifactList(game.world,
          Objects.requireNonNull(configMap.node("artifactLocations").getList(String.class)));
      game.berrys = Utils.readStringListToLocationList(game.world,
          Objects.requireNonNull(configMap.node("berrys").getList(String.class)));

      game.start();
      OpenDeckedOut.LOGGER.log(Level.INFO, String.format("Arena '%s' loaded!", game.name));

      return game;
    } catch (Throwable throwable) {
      throwable.printStackTrace();
      return null;
    }
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


  public void run() {
    if (status == GameStatus.DISABLED) {
      cancelTask();
      return;
    }

    if (status == GameStatus.WAITING) {
      prepareDungeon();
    }

    if (status == GameStatus.RUNNING) {
      onEveryDungeonTick();

      if (tick % 10 == 0) {
        onEveryTenthDungeonTick();
      }

      if (random() <= 50) {
        dropCoinOnRandomCoinSpawner();
      }

      if (tick == maxTick) {
        onMaxDungeonTick();
        return;
      }
      tick++;
    }

  }

  private void onEveryTenthDungeonTick() {
    cardManager.applyCardEffect(players.get(0));
  }

  private void onMaxDungeonTick() {
    tick = 0;
    cardManager.drawNewCard();
  }

  private void onEveryDungeonTick() {
    sensors.forEach(PlayerSensor::decreaseCooldown);
  }

  private void prepareDungeon() {
    cardManager = new CardManager();

    for (var ravagerSpawn : ravagerSpawns) {
      Entity ravager = ravagerSpawn.getWorld().spawnEntity(ravagerSpawn, EntityType.RAVAGER);
      ravagers.add(ravager);
    }

    status = GameStatus.RUNNING;
  }

  private void dropCoinOnRandomCoinSpawner() {
    Location location = coinSpawners.get(random.nextInt(coinSpawners.size()));
    world.dropItem(location, new ItemStack(Material.SUNFLOWER));
    SoundUtils.playCoinSound(players, location);
  }

  private void setValuesToLoader(ConfigurationNode configMap) throws SerializationException {
    configMap.node("uuid").set(uuid);
    configMap.node("name").set(name);
    configMap.node("world").set(world.getName());
    configMap.node("spawn").set(Utils.writeLocationToString(spawn));
    configMap.node("pos1").set(Utils.writeLocationToString(pos1));
    configMap.node("pos2").set(Utils.writeLocationToString(pos2));
    configMap.node("ravagerSpawns").set(Utils.writeLocationListToStringList(ravagerSpawns));
    configMap.node("coinSpawners").set(Utils.writeLocationListToStringList(coinSpawners));
    configMap.node("playerSensors").set(Utils.writeSensorListToString(sensors));
    configMap.node("artifactLocations").set(Utils.writeArtifactListToString(artifacts));
    configMap.node("berrys").set(Utils.writeLocationListToStringList(berrys));

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
    final int taskFrequency = 20;
    final int taskDelay = 0;
    // Dungeon has a tick frequency of 20 minecraft ticks (1 second) to process its events
    task = new DungeonRunnable(this).runTaskTimer(OpenDeckedOut.getInstance(), taskDelay,
        taskFrequency);
    heartbeat = new Heartbeat(this, 100);
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
      dungeonPlayer.saveInventory();
      player.setScoreboard(getScoreBoard(player.getName()));
      player.teleport(getSpawn());

      player.getInventory().addItem(artifacts.get(0).getCompass());
      player.setCompassTarget(artifacts.get(0).getLocation());

      for (var berry : berrys) {
        Block berryBlock = world.getBlockAt(berry);
        berryBlock.setType(Material.SWEET_BERRY_BUSH);
        Ageable ageable = (Ageable) berryBlock.getBlockData();
        int maxAge = ageable.getMaximumAge();
        ageable.setAge(maxAge);
        berryBlock.setBlockData(ageable);
      }

      if (isEmpty) {
        runTask();
      }
    }

  }

  public void internalLeavePlayer(DungeonPlayer dungeonPlayer) {
    if (status == GameStatus.DISABLED) {
      return;
    }

    dungeonPlayer.restoreInventory();
    dungeonPlayer.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    players.remove(dungeonPlayer);

    if (players.isEmpty()) {
      cancelTask();
      rebuild();
    }
  }

  private void rebuild() {
    status = GameStatus.DISABLED;
    countdown = -1;

    resetToOriginalDungeon();

    for (Entity entity : this.world.getEntities()) {
      if (DungeonUtils.isInArena(entity.getLocation(), getPos1(), getPos2())) {
        entity.remove();
      }
    }

    status = GameStatus.WAITING;
  }

  public Scoreboard getScoreBoard(@NotNull String name) {
    Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

    // Create an objective
    Objective obj = board.registerNewObjective("CustomObjective", "dummy",
        "       openDeckedOut       ");

    // Assign the objective to a display slot (e.g., SIDEBAR)
    obj.setDisplaySlot(DisplaySlot.SIDEBAR);

    obj.getScore(" ").setScore(15);
    obj.getScore("State 1: ██████████").setScore(14);
    obj.getScore("  ").setScore(13);
    obj.getScore("State 2: ██████████").setScore(12);
    obj.getScore("   ").setScore(11);
    obj.getScore("State 3: ██████████").setScore(10);
    obj.getScore("    ").setScore(9);
    obj.getScore("State 4: ██████████").setScore(8);
    obj.getScore("     ").setScore(7);
    obj.getScore("Cards:   ██████████").setScore(6);
    obj.getScore("           ██████████").setScore(5);

    return board;
  }

  public void addRavagerSpawn(Location location) {
    ravagerSpawns.add(location);
  }

  public void addCoinSpawn(Location location) {
    coinSpawners.add(location);
  }

  private int random() {
    return ThreadLocalRandom.current().nextInt(minRandom, maxRandom + 1);
  }

  public void addSensor(Location location) {
    sensors.add(new PlayerSensor(location));
  }

  public List<PlayerSensor> getSensors() {
    return sensors;
  }

  public void activatedSensor(Player player, Location location) {
    sensors.forEach(sensor -> {
      if (sensor.getLocation().equals(location)) {
        if (sensor.hasCooldown()) {
          return;
        }
        sensor.activated(player);
        player.playSound(location, Sound.BLOCK_SCULK_SHRIEKER_SHRIEK, 1, 1);
        player.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 60, 1));
      }
    });
  }

  public void addArtifactSpawn(Location location, int level, String difficulty) {
    artifacts.add(new Artifact(location, level, difficulty));
  }

  public void addBerryPosition(Location location) {
    berrys.add(location);
  }

  public List<DungeonPlayer> getPlayers() {
    return players;
  }
}
