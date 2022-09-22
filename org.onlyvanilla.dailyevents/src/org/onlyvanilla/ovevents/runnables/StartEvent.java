package org.onlyvanilla.ovevents.runnables;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.onlyvanilla.ovevents.Main;
import org.onlyvanilla.ovevents.events.DailyEvents;
import org.onlyvanilla.ovevents.events.craftingevents.CookieClicker;
import org.onlyvanilla.ovevents.events.damageevents.DragonSlayer;
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
	public String winningEvent = mainClass.dev1.getVotedEvent(mainClass.getSmallEvents(), mainClass.dev1.getList(), mainClass);
	String winningEventNS = winningEvent.replaceAll("\\s", "");
	
	//initilize KillMobEvent class variable
	DailyEvents eventWinnerClass;
	
	//create empty scoreboard title name
	String scoreboardTitle;
	
	@Override
	public void run() { 
		
		instance = this;
		
		EndEvent endEvent = new EndEvent();
		
		if(winningEventNS.equals("GhastHunter")) {
			GhastHunter ghastHunter = new GhastHunter();
			
			eventWinnerClass = ghastHunter;
			
			scoreboardTitle = ChatColor.WHITE + "" + ChatColor.BOLD + "GHAST HUNTER";
			createScoreboard(ghastHunter);
			
			if(allOnline == true) {
				ghastHunter.registerEvents();
			}
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, 24000);
			
		} else if (winningEventNS.equals("ShulkerHunt")) {
			ShulkerHunt shulkerHunt = new ShulkerHunt();
			
			eventWinnerClass = shulkerHunt;
			
			scoreboardTitle = ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "SHULKER HUNT";
			createScoreboard(shulkerHunt);
			
			if(allOnline == true) {
				shulkerHunt.registerEvents();
			}
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, 24000);
			
		} else if (winningEventNS.equals("BadBats")) {
			BadBats badBats = new BadBats();
			
			eventWinnerClass = badBats;
			
			scoreboardTitle = ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "BAD BATS";
			createScoreboard(badBats);
			
			if(allOnline == true) {
				badBats.registerEvents();
			}
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, 24000);
			
		} else if (winningEventNS.equals("HilariousHomicide")) {
			HilariousHomicide hilariousHomicide = new HilariousHomicide();
			
			eventWinnerClass = hilariousHomicide;
			
			scoreboardTitle = ChatColor.DARK_RED + "" + ChatColor.BOLD + "HILARIOUS HOMICIDE";
			createScoreboard(hilariousHomicide);
			
			if(allOnline == true) {
				hilariousHomicide.registerEvents();
			}
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, 24000);
			
		} else if (winningEventNS.equals("WorldWarZ")) {
			WorldWarZ worldWarZ = new WorldWarZ();
			
			eventWinnerClass = worldWarZ;
			
			scoreboardTitle = ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "WORLD WAR Z";
			createScoreboard(worldWarZ);
			
			if(allOnline == true) {
				worldWarZ.registerEvents();
			}
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, 24000);
			
		} else if (winningEventNS.equals("WardenWarrior")) {
			WardenWarrior wardenWarrior = new WardenWarrior();
			
			eventWinnerClass = wardenWarrior;
			
			scoreboardTitle = ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "WARDEN WARRIOR";
			createScoreboard(wardenWarrior);
			
			if(allOnline == true) {
				wardenWarrior.registerEvents();
			}
			
			//end event after 2 hours
			endEvent.runTaskLater(mainClass, 144000);
			
		} else if (winningEventNS.equals("FishFrenzy")){
			FishFrenzy fishFrenzy = new FishFrenzy();
			
			eventWinnerClass = fishFrenzy;
			
			scoreboardTitle = ChatColor.BLUE + "" + ChatColor.BOLD + "FISH FRENZY";
			createScoreboard(fishFrenzy);
			
			if(allOnline == true) {
				fishFrenzy.registerEvents();
			}
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, 24000);
			
		} else if (winningEventNS.equals("CowTipper")){
			CowTipper cowTipper = new CowTipper();
		
			eventWinnerClass = cowTipper;
			
			scoreboardTitle = ChatColor.WHITE + "" + ChatColor.BOLD + "COW TIPPER";
			createScoreboard(cowTipper);
			
			if(allOnline == true) {
				cowTipper.registerEvents();
			}
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, 24000);
			
		} else if (winningEventNS.equals("BringHomeTheBacon")){
			BringHomeTheBacon bringHomeTheBacon = new BringHomeTheBacon();
			
			eventWinnerClass = bringHomeTheBacon;
			
			scoreboardTitle = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "BRING HOME THE BACON";
			createScoreboard(bringHomeTheBacon);
			
			if(allOnline == true) {
				bringHomeTheBacon.registerEvents();
			}
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, 24000);
			
		} else if (winningEventNS.equals("CookieClicker")){
			CookieClicker cookieClicker = new CookieClicker();
			
			eventWinnerClass = cookieClicker;
			
			scoreboardTitle = ChatColor.YELLOW + "" + ChatColor.BOLD + "COOKIE CLICKER";
			createScoreboard(cookieClicker);
			
			if(allOnline == true) {
				cookieClicker.registerEvents();
			}
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, 24000);
			
		} else if (winningEventNS.equals("DragonSlayer")){
			DragonSlayer dragonSlayer = new DragonSlayer();
			
			eventWinnerClass = dragonSlayer;
			
			scoreboardTitle = ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "DRAGON SLAYER";
			createScoreboard(dragonSlayer);
			
			if(allOnline == true) {
				dragonSlayer.registerEvents();
			}
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, 72000);
		}
		
		if(allOnline == false) {
			SendDailyEventVote sendDailyEventVote = new SendDailyEventVote();
			sendDailyEventVote.dev1.clearParticipationList(mainClass);
			sendDailyEventVote.runTaskLater(mainClass, 200);
		}
	}
	
	//boolean to check if all players are online
	boolean allOnline = true;
	
	//function to create scoreboard
	void createScoreboard(DailyEvents className) {
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("OVEvents", "dummy", scoreboardTitle);
		
		obj.getScore(ChatColor.RESET.toString()).setScore(7);
		obj.getScore(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Scores:").setScore(6);
		
		if(mainClass.getEventData().getStringList("participants").size() < 3) {
			obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(3);
			obj.getScore(ChatColor.AQUA + "play.onlyvanilla.org").setScore(2);
		} else {
			obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(2);
			obj.getScore(ChatColor.AQUA + "play.onlyvanilla.org").setScore(1);
		}
		
		int counter = 5;
		for(String s : mainClass.getEventData().getStringList("participants")){
			Player p = Bukkit.getPlayer(s);
			
			//if player leaves before event starts REMOVE FROM LIST!
			if(p == null) {
				Bukkit.broadcastMessage(mainClass.prefix + ChatColor.RED + s + " has left the game before the event started and therefore the event will be cancelled!");
				
				//cancel runnable if player is found not to be online
				instance.cancel();
				
				//set boolean to false
				allOnline = false;
			} else {
				obj.getScore(p.getName() + ": " + ChatColor.YELLOW + className.winningEventSection.getInt(p.getName())).setScore(counter);
				counter--;
			
				if(counter == 2) {
					break;
				}
			}
		}
		
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		//if all players are online set scoreboard for all players
		if(allOnline == true) {
			for(String s : mainClass.getEventData().getStringList("participants")){
				Player p = Bukkit.getPlayer(s);
				p.setScoreboard(board);
			}
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
