package com.shepherdjerred.minecraft.inventory;

import com.shepherdjerred.minecraft.inventory.event.BlockEventHandler;
import java.util.HashSet;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings(value = "unused")
public class Main extends JavaPlugin {

  @Override
  public void onEnable() {
    var itemRemover = new ItemRemover();
    var itemReplacer = new ItemReplacer(itemRemover);
    var itemMatcher = new ItemMatcher();
    var itemFinder = new ItemFinder();

    var eventHandlers = new HashSet<Listener>();
    eventHandlers.add(new BlockEventHandler(itemReplacer, itemMatcher));
    //    eventHandlers.add(new PlayerInteractEntityEventHandler(itemReplacer, itemMatcher));
    //    eventHandlers.add(new PlayerItemBreakEventHandler(itemReplacer, itemMatcher, itemFinder));

    eventHandlers.forEach(handler -> getServer().getPluginManager().registerEvents(handler, this));
  }
}
