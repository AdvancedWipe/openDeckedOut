package com.github.advancedwipe.cards;

public class CoinCard implements Card {

  private final String namePath;
  private final int id;
  private final CardType type;
  private final CardRarity rarity;
  private final int value;
  private final String rulePath;
  private final String anecdotePath;

  public CoinCard() {
    this.namePath = "cards.coinCard.name";
    this.id = 1;
    this.type = CardType.GENERATOR;
    this.rarity = CardRarity.COMMON;
    this.value = 7;
    this.rulePath = "cards.coinCard.rule";
    this.anecdotePath = "cards.coinCard.rule";
  }

  @Override
  public String getNamePath() {
    return this.namePath;
  }

  @Override
  public int getID() {
    return this.id;
  }

  @Override
  public CardType getType() {
    return type;
  }

  @Override
  public CardSet getSet() {
    return null;
  }

  @Override
  public CardRarity getRarity() {
    return this.rarity;
  }

  @Override
  public int getValue() {
    return this.value;
  }

  @Override
  public String getRulePath() {
    return this.rulePath;
  }

  @Override
  public String getAnecdotePath() {
    return this.anecdotePath;
  }
}
