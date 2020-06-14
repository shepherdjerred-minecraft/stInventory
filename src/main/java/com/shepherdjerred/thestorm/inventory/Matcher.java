package com.shepherdjerred.thestorm.inventory;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Matcher {

  /**
   * Find a match of an item stack in an inventory
   */
  public Optional<ItemStack> findMatch(Inventory inventory, ItemStack itemStack) {
    if (itemStack.getData() == null) {
      return Optional.empty();
    }
    var exactMatches = findExactMatch(inventory, itemStack);
    if (exactMatches.isPresent()) {
      return exactMatches;
    }
    return findLooseMatch(inventory, itemStack);
  }

  /**
   * Find a strict match ignoring stack size.
   */
  private Optional<ItemStack> findExactMatch(Inventory inventory, ItemStack itemStack) {
    var matches = Arrays
      .stream(inventory.getContents())
      .filter(inventoryItemStack -> inventoryItemStack.getData() != null)
      .filter(inventoryItemStack -> inventoryItemStack.isSimilar(itemStack))
      .sorted(Comparator.comparingInt(ItemStack::getAmount))
      .collect(Collectors.toList());

    // An item stack will should match with itself, so remove it.
    matches.remove(itemStack);

    if (matches.size() > 0) {
      return Optional.of(matches.get(0));
    } else {
      return Optional.empty();
    }
  }

  /**
   * Find a loose match ignoring stack size.
   */
  private Optional<ItemStack> findLooseMatch(Inventory inventory, ItemStack itemStack) {
    var matches = Arrays
      .stream(inventory.getContents())
      .filter(inventoryItemStack -> inventoryItemStack.getData() != null)
      .filter(inventoryItemStack -> inventoryItemStack.getData().getItemType() == itemStack.getData().getItemType())
      .sorted(Comparator.comparingInt(ItemStack::getAmount))
      .collect(Collectors.toList());

    // An item stack should always match with itself, so remove it.
    matches.remove(itemStack);

    if (matches.size() > 0) {
      return Optional.of(matches.get(0));
    } else {
      return Optional.empty();
    }
  }
}
