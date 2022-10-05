package org.onlyvanilla.ovevents.bukkitevents;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import net.md_5.bungee.api.ChatColor;

public class MagicItemsCheck implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void rightClick(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		Action a = event.getAction();
		String itemDisplayName = p.getItemInHand().getItemMeta().getDisplayName();
		
		personalWarpDeviceCheck(p, a, itemDisplayName, event);
	}
	
	void personalWarpDeviceCheck(Player p, Action a, String itemDisplayName, PlayerInteractEvent event) {
		if((a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) && itemDisplayName.equals(ChatColor.RED + "Personal Warping Device")) {
			if(event.getHand() == EquipmentSlot.HAND) {
				
				//create new file with players warps
				//p.getlocation to set warps
				p.sendMessage("warp device right click");	
			}
		} else if((a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK) && itemDisplayName.equals(ChatColor.RED + "Personal Warping Device")){
			if(event.getHand() == EquipmentSlot.HAND) {
				
				//open gui with list of warps
				//add option to buy more in the gui
				//map inventory to player
				p.sendMessage("warp device left click");	
			}
		}
	}
	
	void playerTeleportationDeviceCheck() {
		
	}
	
	void spawnTeleportationDeviceCheck() {
		
	}
	
	void ascensionDeviceCheck() {
		
	}
}
