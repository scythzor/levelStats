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
	public HashMap<String,Double> MH = new HashMap<String,Double>();
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
	            Config.set("MaxHelthInc",1);
	            Config.set("BaseDemageInc",1);
		 }catch(Exception e1) {
         	System.out.println("error.");
         }
		 saveConfig();
	}
	
	
	@Override
	public void onEnable() {
	loadConfig();
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvents(new levelStatsListeners(this), this);
    PluginDescriptionFile pdfFile = this.getDescription();
    System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    try{
		getCommand("levelstats").setExecutor(new levelStatsCommand());
		getCommand("levels").setExecutor(new levelStatsCommand());
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
		if(Config.contains(p.getName().toLowerCase()+".MaxHealtInc:"))
		{
			this.MH.put(p.getName().toLowerCase(), Config.getDouble("MaxHealthInc."+p.getName().toLowerCase()));
		}
		else
		{
			Config.set(p.getName().toLowerCase()+".MaxHealtInc:", 0.0);
			this.MH.put(p.getName().toLowerCase(),0.0);
			saveConfig();
		}
	}
	
	
	
	
	
}
