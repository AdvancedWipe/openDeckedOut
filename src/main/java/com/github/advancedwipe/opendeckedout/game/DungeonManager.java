package com.github.advancedwipe.opendeckedout.game;

import com.github.advancedwipe.opendeckedout.OpenDeckedOut;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
        Dungeon game = Dungeon.loadGame(plugin, arenaFile);
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

  public List<String> getGameNames() {
    return games.stream().map(Dungeon::getName).collect(Collectors.toList());
  }



  public void createGame(String name) {
    final Dungeon dungeon = new Dungeon(plugin, name);
  }
}
