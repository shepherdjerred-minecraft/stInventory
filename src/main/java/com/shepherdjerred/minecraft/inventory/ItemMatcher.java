package com.shepherdjerred.minecraft.inventory;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemMatcher {

  /**
   * Find a match of an item stack in an inventory. Prefers an exact match and falls back on a loose
   * match.
   */
  public Optional<ItemStack> findMatch(Inventory inventory, ItemStack itemStack) {
    if (itemStack.getData() == null) {
      return Optional.empty();
    }
    var matches = findBestMatchesSortedBySize(inventory, itemStack);
    if (matches.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(matches.get(0));
    }
  }

  private List<ItemStack> findBestMatchesSortedBySize(Inventory inventory, ItemStack itemStack) {
    var bestMatches = findBestMatches(inventory, itemStack);
    return bestMatches.stream().sorted(Comparator.comparingInt(ItemStack::getAmount).reversed()).collect(Collectors.toUnmodifiableList());
  }

  private List<ItemStack> findBestMatches(Inventory inventory, ItemStack itemStack) {
    var exactMatches = findExactMatches(inventory, itemStack);
    if (exactMatches.isEmpty()) {
      return findLooseMatches(inventory, itemStack);
    } else {
      return exactMatches;
    }
  }

  /**
   * Find a strict match ignoring stack size. An exact match tries to find an identical item
   * ignoring stack size. This takes into account this like enchantments and item metadata.
   */
  private List<ItemStack> findExactMatches(Inventory inventory, ItemStack itemStack) {
    var matches = Arrays
      .stream(inventory.getContents())
      .filter(Objects::nonNull)
      .filter(inventoryItemStack -> inventoryItemStack.getData() != null)
      .filter(inventoryItemStack -> inventoryItemStack.isSimilar(itemStack))
      .collect(Collectors.toList());

    /*
     * TODO: refactor. this is a little surprising since the method removes the item that it is matching with.
     */
    // An item stack should match with itself, so remove it.
    matches.remove(itemStack);

    return Collections.unmodifiableList(matches);
  }

  /**
   * Find a loose match ignoring stack size. A loose match just tries to find an item based on
   * material type. This ignores things likes enchantments or item metadata.
   */
  private List<ItemStack> findLooseMatches(Inventory inventory, ItemStack itemStack) {
    var matches = Arrays
      .stream(inventory.getContents())
      .filter(Objects::nonNull)
      .filter(inventoryItemStack -> inventoryItemStack.getData() != null)
      .filter(inventoryItemStack -> inventoryItemStack.getData().getItemType() == itemStack.getData().getItemType())
      .collect(Collectors.toList());

    // An item stack should always match with itself, so remove it.
    matches.remove(itemStack);

    return Collections.unmodifiableList(matches);
  }
}
