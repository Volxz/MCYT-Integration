package com.exclnetworks.MCYTIntegration.commands;

import com.exclnetworks.MCYTIntegration.lang.Lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.exclnetworks.MCYTIntegration.Data;
import com.exclnetworks.MCYTIntegration.Perms;

public class TwitchRemoveCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(!sender.hasPermission(Perms.command_twitchRemove)){
			sender.sendMessage(Lang.NO_PERMS.toString());
			return false;
		}
		
		if(args.length != 1){
			sender.sendMessage(ChatColor.RED + "Correct usage: /twitchremove [playername]");
			return false;
		}
		
		String playerName = args[0];
		
		@SuppressWarnings("deprecation")
		OfflinePlayer op = Bukkit.getOfflinePlayer(playerName);
		
		if(op == null){
			sender.sendMessage(ChatColor.RED + "Could not determine the UUID of user \"" + playerName + "\". Have they joined the server before and is their name spelled correctly?");
			return false;
		}
		
		String uuid = op.getUniqueId().toString();
		
		if(!Data.twitchers.containsKey(uuid)){
			sender.sendMessage(ChatColor.RED + "The player " + playerName + " does not have a twitch channel ID set.");
			return false;
		}
		
		Data.twitchers.remove(uuid);
		
		sender.sendMessage(ChatColor.GREEN + "Successfully removed " + playerName + "'s twitch channel ID.");
		
		return false;
	}

}
