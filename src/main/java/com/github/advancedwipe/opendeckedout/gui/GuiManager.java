package com.github.advancedwipe.opendeckedout.gui;

import com.github.advancedwipe.opendeckedout.OpenDeckedOut;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class GuiManager {

  private final OpenDeckedOut plugin;
  public Gui gui = Gui.gui().title(Component.text("GUI Title!")).create();
  GuiItem guiItem = ItemBuilder.from(Material.STONE).asGuiItem();

  public GuiManager(OpenDeckedOut plugin) {
    this.plugin = plugin;
    gui.setItem(0, guiItem);
  }

  public void open(Player player) {
    gui.open(player);
  }
}
