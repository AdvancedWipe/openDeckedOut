package com.github.advancedwipe.player;

import com.github.advancedwipe.cards.Card;
import com.github.advancedwipe.game.Game;
import com.github.advancedwipe.game.Money;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;

public interface Player {
  UUID getUuid();

  boolean isSpectator();

  boolean isInGame();

  Map<Money, Integer> getWallet();

  Stack<Card> getLibary();

  List<Card> getHand();

  void addCardToGraveyard();

  @Nullable
  Game getGame();

}
