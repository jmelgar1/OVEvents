package org.onlyvanilla.ovevents.prompts;

import org.bukkit.Location;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;
import org.onlyvanilla.ovevents.Main;
import org.onlyvanilla.ovevents.bukkitevents.MagicItemsCheck;

import net.md_5.bungee.api.ChatColor;

public class teleportToPrompt extends StringPrompt {
	
	//Main instance
	public static Main mainClass = Main.getInstance();
	
	public static MagicItemsCheck magicItemsCheck = MagicItemsCheck.getInstance();
	
	public String getPromptText(ConversationContext c) {
		return mainClass.prefix + ChatColor.GREEN + magicItemsCheck.playerTPToMap.get(c.getForWhom()).getName() + " wants to warp to you! "
				+ "\nType " + ChatColor.DARK_GREEN + "/accept " + ChatColor.GREEN + "to accept the warp request. "
				+ "\nType " + ChatColor.DARK_GREEN + "/decline " + ChatColor.GREEN + "to decline the warp request.";
	}
	
	public Prompt acceptInput(ConversationContext c, String input) {
		if(!input.isBlank()) {
			c.setSessionData("response", input);
		}
		
		var response = c.getSessionData("response").toString();
		Player teleportee = (Player) c.getForWhom();
		Player teleporter = magicItemsCheck.playerTPToMap.get(c.getForWhom());
		
		if(response.equals("yes")) {	
			Location locTeleportee = teleportee.getLocation();
			teleporter.teleport(locTeleportee);
			teleporter.sendMessage("teleported");
			return Prompt.END_OF_CONVERSATION;
		} else if(response.equals("no")) {
			teleporter.sendMessage(mainClass.prefix + teleportee.getName() + " has declined your warp request!");
			return Prompt.END_OF_CONVERSATION;
		}
		
		return Prompt.END_OF_CONVERSATION;
	}
}
