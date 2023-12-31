package com.github.advancedwipe.opendeckedout.player;

import com.github.advancedwipe.opendeckedout.game.Dungeon;
import org.jetbrains.annotations.Nullable;

public class DungeonPlayer extends Player {

  private Dungeon game;
  private final Stats stats = new Stats();
  private Status status;
  private PlayerStats playerStats;

  public DungeonPlayer(org.bukkit.entity.Player player) {
    super(player);
    this.status = new Status();
    this.playerStats = new PlayerStats(player);
  }

  public void changeGame(Dungeon game) {
    if (this.game == null && game != null) { // On join
      // TODO Maybe save inventory before joining to restore it after game
      this.game = game;
      this.game.internalJoinPlayer(this);
    } else if (this.game != null && game == null) { // On leave
      this.game.internalLeavePlayer(this);
      this.game = null;
      //stats.savePlayerStats(player, stats);
      // TODO clean up values or restore inventory
    }
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
