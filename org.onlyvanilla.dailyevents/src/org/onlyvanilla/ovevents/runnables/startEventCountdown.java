package org.onlyvanilla.ovevents.runnables;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.onlyvanilla.ovevents.Main;
import org.onlyvanilla.ovevents.smallevents.DetermineEventData;

import net.md_5.bungee.api.ChatColor;

public class startEventCountdown extends BukkitRunnable {
    
	//Main instance
	private Main mainClass = Main.getInstance();
	
	public DetermineEventData dev1 = new DetermineEventData();

	public void run() {
		
		//replace with players who signed up
		for(Player p : dev1.getPlayerParticipants(mainClass.getEventData().getStringList("participants"))) {
			
			// Defiantly gonna use lambda, or this would look reaaalllly bad
			CountdownTimer timer = new CountdownTimer(mainClass,
			        10,
			        () -> p.sendMessage(ChatColor.DARK_PURPLE + "The event starts in 10 seconds!"),
			        () -> {
			        	p.sendMessage(ChatColor.DARK_PURPLE + "The event has begun!");
			           
			        },
			        (t) -> p.sendMessage(ChatColor.LIGHT_PURPLE + "Time left: " + (t.getSecondsLeft()) + "/" + (t.getTotalSeconds()) + " seconds")
			       

			);

			// Start scheduling, don't use the "run" method unless you want to skip a second
			timer.scheduleTimer();
		}
		
		StartEvent startEvent = new StartEvent();
		startEvent.runTaskLater(mainClass, 200);
	}
}
