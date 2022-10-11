package org.onlyvanilla.ovevents.commands;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.onlyvanilla.ovevents.Main;
import org.onlyvanilla.ovevents.bukkitevents.MagicItemsCheck;

import net.md_5.bungee.api.ChatColor;

public class decline implements CommandExecutor {
	
	String cmd1 = "decline";
	
	//Main instance
	public static Main mainClass = Main.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player teleportee = (Player) sender;
        	if(cmd.getName().equalsIgnoreCase(cmd1)) {
        		if(args.length == 1) {
        			
        			String teleporterIGN = args[0];
        			Player teleporter = Bukkit.getServer().getPlayer(teleporterIGN);
	        		
	        		MagicItemsCheck magicItemsCheck = MagicItemsCheck.getInstance();
	        		
	        		if(magicItemsCheck != null) { 
	        			
	        			if(magicItemsCheck.playerTPToMap.containsKey(teleporter) &&
	        			   magicItemsCheck.playerTPToMap.get(teleporter).equals(teleportee)) {
	        				warpCancel(magicItemsCheck, teleportee, magicItemsCheck.playerTPToMap, teleporter);
	        			} else if(magicItemsCheck.playerTPFromMap.containsKey(teleporter) &&
	 	        			   magicItemsCheck.playerTPFromMap.get(teleporter).equals(teleportee)) {
	        				warpCancel(magicItemsCheck, teleportee, magicItemsCheck.playerTPFromMap, teleporter);
	        			}
	            	} else {
	        			teleportee.sendMessage(mainClass.prefix + ChatColor.RED + "You have no warp requests!");
	            	}
        		} else {
        			teleportee.sendMessage(mainClass.prefix + ChatColor.RED + "Correct usage: /decline [username]");
        		}
            }
        }
        return true;
    }
    
    void warpCancel(MagicItemsCheck magicItemsCheck, Player teleportee, Map<Player, Player> map, Player teleporter) {
		teleporter.sendMessage(mainClass.prefix + ChatColor.RED + teleportee.getName() + " has denied your warp request!");
		map.remove(teleporter);
    }
}