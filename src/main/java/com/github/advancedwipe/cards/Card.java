package com.github.advancedwipe.cards;

import java.util.ArrayList;
import java.util.List;

public abstract class Card {
  private final String namePath;
  private final int id;
  private final CardType type;
  private final CardRarity rarity;
  private final int value;
  private final String rulePath;
  private final String anecdotePath;
  protected final List<CardEffect> effects = new ArrayList<>();

  public Card(String namePath, int id, CardType type, CardRarity rarity, int value, String rulePath, String anecdotePath) {
    this.namePath = namePath;
    this.id = id;
    this.type = type;
    this.rarity = rarity;
    this.value = value;
    this.rulePath = rulePath;
    this.anecdotePath = anecdotePath;
  }

  public String getNamePath() {
    return this.namePath;
  }

  public int getID() {
    return this.id;
  }

  public CardType getType() {
    return type;
  }

  public CardSet getSet() {
    return null;
  }

  public CardRarity getRarity() {
    return this.rarity;
  }

  public int getValue() {
    return this.value;
  }

  public String getRulePath() {
    return this.rulePath;
  }

  public String getAnecdotePath() {
    return this.anecdotePath;
  }

}
