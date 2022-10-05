package org.onlyvanilla.ovevents.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class ovehelp implements CommandExecutor {
	String cmd1 = "ovehelp";

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            	if(cmd.getName().equalsIgnoreCase(cmd1)) {
            		p.sendMessage(ChatColor.GRAY + "------" + ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "OVEvents" + ChatColor.GRAY + "------");
            		p.sendMessage(ChatColor.YELLOW + "/ovehelp (This page)");
            		p.sendMessage(ChatColor.YELLOW + "/ovprofile [username] (View OnlyVanilla profiles)");
            		p.sendMessage(ChatColor.YELLOW + "/ovshop (See OV-XP rewards)");
            }
        }
        return true;
    } 
}