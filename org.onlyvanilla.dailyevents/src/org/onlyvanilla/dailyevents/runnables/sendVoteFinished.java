package org.onlyvanilla.dailyevents.runnables;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.onlyvanilla.ovevents.Main;
import org.onlyvanilla.ovevents.smallevents.DetermineEventData;

import net.md_5.bungee.api.ChatColor;

public class sendVoteFinished extends BukkitRunnable{
    
	//Main instance
	private Main mainClass = Main.getInstance();
	
	public DetermineEventData dev1 = new DetermineEventData();
	
	FileConfiguration eventData = mainClass.getEventData();

	@Override
	public void run() {	
		//get winning event
		String winningEvent = mainClass.dev1.getVotedEvent(mainClass.getSmallEvents(), mainClass.dev1.getList(), mainClass);
		
		//create section with winning event name
		ConfigurationSection currentEventSection = eventData.createSection("current-event");
		ConfigurationSection winningEventSection = currentEventSection.createSection(winningEvent);
		
		mainClass.getEventData().set("eventid", 0);
		
		for(Player p : dev1.getNonParticipatingPlayers(mainClass.getEventData().getStringList("participants"))) {
			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_FROG_TONGUE, 500F, 0.8F);	
			p.sendMessage(mainClass.prefix + ChatColor.RED + "You did not vote for an event "
					+ "and will not be participating!");
		}
		
		//send to players who joined
		for(Player p : dev1.getPlayerParticipants(mainClass.getEventData().getStringList("participants"))) {		
			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_FROG_TONGUE, 500F, 0.8F);	
			p.sendMessage(mainClass.prefix + ChatColor.GREEN + "The voting time has expired and "
					+ "the event will start in 3 minutes!");
			
			//add participants to config file to track scores
			winningEventSection.createSection(p.getName());
			winningEventSection.set(p.getName(), 0);
		}
		
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			p.sendMessage(mainClass.prefix + " "
						+ ChatColor.LIGHT_PURPLE + winningEvent
						+ " has won the vote!");
		}
		
		
		//switch statement to determine which event will be played
		
		mainClass.saveSmallEventsFile();
		mainClass.saveEventDataFile();
		mainClass.reloadEventDataFile();
		
		send30SecondReminder secondReminder = new send30SecondReminder();
		secondReminder.runTaskLater(mainClass, 200);
	}
}
