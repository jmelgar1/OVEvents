package org.onlyvanilla.ovevents.smalleventmanager;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.onlyvanilla.ovevents.Main;
import org.onlyvanilla.ovevents.managers.DetermineEventData;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;

public class DailyEvents implements Listener {
	
	//Main instance
	protected Main mainClass = Main.getInstance();
	
	public DetermineEventData dev1 = new DetermineEventData();
	
	//get winning event 
	public String winningEvent = mainClass.dev1.getVotedEvent(mainClass.getSmallEvents(), mainClass.dev1.getList(), mainClass);
	
	//event data file from main class
	public FileConfiguration eventData = mainClass.getEventData();
	
	ConfigurationSection currentEventSection = eventData.getConfigurationSection("current-event");
	public ConfigurationSection winningEventSection = currentEventSection.getConfigurationSection(winningEvent);
	
	//register and unregister events1
	public void registerEvents() {
		try{
			Bukkit.getServer().getPluginManager().registerEvents(this, mainClass);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void unregisterEvents() {
		try{
			HandlerList.unregisterAll(this);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	//FOR MINING EVENTS
	protected CoreProtectAPI getCoreProtect() {
        Plugin plugin = mainClass.getServer().getPluginManager().getPlugin("CoreProtect");

        // Check that CoreProtect is loaded
        if (plugin == null || !(plugin instanceof CoreProtect)) {
            return null;
        }

        // Check that the API is enabled
        CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();
        if (CoreProtect.isEnabled() == false) {
            return null;
        }

        // Check that a compatible version of the API is loaded
        if (CoreProtect.APIVersion() < 9) {
            return null;
        }

        return CoreProtect;
	}
}
