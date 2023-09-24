package com.github.advancedwipe.player;

import com.github.advancedwipe.game.DeckedOut;
import com.github.advancedwipe.game.Game;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;

public class DeckedOutPlayer implements Player {

  private final org.bukkit.entity.Player player;
  private final UUID uuid;
  private DeckedOut game;

  public DeckedOutPlayer(org.bukkit.entity.Player player) {
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
    return null;
  }

  public void changeGame(DeckedOut game) {
    this.game = game;
  }
}
