package com.exclnetworks.MCYTIntegration.commands;

import java.util.UUID;

import com.exclnetworks.MCYTIntegration.lang.Lang;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.exclnetworks.MCYTIntegration.Data;
import com.exclnetworks.MCYTIntegration.Perms;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class StreamersCommand implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.hasPermission(Perms.command_youtubers)){
			sender.sendMessage(Lang.NO_PERMS.toString());
			return false;
		}
		
		TextComponent message = new TextComponent("§b§lStreamers: ");
		
		for(String uuid : Data.twitchers.keySet()){
			OfflinePlayer op = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
			
			TextComponent info = new TextComponent("§7" + op.getName());
			info.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://twitch.tv/" + Data.twitchers.get(uuid)));
			info.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(op.isOnline() ? "§aOnline" : "§cOffline").create()));
			
			message.addExtra(info);
			message.addExtra("§b, ");
		}
		
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
			return false;
		}
		
		Player player = (Player) sender;
		
		player.spigot().sendMessage(message);
		
		return false;
	}
	
}
