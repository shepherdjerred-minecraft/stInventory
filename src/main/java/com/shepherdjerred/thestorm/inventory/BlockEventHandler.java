package com.shepherdjerred.thestorm.inventory;

import lombok.extern.log4j.Log4j2;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

@Log4j2
public class BlockEventHandler implements Listener {

  @EventHandler
  public void onBlockPlace(BlockPlaceEvent event) {
    var willHandBeEmpty = event.getItemInHand().getAmount() == 1;
    if (willHandBeEmpty && !event.isCancelled() && event.canBuild()) {
      var matcher = new Matcher();
      var inventory = event.getPlayer().getInventory();
      var hand = event.getHand();
      var item = inventory.getItem(hand);
      var possibleItemMatch = matcher.findMatch(inventory, item);

      if (possibleItemMatch.isPresent()) {
        var itemMatch = possibleItemMatch.get();
        // We overwrite the original item stack, so we add one which will be removed when the block is actually placed.
        itemMatch.setAmount(item.getAmount() + 1);
        inventory.setItem(hand, itemMatch);
        log.info("Replaced item");
        event.getPlayer().sendMessage("Replaced item");
      } else {
        log.info("Could not replace item");
        event.getPlayer().sendMessage("Could not replace item");
      }
    }
  }
}
