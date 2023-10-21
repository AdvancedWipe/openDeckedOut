package com.github.advancedwipe.game;


import com.github.advancedwipe.OpenDeckedOut;
import com.github.advancedwipe.player.DungeonPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.megavex.scoreboardlibrary.api.sidebar.Sidebar;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Scoreboard {

  private final int maxTreasureDrops;
  private int treasureDrops = 0;

  private Sidebar sidebar;

  public Scoreboard() {
    this.maxTreasureDrops = 10;

//    bj.getScore(" ").setScore(15);
//    obj.getScore(NamedTextColor.RED + "State 1: ██████████" + NamedTextColor.RED).setScore(14);
//    obj.getScore("  ").setScore(13);
//    obj.getScore("State 2: ██████████").setScore(12);
//    obj.getScore("   ").setScore(11);
//    obj.getScore("State 3: ██████████").setScore(10);
//    obj.getScore("    ").setScore(9);
//    obj.getScore("State 4: ██████████").setScore(8);
//    obj.getScore("     ").setScore(7);
//    obj.getScore("Cards:   ██████████").setScore(6);
//    obj.getScore("           ██████████").setScore(5);
  }

  public void addPlayer(@NotNull DungeonPlayer dungeonPlayer) {
    addPlayer(dungeonPlayer.getPlayer());
  }

  public void addPlayer(@NotNull Player player) {
    if (sidebar == null) {
      createSidebar();
    }

    sidebar.addPlayer(player);
  }

  private void createSidebar() {
    sidebar = OpenDeckedOut.getInstance().getScoreboardLibrary().createSidebar();

    sidebar.title(Component.text("Sidebar Title"));
    sidebar.line(0, Component.empty());
    sidebar.line(1, Component.text("Line 1"));
    sidebar.line(2, Component.text("Line 2"));
    sidebar.line(2, Component.empty());
    sidebar.line(3, Component.text("epicserver.net"));
  }

  public void removePlayer(DungeonPlayer player) {
    removePlayer(player.getPlayer());
  }

  public void removePlayer(Player player) {
    sidebar.removePlayer(player);

    if (sidebar.players().isEmpty()) {
      sidebar.close();
      sidebar = null;
    }
  }


  public String getTreasureDrops() {
    return null;
  }

  public void increaseTreasureDrops(int value) {
    this.treasureDrops = treasureDrops + value;
  }

}
