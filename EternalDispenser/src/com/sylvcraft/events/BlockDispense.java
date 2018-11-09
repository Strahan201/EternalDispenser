package com.sylvcraft.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import com.sylvcraft.EternalDispenser;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.InventoryHolder;


public class BlockDispense implements Listener {
  EternalDispenser plugin;
  
  public BlockDispense(EternalDispenser instance) {
    plugin = instance;
  }

	@EventHandler
  public void onBlockDispense(BlockDispenseEvent e) {
		if (e.getBlock().getType() != Material.DISPENSER && e.getBlock().getType() != Material.DROPPER) return;
		if (plugin.infiniteDispenser(e.getBlock()).equals("")) return;
		
    InventoryHolder d = (InventoryHolder)e.getBlock().getState();
    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
    	@Override
    	public void run() {
    		d.getInventory().addItem(e.getItem());
    	}
    }, 10L);
  }
}