package com.shepherdjerred.minecraft.inventory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import lombok.extern.log4j.Log4j2;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@Log4j2
public class ItemRemover {

  public void removeItem(PlayerInventory inventory, ItemStack itemStack) {
    removeItem(inventory, itemStack, Collections.emptySet());
  }

  public void removeItem(PlayerInventory inventory, ItemStack itemStack, EquipmentSlot equipmentSlotToIgnore) {
    Set<Integer> ignoreList = new HashSet<>();
    switch (equipmentSlotToIgnore) {
      case HAND:
        ignoreList.add(inventory.getHeldItemSlot());
        break;
      case OFF_HAND:
        ignoreList.add(40);
        break;
      default:
        throw new RuntimeException(String.format("Unknown EquipmentSlot: %s", equipmentSlotToIgnore));
    }
    removeItem(inventory, itemStack, ignoreList);
  }

  public void removeItem(Inventory inventory, ItemStack itemStack, Set<Integer> slotIndexesToIgnore) {
    log.info("Ignored slots: {}", slotIndexesToIgnore);
    for (int index = 0; index < inventory.getSize(); index++) {
      if (!slotIndexesToIgnore.contains(index)) {
        if (itemStack.equals(inventory.getItem(index))) {
          log.info("Removing {} from {}", itemStack, index);
          inventory.setItem(index, null);
          return;
        } else {
          log.info("{} did not match {}", inventory.getItem(index), itemStack);
        }
      }
    }
    throw new RuntimeException(String.format("Unable to remove item %s", itemStack));
  }
}
