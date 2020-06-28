package com.shepherdjerred.minecraft.inventory;

import java.util.Optional;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemFinder {

  /**
   * Finds the index of an {@link ItemStack} in an {@link Inventory}
   *
   * @param inventory The {@link Inventory} to search
   * @param itemStack The {@link ItemStack} to search for
   * @return An {@link Optional<Integer>}containing the index of the {@link ItemStack} if it was found in the
   * {@link Inventory}, or an empty {@link Optional<Integer>}if the {@link ItemStack} could not be found.
   */
  public Optional<Integer> findItem(Inventory inventory, ItemStack itemStack) {
    for (int index = 0; index < inventory.getSize(); index++) {
      var currentItem = inventory.getItem(index);
      if (itemStack.isSimilar(currentItem)) {
        return Optional.of(index);
      }
    }
    return Optional.empty();
  }
}
