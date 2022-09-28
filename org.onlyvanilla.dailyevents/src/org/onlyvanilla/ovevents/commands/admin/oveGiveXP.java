package org.onlyvanilla.ovevents.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.onlyvanilla.ovevents.Main;
import org.onlyvanilla.ovevents.bukkitevents.EditPlayerPoints;
import org.onlyvanilla.ovevents.runnables.SendDailyEventVote;

public class oveGiveXP implements CommandExecutor{
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	EditPlayerPoints epp1 = new EditPlayerPoints();
	
	SendDailyEventVote dailyVote = new SendDailyEventVote();
	
	ConfigurationSection playerDataConfig = mainClass.getPlayerData();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("ovegivexp")) {
        	if(p.hasPermission("ove.givexp")) {	
        		if(!args[0].isEmpty() && !args[1].isEmpty()) {
        			String playerName = args[0];
        			String XP = args[1];
        			
        			String playerUUIDString = Bukkit.getServer().getPlayer(playerName).getUniqueId().toString();
        			ConfigurationSection playerUUID = playerDataConfig.getConfigurationSection(playerUUIDString);
        	    	
        	    	epp1.addLevelXP(Double.parseDouble(XP), playerUUID);
        	    	p.sendMessage(mainClass.prefix + "Added " + XP + " to " + playerName);
        		}
        	}
        }	
		return true;
	}
}
