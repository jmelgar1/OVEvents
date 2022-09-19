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
			ghastHunter.registerEvents();
			
			eventWinnerClass = ghastHunter;
			
			scoreboardTitle = ChatColor.WHITE + "" + ChatColor.BOLD + "GHAST HUNTER";
			createScoreboard(ghastHunter);
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, 24000);
			
		} else if (winningEventNS.equals("ShulkerHunt")) {
			ShulkerHunt shulkerHunt = new ShulkerHunt();
			shulkerHunt.registerEvents();
			
			eventWinnerClass = shulkerHunt;
			
			scoreboardTitle = ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "SHULKER HUNT";
			createScoreboard(shulkerHunt);
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, 24000);
			
		} else if (winningEventNS.equals("BadBats")) {
			BadBats badBats = new BadBats();
			badBats.registerEvents();
			
			eventWinnerClass = badBats;
			
			scoreboardTitle = ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "BAD BATS";
			createScoreboard(badBats);
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, 24000);
			
		} else if (winningEventNS.equals("HilariousHomicide")) {
			HilariousHomicide hilariousHomicide = new HilariousHomicide();
			hilariousHomicide.registerEvents();
			
			eventWinnerClass = hilariousHomicide;
			
			scoreboardTitle = ChatColor.DARK_RED + "" + ChatColor.BOLD + "HILARIOUS HOMICIDE";
			createScoreboard(hilariousHomicide);
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, 24000);
			
		} else if (winningEventNS.equals("WorldWarZ")) {
			WorldWarZ worldWarZ = new WorldWarZ();
			worldWarZ.registerEvents();
			
			eventWinnerClass = worldWarZ;
			
			scoreboardTitle = ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "WORLD WAR Z";
			createScoreboard(worldWarZ);
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, 24000);
			
		} else if (winningEventNS.equals("WardenWarrior")) {
			WardenWarrior wardenWarrior = new WardenWarrior();
			wardenWarrior.registerEvents();
			
			eventWinnerClass = wardenWarrior;
			
			scoreboardTitle = ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "WARDEN WARRIOR";
			createScoreboard(wardenWarrior);
			
			//end event after 2 hours
			endEvent.runTaskLater(mainClass, 144000);
			
		} else if (winningEventNS.equals("FishFrenzy")){
			FishFrenzy fishFrenzy = new FishFrenzy();
			fishFrenzy.registerEvents();
			
			eventWinnerClass = fishFrenzy;
			
			scoreboardTitle = ChatColor.BLUE + "" + ChatColor.BOLD + "FISH FRENZY";
			createScoreboard(fishFrenzy);
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, 24000);
			
		} else if (winningEventNS.equals("CowTipper")){
			CowTipper cowTipper = new CowTipper();
			cowTipper.registerEvents();
		
			eventWinnerClass = cowTipper;
			
			scoreboardTitle = ChatColor.WHITE + "" + ChatColor.BOLD + "COW TIPPER";
			createScoreboard(cowTipper);
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, 24000);
			
		} else if (winningEventNS.equals("BringHomeTheBacon")){
			BringHomeTheBacon bringHomeTheBacon = new BringHomeTheBacon();
			bringHomeTheBacon.registerEvents();
			
			eventWinnerClass = bringHomeTheBacon;
			
			scoreboardTitle = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "BRING HOME THE BACON";
			createScoreboard(bringHomeTheBacon);
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, 24000);
			
		} else if (winningEventNS.equals("CookieClicker")){
			CookieClicker cookieClicker = new CookieClicker();
			cookieClicker.registerEvents();
			
			eventWinnerClass = cookieClicker;
			
			scoreboardTitle = ChatColor.YELLOW + "" + ChatColor.BOLD + "COOKIE CLICKER";
			createScoreboard(cookieClicker);
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, 400);
			
		} else if (winningEventNS.equals("DragonSlayer")){
			DragonSlayer dragonSlayer = new DragonSlayer();
			dragonSlayer.registerEvents();
			
			eventWinnerClass = dragonSlayer;
			
			scoreboardTitle = ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "DRAGON SLAYER";
			createScoreboard(dragonSlayer);
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, 72000);
		}
	}
	
	void createScoreboard(DailyEvents className) {
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("OVEvents", "dummy", scoreboardTitle);
		
		obj.getScore(ChatColor.RESET.toString()).setScore(7);
		obj.getScore(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Scores:").setScore(6);
		
		int counter = 5;
		for(String s : mainClass.getEventData().getStringList("participants")){
			Player p = Bukkit.getPlayer(s);
			obj.getScore(p.getName() + ": " + ChatColor.YELLOW + className.winningEventSection.getInt(p.getName())).setScore(counter);
			counter--;
			
			if(counter == 2) {
				break;
			}
		}
		
		if(mainClass.getEventData().getStringList("participants").size() < 3) {
			obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(3);
			obj.getScore(ChatColor.AQUA + "play.onlyvanilla.org").setScore(2);
		} else {
			obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(2);
			obj.getScore(ChatColor.AQUA + "play.onlyvanilla.org").setScore(1);
		}
		
		
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
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
