package com.github.advancedwipe.cards;

public class CoinCard implements Card {

  private final String name;
  private final int id;
  private final CardType type;
  private final CardRarity rarity;
  private final int value;
  private final String rule;
  private final String anecdote;

  public CoinCard() {
    this.name = "CoinCard";
    this.id = 1;
    this.type = CardType.GENERATOR;
    this.rarity = CardRarity.COMMON;
    this.value = 7;
    this.rule = "When the card is activated, increase the dungeons probability to drop a coin by +4.";
    this.anecdote = "In the shadowy recesses of the dungeon, a map whispers tales of untold riches, its magic able to conjure coins from the abyss, but it warns of boundless greed.";
  }

  @Override
  public String getName() {
    return this.name;
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
  public String getRules() {
    return this.rule;
  }

  @Override
  public String getAnecdote() {
    return this.anecdote;
  }
}
