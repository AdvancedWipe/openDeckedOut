package com.github.advancedwipe.game;

import com.github.advancedwipe.utils.DungeonUtils;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockState;

public abstract class Game {

  protected File file;
  protected final UUID uuid;
  protected String name;
  protected World world;
  protected Location pos1;
  protected Location pos2;
  protected Location spawn;
  protected Location exit;
  protected GameStatus status = GameStatus.DISABLED;
  protected int countdown;
  protected boolean preparing = false;
  private final Map<Location, BlockState> originalState = new HashMap<>();

  public Game(String name) {
    this.name = name;
    this.uuid = java.util.UUID.randomUUID();
  }

  public Game(UUID uuid) {
    this.uuid = uuid;
  }


  public void start() {
    if (status == GameStatus.DISABLED) {
      preparing = true;
      countdown = -1;

      recordOriginalDungeon();

      status = GameStatus.WAITING;
      preparing = false;
    }
  }

  public void stop() {
  }

  public void recordOriginalDungeon() {
    for (Location location : DungeonUtils.getBlocksInBoundingBox(getPos1(), getPos2())) {
      var state = location.getBlock().getState();
      originalState.put(location, state);
    }
  }

  public void resetToOriginalDungeon() {
    for (Map.Entry<Location, BlockState> entry : originalState.entrySet()) {
      Location location = entry.getKey();
      BlockState state = entry.getValue();
      world.setBlockData(location, state.getBlockData());
    }
    originalState.clear();
  }

  public void disable() {
    this.status = GameStatus.DISABLED;
  }

  public File getFile() {
    return file;
  }

  public UUID getUuid() {
    return uuid;
  }

  public String getName() {
    return name;
  }

  public World getWorld() {
    return world;
  }

  public void setWorld(World world) {
    this.world = world;
  }

  public Location getPos1() {
    return this.pos1;
  }

  public void setPos1(Location location) {
    this.pos1 = location;
  }

  public Location getPos2() {
    return this.pos2;
  }

  public void setPos2(Location location) {
    this.pos2 = location;
  }

  public void setSpawn(Location location) {
    this.spawn = location;
  }

  public Location getSpawn() {
    return this.spawn;
  }

  public void setExit(Location location) {
    this.exit = location;
  }

  public Location getExit() {
    return exit;
  }

  public GameStatus getStatus() {
    return null;
  }

  public int getGameTime() {
    return 0;
  }

  public int countConnectedPlayers() {
    return 0;
  }

}
