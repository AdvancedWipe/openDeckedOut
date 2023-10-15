package com.github.advancedwipe.cards;

import com.github.advancedwipe.player.DungeonPlayer;

public class CoinEffect extends CardEffect {

  private final int probability;

  public CoinEffect() {
    this.probability = 3;
  }

  @Override
  public void applyEffect(DungeonPlayer player) {
    player.getStatus().setCoinProbabilityIncrease(probability);
  }
}
