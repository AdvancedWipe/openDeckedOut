package com.github.advancedwipe;

import com.github.advancedwipe.commands.Commands;
import com.github.advancedwipe.game.DungeonManager;
import com.github.advancedwipe.listener.PlayerListener;
import com.github.advancedwipe.player.DungeonPlayerManager;
import com.github.advancedwipe.translation.TranslationManager;
import java.io.File;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class OpenDeckedOut extends JavaPlugin implements Listener {

  private static OpenDeckedOut instance;
  public static final Logger LOGGER = LogManager.getLogger(OpenDeckedOut.class.getSimpleName());
  private DungeonManager dungeonManager;
  private DungeonPlayerManager playerManager;
  private TranslationManager translationManager;
  private File arenasFolder;
  FileConfiguration config = null;

  @Override
  public void onEnable() {
    registerEvents();

    instance = this;
    dungeonManager = new DungeonManager(this);
    playerManager = new DungeonPlayerManager(this);
    translationManager = new TranslationManager(this);

    if (!loadConfig()) {
      LOGGER.log(Level.WARN, "Could not load config file! Disabling plugin.");
      this.getServer().getPluginManager().disablePlugin(this);
      return;
    }

    arenasFolder = new File(getDataFolder(), "arenas");
    if (arenasFolder.exists()) {
      File[] arenaFiles = arenasFolder.listFiles((dir, name) -> name.endsWith(".yml"));
      if (arenaFiles != null) {
        dungeonManager.loadGames(arenaFiles);
      }
    }

    new Commands(this).register();
  }


  private void registerEvents() {
    Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
  }

  public boolean loadConfig() {
    LOGGER.log(Level.INFO, "Loading configuration...");
    File configFile = new File(getDataFolder(), "config.yml");

    if (!configFile.exists()) {
      saveDefaultConfig();
    }
    reloadConfig();
    this.config = getConfig();
    return true;
  }

  public static OpenDeckedOut getInstance() {
    return instance;
  }

  public DungeonManager getDeckedOutManager() {
    return dungeonManager;
  }

  public DungeonPlayerManager getPlayerManager() {
    return playerManager;
  }

  public TranslationManager getTranslationManager() {
    return translationManager;
  }
}
