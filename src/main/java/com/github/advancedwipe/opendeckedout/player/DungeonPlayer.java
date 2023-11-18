package com.github.advancedwipe.opendeckedout.player;

import com.github.advancedwipe.opendeckedout.game.Dungeon;
import org.jetbrains.annotations.Nullable;

public class DungeonPlayer extends Player {

  private Dungeon game;
  //private final Stats stats = new Stats();
  private Status status;
  private PlayerStats playerStats;

  public DungeonPlayer(org.bukkit.entity.Player player) {
    super(player);
    this.status = new Status();
    this.playerStats = new PlayerStats(player);
  }

  public void join(Dungeon dungeon) {
    saveInventory();
    this.game = dungeon;
    this.game.join(this);
  }

  public void quit() {
    this.game.internalLeavePlayer(this);
    this.game = null;
  }

  @Override
  public boolean isInGame() {
    return game != null;
  }

  public @Nullable Dungeon getDungeon() {
    return game;
  }

  public Status getStatus() {
    return status;
  }
}
