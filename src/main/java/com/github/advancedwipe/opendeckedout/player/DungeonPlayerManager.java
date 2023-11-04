package com.github.advancedwipe.opendeckedout.player;

import com.github.advancedwipe.opendeckedout.OpenDeckedOut;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bukkit.entity.Player;

public class DungeonPlayerManager {
  private final OpenDeckedOut plugin;
  private final List<DungeonPlayer> players = new ArrayList<>();

  public DungeonPlayerManager(OpenDeckedOut plugin) {
    this.plugin = plugin;
  }

  public boolean isPlayerInGame(Player player) {
    return getPlayer(player).map(p -> p.getDungeon() != null).orElse(false);
  }



  public Optional<DungeonPlayer> getPlayer(Player player) {
    return players.stream()
        .filter(dungeonPlayer -> dungeonPlayer.getUuid().equals(player.getUniqueId()))
        .findFirst();
  }

  public DungeonPlayer getPlayerOrCreate(Player player) {
    return getPlayer(player).orElseGet(()-> {
      DungeonPlayer dungeonPlayer = new DungeonPlayer(player);
      players.add(dungeonPlayer);
      return dungeonPlayer;
    });
  }
}
