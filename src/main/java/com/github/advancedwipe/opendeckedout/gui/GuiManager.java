package com.github.advancedwipe.opendeckedout.gui;

import com.github.advancedwipe.opendeckedout.OpenDeckedOut;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.GuiType;
import dev.triumphteam.gui.components.ScrollType;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.ScrollingGui;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class GuiManager {

  private final OpenDeckedOut plugin;
  public Gui deck = Gui.gui().type(GuiType.CHEST).title(Component.text("Deck")).rows(3)
      .disableAllInteractions().create();

  public ScrollingGui cardBrowser = Gui.scrolling().title(Component.text("All available Cards")).rows(6).pageSize(45).scrollType(
      ScrollType.HORIZONTAL).disableItemTake().create();

  GuiItem guiItem = ItemBuilder.from(Material.STONE).asGuiItem();

  public GuiManager(OpenDeckedOut plugin) {
    this.plugin = plugin;
    deck.setItem(0, guiItem);
    cardBrowser.setItem(6, 3, ItemBuilder.from(Material.PAPER).name(Component.text("Previous")).asGuiItem(event -> cardBrowser.previous()));
    cardBrowser.setItem(6, 7, ItemBuilder.from(Material.PAPER).name(Component.text("Next")).asGuiItem(event -> cardBrowser.next()));


  }

  public void open(Player player) {
    //deck.open(player);
    //cardBrowser.open(player);
  }
}
