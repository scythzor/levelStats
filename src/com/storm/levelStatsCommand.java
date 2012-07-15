package com.storm;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class levelStatsCommand implements CommandExecutor {
	public levelStatsListeners lst;
	public levelStats sts;

	
	public levelStatsCommand(levelStats pl, levelStatsListeners listener) {
		sts = pl;
		lst = listener;
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player p =(Player) sender;
		if(args[0].equalsIgnoreCase("points"))
		{
		
		double points = sts.playerPoints.get(p.getName().toLowerCase());
		p.sendMessage("Puntos: "+ChatColor.GRAY+ points);
		return true;
		}else if(args[0].equalsIgnoreCase("stats")){
			double MH = sts.playerMH.get(p.getName().toLowerCase());
			double CR = sts.critRate.get(p.getName().toLowerCase());
			p.sendMessage(ChatColor.DARK_RED+"MaxHP: " +ChatColor.GRAY+ MH +ChatColor.AQUA+ " CritRate: "+ChatColor.GRAY+ CR);
			return true;
			
		}else if(args[0].equalsIgnoreCase("buyhealth") && args[1] != null){
			double MH = sts.playerMH.get(p.getName().toLowerCase());
			
			if (MH<=50){
				double CP = sts.playerPoints.get(p.getName().toLowerCase());
				String solicitud = args[1];
				double numero=Double.valueOf(solicitud).doubleValue();
				
				if(numero<=CP){
					CP=CP-numero;
					sts.playerPoints.put(p.getName().toLowerCase(), CP);
					double temp = sts.playerMH.get(p.getName().toLowerCase());
					temp=temp+numero;
					if (temp>50){
						p.sendMessage("No puedes comprar mas de 50 de vida extra!");
						return true;
					}else{
					sts.playerMH.put(p.getName().toLowerCase(), temp);
					sts.save(p);
					sts.registerPlayer(p);
					p.sendMessage("Has comprado " +ChatColor.GRAY+numero+ChatColor.DARK_RED+" MaxHP!!");
					return true;
					}
				}else{
					p.sendMessage("No tienes suficientes puntos!");
					return true;
				}
				
			}else{
				p.sendMessage("No puedes comprar mas HP!");
				return true;
			}
			
		}else if(args[0].equalsIgnoreCase("buyrate") && args[1] != null){
			double CR = sts.critRate.get(p.getName().toLowerCase());
			
			if (CR <= 50){
				String solicitud = args[1];
				double numero=Double.valueOf(solicitud).doubleValue();
				CR=CR-numero;
				sts.critRate.put(p.getName().toLowerCase(), CR);
				double temp = sts.critRate.get(p.getName().toLowerCase());
				temp=temp+numero;
				if (temp>50){
					p.sendMessage("No puedes comprar mas de 50 de Critical Rate!");
					return true;
					}else{
						sts.critRate.put(p.getName().toLowerCase(), temp);
						sts.save(p);
						sts.registerPlayer(p);
						p.sendMessage("Has comprado "+ChatColor.GRAY+numero+ChatColor.RESET+"% de" +ChatColor.AQUA+"Critical Rate!!");
						return true;
					}
			}
			
		}else{
			p.sendMessage("uso: lvls <points/stats/buyhealth/buyrate> (<numero>)");
			return true;
		}
		return false;
	}

}
