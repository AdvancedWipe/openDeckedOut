package com.github.advancedwipe.player;

import com.github.advancedwipe.cards.Card;
import com.github.advancedwipe.cards.CardManager;
import com.github.advancedwipe.game.Dungeon;
import com.github.advancedwipe.game.Game;
import com.github.advancedwipe.game.Money;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.UUID;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.Nullable;

public class DungeonPlayer extends Player {

  private Dungeon game;
  private final Stats stats = new Stats();
  private Status status;

  public DungeonPlayer(org.bukkit.entity.Player player) {
    super(player);
    this.status = new Status();
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
