package com.shepherdjerred.thestorm.inventory;

import java.util.Optional;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockEventHandler implements Listener {

  @EventHandler
  public void onBlockPlace(BlockPlaceEvent event) {
    var handIndex = getHeldItemSlot(event.getPlayer());
    var willBeEmpty = event.getItemInHand().getAmount() == 1;
    if (willBeEmpty && !event.isCancelled()) {
      var replacementItemIndexOptional = getReplacementItemSlot(event.getPlayer(), handIndex);
      if (replacementItemIndexOptional.isEmpty()) {
        event.getPlayer().sendMessage("Can't refill inventory");
      } else {
        var replacementItemIndex = replacementItemIndexOptional.get();
        var replacementItem = event.getPlayer().getInventory().getItem(replacementItemIndex);
        event.getPlayer().getInventory().setItem(handIndex, replacementItem);
        event.getPlayer().sendMessage("Refilled inventory");
      }
    }
  }

  private int getHeldItemSlot(Player player) {
    return player.getInventory().getHeldItemSlot();
  }

  private Optional<Integer> getReplacementItemSlot(Player player, int slot) {
    var itemInHand = player.getInventory().getItem(slot).getData().getItemType();
    for (int iSlot = 0; iSlot < player.getInventory().getSize(); iSlot++) {
      var item = player.getInventory().getItem(iSlot);
      if (item != null) {
        var itemType = item.getType();
        if (itemInHand == itemType) {
          return Optional.of(iSlot);
        }
      }
    }
    return Optional.empty();
  }
}
