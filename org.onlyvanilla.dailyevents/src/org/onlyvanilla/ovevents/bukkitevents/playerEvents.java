package org.onlyvanilla.ovevents.bukkitevents;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.onlyvanilla.ovevents.Main;
import org.onlyvanilla.ovevents.commands.ovprofile;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;

public class playerEvents implements Listener {
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	//Get playerdataconfig
	FileConfiguration playerDataConfig = mainClass.getPlayerData();
	
	//Luckperms api
	static LuckPerms api = LuckPermsProvider.get();
	
	//ovprofile
	ovprofile OVProfile = new ovprofile();
	
	//when a player joins the server
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		Player p = event.getPlayer();
		String uniqueIDString = p.getUniqueId().toString();
		
		if(playerDataConfig.get(uniqueIDString) == null) {
			
			//send message to console
			System.out.println("[DailyEvents] Creating new user for " + p.getName());
			
			//create new player
			ConfigurationSection playerUUID = playerDataConfig.createSection(uniqueIDString);
			
			//create sections playername section apart from UUID
			playerUUID.createSection("player-name");
			playerUUID.set("player-name", p.getName());
			
			//create and set level to 0
			playerUUID.createSection("level");
			playerUUID.set("level", 1);
			playerUUID.createSection("levelExperience");
			playerUUID.set("levelExperience", 0);
			
			//create stats section for big/small events
			playerUUID.createSection("stats");
			ConfigurationSection statsSection = playerUUID.getConfigurationSection("stats");
			statsSection.createSection("big-events");
			statsSection.createSection("small-events");
			
			//create section for individual big events
			ConfigurationSection bigEventsSection = statsSection.getConfigurationSection("big-events");
			bigEventsSection.createSection("champions-tour");
			bigEventsSection.createSection("build-competitions");
			bigEventsSection.createSection("map-art-contests");
			bigEventsSection.createSection("red-vs-blue");
			
			//create section for individual small events
			ConfigurationSection smallEventsSection = statsSection.getConfigurationSection("small-events");
			for(String events : ovprofile.events) {
				smallEventsSection.set(events, 0);
			}

			
			playerUUID.createSection("rank");
			playerUUID.set("rank", getPlayerGroup(p.getName()));
			playerUUID.createSection("date-joined");
			mainClass.savePlayerDataFile();		
		} else {
			//get existing user
			ConfigurationSection existingPlayerUUID = playerDataConfig.getConfigurationSection(uniqueIDString);
			
			//if rank in config does not equal new rank change it
			if(!existingPlayerUUID.getString("rank").equals(getPlayerGroup(p.getName()))) {
				existingPlayerUUID.set("rank", getPlayerGroup(p.getName()));
				mainClass.savePlayerDataFile();	
			}
		}
	}
	
	//get player rank
    public static String getPlayerGroup(String username) {
		//Find user group
		UUID userUUID = Bukkit.getOfflinePlayer(username).getUniqueId();
		User user = api.getUserManager().loadUser(userUUID).join();
		
		//get user groups
		String group = user.getPrimaryGroup();
		
		//return player group
		return group;
    }
}
