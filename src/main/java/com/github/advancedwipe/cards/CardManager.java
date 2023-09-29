package com.github.advancedwipe.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class CardManager {

  private final List<Card> cards = new ArrayList<>();
  private final Stack<Card> library = new Stack<>();
  private final List<Card> hand = new ArrayList<>();
  private final List<Card> graveyard = new ArrayList<>();

  public CardManager() {

  }

  public void shuffleCards() {
    Collections.shuffle(cards);
  }

  public void drawNewCard() {
    try {
      Card drawn = library.pop();
      hand.add(drawn);
    } catch (EmptyStackException e) {
      return; // Stack is empty, no more cards to draw
    }
  }

  public void addCardToGraveyard() {

  }

  public List<Card> getCards() {
    return cards;
  }

  public List<Card> getLibrary() {
    return library;
  }

  public List<Card> getHand() {
    return hand;
  }

  public List<Card> getGraveyard() {
    return graveyard;
  }
}
