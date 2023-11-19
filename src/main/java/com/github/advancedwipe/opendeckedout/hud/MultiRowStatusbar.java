package com.github.advancedwipe.opendeckedout.hud;

import java.util.ArrayList;
import java.util.List;
import net.kyori.adventure.text.format.NamedTextColor;

public class MultiRowStatusbar {

  private final int size;
  private int status;
  private final int sizeOfEachRow;
  private final List<Statusbar> rows = new ArrayList<>();

  public MultiRowStatusbar(int numberOfRows, String key, String keyPlaceholder, char statusIcon,
      int size, int status, NamedTextColor keyColor, NamedTextColor disabledColor) {
    this.size = size;
    this.status = status;
    this.sizeOfEachRow = size / numberOfRows;

    for (int i = 0; i < numberOfRows; i++) {
      if (i == 0) {
        this.rows.add(new Statusbar(key, statusIcon, sizeOfEachRow, 0, keyColor,
            NamedTextColor.GRAY));
      } else {
        this.rows.add(new Statusbar(keyPlaceholder, statusIcon, sizeOfEachRow, 0, disabledColor,
            NamedTextColor.GRAY));
      }
    }
    update();
  }

  private void update() {
    int halfStatus = status / 2;
    if (status % 2 == 0) {
      rows.get(0).setStatus(halfStatus);
      rows.get(1).setStatus(halfStatus);
    } else {
      int remaining = status % 2;
      rows.get(0).setStatus(halfStatus + remaining);
      rows.get(1).setStatus(halfStatus);
    }
  }

  public void setStatus(int status) {
    if (this.status != status) {
      this.status = status;
      update();
    }
  }

  public Statusbar getRow(int row) {
    return rows.get(row);
  }

  public int getStatus() {
    return status;
  }

}
