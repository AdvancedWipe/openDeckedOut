package com.github.advancedwipe.opendeckedout.hud;


import com.github.advancedwipe.opendeckedout.OpenDeckedOut;
import com.github.advancedwipe.opendeckedout.player.DungeonPlayer;
import java.util.ArrayList;
import java.util.List;
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

public class Scoreboard implements Component {

  private final OpenDeckedOut plugin;
  private final int maxTreasureDrops;
  private int treasureDrops = 0;
  private int librarySize = 0;

  private Sidebar sidebar;
  private ComponentSidebarLayout componentSidebar;
  private SidebarAnimation<net.kyori.adventure.text.Component> titleAnimation;
  private StringBuilder coinFields = new StringBuilder();
  private final Statusbar treasureStatus;
  private final Statusbar stateTwoStatus;
  private final Statusbar stateThreeStatus;
  private final Statusbar stateFourStatus;
  private final MultiRowStatusbar libraryStatus;
  private final Counter counter;
  private net.kyori.adventure.text.Component libraryMessageOne;
  private net.kyori.adventure.text.Component libraryMessageTwo;


  public Scoreboard(OpenDeckedOut plugin) {
    this.plugin = plugin;
    this.maxTreasureDrops = 10;
    this.treasureStatus = new Statusbar("Treasure:", '■', 15, treasureDrops, NamedTextColor.YELLOW,
        NamedTextColor.GRAY);
    this.stateTwoStatus = new Statusbar("State2:", '■', 15, treasureDrops, NamedTextColor.AQUA,
        NamedTextColor.GRAY);
    this.stateThreeStatus = new Statusbar("State3:", '■', 15, treasureDrops, NamedTextColor.RED,
        NamedTextColor.GRAY);
    this.stateFourStatus = new Statusbar("State4:", '■', 15, 0, NamedTextColor.DARK_AQUA,
        NamedTextColor.GRAY);
    this.libraryStatus = new MultiRowStatusbar(2, "Library:", "          ", '▌', 40, 0,
        NamedTextColor.WHITE, NamedTextColor.GRAY);
    this.counter = new Counter("Elapsed time: ", NamedTextColor.GRAY);

    createSidebar();
  }

  public void addPlayer(@NotNull DungeonPlayer dungeonPlayer) {
    addPlayer(dungeonPlayer.getBukkitPlayer());
  }

  public void addPlayer(@NotNull Player player) {
    sidebar.addPlayer(player);
  }

  private void createSidebar() {
    sidebar = plugin.getScoreboardLibrary().createSidebar();

    titleAnimation = createGradientAnimation(
        net.kyori.adventure.text.Component.text("     openDO     ", Style.style(TextDecoration.BOLD)));
    var title = SidebarComponent.animatedLine(titleAnimation);

    SidebarComponent lines = SidebarComponent.builder()
        .addBlankLine()
        .addDynamicLine(treasureStatus::getMessage)
        .addBlankLine()
        .addDynamicLine(stateTwoStatus::getMessage)
        .addBlankLine()
        .addDynamicLine(stateThreeStatus::getMessage)
        .addBlankLine()
        .addDynamicLine(stateFourStatus::getMessage)
        .addBlankLine()
        .addDynamicLine(() -> libraryStatus.getRow(0).getMessage())
        .addDynamicLine(() -> libraryStatus.getRow(1).getMessage())
        .addDynamicLine(counter::getMessage)
        .build();

    this.componentSidebar = new ComponentSidebarLayout(title, lines);
  }

  @Override
  public void update() {
    treasureStatus.setStatus(treasureDrops);
    stateTwoStatus.setStatus(0);
    stateThreeStatus.setStatus(0);
    stateFourStatus.setStatus(0);
    libraryStatus.setStatus(librarySize);
    counter.update();

    titleAnimation.nextFrame();
    componentSidebar.apply(sidebar);
  }

  private net.kyori.adventure.text.Component buildStatusbar(String key, String statusIcon, int size, int status,
      NamedTextColor keyColor, NamedTextColor disabledColor) {
    net.kyori.adventure.text.Component statusbar = net.kyori.adventure.text.Component.text(String.format("%s ", key)).color(keyColor);

    for (int i = 0; i < size; i++) {
      if (i < status) {
        statusbar = statusbar.append(net.kyori.adventure.text.Component.text(statusIcon).color(keyColor));
      } else {
        statusbar = statusbar.append(net.kyori.adventure.text.Component.text(statusIcon).color(disabledColor));
      }
    }

    return statusbar;
  }

  public void removePlayer(DungeonPlayer player) {
    removePlayer(player.getBukkitPlayer());
  }

  public void removePlayer(Player player) {
    sidebar.removePlayer(player);

    if (sidebar.players().isEmpty()) {
      sidebar.close();
      sidebar = null;
    }
  }

  private @NotNull SidebarAnimation<net.kyori.adventure.text.Component> createGradientAnimation(@NotNull net.kyori.adventure.text.Component text) {
    float step = 1f / 50f;

    List<net.kyori.adventure.text.Component> frames = new ArrayList<>((int) (2f / step));

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

  public int getLibrarySize() {
    return 0;
  }

  public void setLibrarySize(int size) {
    this.librarySize = size;
  }

  public void decreaseLibrarySize() {
    this.librarySize--;
  }

}