package com.github.advancedwipe.opendeckedout.game;

import com.github.advancedwipe.opendeckedout.OpenDeckedOut;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;

public class DungeonManager {

  private final List<Dungeon> games;
  private final OpenDeckedOut plugin;

  public DungeonManager(final OpenDeckedOut plugin) {
    this.plugin = plugin;
    this.games = new ArrayList<>();
  }

  public void loadGames(File @NotNull [] arenaFiles) {
    for (File arenaFile : arenaFiles) {
      Dungeon game = Dungeon.loadFromFile(plugin, arenaFile);
      if (game != null) {
        this.games.add(game);
        OpenDeckedOut.LOGGER.log(Level.INFO, "Dungeon '{}' loaded!", game.name);
      } else {
        OpenDeckedOut.LOGGER.log(Level.WARN, "Unable to load {}", arenaFile);
      }
    }
  }

  public void initializeGames() {
    if (games.isEmpty()) {
      return;
    }

    for (Game game: games) {
      game.initialize();
    }
  }

  public Optional<Dungeon> getGame(final String name) {
    return games.stream().filter(game -> game.getName().equals(name)).findFirst();
  }

  public List<String> getGameNames() {
    return games.stream().map(Dungeon::getName).toList();
  }


  public void createGame(String name) {
    throw new RuntimeException("Not implemented yet");
  }
}
