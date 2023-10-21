package com.github.advancedwipe.cards;

import com.github.advancedwipe.player.DungeonPlayer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;
import org.bukkit.Sound;

public class CardManager {

  private final List<Card> cards = new ArrayList<>();
  private final Stack<Card> library = new Stack<>();
  private final Stack<Card> hand = new Stack<>();
  private final List<Card> graveyard = new ArrayList<>();

  public CardManager() {
    library.push(new CoinCard());
    shuffleCards();
  }

  public void shuffleCards() {
    Collections.shuffle(cards);
  }

  public void drawNewCard() {
    try {
      hand.push(library.pop());
    } catch (EmptyStackException e) {
      return; // Stack is empty, no more cards to draw
    }
  }

  public void applyCardEffect(DungeonPlayer player) {
    try {
      hand.pop().playCard(player);
    } catch (EmptyStackException e) {
      return;
    }
  }

  public void moveCardToGraveyard() {

  }

  public List<Card> getCards() {
    return cards;
  }

  public List<Card> getLibrary() {
    return library;
  }

  public int getLibrarySize() {
    return library.size();
  }

  public List<Card> getHand() {
    return hand;
  }

  public List<Card> getGraveyard() {
    return graveyard;
  }
}
