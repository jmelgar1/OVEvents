package org.onlyvanilla.dailyevents.runnables;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.onlyvanilla.dailyevents.events.killingevents.GhastHunter;
import org.onlyvanilla.ovevents.Main;

public class UpdateScoreboard extends BukkitRunnable {
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	GhastHunter ghastHunter = new GhastHunter();
	
	@Override
	public void run() {
		for(String s : mainClass.getEventData().getStringList("participants")){
			Player p = Bukkit.getPlayer(s);
		if(p == null) {
			this.cancel();
		} else {
			Scoreboard board = p.getScoreboard();
			Objective obj = board.getObjective(DisplaySlot.SIDEBAR);
			obj.getScore(p.getName()).setScore(ghastHunter.winningEventSection.getInt(p.getName()));
			p.setScoreboard(board);
			}
		}
	}
}
