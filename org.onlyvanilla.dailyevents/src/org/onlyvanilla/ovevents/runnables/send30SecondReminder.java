package org.onlyvanilla.ovevents.runnables;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.onlyvanilla.ovevents.Main;
import org.onlyvanilla.ovevents.managers.DetermineEventData;


public class send30SecondReminder extends BukkitRunnable{
    
	//Main instance
	private Main mainClass = Main.getInstance();
	
	public DetermineEventData dev1 = new DetermineEventData();

	public void run() {
		//replace with players who signed up
		for(Player p : dev1.getPlayerParticipants(mainClass.getEventData().getStringList("participants"))) {		
			p.sendMessage(mainClass.prefix + "The event starts in 30 seconds!");
			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 0.5F);	
		}	
	}
}
