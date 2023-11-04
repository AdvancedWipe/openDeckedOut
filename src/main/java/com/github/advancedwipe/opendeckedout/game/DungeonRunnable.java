package com.github.advancedwipe.opendeckedout.game;

import org.bukkit.scheduler.BukkitRunnable;

public class DungeonRunnable extends BukkitRunnable {

  private final Dungeon dungeon;

  public DungeonRunnable(Dungeon dungeon) {
    this.dungeon = dungeon;
  }

  @Override
  public void run() {
    dungeon.run();
  }
}
