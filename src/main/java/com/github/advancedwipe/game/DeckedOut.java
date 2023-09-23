package com.github.advancedwipe.game;

import com.github.advancedwipe.OpenDeckedOut;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.bukkit.Location;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

public class DeckedOut implements Game {

  private File file;
  private final UUID uuid;
  private final String name;
  private Location pos1;
  private Location pos2;

  public DeckedOut(String name) {
    this.name = name;
    this.uuid = java.util.UUID.randomUUID();
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public UUID getUuid() {
    return uuid;
  }

  @Override
  public GameStatus getStatus() {
    return null;
  }

  @Override
  public void start() {

  }

  @Override
  public void stop() {

  }

  @Override
  public Location getPos1() {
    return null;
  }

  @Override
  public void setPos1(Location location) {
    this.pos1 = location;

  }

  @Override
  public Location getPos2() {
    return null;
  }

  @Override
  public void setPos2(Location location) {
    this.pos2 = location;

  }

  @Override
  public int getGameTime() {
    return 0;
  }

  @Override
  public int countConnectedPlayers() {
    return 0;
  }

  public void saveToConfig() {
    File directory = new File(OpenDeckedOut.getInstance().getDataFolder(), "arena");

    if (!directory.exists()) {
      if (!directory.mkdirs()) {
        OpenDeckedOut.LOGGER.warn("Failed to create folder 'arena', can not save arenas to file!");
        return;
      }
    }
    if (file == null) {
      do {
        file = new File(directory, uuid + ".yml");
      } while (file.exists());
    }

    final ConfigurationLoader<? extends CommentedConfigurationNode> loader;
    loader = YamlConfigurationLoader.builder()
        .nodeStyle(NodeStyle.BLOCK)
        .file(file)
        .build();

    var configMap = loader.createNode();
    try {
      setValuesToLoader(configMap);
    } catch (SerializationException exception) {
      exception.printStackTrace();
    }

    try {
      loader.save(configMap);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void setValuesToLoader(ConfigurationNode configMap) throws SerializationException {
    configMap.node("uuid").set(uuid);
    configMap.node("name").set(name);
  }
}
