package org.onlyvanilla.ovevents;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.onlyvanilla.ovevents.bukkitevents.editPlayerPoints;
import org.onlyvanilla.ovevents.bukkitevents.playerEvents;
import org.onlyvanilla.ovevents.commands.ovevote;
import org.onlyvanilla.ovevents.commands.ovprofile;
import org.onlyvanilla.ovevents.commands.admin.configureStats;
import org.onlyvanilla.ovevents.commands.admin.configureStatsBC;
import org.onlyvanilla.ovevents.commands.admin.configureStatsCT;
import org.onlyvanilla.ovevents.commands.admin.configureStatsMA;
import org.onlyvanilla.ovevents.commands.admin.configureStatsRVB;
import org.onlyvanilla.ovevents.commands.admin.oveEndVote;
import org.onlyvanilla.ovevents.commands.admin.oveForceVote;
import org.onlyvanilla.ovevents.commands.admin.overeload;
import org.onlyvanilla.ovevents.runnables.CountdownTimer;
import org.onlyvanilla.ovevents.runnables.sendDailyEventVote;
import org.onlyvanilla.ovevents.smallevents.DetermineEventData;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Main extends JavaPlugin implements Listener{
	
	//Main instance
	private static Main instance;
	
	//main config.yml
	FileConfiguration config;
	File cfile;
	
	//player data file
	private File playerDataFile;
	private FileConfiguration playerData;
	
	//small events file
	private File smallEventsFile;
	private FileConfiguration smallEvents;
	
	//event data file
	private File eventDataFile;
	private FileConfiguration eventData;
	
	public DetermineEventData dev1 = new DetermineEventData();
	
	//OnlyVanilla Prefix
	public String prefix = ChatColor.LIGHT_PURPLE + "[" +
			 ChatColor.AQUA + "" + ChatColor.BOLD + "O" +
			 ChatColor.WHITE + "" + ChatColor.BOLD + "V" +
			 ChatColor.LIGHT_PURPLE + "] ";
	 
	public static void main(String[] args) {
		
	}
		@Override
		public void onEnable() {
			instance = this;
			System.out.println("(!) OVEvents Enabled");	
			
			//get main config
			this.saveDefaultConfig();
			config = getConfig();
			config.options().copyDefaults(true);
			saveConfig();
			cfile = new File(getDataFolder(), "config.yml");
			
			//create playerdatafiles
			createPlayerDataFile();
			
			//create smalleventfiles
			createSmallEventsFile();

			//create eventdata
			createEventDataFile();
			
			//create eventdata
			createEventDataFile();
			
			//shuffle events and clear participation list
			dev1.ShuffleEvents(smallEvents);
			System.out.println("[OVEvents] Events Shuffled");
			dev1.clearParticipationList(instance);
			
			//plugin commands
			this.getCommand("ovprofile").setExecutor(new ovprofile());
			this.getCommand("ovevote").setExecutor(new ovevote());
			
			//admin commands
			this.getCommand("configurestats").setExecutor(new configureStats());
			this.getCommand("configurestatsbc").setExecutor(new configureStatsBC());
			this.getCommand("configurestatsct").setExecutor(new configureStatsCT());
			this.getCommand("configurestatsma").setExecutor(new configureStatsMA());
			this.getCommand("configurestatsrvb").setExecutor(new configureStatsRVB());
			this.getCommand("overeload").setExecutor(new overeload());
			this.getCommand("oveforcevote").setExecutor(new oveForceVote());
			this.getCommand("oveendvote").setExecutor(new oveEndVote());
			
			//register events
			getServer().getPluginManager().registerEvents(new ovprofile(), this);
			
			//admin events
			getServer().getPluginManager().registerEvents(new playerEvents(), this);
			getServer().getPluginManager().registerEvents(new configureStats(), this);
			getServer().getPluginManager().registerEvents(new configureStatsCT(), this);
			getServer().getPluginManager().registerEvents(new configureStatsBC(), this);
			getServer().getPluginManager().registerEvents(new configureStatsMA(), this);
			getServer().getPluginManager().registerEvents(new configureStatsRVB(), this);
			getServer().getPluginManager().registerEvents(new editPlayerPoints(), this);
			getServer().getPluginManager().registerEvents(new ovevote(), this);
	
			//Bukkit runnable (THIS WILL ONLY RUN IF >=2 PLAYERS ARE ONLINE!!!)	
			sendDailyEventVote dailyVote = new sendDailyEventVote();
			
			dailyVote.runTaskTimer(this, 0, 30000);
		}
		
		@Override
		public void onDisable() {
			System.out.println("(!) OVEvents Disabled");
			
		}
		
		//Main instance
		public static Main getInstance() {
			return instance;
		}
		
		//PLAYER DATA FILE
		public void savePlayerDataFile() {
			try {
				playerData.save(playerDataFile);
			} catch (IOException e) {
				Bukkit.getConsoleSender().sendMessage("Couldn't save playerdata.yml");
			}
		}
		
		public void reloadPlayerDataFile() {
			playerData = YamlConfiguration.loadConfiguration(playerDataFile);
		}
		
		public FileConfiguration getPlayerData() {
			return this.playerData;
		}
		
		private void createPlayerDataFile() {
			playerDataFile = new File(getDataFolder(), "playerdata.yml");
			if(!playerDataFile.exists()) {
				playerDataFile.getParentFile().mkdirs();
				saveResource("playerdata.yml", false);
				System.out.println("(!) playerdata.yml created");
			}
			
			playerData = new YamlConfiguration();
			try {
				playerData.load(playerDataFile);
				System.out.println("(!) playerdata.yml loaded");
			} catch(IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			} 
		}
		
		//SMALL EVENTS FILE
		public void saveSmallEventsFile() {
			try {
				smallEvents.save(smallEventsFile);
			} catch (IOException e) {
				Bukkit.getConsoleSender().sendMessage("Couldn't save smallevents.yml");
			}
		}
		
		private void createSmallEventsFile() {
			smallEventsFile = new File(getDataFolder(), "smallevents.yml");
			if(!smallEventsFile.exists()) {
				smallEventsFile.getParentFile().mkdirs();
				saveResource("smallevents.yml", false);
				System.out.println("(!) smallevents.yml created");
			}
			
			smallEvents = new YamlConfiguration();
			try {
				smallEvents.load(smallEventsFile);
				System.out.println("(!) smallevents.yml loaded");
			} catch(IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			} 
		}
		
		public FileConfiguration getSmallEvents() {
			return this.smallEvents;
		}
		
		//EVENT DATA FILE
		public void saveEventDataFile() {
			try {
				eventData.save(eventDataFile);
			} catch (IOException e) {
				Bukkit.getConsoleSender().sendMessage("Couldn't save eventdata.yml");
			}
		}
		
		public void reloadEventDataFile() {
			eventData = YamlConfiguration.loadConfiguration(eventDataFile);
		}
		
		private void createEventDataFile() {
			eventDataFile = new File(getDataFolder(), "eventdata.yml");
			if(!eventDataFile.exists()) {
				eventDataFile.getParentFile().mkdirs();
				saveResource("eventdata.yml", false);
				System.out.println("(!) eventdata.yml created");
			}
			
			eventData = new YamlConfiguration();
			try {
				eventData.load(eventDataFile);
				System.out.println("(!) eventdata.yml loaded");
			} catch(IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			} 
		}
		public FileConfiguration getEventData() {
			return this.eventData;
		}
	}