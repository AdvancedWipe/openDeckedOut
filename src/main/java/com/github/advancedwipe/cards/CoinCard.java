package com.github.advancedwipe.cards;

import com.github.advancedwipe.player.DungeonPlayer;

public class CoinCard extends Card implements Playable {

  public CoinCard() {
    super("cards.coinCard.name", 1, CardType.GENERATOR, CardRarity.COMMON, 7, "cards.coinCard.rule",
        "cards.coinCard.rule");
  }

  @Override
  public void playCard(DungeonPlayer player) {
    effects.forEach(effect -> effect.applyEffect(player));
  }

}
