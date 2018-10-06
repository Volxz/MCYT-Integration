package com.exclnetworks.MCYTIntegration;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.configuration.file.FileConfiguration;

public class Data {
	
	public static String yt_api_key;
	public static String twitch_api_key;
	
	//UUID : Channel ID
	public static HashMap<String, String> youtubers = new HashMap<String, String>();
	
	//UUID : User name
	public static HashMap<String, String> twitchers = new HashMap<String, String>();
	
	static FileConfiguration c = MCYTIntegration.get().getConfig();
	
	public static void load(){
		MCYTIntegration.get().saveDefaultConfig();
		
		yt_api_key = c.getString("yt_api_key");
		twitch_api_key = c.getString("twitch_api_key");
		
		if(c.get("youtubers") != null){
			for(String uuid : c.getConfigurationSection("youtubers").getKeys(false)){
				youtubers.put(uuid, c.getString("youtubers." + uuid));
			}
		}
		
		if(c.get("twitchers") != null){
			for(String uuid : c.getConfigurationSection("twitchers").getKeys(false)){
				twitchers.put(uuid, c.getString("twitchers." + uuid));
			}
		}
	}
	
	public static void save(){
		if(!youtubers.isEmpty()){
			c.set("youtubers", null);
			
			for(Entry<String, String> e : youtubers.entrySet()){
				c.set("youtubers." + e.getKey(), e.getValue());
			}
		}
		
		if(!twitchers.isEmpty()){
			c.set("twitchers", null);
			
			for(Entry<String, String> e : twitchers.entrySet()){
				c.set("twitchers." + e.getKey(), e.getValue());
			}
		}
		
		MCYTIntegration.get().saveConfig();
	}
	
}
