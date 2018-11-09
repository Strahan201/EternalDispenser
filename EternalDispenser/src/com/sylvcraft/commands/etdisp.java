package com.sylvcraft.commands;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.sylvcraft.EternalDispenser;

public class etdisp implements CommandExecutor {
  EternalDispenser plugin;
  
  public etdisp(EternalDispenser instance) {
    plugin = instance;
  }

	@Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    try {
      if (!(sender instanceof Player)) {
        plugin.msg("player-only", null);
        return true;
      }
      Player p = (Player)sender;

      if (!p.hasPermission("etdisp.admin")) {
      	plugin.msg("access-denied", p);
      	return true;
      }
      if (args.length == 0) {
        plugin.msg("help", p);
        return true;
      }

      HashMap<String, String> data = new HashMap<String, String>();
      data.put("%playername%", p.getName());
      data.put("%playeruuid%", p.getUniqueId().toString());
      switch (args[0].toLowerCase()) {
      case "tog":
      	if (plugin.selecting.contains(p.getUniqueId())) {
      		plugin.msg("already-selecting", p, data);
      		return true;
      	}
      	plugin.selecting.add(p.getUniqueId());
      	plugin.msg("selecting", p, data);
        break;
      case "reload":
      	plugin.reloadConfig();
      	plugin.msg("reloaded", p);
      	break;
      case "list":
      	if (plugin.getConfig().getConfigurationSection("dispensers") == null) {
      		plugin.msg("list-none", p, data);
      		return true;
      	}

      	plugin.msg("list-header", p, data);
      	for (String key : plugin.getConfig().getConfigurationSection("dispensers").getKeys(false)) {
      		Location tmp = (Location)plugin.getConfig().get("dispensers." + key + ".location");
      		if (tmp == null) { plugin.getLogger().warning("** Notice: EternalDispensers dispenser #" + key + " has no location data!"); continue; }
      		data.put("%x%", String.valueOf(tmp.getBlockX()));
      		data.put("%y%", String.valueOf(tmp.getBlockY()));
      		data.put("%z%", String.valueOf(tmp.getBlockZ()));
      		data.put("%world%", tmp.getWorld().getName());
      		data.put("%id%", key);
      		plugin.msg("list-data", p, data);
      	}
    		plugin.msg("list-footer", p);
    		break;
      default:
      	plugin.msg("help", p, data);
      	break;
      }

      return true;
    } catch (Exception ex) {
      return false;
    }
  }
}
