package com.sylvcraft.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.sylvcraft.EternalDispenser;

public class PlayerQuit implements Listener {
  EternalDispenser plugin;
  
  public PlayerQuit(EternalDispenser instance) {
    plugin = instance;
  }

	@EventHandler
  public void onPlayerQuit(PlayerQuitEvent e) {
		if (plugin.selecting.contains(e.getPlayer().getUniqueId())) plugin.selecting.remove(e.getPlayer().getUniqueId());
  }
}
