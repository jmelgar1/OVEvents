package org.onlyvanilla.ovevents.commands.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.onlyvanilla.dailyevents.runnables.sendDailyEventVote;
import org.onlyvanilla.ovevents.Main;

public class oveForceVote implements CommandExecutor{
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	sendDailyEventVote dailyVote = new sendDailyEventVote();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("oveforcevote")) {
        	if(p.hasPermission("ove.forcevote")) {	
        		dailyVote.runTaskTimer(mainClass, 0, 30000);
        		p.sendMessage(mainClass.prefix + "Vote started successfully!");
        	}
        }	
		return true;
	}
}
