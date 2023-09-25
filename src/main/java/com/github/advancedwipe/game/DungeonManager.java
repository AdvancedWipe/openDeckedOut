package com.github.advancedwipe.game;

import com.github.advancedwipe.OpenDeckedOut;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.Level;

public class DungeonManager {
  private final List<Dungeon> games;
  private final OpenDeckedOut plugin;

  public DungeonManager(final OpenDeckedOut plugin) {
    this.plugin = plugin;
    this.games = new ArrayList<>();
  }

  public void loadGames(File[] arenaFiles) {
      for (File arenaFile : arenaFiles) {
        Dungeon game = Dungeon.loadGame(arenaFile);
        if (game != null) {
          this.games.add(game);
        } else {
          OpenDeckedOut.LOGGER.log(Level.WARN, String.format("Unable to load %s", arenaFile.toString()));
        }
      }
  }

  public Optional<Dungeon> getGame(final String name) {
    return games.stream().filter(game -> game.getName().equals(name)).findFirst();
  }



  public void createGame(String name) {
    final Dungeon dungeon = new Dungeon(name);
  }
}
