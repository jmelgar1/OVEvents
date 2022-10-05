package org.onlyvanilla.ovevents.runnables;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.onlyvanilla.ovevents.Main;
import org.onlyvanilla.ovevents.bukkitevents.EditPlayerPoints;
import org.onlyvanilla.ovevents.managers.DetermineEventData;
import org.onlyvanilla.ovevents.smalleventmanager.DailyEvents;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.InheritanceNode;
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
	
	//Luckperms api
	static LuckPerms api = LuckPermsProvider.get();
	
	ConfigurationSection eventConfig = mainClass.getSmallEvents();
	ConfigurationSection event = eventConfig.getConfigurationSection(dailyEvents.winningEvent);
	ConfigurationSection placement = event.getConfigurationSection("placements");
	int first = placement.getInt("first");
	int second = placement.getInt("second");
	int third = placement.getInt("third");

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		
		HashMap<String, Integer> topScores = new HashMap<String, Integer>();
		
		for(String s : mainClass.getEventData().getStringList("participants")) {
			topScores.put(s, dailyEvents.winningEventSection.getInt(s));
			
			//Player p = Bukkit.getServer().getPlayer(s);
			
//			if(p == null) {
//				OfflinePlayer p2 = Bukkit.getServer().getOfflinePlayer(s);
//				String group = "in_event";
//				User user = api.getPlayerAdapter(Player.class).getUser(p);
//				user.data().remove(InheritanceNode.builder(group).value(true).build());
//				api.getUserManager().saveUser(user);
//			}
			
//			//group change not working since you cant get offline player to get user
//			
//			String group = "in_event";
//			User user = api.getPlayerAdapter(Player.class).getUser(p);
//			user.data().remove(InheritanceNode.builder(group).value(true).build());
//			api.getUserManager().saveUser(user);
		}
		
		//get greatest to least 
		Map<String, Integer> topScores1 = UpdateScoreboard.sortByValue(topScores);
		
		Bukkit.broadcastMessage(ChatColor.GRAY + "----- " + ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + dailyEvents.winningEvent.toUpperCase() + " RESULTS!" + ChatColor.GRAY + " -----");
		
		int counter = 1;
		int XPGiven = 0;
		for(Map.Entry<String, Integer> entry : topScores1.entrySet()) {
			
			Player p = Bukkit.getPlayer(entry.getKey());
			
			if(p != null) {
				String playerUUIDString = Bukkit.getPlayer(entry.getKey()).getUniqueId().toString();
				
					if(counter < 4) {
		
						Bukkit.broadcastMessage(ChatColor.GOLD + "" + counter + ". " + ChatColor.YELLOW + entry.getKey() + ChatColor.GOLD + " - " + ChatColor.GRAY + entry.getValue());
						
						ConfigurationSection playerDataConfig = mainClass.getPlayerData();
						ConfigurationSection playerUUID = playerDataConfig.getConfigurationSection(playerUUIDString);
						ConfigurationSection statsSection = playerUUID.getConfigurationSection("stats");
						ConfigurationSection smallEventsSection = statsSection.getConfigurationSection("small-events");
						
						if(counter == 1) {
							int score = smallEventsSection.getInt(dailyEvents.winningEvent);
							score += 1;
							smallEventsSection.set(dailyEvents.winningEvent, score);
							XPGiven = first;
						} else if(counter == 2) {
							XPGiven = second;
						} else if(counter == 3) {
							XPGiven = third;
						}
						
						counter++;
						
						if(entry.getValue() != 0) {
							//set new players xp
							editPlayerPoints.addLevelXP(XPGiven, playerUUID);
						}
				}
				mainClass.savePlayerDataFile();
			}
		}
		
		counter = 1;
		for(Map.Entry<String, Integer> entry : topScores1.entrySet()) {
			Player p = Bukkit.getServer().getPlayer(entry.getKey());
			if(counter == 1) {
				XPGiven = first;
			} else if(counter == 2) {
				XPGiven = second;
			} else if(counter == 3) {
				XPGiven = third;
			}
			
			if(p != null) {
				if(entry.getValue() != 0) {
						p.sendMessage(mainClass.prefix + "You earned " + ChatColor.GOLD + XPGiven + " experience " + ChatColor.LIGHT_PURPLE + "from the event!");
						counter++;
				} else {
					Bukkit.getPlayer(entry.getKey()).sendMessage(mainClass.prefix + ChatColor.RED + "You did not score any points so you did not receive any XP!");
				}
			}
		}
		
		try {
			//unregister event
			startEventClass.unregisterEvent(startEventClass.getEvent());
		} catch (Exception e) {
			System.out.println(e);
		}
		
		//send a new task in 20 minutes 
		SendDailyEventVote sendDailyEventVote = new SendDailyEventVote();
		sendDailyEventVote.runTaskLater(mainClass, 24000);
		
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
			
			if(p != null) {
				p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
			}
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
