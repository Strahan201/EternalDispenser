package com.sylvcraft.events;

import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import com.sylvcraft.EternalDispenser;

public class PlayerInteract implements Listener {
  EternalDispenser plugin;
  
  public PlayerInteract(EternalDispenser instance) {
    plugin = instance;
  }

	@EventHandler
  public void onPlayerInteract(PlayerInteractEvent e) {
		if (!plugin.selecting.contains(e.getPlayer().getUniqueId())) return;
		if (e.getAction() != Action.LEFT_CLICK_BLOCK) return;
		if (e.getHand() != EquipmentSlot.HAND) return;
		if (e.getClickedBlock().getType() != Material.DISPENSER && e.getClickedBlock().getType() != Material.DROPPER) {
			plugin.msg("selecting-aborted", e.getPlayer());
			return;
		}

		String key = plugin.infiniteDispenser(e.getClickedBlock());
		String blockType = (e.getClickedBlock().getType() == Material.DISPENSER)?"dispenser":"dropper";
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("%playername%", e.getPlayer().getName());
		data.put("%playeruuid%", e.getPlayer().getUniqueId().toString());
		data.put("%blocktype%", blockType);
		
		if (key.equals("")) {
			int nextID = (plugin.getConfig().getConfigurationSection("dispensers") == null)?1:plugin.getConfig().getConfigurationSection("dispensers").getKeys(false).size() + 1;
			plugin.getConfig().set("dispensers." + nextID + ".location", e.getClickedBlock().getLocation());
			plugin.msg("infinite-on", e.getPlayer(), data);
		} else {
			plugin.getConfig().set("dispensers." + key, null);
			plugin.msg("infinite-off", e.getPlayer(), data);
		}
		plugin.saveConfig();
		plugin.selecting.remove(e.getPlayer().getUniqueId());
  }
}
