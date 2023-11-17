package com.github.advancedwipe.opendeckedout.hud;

import java.util.ArrayList;
import java.util.List;

public class CompositeContainer {

  private List<Element> elements;

  public CompositeContainer() {
    elements = new ArrayList<>();
  }

  public void update() {
    for (Element element : elements) {
      element.update();
    }
  }

  public void addElement(Element element) {
    elements.add(element);
  }

  public void removeElement(Element element) {
    elements.remove(element);
  }

}
