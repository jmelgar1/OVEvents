package org.onlyvanilla.dailyevents.runnables;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.onlyvanilla.ovevents.Main;
import org.onlyvanilla.ovevents.smallevents.DetermineEventData;

public class EndEvent extends BukkitRunnable{
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	public DetermineEventData dev1 = new DetermineEventData();
	
	//start event instance
	private StartEvent startEventClass = StartEvent.getInstance();

	@Override
	public void run() {
		
		//not unregistering
		startEventClass.unregisterEvent(startEventClass.getEvent());
		dev1.clearVotes(mainClass.getSmallEvents(), mainClass.dev1.getList(), mainClass);
		removeScoreboard();
	}
	
	public void removeScoreboard() {
		for(String s : mainClass.getEventData().getStringList("participants")){
			Player p = Bukkit.getPlayer(s);
			p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
			p.sendMessage(mainClass.prefix + " The event has ended!");
		}
	}
}
