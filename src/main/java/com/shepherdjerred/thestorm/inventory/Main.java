package com.shepherdjerred.thestorm.inventory;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

  @Override
  public void onEnable() {
    getServer().getPluginManager().registerEvents(new BlockEventHandler(), this);
  }
}
