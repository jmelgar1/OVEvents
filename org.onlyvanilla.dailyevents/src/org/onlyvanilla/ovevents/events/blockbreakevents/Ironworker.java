package org.onlyvanilla.ovevents.events.blockbreakevents;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.onlyvanilla.ovevents.runnables.UpdateScoreboard;
import org.onlyvanilla.ovevents.smalleventmanager.DailyEvents;

import net.coreprotect.CoreProtectAPI;
import net.coreprotect.CoreProtectAPI.ParseResult;

public class Ironworker extends DailyEvents implements Listener {
	
	//takes about a second to log a block. If player places diamond_ore. dont let player break that ore for 1-2 seconds.
	
	CoreProtectAPI api = getCoreProtect();

	@EventHandler
	public void breakDiamond(BlockBreakEvent e) {
		Block b = (Block) e.getBlock();
		Player p = e.getPlayer();
		
		Material material = b.getType();
		
		//check for block type (aka. emerald_ore, diamond ore, etc)
		if(material == Material.IRON_ORE || material == Material.DEEPSLATE_IRON_ORE) {
			
			boolean contains = dev1.getPlayerParticipants(mainClass.getEventData().getStringList("participants")).contains(p);
			
			boolean blockPlaced = false;
			
			b.getMetadata("placed");
			if(!b.getMetadata("placed").isEmpty()) {
				blockPlaced = true;
			}
			
			List<String[]> lookup = api.blockLookup(e.getBlock(), 86400);
			for(String[] result : lookup) {
				ParseResult parseResult = api.parseResult(result);
				if(parseResult.getPlayer() != null) {
					blockPlaced = true;
				}
			}
			
			if(contains == true) {
				if(blockPlaced == false) {
					int currentScore = winningEventSection.getInt(p.getName());
					currentScore += 1;
					winningEventSection.set(p.getName(), currentScore);
					p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
					mainClass.saveEventDataFile();
					
					UpdateScoreboard updateScoreboard = new UpdateScoreboard();
					updateScoreboard.run();
				}
			}		
		}
	}
	
	//set metadata to prevent players from gaining points from non naturally generated blocks
	@EventHandler 
	public void blockPlaced(BlockPlaceEvent e){
		Block b = e.getBlock();
		Material material = b.getType();
		
		if(material == Material.IRON_ORE || material == Material.DEEPSLATE_IRON_ORE) {
			b.setMetadata("placed", new FixedMetadataValue(mainClass, "something"));
		}
	}
}
