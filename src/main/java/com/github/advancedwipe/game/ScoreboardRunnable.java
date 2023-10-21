package com.github.advancedwipe.game;

import org.bukkit.scheduler.BukkitRunnable;

public class ScoreboardRunnable extends BukkitRunnable {

  private final Scoreboard scoreboard;

  public ScoreboardRunnable(Scoreboard scoreboard) {
    this.scoreboard = scoreboard;
  }

  @Override
  public void run() {
    scoreboard.update();
  }
}
