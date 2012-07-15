package com.storm;


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
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class levelStatsListeners implements Listener {
	
	levelStats plugin;
	public levelStatsListeners(levelStats instance){
		this.plugin=instance;
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
						//recuperar HP
						plugin.registerPlayer(p);
						
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
	
	
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onEDamageByE(EntityDamageByEntityEvent event) {
		//if (event.getEntity() instanceof Player) {
		/*	Player Damagee = (Player) event.getEntity();
			String damager = event.getDamager().toString();
			Damagee.
			Bukkit.broadcastMessage("entity "+ Damagee);
			Bukkit.broadcastMessage("damager "+ damager);*/

		if (event.getDamager() instanceof Player){
			Player p = (Player) event.getDamager();
			//int dmg = event.getDamage();
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
	
	
	
	@EventHandler(priority=EventPriority.NORMAL)
	public void onEDamaged(EntityDamageEvent event) {
		
		if (event.getEntity() instanceof Player){
			DamageCause cause=event.getCause();
			if(cause!=DamageCause.LAVA && cause!=DamageCause.FIRE){
			Player p = (Player) event.getEntity();
			//p.sendMessage("Au!");
			//formula daño equivalente
			int evdmg = event.getDamage();
			double resHP= event.getDamage();
			double tempop=0;
			event.setDamage(0);
			double CH = plugin.PCH.get(p.getName().toLowerCase());
			double MH = plugin.PMH.get(p.getName().toLowerCase());
			CH=CH-evdmg;
			tempop=MH/20;
			resHP= CH/tempop;
			p.setHealth((int) resHP);
			plugin.PCH.put(p.getName().toLowerCase(), CH);
			p.sendMessage("HP: "+ChatColor.AQUA+CH+ChatColor.GRAY+"/"+ChatColor.DARK_AQUA+MH);
		
		}else{
			Player p = (Player) event.getEntity();
			double HP = p.getHealth();
			double CH = plugin.PCH.get(p.getName().toLowerCase());
			double MH = plugin.PMH.get(p.getName().toLowerCase());
			double tempop=0;
			tempop=MH/20;
			CH=HP*tempop;
			plugin.PCH.put(p.getName().toLowerCase(), CH);
		}
			
			
		}
		
	}
	
	
	

	/*@EventHandler(priority=EventPriority.NORMAL)
	public void onCombust(EntityCombustEvent event){
		Bukkit.broadcastMessage("event");
		
	}*/
	
	
	
	
	
	
	@EventHandler(priority=EventPriority.NORMAL)
	public void onRegainH(EntityRegainHealthEvent event){
		Player p = (Player) event.getEntity();
		int regain = event.getAmount();
		//regen HP
		double CH = plugin.PCH.get(p.getName().toLowerCase());
		double MH = plugin.PMH.get(p.getName().toLowerCase());
		double tempop=0;
		double resHP= 0;
		CH=CH+regain;
		tempop=MH/20;
		resHP= CH/tempop;
		//event.setDamage((int) resdmg);
		p.setHealth((int) resHP);
		plugin.PCH.put(p.getName().toLowerCase(), CH);
		
		
		//Bukkit.broadcastMessage("regain "+ p);
		//event.
		//añadir sistema de max health nuevo
	}
	
	
	
	//parchear en evento de daño
/*	@EventHandler(priority=EventPriority.NORMAL)
	public void onFood(FoodLevelChangeEvent event) {
		String temp = event.getEntityType().getName();
	//	int temp = event.getFoodLevel();

		Bukkit.broadcastMessage("event "+ temp);
		
	}*/

	
	
	
	
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
