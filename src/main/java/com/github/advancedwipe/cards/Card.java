package com.github.advancedwipe.cards;

public interface Card {

  String getNamePath();

  int getID();

  CardType getType();

  CardSet getSet();

  CardRarity getRarity();

  int getValue();

  String getRulePath();

  String getAnecdotePath();

}
