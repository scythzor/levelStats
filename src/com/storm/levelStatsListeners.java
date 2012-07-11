package com.storm;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class levelStatsListeners implements Listener {
	
	levelStats plugin;
	public levelStatsListeners(levelStats instance){
		plugin=instance;
	}
	@EventHandler 
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		Player p = event.getPlayer();
		plugin.registerPlayer(p);
	}
	
	

}
