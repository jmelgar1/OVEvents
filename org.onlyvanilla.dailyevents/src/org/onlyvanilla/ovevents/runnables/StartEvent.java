package org.onlyvanilla.ovevents.runnables;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.onlyvanilla.ovevents.Main;
import org.onlyvanilla.ovevents.bukkitevents.EditPlayerPoints;
import org.onlyvanilla.ovevents.events.blockbreakevents.CoalDigger;
import org.onlyvanilla.ovevents.events.blockbreakevents.CrazyCarrots;
import org.onlyvanilla.ovevents.events.blockbreakevents.DeepDiamonds;
import org.onlyvanilla.ovevents.events.blockbreakevents.Ironworker;
import org.onlyvanilla.ovevents.events.blockbreakevents.Lumberjack;
import org.onlyvanilla.ovevents.events.blockbreakevents.NastyNetherite;
import org.onlyvanilla.ovevents.events.blockbreakevents.PreciousPotatoes;
import org.onlyvanilla.ovevents.events.craftingevents.BestBaker;
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
import org.onlyvanilla.ovevents.managers.DetermineEventData;
import org.onlyvanilla.ovevents.smalleventmanager.DailyEvents;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.InheritanceNode;
import net.md_5.bungee.api.ChatColor;

public class StartEvent extends BukkitRunnable{
	
	//Main instance
	private static StartEvent instance;
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	public DetermineEventData dev1 = new DetermineEventData();
	
	EditPlayerPoints editPlayerPoints = new EditPlayerPoints();
	
	//get winning event 
	public String winningEvent = mainClass.dev1.getVotedEvent(mainClass.getSmallEvents(), mainClass.dev1.getList(), mainClass);
	String winningEventNS = winningEvent.replaceAll("\\s", "");
	
	//initilize KillMobEvent class variable
	DailyEvents eventWinnerClass;
	
	//create empty scoreboard title name
	String scoreboardTitle;
	
	long tenMinutes = 12000;
	long twentyMinutes = 24000;
	long thirtyMinutes = 36000;
	
	//Luckperms api
	static LuckPerms api = LuckPermsProvider.get();
	
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
			endEvent.runTaskLater(mainClass, twentyMinutes);
			
		} else if (winningEventNS.equals("ShulkerHunt")) {
			ShulkerHunt shulkerHunt = new ShulkerHunt();
			
			eventWinnerClass = shulkerHunt;
			
			scoreboardTitle = ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "SHULKER HUNT";
			createScoreboard(shulkerHunt);
			
			if(allOnline == true) {
				shulkerHunt.registerEvents();
			}
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, thirtyMinutes);
			
		} else if (winningEventNS.equals("BadBats")) {
			BadBats badBats = new BadBats();
			
			eventWinnerClass = badBats;
			
			scoreboardTitle = ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "BAD BATS";
			createScoreboard(badBats);
			
			if(allOnline == true) {
				badBats.registerEvents();
			}
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, tenMinutes);
			
		} else if (winningEventNS.equals("HilariousHomicide")) {
			HilariousHomicide hilariousHomicide = new HilariousHomicide();
			
			eventWinnerClass = hilariousHomicide;
			
			scoreboardTitle = ChatColor.DARK_RED + "" + ChatColor.BOLD + "HILARIOUS HOMICIDE";
			createScoreboard(hilariousHomicide);
			
			if(allOnline == true) {
				hilariousHomicide.registerEvents();
			}
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, twentyMinutes);
			
		} else if (winningEventNS.equals("WorldWarZ")) {
			WorldWarZ worldWarZ = new WorldWarZ();
			
			eventWinnerClass = worldWarZ;
			
			scoreboardTitle = ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "WORLD WAR Z";
			createScoreboard(worldWarZ);
			
			if(allOnline == true) {
				worldWarZ.registerEvents();
			}
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, twentyMinutes);
			
		} else if (winningEventNS.equals("WardenWarrior")) {
			WardenWarrior wardenWarrior = new WardenWarrior();
			
			eventWinnerClass = wardenWarrior;
			
			scoreboardTitle = ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "WARDEN WARRIOR";
			createScoreboard(wardenWarrior);
			
			if(allOnline == true) {
				wardenWarrior.registerEvents();
			}
			
			//end event after 2 hours
			endEvent.runTaskLater(mainClass, thirtyMinutes);
			
		} else if (winningEventNS.equals("FishFrenzy")){
			FishFrenzy fishFrenzy = new FishFrenzy();
			
			eventWinnerClass = fishFrenzy;
			
			scoreboardTitle = ChatColor.BLUE + "" + ChatColor.BOLD + "FISH FRENZY";
			createScoreboard(fishFrenzy);
			
			if(allOnline == true) {
				fishFrenzy.registerEvents();
			}
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, tenMinutes);
			
		} else if (winningEventNS.equals("CowTipper")){
			CowTipper cowTipper = new CowTipper();
		
			eventWinnerClass = cowTipper;
			
			scoreboardTitle = ChatColor.WHITE + "" + ChatColor.BOLD + "COW TIPPER";
			createScoreboard(cowTipper);
			
			if(allOnline == true) {
				cowTipper.registerEvents();
			}
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, tenMinutes);
			
		} else if (winningEventNS.equals("BringHomeTheBacon")){
			BringHomeTheBacon bringHomeTheBacon = new BringHomeTheBacon();
			
			eventWinnerClass = bringHomeTheBacon;
			
			scoreboardTitle = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "BRING HOME THE BACON";
			createScoreboard(bringHomeTheBacon);

			if(allOnline == true) {
				bringHomeTheBacon.registerEvents();
			}
			
			//end event after 10 minutes
			endEvent.runTaskLater(mainClass, tenMinutes);
			
		} else if (winningEventNS.equals("CookieClicker")){
			CookieClicker cookieClicker = new CookieClicker();
			
			eventWinnerClass = cookieClicker;
			
			scoreboardTitle = ChatColor.YELLOW + "" + ChatColor.BOLD + "COOKIE CLICKER";
			createScoreboard(cookieClicker);
			
			if(allOnline == true) {
				cookieClicker.registerEvents();
			}
			
			//end event after 10 minutes
			endEvent.runTaskLater(mainClass, tenMinutes);
			
		} else if (winningEventNS.equals("DragonSlayer")){
			DragonSlayer dragonSlayer = new DragonSlayer();
			
			eventWinnerClass = dragonSlayer;
			
			scoreboardTitle = ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "DRAGON SLAYER";
			createScoreboard(dragonSlayer);
			
			if(allOnline == true) {
				dragonSlayer.registerEvents();
			}
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, thirtyMinutes);
			
		} else if (winningEventNS.equals("DeepDiamonds")){
			DeepDiamonds deepDiamonds = new DeepDiamonds();
			
			eventWinnerClass = deepDiamonds;
			
			scoreboardTitle = ChatColor.AQUA + "" + ChatColor.BOLD + "DEEP DIAMONDS";
			createScoreboard(deepDiamonds);
			
			if(allOnline == true) {
				deepDiamonds.registerEvents();
			}
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, thirtyMinutes);
		} else if (winningEventNS.equals("NastyNetherite")){
			NastyNetherite nastyNetherite = new NastyNetherite();
			
			eventWinnerClass = nastyNetherite;
			
			scoreboardTitle = ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "NASTY NETHERITE";
			createScoreboard(nastyNetherite);
			
			if(allOnline == true) {
				nastyNetherite.registerEvents();
			}
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, thirtyMinutes);
		} else if (winningEventNS.equals("CrazyCarrots")){
			CrazyCarrots crazyCarrots = new CrazyCarrots();
			
			eventWinnerClass = crazyCarrots;
			
			scoreboardTitle = ChatColor.YELLOW + "" + ChatColor.BOLD + "CRAZY CARROTS";
			createScoreboard(crazyCarrots);
			
			if(allOnline == true) {
				crazyCarrots.registerEvents();
			}
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, tenMinutes);
		} else if (winningEventNS.equals("PreciousPotatoes")){
			PreciousPotatoes preciousPotatoes = new PreciousPotatoes();
			
			eventWinnerClass = preciousPotatoes;
			
			scoreboardTitle = ChatColor.YELLOW + "" + ChatColor.BOLD + "PRECIOUS POTATOES";
			createScoreboard(preciousPotatoes);
			
			if(allOnline == true) {
				preciousPotatoes.registerEvents();
			}
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, tenMinutes);
		} else if (winningEventNS.equals("CoalDigger")){
			CoalDigger coalDigger = new CoalDigger();
			
			eventWinnerClass = coalDigger;
			
			scoreboardTitle = ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "COAL DIGGER";
			createScoreboard(coalDigger);
			
			if(allOnline == true) {
				coalDigger.registerEvents();
			}
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, tenMinutes);
		} else if (winningEventNS.equals("BestBaker")){
			BestBaker bestBaker = new BestBaker();
			
			eventWinnerClass = bestBaker;
			
			scoreboardTitle = ChatColor.GOLD + "" + ChatColor.BOLD + "BEST BAKER";
			createScoreboard(bestBaker);
			
			if(allOnline == true) {
				bestBaker.registerEvents();
			}
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, tenMinutes);
		} else if (winningEventNS.equals("Lumberjack")){
			Lumberjack lumberjack = new Lumberjack();
			
			eventWinnerClass = lumberjack;
			
			scoreboardTitle = ChatColor.BLUE + "" + ChatColor.BOLD + "LUMBERJACK";
			createScoreboard(lumberjack);
			
			if(allOnline == true) {
				lumberjack.registerEvents();
			}
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, tenMinutes);
		} else if (winningEventNS.equals("Ironworker")){
			Ironworker ironworker = new Ironworker();
			
			eventWinnerClass = ironworker;
			
			scoreboardTitle = ChatColor.GRAY + "" + ChatColor.BOLD + "IRONWORKER";
			createScoreboard(ironworker);
			
			if(allOnline == true) {
				ironworker.registerEvents();
			}
			
			//end event after 20 minutes
			endEvent.runTaskLater(mainClass, twentyMinutes);
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
		
		obj.getScore(ChatColor.RESET.toString()).setScore(10);
		obj.getScore(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Scores:").setScore(9);
		
		if(mainClass.getEventData().getStringList("participants").size() < 3) {
			obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(5);
			obj.getScore(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Time Left:").setScore(4);
			obj.getScore("time left").setScore(3);
			obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(2);
			obj.getScore(ChatColor.AQUA + "play.onlyvanilla.org").setScore(1);
		} else {
			obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(6);
			obj.getScore(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Time Left:").setScore(5);
			obj.getScore("time left").setScore(4);
			obj.getScore(ChatColor.RESET.toString() + ChatColor.RESET.toString() + ChatColor.RESET.toString()).setScore(3);
			obj.getScore(ChatColor.AQUA + "play.onlyvanilla.org").setScore(2);
		}
		
		int counter = 8;
		for(String s : mainClass.getEventData().getStringList("participants")){
			Player p = Bukkit.getPlayer(s);
			
//			String group = "in_event";
//			User user = api.getPlayerAdapter(Player.class).getUser(p);
//			user.data().add(InheritanceNode.builder(group).value(true).build());
//			api.getUserManager().saveUser(user);
			
			//if player leaves before event starts REMOVE FROM LIST!
			if(p == null) {
				
				Bukkit.broadcastMessage(mainClass.prefix + ChatColor.RED + s + " left the game before the event started and will be removed from the event!");
				
				mainClass.getEventData().set("participants", mainClass.getEventData().getStringList("participants").remove(s));
				
				mainClass.saveEventDataFile();
				
				if(mainClass.getEventData().getStringList("participants").size() < 2) {
					Bukkit.broadcastMessage(mainClass.prefix + ChatColor.RED + "The event will be cancelled since there are less than two players in the event.");
					
					//cancel runnable if player is found not to be online
					instance.cancel();
					
					//set boolean to false
					allOnline = false;
					
					SendDailyEventVote sendVote = new SendDailyEventVote();
					sendVote.runTaskLater(mainClass, 3000);
					this.cancel();
				}
			} else {
				obj.getScore(p.getName() + ": " + ChatColor.YELLOW + className.winningEventSection.getInt(p.getName())).setScore(counter);
				counter--;
			
				if(counter == 5) {
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
		
		UpdateScoreboard updateScoreboard = new UpdateScoreboard();
		updateScoreboard.setCountdown();
		updateScoreboard.run();
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
