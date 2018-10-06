package com.exclnetworks.MCYTIntegration;

import java.util.ArrayList;
import java.util.Map.Entry;

import java.util.UUID;

import com.exclnetworks.MCYTIntegration.lang.Lang;
import com.exclnetworks.MCYTIntegration.utils.TwitchAPIUtils;
import com.exclnetworks.MCYTIntegration.utils.YoutubeAPIUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Pulse1Minute implements Runnable{
	
	public static void start(MCYTIntegration plugin){
		//Incase of a reload or such. Close off any other task being run by this plugin.
		Bukkit.getScheduler().cancelTasks(plugin);
		
		Bukkit.getScheduler().runTaskTimer(plugin, new Pulse1Minute(), 0L, 60 * 20L);
	}
	
	static ArrayList<String> twitchJustBroadcasted = new ArrayList<String>();
	
	@Override
	public void run() {
		for(Entry<String, String> e : Data.youtubers.entrySet()){
			try {
				String vid = YoutubeAPIUtils.isNewVideo(e.getValue());
				
				if(vid != null){
					//Broadcast to server
					OfflinePlayer op = Bukkit.getOfflinePlayer(UUID.fromString(e.getKey()));
					
					String broadcast = Lang.YT_UPLOADED.toString().replaceAll("%youtuber%", op.getName());
					
					String videoLink = "https://www.youtube.com/watch?v=" + vid;
					
					TextComponent part1 = new TextComponent(broadcast.split("%video_url%")[0]);
					
					TextComponent link = new TextComponent(videoLink);
					link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, videoLink));
					
					TextComponent part2 = new TextComponent(broadcast.split("%video_url%")[1]);
					
					TextComponent finalMessage = part1;
					finalMessage.addExtra(link);
					finalMessage.addExtra(part2);
					
					for(Player p : Bukkit.getOnlinePlayers()) p.spigot().sendMessage(finalMessage);
					
					//Bukkit.broadcastMessage(Lang.YT_UPLOADED.toString().replaceAll("%video_url%", "https://www.youtube.com/watch?v=" + vid));
				}
			} catch (Exception e2) {
			}
		}
		
		for(Entry<String, String> e : Data.twitchers.entrySet()){
			try {
				final String twitchLink = TwitchAPIUtils.isNewStream(e.getValue());
				
				if(twitchLink != null){
					//Broadcast to server
					OfflinePlayer op = Bukkit.getOfflinePlayer(UUID.fromString(e.getKey()));
					
					if(twitchJustBroadcasted.contains(twitchLink)) continue;
					
					twitchJustBroadcasted.add(twitchLink);
					
					Bukkit.getScheduler().runTaskLater(MCYTIntegration.get(), new Runnable() {
						@Override
						public void run() {
							twitchJustBroadcasted.remove(twitchLink);
						}
						//Remove 15 minutes later
					}, (60 * 15) * 20);
					
					//Bukkit.broadcastMessage(Lang.TWITCH_STARTED.toString().replaceAll("%twitch%", op.getName()).replaceAll("%profile_url%", twitchLink));
					String broadcast = Lang.TWITCH_STARTED.toString().replaceAll("%twitch%", op.getName());
					
					TextComponent part1 = new TextComponent(broadcast.split("%profile_url%")[0]);
					
					TextComponent link = new TextComponent(twitchLink);
					link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, twitchLink));
					
					TextComponent part2 = new TextComponent(broadcast.split("%profile_url%")[1]);
					
					TextComponent finalMessage = part1;
					finalMessage.addExtra(link);
					finalMessage.addExtra(part2);
					
					for(Player p : Bukkit.getOnlinePlayers()) p.spigot().sendMessage(finalMessage);
					
				}
			} catch (Exception e2) {
			}
		}
		
	}

}
