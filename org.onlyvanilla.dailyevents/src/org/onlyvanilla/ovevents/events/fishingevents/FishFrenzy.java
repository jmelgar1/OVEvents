package org.onlyvanilla.ovevents.events.fishingevents;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.onlyvanilla.ovevents.runnables.UpdateScoreboard;
import org.onlyvanilla.ovevents.smalleventmanager.DailyEvents;

import net.md_5.bungee.api.ChatColor;

public class FishFrenzy extends DailyEvents implements Listener {
	
	@EventHandler
	public void catchFish(PlayerFishEvent event) {
		
		Player p = event.getPlayer();
		
		//check for block type (aka. emerald_ore, diamond ore, etc)
		if(event.getState() == State.CAUGHT_FISH) {
			
			boolean contains = dev1.getPlayerParticipants(mainClass.getEventData().getStringList("participants")).contains(p);
			
			if(contains == true) {
				
				int currentScore = winningEventSection.getInt(p.getName());
				
				Item item = (Item) event.getCaught();
				
				//if player catches fish
				if (item.getItemStack().getType() == Material.COD) {
					currentScore += 1;
					p.sendMessage(ChatColor.LIGHT_PURPLE + "You caught cod! " + ChatColor.GOLD + "+1 point");
				} else if (item.getItemStack().getType() == Material.SALMON) {
					currentScore += 2;
					p.sendMessage(ChatColor.LIGHT_PURPLE + "You caught salmon! " + ChatColor.GOLD + "+2 points");
				} else if (item.getItemStack().getType() == Material.PUFFERFISH) {
					currentScore += 3;
					p.sendMessage(ChatColor.LIGHT_PURPLE + "You caught pufferfish! " + ChatColor.GOLD + "+3 points");
				} else if (item.getItemStack().getType() == Material.TROPICAL_FISH) {
					currentScore += 4;
					p.sendMessage(ChatColor.LIGHT_PURPLE + "You caught tropical fish! " + ChatColor.GOLD + "+4 points");
					
				//if player catches treasure
				} else if (item.getItemStack().getType() == Material.ENCHANTED_BOOK ||
						  item.getItemStack().getType() == Material.FISHING_ROD ||
						  item.getItemStack().getType() == Material.NAME_TAG ||
						  item.getItemStack().getType() == Material.NAUTILUS_SHELL ||
						  item.getItemStack().getType() == Material.SADDLE) {
					currentScore += 7;
					p.sendMessage(ChatColor.LIGHT_PURPLE + "You caught treasure! " + ChatColor.GOLD + "+7 points");
				} 
				
				winningEventSection.set(p.getName(), currentScore);

				p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
				mainClass.saveEventDataFile();
				
				UpdateScoreboard updateScoreboard = new UpdateScoreboard();
				updateScoreboard.run();
			}
		}
	}
}
