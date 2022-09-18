package org.onlyvanilla.ovevents.events;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.onlyvanilla.ovevents.Main;
import org.onlyvanilla.ovevents.smallevents.DetermineEventData;

public class DailyEvents implements Listener {
	
	//Main instance
	protected Main mainClass = Main.getInstance();
	
	public DetermineEventData dev1 = new DetermineEventData();
	
	//get winning event 
	String winningEvent = mainClass.dev1.getVotedEvent(mainClass.getSmallEvents(), mainClass.dev1.getList(), mainClass);
	
	//event data file from main class
	FileConfiguration eventData = mainClass.getEventData();
	
	ConfigurationSection currentEventSection = eventData.getConfigurationSection("current-event");
	public ConfigurationSection winningEventSection = currentEventSection.getConfigurationSection(winningEvent);
	
	//register and unregister events
	public void registerEvents() {
		Bukkit.broadcastMessage("Event registered");
		Bukkit.getServer().getPluginManager().registerEvents(this, mainClass);
	}
	
	public void unregisterEvents() {
		Bukkit.broadcastMessage("Event unregistered");
		HandlerList.unregisterAll(this);
	}
}
