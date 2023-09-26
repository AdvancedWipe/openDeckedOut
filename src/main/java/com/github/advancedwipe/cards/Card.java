package com.github.advancedwipe.cards;

public interface Card {

  String getName();

  int getID();

  CardType getType();

  CardSet getSet();

  CardRarity getRarity();

  int getValue();

  String getRules();

  String getAnecdote();

}
