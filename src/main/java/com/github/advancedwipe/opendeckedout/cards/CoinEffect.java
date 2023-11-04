package com.github.advancedwipe.opendeckedout.cards;

import com.github.advancedwipe.opendeckedout.player.DungeonPlayer;

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
