package com.shepherdjerred.minecraft.inventory.event;

import static org.bukkit.event.EventPriority.MONITOR;

import com.shepherdjerred.minecraft.inventory.ItemFinder;
import com.shepherdjerred.minecraft.inventory.ItemMatcher;
import com.shepherdjerred.minecraft.inventory.ItemReplacer;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemBreakEvent;

@Log4j2
@AllArgsConstructor
public class PlayerItemBreakEventHandler implements Listener {
  private final ItemReplacer itemReplacer;
  private final ItemMatcher itemMatcher;
  private final ItemFinder itemFinder;

  @EventHandler(priority = MONITOR, ignoreCancelled = true)
  public void onItemBreak(PlayerItemBreakEvent event) {
    var inventory = event.getPlayer().getInventory();
    var brokenItem = event.getBrokenItem();
    var brokenItemSlot = itemFinder.findItem(inventory, brokenItem);

    var possibleItemMatch = itemMatcher.findMatch(inventory, brokenItem);

    if (brokenItemSlot.isEmpty()) {
      log.warn("Unable to find broken item {}", brokenItem);
    }

    if (possibleItemMatch.isPresent()) {
      var itemMatch = possibleItemMatch.get();
      itemReplacer.replace(inventory, brokenItemSlot.get(), itemMatch);
    }
  }
}
