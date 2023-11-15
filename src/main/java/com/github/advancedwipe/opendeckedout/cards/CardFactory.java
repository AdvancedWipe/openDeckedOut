package com.github.advancedwipe.opendeckedout.cards;

import com.github.advancedwipe.opendeckedout.OpenDeckedOut;
import org.apache.logging.log4j.Level;

public class CardFactory {

  public static Card createCard(int cardId) {
    switch (cardId) {
      case 1:
        return new CoinCard();
      default:
        OpenDeckedOut.LOGGER.log(Level.WARN, "Unknown card ID: {}", cardId);
        return null;
    }
  }

}
