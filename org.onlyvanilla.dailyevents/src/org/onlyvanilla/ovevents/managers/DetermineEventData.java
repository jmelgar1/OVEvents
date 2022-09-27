package org.onlyvanilla.ovevents.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.onlyvanilla.ovevents.Main;

public class DetermineEventData {
	
	public DetermineEventData instance = this;
	
	//create smallevent empty list
	ArrayList<String> smallEventNames = new ArrayList<String>();
	
	public void ShuffleEvents(FileConfiguration smallEvents) {
		//get small events in smallevents.yml and shuffle for randomized vote opportunities
		for(String smallEvent : smallEvents.getConfigurationSection("").getKeys(false)) {
			String rawName = smallEvent;
			
			//add names to a list
			smallEventNames.add(rawName);
		}
		
		Collections.shuffle(smallEventNames);
	}
	
	public List<String> RotateEvents(List<String> smallEventNames){
		//loop per # of rotations
		for(int i = 0; i < 3; i++) {
			
			//store last element in list
			String temp = smallEventNames.get(14);
			
			//traverse list and move elements to the right
			for(int j = 14; j > 0; j--) {
				smallEventNames.set(j, smallEventNames.get(j - 1));
			}
			smallEventNames.set(0, temp);
		}
		return smallEventNames.subList(0, 3);
	}
	
	public List<String> getList(){
		return smallEventNames;
	}
	
	public void clearParticipationList(Main mainClass) {
		//clear participant list
    	try{
    		List<String> clearedList = mainClass.getEventData().getStringList("participants");
    		clearedList.clear();
    		mainClass.getEventData().set("participants", clearedList);
    		mainClass.saveEventDataFile();
    		System.out.println("Cleared participant list!");
    	} catch (Exception e) {
    		System.out.println(e);
    	}
	}
	
	public List<Player> getPlayerParticipants(List<String> participantList){
		
		List<Player> playerParticipants = new ArrayList<>();
		
		for(String s : participantList) {
			Player p = Bukkit.getServer().getPlayer(s);
			playerParticipants.add(p);
		}
		
		return playerParticipants;
	}
	
	public List<Player> getNonParticipatingPlayers(List<String> participantList){
		List<Player> nonParticipatingPlayers = new ArrayList<>();
		
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			nonParticipatingPlayers.add(p);
		}
		
		//returning null??
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			for(String p2 : participantList) {
				if (p.getName().equals(p2)){
				nonParticipatingPlayers.remove(Bukkit.getServer().getPlayer(p2));
			}
		}	
	}
		return nonParticipatingPlayers;
	}
	
	public void clearVotes(FileConfiguration smallEvents, List<String> smallEventNames, Main mainClass) {
    	//gets all events from list
    	for(String names : smallEventNames) {	
    		ConfigurationSection eventsSection = smallEvents.getConfigurationSection(names);
    		
    		try {
    			eventsSection.set("votes", 0);
    		} catch(Exception e) {
    			System.out.println("[OVEvents] Could not clear " + names + " votes!");
    		}
    	}  	
    	System.out.println("[OVEvents] Votes cleared!");
    	mainClass.saveSmallEventsFile();
    	
	}
	
	public String getVotedEvent(FileConfiguration smallEvents, List<String> smallEventNames, Main mainClass) {
		String topEvent = "NONE";
		int topVote = 0;
		int totalVotes = 0;
		
	   	//gets all events from list
    	for(String names : smallEventNames) {	
    		ConfigurationSection eventsSection = smallEvents.getConfigurationSection(names);
    		int votes = eventsSection.getInt("votes");
    		
    		if(votes > topVote) {
    			topVote = votes;
    			topEvent = names;
    		}
    		
    		totalVotes += votes;
    	}
    	
    	//if less than 2 players vote return none
    	if(totalVotes < 2) {
    		return "NONE";
    	}
    	
    	//if one person votes cancel.
    	
    	return topEvent;
	}
	
	public DetermineEventData getInstance() {
		return instance;
	}
}
