package com.exclnetworks.MCYTIntegration.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.exclnetworks.MCYTIntegration.Data;
import com.exclnetworks.MCYTIntegration.Perms;
import com.exclnetworks.MCYTIntegration.lang.Lang;

public class YTSetCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(!sender.hasPermission(Perms.command_ytSet)){
			sender.sendMessage(Lang.NO_PERMS.toString());
			return false;
		}
		
		if(args.length != 2){
			sender.sendMessage(ChatColor.RED + "Correct usage: /ytset [playername] [channel ID]");
			return false;
		}
		
		String playerName = args[0];
		String channelID = args[1];
		
		@SuppressWarnings("deprecation")
		OfflinePlayer op = Bukkit.getOfflinePlayer(playerName);
		
		if(op == null){
			sender.sendMessage(ChatColor.RED + "Could not determine the UUID of user \"" + playerName + "\". Have they joined the server before and is their name spelled correctly?");
			return false;
		}
		
		String uuid = op.getUniqueId().toString();
		
		if(Data.youtubers.containsKey(uuid)){
			sender.sendMessage(ChatColor.GREEN + "The player " + playerName + " already has their youtube's channel ID set. Updating it to the given value...");
		}
		
		Data.youtubers.put(uuid, channelID);
		
		sender.sendMessage(ChatColor.GREEN + "Successfully set " + playerName + "'s youtube channel ID to \"" + channelID + "\".");
		return false;
	}

}
