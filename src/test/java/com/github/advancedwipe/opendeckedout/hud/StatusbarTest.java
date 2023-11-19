package com.github.advancedwipe.opendeckedout.hud;

import static org.junit.jupiter.api.Assertions.assertEquals;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.junit.jupiter.api.Test;

class StatusbarTest {

  @Test
  void testSetStatus_WithDifferentStatus_ShouldUpdate() {
    // Arrange
    Statusbar statusbar = new Statusbar("Key", '*', 5, 2, NamedTextColor.RED, NamedTextColor.GRAY);

    // Act
    statusbar.setStatus(3);

    // Assert
    assertEquals(3, statusbar.getStatus());
  }

  @Test
  void testSetStatus_WithSameStatus_ShouldNotUpdate() {
    // Arrange
    Statusbar statusbar = new Statusbar("Key", '*', 5, 2, NamedTextColor.RED, NamedTextColor.GRAY);

    // Act
    statusbar.setStatus(2);

    // Assert
    assertEquals(2, statusbar.getStatus());
  }

  @Test
  void testUpdate_ShouldGenerateCorrectMessage() {
    // Arrange
    Statusbar statusbar = new Statusbar("Key", '*', 5, 3, NamedTextColor.RED, NamedTextColor.GRAY);

    // Act
    Component expectedMessage = Component.text("Key ").color(NamedTextColor.RED)
        .append(Component.text('*').color(NamedTextColor.RED))
        .append(Component.text('*').color(NamedTextColor.RED))
        .append(Component.text('*').color(NamedTextColor.RED))
        .append(Component.text('*').color(NamedTextColor.GRAY))
        .append(Component.text('*').color(NamedTextColor.GRAY));

    // Assert
    assertEquals(expectedMessage, statusbar.getMessage());
  }

}
