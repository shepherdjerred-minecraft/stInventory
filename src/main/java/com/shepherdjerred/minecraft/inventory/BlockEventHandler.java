package com.shepherdjerred.minecraft.inventory;

import static org.bukkit.event.EventPriority.MONITOR;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

@Log4j2
@AllArgsConstructor
public class BlockEventHandler implements Listener {
  private final ItemRemover itemRemover;
  private final ItemMatcher itemMatcher;

  @EventHandler(priority = MONITOR, ignoreCancelled = true)
  public void onBlockPlace(BlockPlaceEvent event) {
    var inventory = event.getPlayer().getInventory();
    var hand = event.getHand();
    var heldItem = inventory.getItem(hand);

    if (event.getBlockPlaced().getBlockData().getMaterial() != heldItem.getType()) {
      return;
    }

    if (event.isCancelled()) {
      return;
    }

    var willHandBeEmpty = event.getItemInHand().getAmount() == 1;
    if (willHandBeEmpty && event.canBuild()) {
      var possibleItemMatch = itemMatcher.findMatch(inventory, heldItem);

      if (possibleItemMatch.isPresent()) {
        var itemMatch = possibleItemMatch.get();
        log.info("Matched {}", itemMatch);
        itemRemover.removeItem(inventory, itemMatch, hand);
        log.info("Removed item");
        inventory.setItem(hand, itemMatch);
        log.info("Replaced {} with {}", heldItem, itemMatch);
      } else {
        log.info("Could not replace item");
      }
    }
  }
}
