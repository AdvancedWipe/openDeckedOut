package com.github.advancedwipe.player;

public class Status {

  private int coinProbabilityIncrease;

  public Status() {
    this.coinProbabilityIncrease = 0;
  }

  public int getCoinProbabilityIncrease() {
    int probability = coinProbabilityIncrease;
    coinProbabilityIncrease = 0;
    return probability;
  }

  public void setCoinProbabilityIncrease(int coinProbability) {
    this.coinProbabilityIncrease = coinProbability;
  }

}
