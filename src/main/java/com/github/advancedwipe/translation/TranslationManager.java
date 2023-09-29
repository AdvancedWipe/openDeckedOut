package com.github.advancedwipe.translation;

import com.github.advancedwipe.OpenDeckedOut;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

public class TranslationManager {

  private final OpenDeckedOut plugin;
  private YamlConfiguration translations;

  public TranslationManager(OpenDeckedOut plugin) {
    this.plugin = plugin;
    loadTranslations();
  }

  public String getTranslationValue(@NotNull Player player, @NotNull String path) {
    Locale playersLocal = player.locale();

    // TODO maybe convert different english locals to one we support

    return (String) translations.get(playersLocal.toLanguageTag().toLowerCase() + "." + path);
  }

  private void loadTranslations() {
    File translationsFile = new File(plugin.getDataFolder(), "/translations.yml");
    System.out.println(translationsFile);

    if (!translationsFile.exists()) {
      InputStream inputStream = this.plugin.getResource("translations.yml");

      if (inputStream == null) {
        OpenDeckedOut.LOGGER.log(Level.WARN,
            "Could not read bundled translations.yml, please report an issue!");
        return;
      }
      try {
        FileUtils.copyInputStreamToFile(inputStream, translationsFile);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    translations = YamlConfiguration.loadConfiguration(translationsFile);
  }
}
