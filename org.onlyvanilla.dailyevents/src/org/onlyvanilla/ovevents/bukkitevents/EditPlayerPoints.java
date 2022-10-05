package org.onlyvanilla.ovevents.bukkitevents;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.onlyvanilla.ovevents.Main;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.data.DataMutateResult;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.InheritanceNode;
import net.md_5.bungee.api.ChatColor;

public class EditPlayerPoints implements Listener{
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	//Get playerdataconfig
	FileConfiguration playerDataConfig = mainClass.getPlayerData();
	
	//Luckperms api
	static LuckPerms api = LuckPermsProvider.get();
	
	//add points to users profiles
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
    
    //remove points from users profiles
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
    
    //add XP to user
    public void addLevelXP(double XP, ConfigurationSection playerUUIDSection) {
   	
    	double currentXP = playerUUIDSection.getDouble("levelExperience");
    	currentXP += XP;
    	playerUUIDSection.set("levelExperience", currentXP);
    	mainClass.savePlayerDataFile();
    }
    
    
    public void removeLevelXP(double XP, ConfigurationSection playerUUIDSection) {
       	
    	double currentXP = playerUUIDSection.getDouble("levelExperience");
    	currentXP -= XP;
    	playerUUIDSection.set("levelExperience", currentXP);
    	mainClass.savePlayerDataFile();
    }
    
    //set user XP
    public void setLevelXP(double XP, ConfigurationSection playerUUIDSection) {
    	playerUUIDSection.set("levelExperience", XP);
    	mainClass.savePlayerDataFile();
    }
    
    //set user XP
    public int getPlayerXP(ConfigurationSection playerUUIDSection) {
    	double currentXP = playerUUIDSection.getDouble("levelExperience");
    	return (int)currentXP;
    }
    
    //add user to in_event group
    void addUserToGroup(String group, Player p) {
		User user = api.getPlayerAdapter(Player.class).getUser(p);
		user.data().add(InheritanceNode.builder(group).value(true).build());
		api.getUserManager().saveUser(user);
    }
    
//    void sendXPGainNotification(Player p, String rank, ChatColor color, int playerLevel) {
//    	p.sendTitle(ChatColor.GOLD + "" + ChatColor.BOLD + "RANKUP", color + "Level " + playerLevel + " " + rank, 35, 60, 35);
//		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_VILLAGER_CELEBRATE, 1F, 1F);
//    }
    
    @EventHandler
    public void checkClickType(final InventoryClickEvent event, ConfigurationSection eventSection, String configKey, Player p, double XP, String IGN) {
		if(event.getClick() == ClickType.LEFT) {
			addPoints(eventSection.getInt(configKey), configKey, eventSection, p, XP, IGN);
		} else if(event.getClick() == ClickType.RIGHT) {
			removePoints(eventSection.getInt(configKey), configKey, eventSection, p, XP, IGN);
		}
		mainClass.savePlayerDataFile();
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
