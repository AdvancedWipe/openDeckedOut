package com.github.advancedwipe.game;

import com.github.advancedwipe.OpenDeckedOut;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.Level;
import org.bukkit.configuration.file.YamlConfiguration;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

public class DeckedOutManager {
  private final List<DeckedOut> games;
  private final OpenDeckedOut plugin;

  public DeckedOutManager(final OpenDeckedOut plugin) {
    this.plugin = plugin;
    this.games = new ArrayList<>();
  }

  public void loadGames(File[] arenaFiles) {
      for (File arenaFile : arenaFiles) {
        DeckedOut game = DeckedOut.loadGame(arenaFile);
        if (game != null) {
          this.games.add(game);
        } else {
          OpenDeckedOut.LOGGER.log(Level.WARN, String.format("Unable to load %s", arenaFile.toString()));
        }
      }
  }

  public Optional<DeckedOut> getGame(final String name) {
    return games.stream().filter(game -> game.getName().equals(name)).findFirst();
  }



  public void createGame(String name) {
    final DeckedOut deckedOut = new DeckedOut(name);
  }
}
