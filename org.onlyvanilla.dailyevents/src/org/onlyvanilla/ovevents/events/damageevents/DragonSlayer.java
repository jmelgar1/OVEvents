package org.onlyvanilla.ovevents.events.damageevents;

import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.onlyvanilla.ovevents.events.DailyEvents;
import org.onlyvanilla.ovevents.runnables.UpdateScoreboard;

import net.md_5.bungee.api.ChatColor;

public class DragonSlayer extends DailyEvents implements Listener{
	
	@EventHandler
	public void dealDamageToEnderDragon(EntityDamageByEntityEvent event) {
		
		Entity entity = event.getEntity();
		Player p;
		int damage = (int)Math.ceil(event.getDamage());
		
		//ADD ABILITY TO COUNT BROKEN END CRYSTALS AS DAMAGE
			
		if(entity.getType() == EntityType.ENDER_DRAGON) {
			
			//check if a player shot the dragon
			if((event.getDamager() instanceof Arrow)) {
				Arrow a = (Arrow) event.getDamager();
				p = (Player) a.getShooter();
				
			//check if a player threw a trident at the dragon
			} else if(event.getDamager() instanceof Trident) {
				Trident t = (Trident) event.getDamager();
				p = (Player) t.getShooter();
			} else {
				
				//else the player just hit the dragon (melee)
				p = (Player) event.getDamager();
			}
			
			boolean contains = dev1.getPlayerParticipants(mainClass.getEventData().getStringList("participants")).contains(p);
			
			if(contains == true) {	
				int score = winningEventSection.getInt(p.getName());
				score += damage;
				winningEventSection.set(p.getName(), score);
				p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
				p.sendMessage(ChatColor.LIGHT_PURPLE + "You dealt " + damage + " damage to the enderdragon!");
				mainClass.saveEventDataFile();
				
				UpdateScoreboard updateScoreboard = new UpdateScoreboard();
				updateScoreboard.run();
			}
		}
	}
}
