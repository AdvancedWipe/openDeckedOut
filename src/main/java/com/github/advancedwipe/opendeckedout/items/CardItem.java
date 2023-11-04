package com.github.advancedwipe.opendeckedout.items;

import com.github.advancedwipe.opendeckedout.cards.Card;
import java.util.List;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CardItem extends ItemStack {

  private final Card card;

  public CardItem(Card card) {
    super(Material.PAPER);
    this.card = card;
    ItemMeta meta = getItemMeta();
    meta.displayName(Component.text(card.getNamePath()));
    meta.lore(List.of(Component.text(card.getAnecdotePath())));
    setItemMeta(meta);
  }

  public Card getCard() {
    return card;
  }
}
