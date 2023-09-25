package com.github.advancedwipe.player;

import com.github.advancedwipe.game.Dungeon;
import com.github.advancedwipe.game.Game;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;

public class DungeonPlayer implements Player {

  private final org.bukkit.entity.Player player;
  private final UUID uuid;
  private Dungeon game;

  public DungeonPlayer(org.bukkit.entity.Player player) {
    this.player = player;
    this.uuid = player.getUniqueId();
  }

  @Override
  public UUID getUuid() {
    return uuid;
  }

  public org.bukkit.entity.Player getPlayer() {
    return player;
  }

  @Override
  public boolean isSpectator() {
    return false;
  }

  @Override
  public boolean isInGame() {
    return game != null;
  }

  @Override
  public @Nullable Game getGame() {
    return game;
  }

  public void changeGame(Dungeon game) {
    if (this.game == null && game != null) { // On join
      // TODO Maybe save inventory before joining to restore it after game
      this.game = game;
      this.game.internalJoinPlayer(this);
    } else if (this.game != null && game == null) { // On leave
      this.game.internalLeavePlayer(this);
      this.game = null;
      // TODO clean up values or restore inventory
    }
  }
}
