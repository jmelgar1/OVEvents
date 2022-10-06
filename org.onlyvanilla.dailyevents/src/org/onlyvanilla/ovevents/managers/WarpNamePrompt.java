package org.onlyvanilla.ovevents.managers;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;
import org.onlyvanilla.ovevents.Main;

import net.md_5.bungee.api.ChatColor;

public class WarpNamePrompt extends StringPrompt {
	
	//Main instance
	public static Main mainClass = Main.getInstance();
	
	public String getPromptText(ConversationContext c) {
		return mainClass.prefix + ChatColor.GREEN + "Enter the name of your new warp or type \"exit\": ";
	}
	
	public Prompt acceptInput(ConversationContext c, String input) {
		if(!input.isBlank() || !"Warp".equals(input.toLowerCase())) {
			input = input.substring(0, 1).toUpperCase() + input.substring(1);
			c.setSessionData("warpName", input);
		}
		
		var warpName = c.getSessionData("warpName").toString();
		@SuppressWarnings("unused")
		String source;
		if(c.getForWhom() instanceof Player) {
			source = ((Player) c.getForWhom()).getName();
		} else {
			source = "console";
		}
		
		Player p = (Player) c.getForWhom();
		ConfigurationSection playerUUIDSection = mainClass.getPlayerData().getConfigurationSection(p.getUniqueId().toString());
		ConfigurationSection warpSection = playerUUIDSection.getConfigurationSection("warps");
		
		Location loc = p.getLocation();
		ConfigurationSection warp = warpSection.createSection(warpName);
		warp.set("world", loc.getWorld().getName() + "");
		warp.set("x", loc.getX() + "");
		warp.set("y", loc.getY() + "");
		warp.set("z", loc.getZ() + "");
		warp.set("yaw", loc.getYaw() + "");
		warp.set("pitch", loc.getPitch() + "");
        
		mainClass.savePlayerDataFile();
		
		p.sendMessage(mainClass.prefix + ChatColor.GREEN + "Warp set!");
		
		return Prompt.END_OF_CONVERSATION;
	}
}
