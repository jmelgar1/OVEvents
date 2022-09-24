package org.onlyvanilla.ovevents.runnables;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.onlyvanilla.ovevents.Main;
import org.onlyvanilla.ovevents.smalleventmanager.DailyEvents;

import net.md_5.bungee.api.ChatColor;

public class UpdateScoreboard extends BukkitRunnable {
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	DailyEvents dailyEvents = new DailyEvents();
	
	//Main instance
	private StartEvent startEvent = StartEvent.getInstance();
	
	@Override
	public void run() {
		HashMap<String, Integer> topScores = new HashMap<String, Integer>();
		
		for(String s : mainClass.getEventData().getStringList("participants")) {
			Player p = Bukkit.getPlayer(s);
			topScores.put(p.getName(), dailyEvents.winningEventSection.getInt(p.getName()));
		}
		
		//get greatest to least 
		Map<String, Integer> topScores1 = sortByValue(topScores);
		
		for(String s : mainClass.getEventData().getStringList("participants")){
			Player p = Bukkit.getPlayer(s);
		if(p == null) {
			this.cancel();
		} else {
			Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
			Objective obj = board.registerNewObjective("OVEvents", "dummy", startEvent.scoreboardTitle);
			
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			
			obj.getScore(ChatColor.RESET.toString()).setScore(7);
			obj.getScore(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Scores:").setScore(6);;
			
			int counter = 5;
			for(Map.Entry<String, Integer> entry : topScores1.entrySet()) {
				if(counter >= 3) {
					obj.getScore(entry.getKey() + ": " + ChatColor.YELLOW + entry.getValue()).setScore(counter);
					counter--;
				}
				if(topScores1.size() < 3) {
					obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(3);
					obj.getScore(ChatColor.AQUA + "play.onlyvanilla.org").setScore(2);
				} else {
					obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(2);
					obj.getScore(ChatColor.AQUA + "play.onlyvanilla.org").setScore(1);
				}
			}	
			p.setScoreboard(board);
			}
		}
	}
	
	static Map<String, Integer> sortByValue(HashMap<String, Integer> topScores) {
		Map<String, Integer> sortedMap = topScores.entrySet().stream()
		        .sorted(Comparator.comparingInt(e -> -e.getValue()))
		        .collect(Collectors.toMap(
		                Map.Entry::getKey,
		                Map.Entry::getValue,
		                (a, b) -> { throw new AssertionError(); },
		                LinkedHashMap::new
		        ));
		
		return sortedMap;
	}
}
