package com.github.advancedwipe.game;

import org.bukkit.scheduler.BukkitRunnable;

public class DungeonRunnable extends BukkitRunnable {

  private final DeckedOut dungeon;

  public DungeonRunnable(DeckedOut dungeon) {
    this.dungeon = dungeon;
  }

  @Override
  public void run() {
    dungeon.run();
  }
}
