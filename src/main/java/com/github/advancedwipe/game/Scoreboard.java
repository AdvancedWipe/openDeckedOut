package com.github.advancedwipe.game;


import com.github.advancedwipe.OpenDeckedOut;
import com.github.advancedwipe.player.DungeonPlayer;
import java.util.ArrayList;
import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.megavex.scoreboardlibrary.api.sidebar.Sidebar;
import net.megavex.scoreboardlibrary.api.sidebar.component.ComponentSidebarLayout;
import net.megavex.scoreboardlibrary.api.sidebar.component.SidebarComponent;
import net.megavex.scoreboardlibrary.api.sidebar.component.animation.CollectionSidebarAnimation;
import net.megavex.scoreboardlibrary.api.sidebar.component.animation.SidebarAnimation;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Scoreboard {

  private final int maxTreasureDrops;
  private int treasureDrops = 0;

  private Sidebar sidebar;
  private ComponentSidebarLayout componentSidebar;
  private SidebarAnimation<Component> titleAnimation;
  private StringBuilder coinFields = new StringBuilder();
  private Component treasureMessage;
  private Component stateTwoMessage;
  private Component stateThreeMessage;
  private Component stateFourMessage;
  private Component libraryMessageOne;
  private Component libraryMessageTwo;


  public Scoreboard() {
    this.maxTreasureDrops = 10;
    createSidebar();
  }

  public void addPlayer(@NotNull DungeonPlayer dungeonPlayer) {
    addPlayer(dungeonPlayer.getPlayer());
  }

  public void addPlayer(@NotNull Player player) {

    sidebar.addPlayer(player);
  }

  private void createSidebar() {
    sidebar = OpenDeckedOut.getInstance().getScoreboardLibrary().createSidebar();

    titleAnimation = createGradientAnimation(
        Component.text("     openDO     ", Style.style(TextDecoration.BOLD)));
    var title = SidebarComponent.animatedLine(titleAnimation);

    updateMessages();

    SidebarComponent lines = SidebarComponent.builder()
        .addBlankLine()
        .addDynamicLine(() -> treasureMessage)
        .addBlankLine()
        .addDynamicLine(() -> stateTwoMessage)
        .addBlankLine()
        .addDynamicLine(() -> stateThreeMessage)
        .addBlankLine()
        .addDynamicLine(() -> stateFourMessage)
        .addBlankLine()
        .addDynamicLine(() -> libraryMessageOne)
        .addDynamicLine(() -> libraryMessageTwo)
        .build();

    this.componentSidebar = new ComponentSidebarLayout(title, lines);
  }

  public void update() {
    updateMessages();

    titleAnimation.nextFrame();
    componentSidebar.apply(sidebar);
  }

  private void updateMessages() {
    treasureMessage = buildStatusbar("Treasure:", "■", 15, treasureDrops, NamedTextColor.YELLOW, NamedTextColor.GRAY);
    stateTwoMessage = buildStatusbar("State2:", "■", 15, 0, NamedTextColor.AQUA, NamedTextColor.GRAY);
    stateThreeMessage = buildStatusbar("State3:", "■", 15, 0, NamedTextColor.RED, NamedTextColor.GRAY);
    stateFourMessage = buildStatusbar("State4:", "■", 15, 0, NamedTextColor.DARK_AQUA, NamedTextColor.GRAY);
    libraryMessageOne = buildStatusbar("Library:", "▌", 20, 0, NamedTextColor.WHITE, NamedTextColor.GRAY);
    libraryMessageTwo = buildStatusbar("          ", "▌", 20, 0, NamedTextColor.WHITE, NamedTextColor.GRAY);
  }

  private Component buildStatusbar(String key, String statusIcon, int size, int status,
      NamedTextColor keyColor, NamedTextColor disabledColor) {
    Component statusbar = Component.text(String.format("%s ", key)).color(keyColor);

    for (int i = 0; i < size; i++) {
      if (i < status) {
        statusbar = statusbar.append(Component.text(statusIcon).color(keyColor));
      } else {
        statusbar = statusbar.append(Component.text(statusIcon).color(disabledColor));
      }
    }

    return statusbar;
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

  private @NotNull SidebarAnimation<Component> createGradientAnimation(@NotNull Component text) {
    float step = 1f / 8f;

    List<Component> frames = new ArrayList<>((int) (2f / step));

    float phase = -1f;
    while (phase < 1) {
      frames.add(MiniMessage.miniMessage().deserialize("<gradient:yellow:gold:" + phase + "><text>",
          Placeholder.component("text", text)));
      phase += step;
    }

    return new CollectionSidebarAnimation<>(frames);
  }

  public int getTreasureDrops() {
    return treasureDrops;
  }

  public void setTreasureDrops(int value) {
    this.treasureDrops = value;
  }

  public void decreaseTreasureDrops() {
    this.treasureDrops--;
  }

}
