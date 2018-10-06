package com.exclnetworks.MCYTIntegration.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.exclnetworks.MCYTIntegration.Data;
import com.exclnetworks.MCYTIntegration.Perms;
import com.exclnetworks.MCYTIntegration.lang.Lang;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class YoutubersCommand implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.hasPermission(Perms.command_youtubers)){
			sender.sendMessage(Lang.NO_PERMS.toString());
			return false;
		}
		
		TextComponent message = new TextComponent("§6§lYoutubers: ");
		
		for(String uuid : Data.youtubers.keySet()){
			OfflinePlayer op = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
			
			TextComponent info = new TextComponent("§7" + op.getName());
			info.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.youtube.com/channel/" + Data.youtubers.get(uuid)));
			info.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(op.isOnline() ? "§aOnline" : "§cOffline").create()));
			
			message.addExtra(info);
			message.addExtra("§6, ");
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
