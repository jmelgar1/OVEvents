package org.onlyvanilla.ovevents.bukkitevents;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
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
	
    @SuppressWarnings("deprecation")
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
    
    @SuppressWarnings("deprecation")
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
    	String playerName = playerUUIDSection.getString("player-name");
    	Player p = Bukkit.getServer().getPlayer(playerName);
    	
    	currentXP += XP;
    	
    	int requiredXP = (int)(Math.pow((playerLevel+1)/0.71, 2.2));
    	
    	while(currentXP >= requiredXP) {
    		playerLevel += 1;
    		currentXP -= requiredXP;
    		
    		if(playerLevel >= 1 && playerLevel < 10) {
    			sendRankupNotification(p, "NOVICE", ChatColor.GRAY, playerLevel);
    		
    		}else if(playerLevel >= 10 && playerLevel < 20) {
    			if(playerLevel % 10 == 0) {
    				sendRankupNotificationToServer("APPRENTICE", p.getName(), ChatColor.WHITE);
    			}
    			sendRankupNotification(p, "APPRENTICE", ChatColor.WHITE, playerLevel);
    		
    		} else if(playerLevel >= 20 && playerLevel < 30) {
    			if(playerLevel % 20 == 0) {
    				sendRankupNotificationToServer("INITIATE", p.getName(), ChatColor.YELLOW);
    			}
    			sendRankupNotification(p, "INITIATE", ChatColor.YELLOW, playerLevel);
    		
    		} else if(playerLevel >= 30 && playerLevel < 40) {
    			if(playerLevel % 30 == 0) {
    				sendRankupNotificationToServer("ADEPT", p.getName(), ChatColor.GOLD);
    			}
    			sendRankupNotification(p, "ADEPT", ChatColor.GOLD, playerLevel);
    		
    		} else if(playerLevel >= 40 && playerLevel < 50) {
    			if(playerLevel % 40 == 0) {
    				sendRankupNotificationToServer("SKILLED", p.getName(), ChatColor.GREEN);
    			}
    			sendRankupNotification(p, "SKILLED", ChatColor.GREEN, playerLevel);
    		
    		} else if(playerLevel >= 50 && playerLevel < 60) {
    			if(playerLevel % 50 == 0) {
    				sendRankupNotificationToServer("VETERAN", p.getName(), ChatColor.DARK_BLUE);
    			}
    			sendRankupNotification(p, "VETERAN", ChatColor.DARK_BLUE, playerLevel);
    		
    		} else if(playerLevel >= 60 && playerLevel < 70) {
    			if(playerLevel % 60 == 0) {
    				sendRankupNotificationToServer("SPECIALIST", p.getName(), ChatColor.DARK_PURPLE);
    			}
    			sendRankupNotification(p, "SPECIALIST", ChatColor.DARK_PURPLE, playerLevel);
    		
    		} else if(playerLevel >= 70 && playerLevel < 80) {
    			if(playerLevel % 70 == 0) {
    				sendRankupNotificationToServer("EXPERT", p.getName(), ChatColor.LIGHT_PURPLE);
    			}
    			sendRankupNotification(p, "EXPERT", ChatColor.LIGHT_PURPLE, playerLevel);
    		
    		} else if(playerLevel >= 80 && playerLevel < 90) {
    			if(playerLevel % 80 == 0) {
    				sendRankupNotificationToServer("ELITE", p.getName(), ChatColor.AQUA);
    			}
    			sendRankupNotification(p, "ELITE", ChatColor.AQUA, playerLevel);
    		
    		} else if(playerLevel >= 90 && playerLevel < 100) {
    			if(playerLevel % 90 == 0) {
    				sendRankupNotificationToServer("MASTER", p.getName(), ChatColor.DARK_GREEN);
    			}
    			sendRankupNotification(p, "MASTER", ChatColor.DARK_GREEN, playerLevel);
    		
    		} else if(playerLevel >= 100) {
    			if(playerLevel % 100 == 0) {
    				sendRankupNotificationToServer("GRANDMASTER", p.getName(), ChatColor.DARK_AQUA);
    			}
    			sendRankupNotification(p, "GRANDMASTER", ChatColor.DARK_AQUA, playerLevel);
    		}
    		
    		requiredXP = (int)(Math.pow(playerLevel/0.71, 2.2));
    	}
    	
    	playerUUIDSection.set("level", playerLevel);
    	playerUUIDSection.set("levelExperience", currentXP);
    }
    
    void sendRankupNotification(Player p, String rank, ChatColor color, int playerLevel) {
    	p.sendTitle(ChatColor.GOLD + "" + ChatColor.BOLD + "RANKUP", color + "Level " + playerLevel + " " + rank, 35, 60, 35);
		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_VILLAGER_CELEBRATE, 1F, 1F);
    }
    
    void sendRankupNotificationToServer(String rank, String playerName, ChatColor color) {
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			p.sendMessage("");
			p.sendMessage(ChatColor.LIGHT_PURPLE + playerName + " has ranked up to " + color + "" + ChatColor.BOLD + rank);
			p.sendMessage("");
			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1F, 1F);
		}
    }
    
    public void removeLevelXP(double XP, ConfigurationSection playerUUIDSection) {
       	
    	double currentXP = playerUUIDSection.getDouble("levelExperience");
    	int playerLevel = playerUUIDSection.getInt("level");
    	
    	currentXP -= XP;
    	
    	double requiredXP = (Math.pow((playerLevel-1)/0.71, 2.2));
    	
    	while(currentXP < requiredXP) {
    		if(playerLevel == 1) {
    			break;
    		} else {
    			playerLevel -= 1;
        		requiredXP = (Math.pow((playerLevel-1)/0.71, 2.2));
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
    
    @SuppressWarnings("deprecation")
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
