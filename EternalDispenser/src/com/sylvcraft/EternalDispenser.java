package com.sylvcraft;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import com.sylvcraft.events.BlockDispense;
import com.sylvcraft.events.PlayerInteract;
import com.sylvcraft.events.PlayerQuit;
import com.sylvcraft.commands.etdisp;


public class EternalDispenser extends JavaPlugin {
  public List<UUID> selecting = new ArrayList<UUID>();

  @Override
  public void onEnable() {
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvents(new BlockDispense(this), this);
    pm.registerEvents(new PlayerInteract(this), this);
    pm.registerEvents(new PlayerQuit(this), this);

    getCommand("etdisp").setExecutor(new etdisp(this));
    saveDefaultConfig();
  }
	
	public String infiniteDispenser(Block dispenser) {
		if (getConfig().getConfigurationSection("dispensers") == null) return "";
		for (String key : getConfig().getConfigurationSection("dispensers").getKeys(false)) {
			if (dispenser.getLocation().equals((Location)getConfig().get("dispensers." + key + ".location"))) return key;
		}
		return "";
	}
  
  public void msg(String msgCode, Player p) {
  	String tmp = getConfig().getString("messages." + msgCode, msgCode);
  	if (tmp.equals("")) return;
  	for (String m : tmp.split("%br%")) {
  		if (p == null) {
  			getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', m));
  		} else {
  			p.sendMessage(ChatColor.translateAlternateColorCodes('&', m));
  		}
  	}
  }

  public void msg(String msgCode, Player p, HashMap<String, String> data) {
  	String tmp = getConfig().getString("messages." + msgCode, msgCode);
  	if (tmp.equals("")) return;
  	Iterator<Map.Entry<String, String>> it = data.entrySet().iterator();
  	while (it.hasNext()) {
  	  Map.Entry<String, String> mapData = (Map.Entry<String, String>)it.next();
  	  tmp = tmp.replaceAll(mapData.getKey(), mapData.getValue());
  	}
  	msg(tmp, p);
  }
}