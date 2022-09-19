package org.onlyvanilla.ovevents.bukkitevents;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.onlyvanilla.ovevents.Main;

import net.md_5.bungee.api.ChatColor;

public class EditPlayerPoints implements Listener{
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	//Get playerdataconfig
	FileConfiguration playerDataConfig = mainClass.getPlayerData();
	
    public void addPoints(int standingCount,  String standingKey, ConfigurationSection eventSection, Player p, double XP, String IGN) {
		//get firstplace count
		standingCount = eventSection.getInt(standingKey);	

		//add one to count
		standingCount += 1;
		
		//set the value in config
		eventSection.set(standingKey, standingCount);
		p.sendMessage(ChatColor.GREEN + "Added 1 point to " + standingKey);
		
		//xp leveling stuff
		String playerUUID = Bukkit.getServer().getOfflinePlayer(IGN).getUniqueId().toString();
		ConfigurationSection playerUUIDSection = playerDataConfig.getConfigurationSection(playerUUID);
		addLevelXP(XP, playerUUIDSection);
		
		mainClass.savePlayerDataFile();
    }
    
    public void removePoints(int standingCount,  String standingKey, ConfigurationSection eventSection, Player p, double XP, String IGN) {
		//get firstplace count
		standingCount = eventSection.getInt(standingKey);	

		//add one to count
		standingCount -= 1;
		
		//set the value in config
		eventSection.set(standingKey, standingCount);
		p.sendMessage(ChatColor.RED + "Removed 1 point from " + standingKey);
		
		//xp leveling stuff
		String playerUUID = Bukkit.getServer().getOfflinePlayer(IGN).getUniqueId().toString();
		ConfigurationSection playerUUIDSection = playerDataConfig.getConfigurationSection(playerUUID);
		removeLevelXP(XP, playerUUIDSection);
		
		mainClass.savePlayerDataFile(); 
    }
    
    public void addLevelXP(double XP, ConfigurationSection playerUUIDSection) {
   	
    	double currentXP = playerUUIDSection.getDouble("levelExperience");
    	int playerLevel = playerUUIDSection.getInt("level");
    	
    	currentXP += XP;
    	
    	double requiredXP = (Math.pow(playerLevel/0.71, 2.2));
    	
    	while(currentXP >= requiredXP) {
    		playerLevel += 1;
    		requiredXP = (Math.pow(playerLevel/0.71, 2.2));
    	}
    	
    	playerUUIDSection.set("level", playerLevel);
    	playerUUIDSection.set("levelExperience", currentXP);
    }
    
    public void removeLevelXP(double XP, ConfigurationSection playerUUIDSection) {
       	
    	double currentXP = playerUUIDSection.getDouble("levelExperience");
    	int playerLevel = playerUUIDSection.getInt("level");
    	
    	currentXP -= XP;
    	
    	double requiredXP = (Math.pow(playerLevel/0.71, 2.2));
    	
    	while(currentXP < requiredXP) {
    		if(playerLevel == 1) {
    			break;
    		} else {
    			playerLevel -= 1;
        		requiredXP = (Math.pow(playerLevel/0.71, 2.2));
    		}
    	}
    	
    	playerUUIDSection.set("level", playerLevel);
    	playerUUIDSection.set("levelExperience", currentXP);
    }
    
    @EventHandler
    public void checkClickType(final InventoryClickEvent event, ConfigurationSection eventSection, String configKey, Player p, double XP, String IGN) {
		if(event.getClick() == ClickType.LEFT) {
			addPoints(eventSection.getInt(configKey), configKey, eventSection, p, XP, IGN);
		} else if(event.getClick() == ClickType.RIGHT) {
			removePoints(eventSection.getInt(configKey), configKey, eventSection, p, XP, IGN);
		}
    }
    
    public ConfigurationSection getBigEventSection(String IGN, String eventKey) {
		//player name to uuid
		UUID playerUUID = Bukkit.getServer().getOfflinePlayer(IGN).getUniqueId();
		
		//find player in playerdata.yml
		ConfigurationSection playerUUIDSection = playerDataConfig.getConfigurationSection(playerUUID.toString());
		ConfigurationSection statsSection = playerUUIDSection.getConfigurationSection("stats");
		ConfigurationSection bigEventsSection = statsSection.getConfigurationSection("big-events");
		ConfigurationSection eventSection = bigEventsSection.getConfigurationSection(eventKey);
		
		return eventSection;
    }
}
