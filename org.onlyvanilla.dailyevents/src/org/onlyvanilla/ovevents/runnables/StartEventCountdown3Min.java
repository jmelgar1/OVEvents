package org.onlyvanilla.ovevents.runnables;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.onlyvanilla.ovevents.Main;
import org.onlyvanilla.ovevents.managers.DetermineEventData;

public class StartEventCountdown3Min extends BukkitRunnable {
    
	//Main instance
	private Main mainClass = Main.getInstance();
	
	public DetermineEventData dev1 = new DetermineEventData();
	
	//get winning event 
	String winningEvent = mainClass.dev1.getVotedEvent(mainClass.getSmallEvents(), mainClass.dev1.getList(), mainClass);
	
	BossBar bar = Bukkit.createBossBar("countdown", BarColor.RED, BarStyle.SEGMENTED_10);
	int seconds = 180;
	
    DecimalFormat dFormat = new DecimalFormat("00");

	public void run() {
		if(seconds <= 5) {
			for(Player p : dev1.getPlayerParticipants(mainClass.getEventData().getStringList("participants"))) {
				p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 0.5F);
			}
		}
		
		if((seconds -= 1) == 0) {
			cancel();
			bar.removeAll();
			StartEvent startEvent = new StartEvent();
			startEvent.runTaskLater(mainClass, 0);
		} else {
			bar.setProgress(seconds / 180D);
			String minutesTimer = String.valueOf(seconds/60);
			String secondsTimer = dFormat.format(seconds % 60);
			bar.setTitle(winningEvent + " starts in " + minutesTimer + ":" + secondsTimer);
		}
	}
	
	public void addPlayer(Player p) {
		bar.addPlayer(p);
	}
}
