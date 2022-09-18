package org.onlyvanilla.ovevents.commands.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.onlyvanilla.ovevents.Main;

import net.md_5.bungee.api.ChatColor;

public class overeload implements CommandExecutor{
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("overeload")) {
        	p.sendMessage(ChatColor.GREEN + "Reloaded DailyEvents Config");
        	mainClass.reloadPlayerDataFile();
        }	
		return true;
	}
}
