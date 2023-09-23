package com.github.advancedwipe.player;

import com.github.advancedwipe.game.Game;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;

public interface Player {
  UUID getUuid();

  boolean isSpectator();

  boolean isInGame();

  @Nullable
  Game getGame();

}
