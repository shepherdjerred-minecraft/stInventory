package com.shepherdjerred.minecraft.inventory;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

  @Override
  public void onEnable() {
    getServer().getPluginManager().registerEvents(new BlockEventHandler(new ItemRemover(), new ItemMatcher()), this);
  }
}
