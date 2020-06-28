package com.shepherdjerred.minecraft.inventory;

import com.google.common.collect.ImmutableSet;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@Log4j2
@AllArgsConstructor
public class ItemReplacer {
  private final ItemRemover itemRemover;

  public void replace(PlayerInventory inventory, EquipmentSlot equipmentSlot, ItemStack itemStack) {
    itemRemover.removeItem(inventory, itemStack, equipmentSlot);
    inventory.setItem(equipmentSlot, itemStack);
  }

  public void replace(Inventory inventory, int index, ItemStack itemStack) {
    itemRemover.removeItem(inventory, itemStack, ImmutableSet.of(index));
    inventory.setItem(index, itemStack);
  }
}
