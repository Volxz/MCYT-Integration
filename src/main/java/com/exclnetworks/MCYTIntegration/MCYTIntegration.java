package com.exclnetworks.MCYTIntegration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.exclnetworks.MCYTIntegration.lang.Lang;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.exclnetworks.MCYTIntegration.commands.StreamersCommand;
import com.exclnetworks.MCYTIntegration.commands.TwitchRemoveCommand;
import com.exclnetworks.MCYTIntegration.commands.TwitchSetCommand;
import com.exclnetworks.MCYTIntegration.commands.YTRemoveCommand;
import com.exclnetworks.MCYTIntegration.commands.YTSetCommand;
import com.exclnetworks.MCYTIntegration.commands.YoutubersCommand;

public class MCYTIntegration extends JavaPlugin{
	
	public static MCYTIntegration instance = null;
	
	Logger log = getLogger();
	
	public static YamlConfiguration LANG;
	public static File LANG_FILE;
	
	@Override
	public void onEnable(){
		instance = this;
		
		loadLang();
		
		Data.load();
		
		getCommand("ytset").setExecutor(new YTSetCommand());
		getCommand("twitchset").setExecutor(new TwitchSetCommand());
		getCommand("ytremove").setExecutor(new YTRemoveCommand());
		getCommand("twitchremove").setExecutor(new TwitchRemoveCommand());
		getCommand("youtubers").setExecutor(new YoutubersCommand());
		getCommand("streamers").setExecutor(new StreamersCommand());
		
		Pulse1Minute.start(instance);
	}
	
	@Override
	public void onDisable(){
		Data.save();
		
		instance = null;
	}
	
	public static MCYTIntegration get(){
		return instance;
	}
	
	/**
	 * Load the messages.yml file.
	 * @return The messages.yml config.
	 */
	public void loadLang() {
		File lang = new File(getDataFolder(), "messages.yml");
		if (!lang.exists()) {
			try {
	            getDataFolder().mkdir();
	            lang.createNewFile();
	            InputStream defConfigStream = this.getResource("messages.yml");
	            if (defConfigStream != null) {
	            	@SuppressWarnings("deprecation")
					YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	                defConfig.save(lang);
	                Lang.setFile(defConfig);
	                return;
	                //return defConfig;
	            }
			} catch(IOException e) {
				e.printStackTrace(); // So they notice
				log.severe("[MCYTIntegration] Couldn't create messages file.");
				log.severe("[MCYTIntegration] This is a fatal error. Now disabling");
				this.setEnabled(false); // Without it loaded, we can't send them messages
	        }
	    }
		YamlConfiguration conf = YamlConfiguration.loadConfiguration(lang);
	    for(Lang item : Lang.values()) {
	        if (conf.getString(item.getPath()) == null) {
	            conf.set(item.getPath(), item.getDefault());
	        }
	    }
	    Lang.setFile(conf);
	    LANG = conf;
	    LANG_FILE = lang;
	    try {
	        conf.save(getLangFile());
	    } catch(IOException e) {
	        log.log(Level.WARNING, "[MCYTIntegration] Failed to save messages.yml.");
	        log.log(Level.WARNING, "[MCYTIntegration] Report this stack trace to Electro.");
	        e.printStackTrace();
	    }
	}
	
	/**
	* Gets the messages.yml config.
	* @return The messages.yml config.
	*/
	public YamlConfiguration getLang() {
	    return LANG;
	}
	 
	/**
	* Get the messages.yml file.
	* @return The messages.yml file.
	*/
	public File getLangFile() {
	    return LANG_FILE;
	}
	
}
