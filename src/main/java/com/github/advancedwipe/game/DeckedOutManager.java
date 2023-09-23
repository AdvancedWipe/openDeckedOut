package com.github.advancedwipe.game;

import com.github.advancedwipe.OpenDeckedOut;
import java.util.HashMap;
import java.util.Map;

public class DeckedOutManager {
  private final Map<String, DeckedOut> games;
  private final OpenDeckedOut plugin;

  public DeckedOutManager(final OpenDeckedOut plugin) {
    this.plugin = plugin;
    this.games = new HashMap<>();
  }

  public DeckedOut getGame(final String name) {
    return this.games.get(name);
  }

  public void createGame(String name) {
    final DeckedOut deckedOut = new DeckedOut(name);
  }
}
