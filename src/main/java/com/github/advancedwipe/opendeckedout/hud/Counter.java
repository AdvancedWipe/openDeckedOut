package com.github.advancedwipe.opendeckedout.hud;


import java.util.Date;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class Counter {

  private final Date startTime;
  private final NamedTextColor keyColor;
  private final Component keyComponent;
  private Component message;

  public Counter(String key, NamedTextColor keyColor) {
    this.startTime = new Date();
    this.keyComponent = Component.text(String.format("%s ", key)).color(keyColor);
    this.keyColor = keyColor;
    update();
  }

  public void update() {
    Date currentTime = new Date();
    long elapsedTimeInMilliseconds = currentTime.getTime() - startTime.getTime();
    long elapsedSeconds = elapsedTimeInMilliseconds / 1000;
    long hours = elapsedSeconds / 3600;
    long remainingMinutes = (elapsedSeconds % 3600) / 60;
    long remainingSeconds = elapsedSeconds % 60;
    long milliseconds = elapsedTimeInMilliseconds % 1000;

    message = keyComponent;
    message = message.append(Component.text(formatTime(hours, remainingMinutes, remainingSeconds, milliseconds)).color(keyColor));
  }

  private String formatTime(long hours, long minutes, long seconds, long milliseconds) {
    if (hours > 0) {
      return String.format("%02d:%02d", hours, minutes);
    } else if (minutes > 0) {
      return String.format("%02d:%02d", minutes, seconds);
    } else {
      return String.format("%02d:%03d", seconds, milliseconds);
    }
  }

  public Component getMessage() {
    return message;
  }

}
