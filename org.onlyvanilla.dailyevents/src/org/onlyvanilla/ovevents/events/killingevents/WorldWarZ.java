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

public class WorldWarZ extends DailyEvents implements Listener{
	
	@EventHandler
	public void killZombie(EntityDeathEvent event) {
		
		LivingEntity entity = event.getEntity();
		Player p = entity.getKiller();
		
		//ensure mob was killed by a player
		if(!(entity.getKiller() == null)) {
			
			if(entity.getType() == EntityType.ZOMBIE ||
			   entity.getType() == EntityType.ZOMBIE_VILLAGER) {
				
				boolean contains = dev1.getPlayerParticipants(mainClass.getEventData().getStringList("participants")).contains(p);
				
				if(contains == true) {
					
					int score = winningEventSection.getInt(p.getName());
					
					if(entity.getType() == EntityType.ZOMBIE) {
						score += 1;
					} else if(entity.getType() == EntityType.ZOMBIE_VILLAGER) {
						score += 2;
						p.sendMessage(ChatColor.LIGHT_PURPLE + "You killed a villager zombie! " + ChatColor.GOLD + "+2 Points!");
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
