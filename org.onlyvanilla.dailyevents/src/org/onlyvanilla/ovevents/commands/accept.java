package org.onlyvanilla.ovevents.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.onlyvanilla.ovevents.Main;
import org.onlyvanilla.ovevents.bukkitevents.MagicItemsCheck;

import net.md_5.bungee.api.ChatColor;

public class accept implements CommandExecutor {
	
	String cmd1 = "accept";
	
	//Main instance
	public static Main mainClass = Main.getInstance();
	
	//Main instance
	private static accept instance;
	
	//list to store task ids
	public Map<Player, Integer> waitingWarps = new HashMap<Player, Integer>();
	
	//in current warp
	private boolean playerWaiting = false;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player teleportee = (Player) sender;
        	if(cmd.getName().equalsIgnoreCase(cmd1)) {
        		if(args.length == 1) {
	        		
        			String teleporterIGN = args[0];
        			Player teleporter = Bukkit.getServer().getPlayer(teleporterIGN);
        			
	        		instance = this;
	        		
	        		MagicItemsCheck magicItemsCheck = MagicItemsCheck.getInstance();
	        		
	        		if(magicItemsCheck != null) { 
	        			
	        			if(magicItemsCheck.playerTPToMap.containsKey(teleporter) && 
	        			   magicItemsCheck.playerTPToMap.get(teleporter).equals(teleportee) &&
	        			   !waitingWarps.containsKey(teleporter)) {
	        				//cool down for player teleport
	        	            
	        				warpCheck(magicItemsCheck, teleportee, magicItemsCheck.playerTPToMap, teleporter);
	        			} else if(magicItemsCheck.playerTPFromMap.containsKey(teleporter) &&
	        					  magicItemsCheck.playerTPFromMap.get(teleporter).equals(teleportee) &&
	        					  !waitingWarps.containsKey(teleporter)) {
	        				warpCheck(magicItemsCheck, teleportee, magicItemsCheck.playerTPFromMap, teleporter);
	        			} else {
	        				teleportee.sendMessage(mainClass.prefix + ChatColor.RED + "You have no warp requests from this player!");
	        			}
	            	} else {
	        			teleportee.sendMessage(mainClass.prefix + ChatColor.RED + "You have no warp requests!");
	            	}
        		} else {
        			teleportee.sendMessage(mainClass.prefix + ChatColor.RED + "Correct usage: /accept [username]");
        		}
            }
        }
        return true;
    } 
    
    void warpCheck(MagicItemsCheck magicItemsCheck, Player teleportee, Map<Player, Player> map, Player teleporter) {
		teleportee.sendMessage(mainClass.prefix + ChatColor.GREEN + "You have accepted " + teleporter.getName() + "'s warp request! " +
		teleporter.getName() + " will warp in 10 seconds!");
		teleporter.sendMessage(mainClass.prefix + ChatColor.GREEN + teleportee.getName() + " has accepted your warp request! You will warp in 10 seconds!");
		playerWaiting = true;
		
		waitingWarps.put(teleporter, (new BukkitRunnable(){

            @Override
            public void run() {
        		teleporter.teleport(teleportee.getLocation());
        		map.remove(teleporter);	
        		playerWaiting = false;
                } 
	        }.runTaskLater(mainClass, 200).getTaskId()));
	    }
    
    public static accept getInstance() {
    	return instance;
    }
    
    public boolean getWaitingStatus() {
    	return playerWaiting;
    }
    
    public Map<Player, Integer> getWaitingWarps() {
    	return waitingWarps;
    }
}