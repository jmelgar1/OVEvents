package org.onlyvanilla.ovevents.runnables;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.onlyvanilla.ovevents.Main;
import org.onlyvanilla.ovevents.bukkitevents.EditPlayerPoints;
import org.onlyvanilla.ovevents.events.DailyEvents;
import org.onlyvanilla.ovevents.smallevents.DetermineEventData;

import net.md_5.bungee.api.ChatColor;

public class EndEvent extends BukkitRunnable{
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	public DetermineEventData dev1 = new DetermineEventData();
	
	//start event instance
	private StartEvent startEventClass = StartEvent.getInstance();
	
	UpdateScoreboard updateScoreboard = new UpdateScoreboard();
	
	DailyEvents dailyEvents = new DailyEvents();
	
	EditPlayerPoints editPlayerPoints = new EditPlayerPoints();

	@Override
	public void run() {
		
		HashMap<String, Integer> topScores = new HashMap<String, Integer>();
		
		for(String s : mainClass.getEventData().getStringList("participants")) {
			Player p = Bukkit.getPlayer(s);
			topScores.put(p.getName(), dailyEvents.winningEventSection.getInt(p.getName()));
		}
		
		//get greatest to least 
		Map<String, Integer> topScores1 = UpdateScoreboard.sortByValue(topScores);
		
		Bukkit.broadcastMessage(ChatColor.GRAY + "-----" + ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + " EVENT RESULTS!" + ChatColor.GRAY + "-----");
		
		int counter = 1;
		int XPGiven = getDuration();
		for(Map.Entry<String, Integer> entry : topScores1.entrySet()) {
			if(counter < 4) {
				
				String playerUUIDString = Bukkit.getPlayer(entry.getKey()).getUniqueId().toString();
				System.out.println(playerUUIDString);
				Player p = Bukkit.getServer().getPlayer(entry.getKey());
				
				Bukkit.broadcastMessage(ChatColor.GOLD + "" + counter + ". " + ChatColor.YELLOW + entry.getKey() + ChatColor.GOLD + " - " + ChatColor.GRAY + entry.getValue());
				
				ConfigurationSection playerDataConfig = mainClass.getPlayerData();
				ConfigurationSection playerUUID = playerDataConfig.getConfigurationSection(playerUUIDString);
				ConfigurationSection statsSection = playerUUID.getConfigurationSection("stats");
				ConfigurationSection smallEventsSection = statsSection.getConfigurationSection("small-events");
				
				if(counter == 1) {
					int score = smallEventsSection.getInt(dailyEvents.winningEvent);
					score += 1;
					smallEventsSection.set(dailyEvents.winningEvent, score);
				}
				
				if(counter == 3) {
					XPGiven = XPGiven/(counter+1);
				} else {
					XPGiven = XPGiven/counter;
				}
				
				counter++;
				
				//set new players xp
				editPlayerPoints.addLevelXP(XPGiven, playerUUID);
				
				mainClass.savePlayerDataFile();
			}
		}
		
		counter = 1;
		XPGiven = getDuration();
		for(Map.Entry<String, Integer> entry : topScores1.entrySet()) {
			Player p = Bukkit.getServer().getPlayer(entry.getKey());
			
			p.sendMessage(mainClass.prefix + "You earned " + ChatColor.GOLD + XPGiven/counter + " experience " + ChatColor.LIGHT_PURPLE + "from the event!");

			counter++;
		}
		
		try {
			//unregister event
			startEventClass.unregisterEvent(startEventClass.getEvent());
		} catch (Exception e) {
			System.out.println(e);
		}
		
		//send a new task in 20 minutes
		SendDailyEventVote sendDailyEventVote = new SendDailyEventVote();
		sendDailyEventVote.runTaskLater(mainClass, 240);
		
		//remove scoreboard
		removeScoreboard();
		
		//clearing previous votes and removing the scoreboard from players
		dev1.clearParticipationList(mainClass);
		dev1.clearVotes(mainClass.getSmallEvents(), mainClass.dev1.getList(), mainClass);
		
		mainClass.saveEventDataFile();
	}
	
	public void removeScoreboard() {
		for(String s : mainClass.getEventData().getStringList("participants")){
			Player p = Bukkit.getPlayer(s);
			p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		}
	}
	
	//get duration of the event (equal to the amount of XP given, 120 minutes = 1st place gets 120 xp)
	int getDuration() {
		ConfigurationSection smallEvents = mainClass.getSmallEvents();
		ConfigurationSection winningEvent = smallEvents.getConfigurationSection(dailyEvents.winningEvent);
		int duration = winningEvent.getInt("duration");
		
		return duration;
	}
	
	//get players current xp
	int getXP(String playerUUID) {
		ConfigurationSection playerDataConfig = mainClass.getPlayerData();
		ConfigurationSection playerUUIDSection = playerDataConfig.getConfigurationSection(playerUUID);
		int levelExperience = playerUUIDSection.getInt("levelExperience");
		
		return levelExperience;
	}
}
