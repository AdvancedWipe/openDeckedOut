package com.github.advancedwipe.game;

import java.util.UUID;
import org.bukkit.Location;

public class DeckedOut implements Game {

  private final UUID UUID;
  private final String name;
  private Location pos1;
  private Location pos2;

  public DeckedOut(String name) {
    this.name = name;
    this.UUID = java.util.UUID.randomUUID();
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public UUID getUuid() {
    return UUID;
  }

  @Override
  public GameStatus getStatus() {
    return null;
  }

  @Override
  public void start() {

  }

  @Override
  public void stop() {

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
}
