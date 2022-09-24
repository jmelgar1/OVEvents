package org.onlyvanilla.ovevents.events.killingevents;

import org.bukkit.Sound;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.onlyvanilla.ovevents.runnables.UpdateScoreboard;
import org.onlyvanilla.ovevents.smalleventmanager.DailyEvents;

import net.md_5.bungee.api.ChatColor;

public class BringHomeTheBacon extends DailyEvents implements Listener {
	
	@EventHandler
	public void killPig(EntityDeathEvent event) {
		
		LivingEntity entity = event.getEntity();
		Player p = entity.getKiller();
		boolean baby = false; 

		//ensure mob was killed by a player
		if(!(entity.getKiller() == null)) {
			
			if(entity.getType() == EntityType.PIG) {
				
				Ageable age = (Ageable) entity;
			    baby = !age.isAdult();
				
				boolean contains = dev1.getPlayerParticipants(mainClass.getEventData().getStringList("participants")).contains(p);
				
				if(contains == true) {
					int score = winningEventSection.getInt(p.getName());
					
					if(baby == true) {
						score += 2;
						p.sendMessage(ChatColor.LIGHT_PURPLE + "You killed a baby pig! " + ChatColor.GOLD + "+2 Points!");
					} else {
						score += 1;
					}
					
					winningEventSection.set(p.getName(), score);
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
					mainClass.saveEventDataFile();
					
					UpdateScoreboard updateScoreboard = new UpdateScoreboard();
					updateScoreboard.run();
				}
			}
		}
	}
}
