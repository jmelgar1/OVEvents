package org.onlyvanilla.ovevents.events.killingevents;

import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.onlyvanilla.ovevents.events.DailyEvents;
import org.onlyvanilla.ovevents.runnables.UpdateScoreboard;

import net.md_5.bungee.api.ChatColor;

public class GhastHunter extends DailyEvents implements Listener{
	
	@EventHandler
	public void killGhast(EntityDeathEvent event) {
		
		LivingEntity entity = event.getEntity();
		Player p = entity.getKiller();
		
		//ensure mob was killed by a player
		if(!(entity.getKiller() == null)) {
			
			if(entity.getType() == EntityType.GHAST) {
				
				boolean contains = dev1.getPlayerParticipants(mainClass.getEventData().getStringList("participants")).contains(p);
				
				if(contains == true) {
					int currentScore = winningEventSection.getInt(p.getName());
					int newScore = currentScore += 1;
					winningEventSection.set(p.getName(), newScore);
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
					mainClass.saveEventDataFile();
					
					UpdateScoreboard updateScoreboard = new UpdateScoreboard();
					updateScoreboard.run();
				}
			}
		}
	}
}
