package com.github.advancedwipe.opendeckedout.cards;

import com.github.advancedwipe.opendeckedout.cards.effects.CoinEffect;

public class CoinCard extends Card {

  public CoinCard() {
    super("cards.coinCard.name", 1, CardType.GENERATOR, CardRarity.COMMON, 7, "cards.coinCard.rule",
        "cards.coinCard.rule");
    effects.add(new CoinEffect());
  }

}
