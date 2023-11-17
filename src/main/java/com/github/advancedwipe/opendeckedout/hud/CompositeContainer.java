package com.github.advancedwipe.opendeckedout.hud;

import java.util.ArrayList;
import java.util.List;

public class CompositeContainer {

  private List<Component> components;

  public CompositeContainer() {
    components = new ArrayList<>();
  }

  public void update() {
    for (Component component : components) {
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
