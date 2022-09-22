package org.onlyvanilla.ovevents.runnables;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.onlyvanilla.ovevents.Main;
import org.onlyvanilla.ovevents.smallevents.DetermineEventData;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class SendDailyEventVote extends BukkitRunnable{
    
	//Main instance
	private Main mainClass = Main.getInstance();
	
	public DetermineEventData dev1 = new DetermineEventData();

	@Override
	public void run() {
		
		if(Bukkit.getServer().getOnlinePlayers().size() >= 2) {
			
			//clear previous participation list
			mainClass.dev1.clearParticipationList(mainClass);
			
			mainClass.dev1.clearVotes(mainClass.getSmallEvents(), mainClass.dev1.getList(), mainClass);
	
			//rotate the events
			mainClass.dev1.RotateEvents(mainClass.dev1.getList());
			
			//generate random eventid
			int eventID = (int)(Math.random()*10000);
			mainClass.getEventData().set("eventid", eventID);
			mainClass.saveEventDataFile();
			
			for(Player p : Bukkit.getServer().getOnlinePlayers()) {
				
				p.getWorld().playSound(p.getLocation(), Sound.ITEM_GOAT_HORN_SOUND_1, 500F, 1.2F);
				
				TextComponent message = new TextComponent("          ✦ Click Here To Vote! ✦");
		    	message.setColor(ChatColor.RED);
		    	message.setItalic(true);
		    	message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ovevote"));
		    	p.sendMessage(ChatColor.GRAY + "-----" + ChatColor.AQUA + ChatColor.BOLD + "Only"
		    										   + ChatColor.WHITE + "" + ChatColor.BOLD + "Vanilla" 
		    										   + ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + " EVENT VOTE!" + ChatColor.GRAY + "-----");
		    	p.spigot().sendMessage(message);
		    	p.sendMessage("");
		    	p.sendMessage(ChatColor.GREEN + " Voting will be open for 2 minutes!");
			}
			
			sendVoteFinished voteFinished = new sendVoteFinished();
			voteFinished.runTaskLater(mainClass, 200);
			
		} else {
			System.out.println("[OVEvents] Tried to send event vote. Less than 2 players online. Trying again in 20 minutes.");
			mainClass.runEventNotif20Minutes();
		}
	}
}
