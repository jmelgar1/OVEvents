package org.onlyvanilla.ovevents.runnables;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.onlyvanilla.ovevents.Main;
import org.onlyvanilla.ovevents.events.DailyEvents;
import org.onlyvanilla.ovevents.events.craftingevents.CookieClicker;
import org.onlyvanilla.ovevents.events.fishingevents.FishFrenzy;
import org.onlyvanilla.ovevents.events.killingevents.BadBats;
import org.onlyvanilla.ovevents.events.killingevents.BringHomeTheBacon;
import org.onlyvanilla.ovevents.events.killingevents.CowTipper;
import org.onlyvanilla.ovevents.events.killingevents.GhastHunter;
import org.onlyvanilla.ovevents.events.killingevents.HilariousHomicide;
import org.onlyvanilla.ovevents.events.killingevents.ShulkerHunt;
import org.onlyvanilla.ovevents.events.killingevents.WardenWarrior;
import org.onlyvanilla.ovevents.events.killingevents.WorldWarZ;
import org.onlyvanilla.ovevents.smallevents.DetermineEventData;

import net.md_5.bungee.api.ChatColor;

public class StartEvent extends BukkitRunnable{
	
	//Main instance
	private static StartEvent instance;
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	public DetermineEventData dev1 = new DetermineEventData();
	
	//get winning event 
	String winningEvent = mainClass.dev1.getVotedEvent(mainClass.getSmallEvents(), mainClass.dev1.getList(), mainClass);
	String winningEventNS = winningEvent.replaceAll("\\s", "");
	
	//initilize KillMobEvent class variable
	DailyEvents eventWinnerClass;
	
	@Override
	public void run() {
		
		instance = this;
		
		if(winningEventNS.equals("GhastHunter")) {
			GhastHunter ghastHunter = new GhastHunter();
			ghastHunter.registerEvents();
			createScoreboard(ghastHunter);
			
			eventWinnerClass = ghastHunter;
			
		} else if (winningEventNS.equals("ShulkerHunt")) {
			ShulkerHunt shulkerHunt = new ShulkerHunt();
			shulkerHunt.registerEvents();
			createScoreboard(shulkerHunt);
			
			eventWinnerClass = shulkerHunt;
			
		} else if (winningEventNS.equals("BadBats")) {
			BadBats badBats = new BadBats();
			badBats.registerEvents();
			createScoreboard(badBats);
			
			eventWinnerClass = badBats;
			
		} else if (winningEventNS.equals("HilariousHomicide")) {
			HilariousHomicide hilariousHomicide = new HilariousHomicide();
			hilariousHomicide.registerEvents();
			createScoreboard(hilariousHomicide);
			
			eventWinnerClass = hilariousHomicide;
			
		} else if (winningEventNS.equals("WorldWarZ")) {
			WorldWarZ worldWarZ = new WorldWarZ();
			worldWarZ.registerEvents();
			createScoreboard(new WorldWarZ());
			
			eventWinnerClass = worldWarZ;
			
		} else if (winningEventNS.equals("WardenWarrior")) {
			WardenWarrior wardenWarrior = new WardenWarrior();
			wardenWarrior.registerEvents();
			createScoreboard(wardenWarrior);
			
			eventWinnerClass = wardenWarrior;
			
		} else if (winningEventNS.equals("FishFrenzy")){
			FishFrenzy fishFrenzy = new FishFrenzy();
			fishFrenzy.registerEvents();
			createScoreboard(fishFrenzy);
			
			eventWinnerClass = fishFrenzy;
			
		} else if (winningEventNS.equals("CowTipper")){
			CowTipper cowTipper = new CowTipper();
			cowTipper.registerEvents();
			createScoreboard(cowTipper);
			
			eventWinnerClass = cowTipper;
			
		} else if (winningEventNS.equals("BringHomeTheBacon")){
			BringHomeTheBacon bringHomeTheBacon = new BringHomeTheBacon();
			bringHomeTheBacon.registerEvents();
			createScoreboard(bringHomeTheBacon);
			
			eventWinnerClass = bringHomeTheBacon;
			
		} else if (winningEventNS.equals("CookieClicker")){
			CookieClicker cookieClicker = new CookieClicker();
			cookieClicker.registerEvents();
			createScoreboard(cookieClicker);
			
			eventWinnerClass = cookieClicker;
			
		} else {
			System.out.println("event not found");
		}
		
		EndEvent endEvent = new EndEvent();
		endEvent.runTaskLater(mainClass, 400);
	}
	
	void createScoreboard(DailyEvents className) {
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("OnlyVanilla", "dummy", ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + winningEvent);
		
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		for(String s : mainClass.getEventData().getStringList("participants")){
			Player p = Bukkit.getPlayer(s);
			obj.getScore(p.getName()).setScore(className.winningEventSection.getInt(p.getName()));
		}
		
		for(String s : mainClass.getEventData().getStringList("participants")){
			Player p = Bukkit.getPlayer(s);
			p.setScoreboard(board);
		}
	}
	
	public void unregisterEvent(DailyEvents className) {
		className.unregisterEvents();
	}
	
	//Main instance
	public static StartEvent getInstance() {
		return instance;
	}
	
	public DailyEvents getEvent() {
		return eventWinnerClass;
	}
}
