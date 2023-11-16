package com.github.advancedwipe.opendeckedout.hud;

import java.util.ArrayList;
import java.util.List;

public class Hud {

  private List<Component> components;

  public Hud() {
    components = new ArrayList<>();
  }

  public void update() {
    for (Component component: components) {
      component.update();
    }
  }

  public void addComponent(Component component) {
    components.add(component);
  }

  public void removeComponent(Component component) {
    components.remove(component);
  }

}
