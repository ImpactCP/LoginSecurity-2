package com.lenis0012.bukkit.ls.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.lenis0012.bukkit.ls.LoginSecurity;
import com.lenis0012.bukkit.ls.data.ValueType;
import com.lenis0012.bukkit.ls.encryption.PasswordManager;

public class ChangePassCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		LoginSecurity plugin = LoginSecurity.instance;
		if(!(sender instanceof Player)) {
			sender.sendMessage("You must be a player");
			return true;
		}
		
		Player player = (Player)sender;
		String name = player.getName().toLowerCase();
		
		if(!plugin.data.isSet(name)) {
			player.sendMessage(ChatColor.RED+"You are not registered on the server");
			return true;
		}
		if(args.length < 2) {
			player.sendMessage(ChatColor.RED+"Not enough arguments");
			player.sendMessage("Usage: "+cmd.getUsage());
			return true;
		}
		if(!PasswordManager.checkPass(name, args[0])) {
			player.sendMessage(ChatColor.RED+"Password Incorrect");
			return true;
		}
		
		String newPass = plugin.hasher.hash(args[1]);
		plugin.data.setValue(name, ValueType.UPDATE_PASSWORD, newPass, plugin.hasher.getTypeId());
		player.sendMessage(ChatColor.GREEN+"Succesfully changed password to: "+args[1]);
		return true;
	}
}
