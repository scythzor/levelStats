package com.storm;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

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
		 Player player = event.getPlayer();
		    LivingEntity playerEntity = (LivingEntity) player;
		    playerEntity.setHealth(20);
		   // p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 500, 1));
		
		   
		   
		  //  playerEntity.
	}
	/*@EventHandler(priority = EventPriority.HIGH)
	public  void onPLayerDeath(EntityDeathEvent event){
		if(event.getEntity() instanceof Player){
			Player player =(Player) event.getEntity();
			
		}
		if (!(event.getEntity().getPlayer() instanceof Player)) {
			return;
		}
		Player p = (Player) event.);
		
		 = event.getEntity();
		int  level = 0;
		double lvltemp = plugin.playerlvl.get(p.getName().toLowerCase());
		if (lvltemp>=1.0){
			lvltemp--;
			
		}
		
		level=(int)lvltemp;
		p.sendMessage("level:"+ level);
		event.setNewLevel(level);
		Bukkit.broadcastMessage("deadadagsdbvaisdba "+p);


		Bukkit.broadcastMessage("deadadagsdbvaisdba ");
	
	}*/

	@EventHandler 
	public void onPlayerLevelChange(PlayerLevelChangeEvent event)
	{
		Player p = event.getPlayer();
		if(event.getNewLevel() > event.getOldLevel()){
			
			plugin.playerlvl.put(p.getName().toLowerCase(), (double) p.getLevel());
			double tempoints = plugin.playerPoints.get(p.getName().toLowerCase());
			tempoints++;
			plugin.playerPoints.put(p.getName().toLowerCase(),tempoints);
			plugin.save(p);
			
		}else if(event.getNewLevel() == 0){
			
			ItemStack[] inv=p.getInventory().getContents();
			int capacidad = p.getInventory().getSize();
			int vacio=0;
			
			for(int i = 0; i < capacidad; i++){
				if(inv[i]==null){
					vacio++;	
				}}
			if(capacidad==vacio){
				//como el evento de muerte no me funciona, se da por sentado que as muerto con este metodo
						int level = event.getOldLevel();
						p.setLevel(level);
						plugin.playerlvl.put(p.getName().toLowerCase(),(double) level);
						plugin.save(p);
						p.sendMessage("Has muerto, pero conservarás tus stats y nivel :) ");
						}else{
							plugin.playerlvl.put(p.getName().toLowerCase(),0.0);
							plugin.playerMH.put(p.getName().toLowerCase(),0.0);
							plugin.playerPoints.put(p.getName().toLowerCase(),0.0);
							plugin.save(p);
							p.sendMessage("Has gastado todo tu nivel, has perdido todos los stats :( ");
							}
												
			
		}else if(event.getNewLevel() < event.getOldLevel()){
			int nlvl = event.getNewLevel();
			int olvl = event.getOldLevel();
			int rlvl = olvl-nlvl;
				//rlvl total de niveles a descontar
			double tpoints = plugin.playerPoints.get(p.getName().toLowerCase());
			
			Double respoints = tpoints-rlvl;
			plugin.playerPoints.put(p.getName().toLowerCase(),respoints);
			plugin.save(p);
			p.sendMessage("Has bajado "+rlvl+" niveles, se te descontaran en puntos pero conservaras tus stats :/ ");
		}	
		
	}
	
	
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onEDamageByE(EntityDamageByEntityEvent event) {
		//if (event.getEntity() instanceof Player) {
		/*	Player Damagee = (Player) event.getEntity();
			String damager = event.getDamager().toString();
			Damagee.
			Bukkit.broadcastMessage("entity "+ Damagee);
			Bukkit.broadcastMessage("damager "+ damager);*/

		if (event.getDamager() instanceof Player){
			Player p = (Player) event.getDamager();
			int dmg = event.getDamage();
			double cRate = plugin.critRate.get(p.getName().toLowerCase());
			double random = ((double) Math.random() * 100);
			
			if (cRate > 0){
				if (random <= cRate){
					int oldmg = event.getDamage();
					int newdmg = oldmg*2;
					event.setDamage(newdmg);
					p.sendMessage(ChatColor.AQUA + "Critical!");
					Location loc = p.getLocation();
					p.playEffect(loc, Effect.BLAZE_SHOOT, 0);
				}
				
			}

		
		}
		
		
		
		/*
		Player p = (Player) event.getDamager();
		String player = p.getName();
		Bukkit.broadcastMessage("name "+ player);*/
		
		//}
		
	}
	
	
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onEDamaged(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player){
			Player p = (Player) event.getEntity();
			p.sendMessage("Au!");
			
		}
		
		
	}
	
	
	/*@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		Player Damagee = (Player) event.getEntity();
		Bukkit.broadcastMessage("entity "+ Damagee);
		
	}*/
	
	
	
	@EventHandler 
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		Player p = event.getPlayer();
		plugin.save(p);
	}
	
	
	
	  
	
	
	
	
	
	
	
	
	
	

}
