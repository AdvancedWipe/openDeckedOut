package com.github.advancedwipe.cards;

import com.github.advancedwipe.player.DungeonPlayer;

public class CoinCard extends Card {

  public CoinCard() {
    super("cards.coinCard.name", 1, CardType.GENERATOR, CardRarity.COMMON, 7, "cards.coinCard.rule",
        "cards.coinCard.rule");
    effects.add(new CoinEffect());
  }

}
