package com.storm;

import java.io.File;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class levelStats extends JavaPlugin {

	public static final Logger log = Logger.getLogger("Minecraft");
/*	public HashMap<String,Double> Points = new HashMap<String,Double>();*/
	public HashMap<String,Double> playerMH = new HashMap<String,Double>();
	public HashMap<String,Double> playerExtraHealth = new HashMap<String,Double>();
	public HashMap<String,Double> playerlvl = new HashMap<String,Double>();
	public HashMap<String,Double> playerPoints = new HashMap<String,Double>();
	public HashMap<String,Double> critRate = new HashMap<String,Double>();
	public HashMap<String,Double> PMH = new HashMap<String,Double>();
	public HashMap<String,Double> PCH = new HashMap<String,Double>();
		
	/*public HashMap<String,Double> BD = new HashMap<String,Double>();*/
	static String mainDirectory = "plugins/levelStats";
	static Properties properties = new Properties(); 
	protected static FileConfiguration Config;
	/*private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();  */
	
	
	public void loadConfig(){
		 try{
			 File cfg = new File("plugins" + File.separator + "levelStats" + File.separator + "config.yml");
				cfg.mkdir();
	            Config = getConfig();
	            Config.set("MaxHealthIncPerUpgrade",1.0);
	            Config.set("CriticalRateIncPerUpgrade",1.0);
		 }catch(Exception e1) {
         	System.out.println("error.");
         }
		 saveConfig();
	}
	
	
	@Override
	public void onEnable() {
	loadConfig();
    PluginManager pm = getServer().getPluginManager();
    levelStatsListeners lst = new levelStatsListeners(this); 
    pm.registerEvents(lst, this);
    PluginDescriptionFile pdfFile = this.getDescription();
    System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    try{
		this.getCommand("levelstats").setExecutor(new levelStatsCommand(this, lst));
		this.getCommand("lvls").setExecutor(new levelStatsCommand(this, lst));
	} catch(Exception e) {
		System.out.println("Error registering the /levelstats command.");
	}
    
	}
	
	/*
	public boolean isDebugging(final Player player) {
        if (debugees.containsKey(player)) {
            return debugees.get(player);
        } else {
            return false;
        }
    }
	*/
	
	public void registerPlayer(Player p)
	{
		
		//lvl
		if(Config.contains(p.getName().toLowerCase()+".level")){
			this.playerlvl.put(p.getName().toLowerCase(), (double) p.getLevel());
		}
		else
		{
			Config.set(p.getName().toLowerCase()+".level",(double) p.getLevel());
			this.playerlvl.put(p.getName().toLowerCase(), (double) p.getLevel());
			saveConfig();
		}	
			
		//health
		if(Config.contains(p.getName().toLowerCase()+".MaxHealthInc"))
		{
			this.playerMH.put(p.getName().toLowerCase(), Config.getDouble(p.getName().toLowerCase()+".MaxHealthInc"));
			this.playerExtraHealth.put(p.getName().toLowerCase(), Config.getDouble(p.getName().toLowerCase()+".MaxHealthInc"));
			double health = Config.getDouble(p.getName().toLowerCase()+".MaxHealthInc");
			health=health+20;
			this.PMH.put(p.getName().toLowerCase(), health);
			this.PCH.put(p.getName().toLowerCase(), health );
			

			//playerExtraHealth = playerMH;
		}
		else
		{
			Config.set(p.getName().toLowerCase()+".MaxHealthInc", 0.0);
			this.playerMH.put(p.getName().toLowerCase(),0.0);
			this.playerExtraHealth.put(p.getName().toLowerCase(),0.0);
			this.PMH.put(p.getName().toLowerCase(), (double) 20);
			this.PCH.put(p.getName().toLowerCase(), (double) 20 );
			
			//playerExtraHealth = playerMH;
			saveConfig();
		}
		
		//points
		if(Config.contains(p.getName().toLowerCase()+".playerPoints")){
			this.playerPoints.put(p.getName().toLowerCase(), Config.getDouble(p.getName().toLowerCase()+".playerPoints"));
		}
		else
		{
			Config.set(p.getName().toLowerCase()+".playerPoints", 0.0);
			this.playerPoints.put(p.getName().toLowerCase(), 0.0);
			saveConfig();
		}	
		//CRate
		if(Config.contains(p.getName().toLowerCase()+".CriticalRate")){
			this.critRate.put(p.getName().toLowerCase(), Config.getDouble(p.getName().toLowerCase()+".CriticalRate"));
		}
		else
		{
			Config.set(p.getName().toLowerCase()+".CriticalRate", 0.0);
			this.critRate.put(p.getName().toLowerCase(), 0.0);
			saveConfig();
		}	
		
		
		
	}
	
	
	
	public void save(Player p)
	{
		Config.set(p.getName().toLowerCase()+".level",this.playerlvl.get(p.getName().toLowerCase()));
		Config.set(p.getName().toLowerCase()+".MaxHealthInc",this.playerMH.get(p.getName().toLowerCase()));
		Config.set(p.getName().toLowerCase()+".playerPoints",this.playerPoints.get(p.getName().toLowerCase()));
		Config.set(p.getName().toLowerCase()+".CriticalRate",this.critRate.get(p.getName().toLowerCase()));
		
		saveConfig();
		
	}
	
	
	
	
}
