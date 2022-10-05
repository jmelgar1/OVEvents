package org.onlyvanilla.ovevents.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.onlyvanilla.ovevents.Main;
import org.onlyvanilla.ovevents.bukkitevents.EditPlayerPoints;
import org.onlyvanilla.ovevents.bukkitevents.playerEvents;
import org.onlyvanilla.ovevents.runnables.SendDailyEventVote;

public class oveSetXP implements CommandExecutor{
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	EditPlayerPoints epp1 = new EditPlayerPoints();
	
	SendDailyEventVote dailyVote = new SendDailyEventVote();
	
	ConfigurationSection playerDataConfig = mainClass.getPlayerData();
	
	playerEvents pe1 = new playerEvents();
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("ovesetxp")) {
        	if(sender.hasPermission("ove.setxp")) {	
        		if(!args[0].isEmpty() && !args[1].isEmpty()) {
        			String playerName = args[0];
        			String XP = args[1];
        			
        			String playerUUIDString = "";
        			ConfigurationSection playerUUID = playerDataConfig.getConfigurationSection("none");
        			Player p = Bukkit.getServer().getPlayer(playerName);
        			
        			if(p != null) {
        				playerUUIDString = Bukkit.getServer().getPlayer(playerName).getUniqueId().toString();
        				playerUUID = playerDataConfig.getConfigurationSection(playerUUIDString);
        			} else {
        				playerUUIDString = Bukkit.getServer().getOfflinePlayer(playerName).getUniqueId().toString();
        				playerUUID = playerDataConfig.getConfigurationSection(playerUUIDString);
        			}
        	    	
        	    	epp1.setLevelXP(Double.parseDouble(XP), playerUUID);
        	    	
        	    	if(sender instanceof Player) {
        	    		sender.sendMessage(mainClass.prefix + "Set " + XP + " to " + playerName);
        	    	}
        		}
        	}
        }	
		return true;
	}
}
