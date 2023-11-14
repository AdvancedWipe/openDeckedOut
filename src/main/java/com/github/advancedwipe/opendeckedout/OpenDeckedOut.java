package com.github.advancedwipe.opendeckedout;

import com.github.advancedwipe.opendeckedout.commands.Commands;
import com.github.advancedwipe.opendeckedout.database.DatabaseManager;
import com.github.advancedwipe.opendeckedout.game.DungeonManager;
import com.github.advancedwipe.opendeckedout.gui.GuiManager;
import com.github.advancedwipe.opendeckedout.listener.PlayerListener;
import com.github.advancedwipe.opendeckedout.player.DungeonPlayerManager;
import com.github.advancedwipe.opendeckedout.player.StatManager;
import com.github.advancedwipe.opendeckedout.translation.TranslationManager;
import java.io.File;
import net.megavex.scoreboardlibrary.api.ScoreboardLibrary;
import net.megavex.scoreboardlibrary.api.exception.NoPacketAdapterAvailableException;
import net.megavex.scoreboardlibrary.api.noop.NoopScoreboardLibrary;
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
  private StatManager statManager;
  private ScoreboardLibrary scoreboardLibrary;
  private GuiManager guiManager;
  private DatabaseManager databaseManager;
  private File arenasFolder;
  FileConfiguration config = null;

  public OpenDeckedOut() {
  }

  public OpenDeckedOut(DungeonManager dungeonManager, DungeonPlayerManager playerManager,
      TranslationManager translationManager, StatManager statManager) {
    if (!isJUnitTest()) {
      throw new IllegalStateException("This constructor should only be used for unit testing.");
    }

    this.dungeonManager = dungeonManager;
    this.playerManager = playerManager;
    this.translationManager = translationManager;
    this.statManager = statManager;
  }

  @Override
  public void onEnable() {
    if (!loadConfig()) {
      LOGGER.log(Level.WARN, "Could not load config file! Disabling plugin.");
      this.getServer().getPluginManager().disablePlugin(this);
      return;
    }

    initializeValues();
    initializeScoreboardLibrary();

    registerEvents();

    arenasFolder = new File(getDataFolder(), "arenas");
    if (arenasFolder.exists()) {
      File[] arenaFiles = arenasFolder.listFiles((dir, name) -> name.endsWith(".yml"));
      if (arenaFiles != null) {
        dungeonManager.loadGames(arenaFiles);
      }
    }

    new Commands(this).register();

    if (!databaseManager.connect()) {
      LOGGER.log(Level.WARN, "Could not connect to database! Disabling plugin.");
      this.getServer().getPluginManager().disablePlugin(this);
    }
    LOGGER.log(Level.INFO, "Database 'openDeckedOut' exists: {}", databaseManager.checkPostgresDatabaseExists("openDeckedOut"));
    LOGGER.log(Level.INFO, "Table 'player' exists: {}", databaseManager.tableExists("player"));
    LOGGER.log(Level.INFO, "Table 'card' exists: {}", databaseManager.tableExists("card"));
  }

  private void registerEvents() {
    Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
  }

  private void initializeValues() {
    instance = this;
    dungeonManager = new DungeonManager(this);
    playerManager = new DungeonPlayerManager(this);
    translationManager = new TranslationManager(this);
    statManager = new StatManager(this);
    guiManager = new GuiManager(this);
    databaseManager = new DatabaseManager(this);
  }

  private void initializeScoreboardLibrary() {
    try {
      scoreboardLibrary = ScoreboardLibrary.loadScoreboardLibrary(this);
    } catch (NoPacketAdapterAvailableException e) {
      // If no packet adapter was found, you can fallback to the no-op implementation:
      scoreboardLibrary = new NoopScoreboardLibrary();
    }
  }


  private boolean loadConfig() {
    LOGGER.log(Level.INFO, "Loading configuration...");
    File configFile = new File(getDataFolder(), "config.yml");

    if (!configFile.exists()) {
      saveDefaultConfig();
    }
    reloadConfig();
    this.config = getConfig();
    return true;
  }

  @Override
  public void onDisable() {
    scoreboardLibrary.close();
    databaseManager.disconnect();
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

  public StatManager getStatManager() {
    return statManager;
  }

  public ScoreboardLibrary getScoreboardLibrary() {
    return scoreboardLibrary;
  }

  public GuiManager getGuiManager() {
    return guiManager;
  }

  private boolean isJUnitTest() {
    String vendor = System.getProperty("java.vendor.url");
    return vendor != null && vendor.contains("junit");
  }
}
