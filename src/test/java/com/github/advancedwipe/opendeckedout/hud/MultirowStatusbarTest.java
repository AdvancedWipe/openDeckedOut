package com.github.advancedwipe.opendeckedout.hud;

import static org.junit.jupiter.api.Assertions.assertEquals;

import net.kyori.adventure.text.format.NamedTextColor;
import org.junit.jupiter.api.Test;

class MultirowStatusbarTest {

  @Test
  void testSetStatus_WithDifferentStatus_ShouldUpdate() {
    MultiRowStatusbar multiRowStatusbar = new MultiRowStatusbar(3, "Key", "Placeholder", '*', 10, 2,
        NamedTextColor.RED, NamedTextColor.GRAY);

    multiRowStatusbar.setStatus(3);

    assertEquals(3, multiRowStatusbar.getStatus());
  }

  @Test
  void testSetStatus_WithSameStatus_ShouldNotUpdate() {
    MultiRowStatusbar multiRowStatusbar = new MultiRowStatusbar(2, "Key", "Placeholder", '*', 10, 2,
        NamedTextColor.RED, NamedTextColor.GRAY);

    multiRowStatusbar.setStatus(2);

    assertEquals(2, multiRowStatusbar.getStatus());
  }

  @Test
  void testUpdate_WithOddStatus_ShouldUpdateRow0() {
    MultiRowStatusbar multiRowStatusbar = new MultiRowStatusbar(2, "Key", "Placeholder", '*', 10, 0,
        NamedTextColor.RED, NamedTextColor.GRAY);

    multiRowStatusbar.setStatus(5);

    assertEquals(3, multiRowStatusbar.getRow(0).getStatus());
  }

  @Test
  void testUpdate_WithEvenStatus_ShouldUpdateRow1() {
    MultiRowStatusbar multiRowStatusbar = new MultiRowStatusbar(2, "Key", "Placeholder", '*', 10, 0,
        NamedTextColor.RED, NamedTextColor.GRAY);

    multiRowStatusbar.setStatus(2);

    assertEquals(1, multiRowStatusbar.getRow(1).getStatus());
  }

  @Test
  void testUpdate_CountUpEvenly() {
    MultiRowStatusbar multiRowStatusbar = new MultiRowStatusbar(2, "Key", "Placeholder", '*', 10, 0,
        NamedTextColor.RED, NamedTextColor.GRAY);

    multiRowStatusbar.setStatus(1);
    assertEquals(1, multiRowStatusbar.getRow(0).getStatus());
    assertEquals(0, multiRowStatusbar.getRow(1).getStatus());

    multiRowStatusbar.setStatus(2);
    assertEquals(1, multiRowStatusbar.getRow(0).getStatus());
    assertEquals(1, multiRowStatusbar.getRow(1).getStatus());

    multiRowStatusbar.setStatus(3);
    assertEquals(2, multiRowStatusbar.getRow(0).getStatus());
    assertEquals(1, multiRowStatusbar.getRow(1).getStatus());

    multiRowStatusbar.setStatus(4);
    assertEquals(2, multiRowStatusbar.getRow(0).getStatus());
    assertEquals(2, multiRowStatusbar.getRow(1).getStatus());
  }

  @Test
  void testUpdate_CountUpUnevenly() {
    MultiRowStatusbar multiRowStatusbar = new MultiRowStatusbar(2, "Key", "Placeholder", '*', 10, 0,
        NamedTextColor.RED, NamedTextColor.GRAY);

    multiRowStatusbar.setStatus(1);
    assertEquals(1, multiRowStatusbar.getRow(0).getStatus());
    assertEquals(0, multiRowStatusbar.getRow(1).getStatus());

    multiRowStatusbar.setStatus(5);
    assertEquals(3, multiRowStatusbar.getRow(0).getStatus());
    assertEquals(2, multiRowStatusbar.getRow(1).getStatus());

    multiRowStatusbar.setStatus(7);
    assertEquals(4, multiRowStatusbar.getRow(0).getStatus());
    assertEquals(3, multiRowStatusbar.getRow(1).getStatus());

    multiRowStatusbar.setStatus(9);
    assertEquals(5, multiRowStatusbar.getRow(0).getStatus());
    assertEquals(4, multiRowStatusbar.getRow(1).getStatus());
  }

  @Test
  void testUpdate_CountDownEvenly() {
    MultiRowStatusbar multiRowStatusbar = new MultiRowStatusbar(2, "Key", "Placeholder", '*', 10, 0,
        NamedTextColor.RED, NamedTextColor.GRAY);

    multiRowStatusbar.setStatus(10);
    assertEquals(5, multiRowStatusbar.getRow(0).getStatus());
    assertEquals(5, multiRowStatusbar.getRow(1).getStatus());

    multiRowStatusbar.setStatus(9);
    assertEquals(5, multiRowStatusbar.getRow(0).getStatus());
    assertEquals(4, multiRowStatusbar.getRow(1).getStatus());

    multiRowStatusbar.setStatus(8);
    assertEquals(4, multiRowStatusbar.getRow(0).getStatus());
    assertEquals(4, multiRowStatusbar.getRow(1).getStatus());
  }

  @Test
  void testUpdate_CountDownUnevenly() {
    MultiRowStatusbar multiRowStatusbar = new MultiRowStatusbar(2, "Key", "Placeholder", '*', 10, 0,
        NamedTextColor.RED, NamedTextColor.GRAY);

    multiRowStatusbar.setStatus(9);
    assertEquals(5, multiRowStatusbar.getRow(0).getStatus());
    assertEquals(4, multiRowStatusbar.getRow(1).getStatus());

    multiRowStatusbar.setStatus(6);
    assertEquals(3, multiRowStatusbar.getRow(0).getStatus());
    assertEquals(3, multiRowStatusbar.getRow(1).getStatus());

    multiRowStatusbar.setStatus(2);
    assertEquals(1, multiRowStatusbar.getRow(0).getStatus());
    assertEquals(1, multiRowStatusbar.getRow(1).getStatus());
  }

  @Test
  void testUpdate_CountUpAndDown() {
    MultiRowStatusbar multiRowStatusbar = new MultiRowStatusbar(2, "Key", "Placeholder", '*', 10, 0,
        NamedTextColor.RED, NamedTextColor.GRAY);

    multiRowStatusbar.setStatus(1);
    assertEquals(1, multiRowStatusbar.getRow(0).getStatus());
    assertEquals(0, multiRowStatusbar.getRow(1).getStatus());

    multiRowStatusbar.setStatus(5);
    assertEquals(3, multiRowStatusbar.getRow(0).getStatus());
    assertEquals(2, multiRowStatusbar.getRow(1).getStatus());

    multiRowStatusbar.setStatus(7);
    assertEquals(4, multiRowStatusbar.getRow(0).getStatus());
    assertEquals(3, multiRowStatusbar.getRow(1).getStatus());

    multiRowStatusbar.setStatus(4);
    assertEquals(2, multiRowStatusbar.getRow(0).getStatus());
    assertEquals(2, multiRowStatusbar.getRow(1).getStatus());

    multiRowStatusbar.setStatus(3);
    assertEquals(2, multiRowStatusbar.getRow(0).getStatus());
    assertEquals(1, multiRowStatusbar.getRow(1).getStatus());

    multiRowStatusbar.setStatus(0);
    assertEquals(0, multiRowStatusbar.getRow(0).getStatus());
    assertEquals(0, multiRowStatusbar.getRow(1).getStatus());
  }

}
