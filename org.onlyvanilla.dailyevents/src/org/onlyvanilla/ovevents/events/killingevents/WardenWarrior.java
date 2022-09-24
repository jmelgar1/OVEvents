package org.onlyvanilla.ovevents.events.killingevents;

import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.onlyvanilla.ovevents.runnables.UpdateScoreboard;
import org.onlyvanilla.ovevents.smalleventmanager.DailyEvents;

public class WardenWarrior extends DailyEvents implements Listener {
	
	@EventHandler
	public void killWarden(EntityDeathEvent event) {
		
		LivingEntity entity = event.getEntity();
		Player p = entity.getKiller();
		
		//ensure mob was killed by a player
		if(!(entity.getKiller() == null)) {
			
			if(entity.getType() == EntityType.WARDEN) {
				
				boolean contains = dev1.getPlayerParticipants(mainClass.getEventData().getStringList("participants")).contains(p);
				
				if(contains == true) {
					int currentScore = winningEventSection.getInt(p.getName());
					int newScore = currentScore += 1;
					winningEventSection.set(p.getName(), newScore);
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 1F, 1F);
					mainClass.saveEventDataFile();
					
					UpdateScoreboard updateScoreboard = new UpdateScoreboard();
					updateScoreboard.run();
				}
			}
		}
	}
}
