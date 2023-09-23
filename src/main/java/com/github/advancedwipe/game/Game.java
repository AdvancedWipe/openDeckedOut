package com.github.advancedwipe.game;

import java.util.UUID;
import org.bukkit.Location;

public interface Game {
  String getName();

  UUID getUuid();

  GameStatus getStatus();

  void start();

  void stop();

  Location getPos1();

  void setPos1(Location location);

  Location getPos2();

  void setPos2(Location location);

  int getGameTime();

  int countConnectedPlayers();

}
