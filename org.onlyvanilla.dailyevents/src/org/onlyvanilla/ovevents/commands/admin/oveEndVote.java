package org.onlyvanilla.ovevents.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.onlyvanilla.ovevents.runnables.send30SecondReminder;
import org.onlyvanilla.ovevents.runnables.sendDailyEventVote;
import org.onlyvanilla.ovevents.runnables.sendVoteFinished;
import org.onlyvanilla.ovevents.runnables.startEventCountdown;

import net.md_5.bungee.api.ChatColor;

public class oveEndVote implements CommandExecutor{
	
	sendDailyEventVote dailyVote = new sendDailyEventVote();
	startEventCountdown eventCountdown = new startEventCountdown();
	send30SecondReminder secondReminder = new send30SecondReminder();
	sendVoteFinished voteFinished = new sendVoteFinished();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("oveendvote")) {
        	if(p.hasPermission("ove.endvote")) {
        		p.sendMessage("broken");
        		if(Bukkit.getScheduler().isQueued(dailyVote.getTaskId()) == true){
        			dailyVote.cancel();
        			p.sendMessage(ChatColor.RED + "Event vote cancelled!");
        		} else if (Bukkit.getScheduler().isQueued(voteFinished.getTaskId()) == true){
        			voteFinished.cancel();
        			p.sendMessage(ChatColor.RED + "Event vote cancelled!");
        		} else if (Bukkit.getScheduler().isQueued(secondReminder.getTaskId()) == true){
        			secondReminder.cancel();
        			p.sendMessage(ChatColor.RED + "Event vote cancelled!");
        		} else if (Bukkit.getScheduler().isQueued(eventCountdown.getTaskId()) == true){
        			eventCountdown.cancel();
        			p.sendMessage(ChatColor.RED + "Event vote cancelled!");
        		} else {
        			p.sendMessage(ChatColor.RED + "There is not event vote running right now.");
        		}
        	}
        }	
		return true;
	}
}
