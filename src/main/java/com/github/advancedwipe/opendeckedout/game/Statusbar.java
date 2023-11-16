package com.github.advancedwipe.opendeckedout.game;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class Statusbar {

  private final char statusChar;
  private final int size;
  private int status;
  private final NamedTextColor keyColor;
  private final NamedTextColor disabledColor;
  private final Component keyComponent;
  private Component message;


  public Statusbar(String key, char statusIcon, int size, int status,
      NamedTextColor keyColor, NamedTextColor disabledColor) {
    this.statusChar = statusIcon;
    this.size = size;
    this.status = status;
    this.keyColor = keyColor;
    this.disabledColor = disabledColor;

    this.keyComponent = Component.text(String.format("%s ", key)).color(keyColor);
    update();
  }

  public void setStatus(int status) {
    if (this.status != status) {
      this.status = status;
      update();
    }
  }

  public int getStatus() {
    return status;
  }

  private void update() {
    message = keyComponent;

    for (int i = 0; i < size; i++) {
      if (i < status) {
        message = message.append(Component.text(statusChar).color(keyColor));
      } else {
        message = message.append(Component.text(statusChar).color(disabledColor));
      }
    }
  }

  public Component getMessage() {
    return message;
  }


}
