package com.shepherdjerred.minecraft.inventory.event;

import static org.bukkit.event.EventPriority.MONITOR;

import com.shepherdjerred.minecraft.inventory.ItemMatcher;
import com.shepherdjerred.minecraft.inventory.ItemReplacer;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

@Log4j2
@AllArgsConstructor
public class BlockEventHandler implements Listener {
  private final ItemReplacer itemReplacer;
  private final ItemMatcher itemMatcher;

  @EventHandler(priority = MONITOR, ignoreCancelled = true)
  public void onBlockPlace(BlockPlaceEvent event) {
    var inventory = event.getPlayer().getInventory();
    var hand = event.getHand();
    var heldItem = inventory.getItem(hand);

    if (event.getBlockPlaced().getBlockData().getMaterial() != heldItem.getType()) {
      return;
    }

    var willHandBeEmpty = event.getItemInHand().getAmount() == 1;
    if (willHandBeEmpty && event.canBuild()) {
      var possibleItemMatch = itemMatcher.findMatch(inventory, heldItem);

      if (possibleItemMatch.isPresent()) {
        var itemMatch = possibleItemMatch.get();
        itemReplacer.replace(inventory, hand, itemMatch);
      }
    }
  }
}
