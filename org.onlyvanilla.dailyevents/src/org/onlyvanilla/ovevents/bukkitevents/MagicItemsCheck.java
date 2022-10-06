package org.onlyvanilla.ovevents.bukkitevents;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.onlyvanilla.ovevents.Main;
import org.onlyvanilla.ovevents.managers.InventoryManager;
import org.onlyvanilla.ovevents.managers.WarpNamePrompt;

import net.md_5.bungee.api.ChatColor;

public class MagicItemsCheck extends InventoryManager implements Listener {
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	ConfigurationSection playerData = mainClass.getPlayerData();
	
	EditPlayerPoints epp1 = new EditPlayerPoints();
	
	private static Inventory inv;
	
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
				
				if(ensureOwnership(p) == true) {
					event.setCancelled(true);
				}
				
				ConfigurationSection playerUUIDSection = playerData.getConfigurationSection(p.getUniqueId().toString());
				
				if(playerUUIDSection.contains("warps") != true) {
					playerUUIDSection.createSection("warps");
				}
				
				ConfigurationSection warpSection = playerUUIDSection.getConfigurationSection("warps");
				
				int numOfWarps = 0;
				
				if(warpSection != null) {
					for(@SuppressWarnings("unused") String key : warpSection.getKeys(false)) {
						numOfWarps += 1;
					}
				}
					
				//if warps section doesnt exist create one
				if(numOfWarps == 0 || numOfWarps < playerUUIDSection.getInt("warpsAllowed") && numOfWarps <= 8) {
					
					if(p.isConversing() != true) {
						ConversationFactory factory = new ConversationFactory(mainClass);
						factory.withFirstPrompt(new WarpNamePrompt())
						.withEscapeSequence("exit")
						.withEscapeSequence("EXIT")
						.withEscapeSequence("Exit")
						.withEscapeSequence("EXit")
						.withEscapeSequence("EXIt")
						.withEscapeSequence("exiT")
						.withEscapeSequence("ExIT")
						.withEscapeSequence("eXIT")
						.withEscapeSequence("ExiT")
						.withEscapeSequence("eXTt")
						.withEscapeSequence("eXiT")
						.withEscapeSequence("ExIt")
						.buildConversation(p)
						.begin();			
					}			
					
				} else {
					p.sendMessage(mainClass.prefix + ChatColor.RED + "You can not create any more warps!");
				}
			}
		} else if((a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK) && itemDisplayName.equals(ChatColor.RED + "Personal Warping Device")){
			if(event.getHand() == EquipmentSlot.HAND) {
				
				if(ensureOwnership(p) == true) {
					event.setCancelled(true);
				} else {
					inv = Bukkit.createInventory(p, 9, p.getName() + "'s Warps");
	        		inventories.put(p.getUniqueId(), inv);
	            	openInventory(p, inventories.get(p.getUniqueId()));
	            	initalizePWDCItems(p);
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	boolean ensureOwnership(Player p) {
		if(p.getItemInHand() != null) {
		
		ItemStack item = p.getItemInHand();
	
			List<String> itemLore = item.getItemMeta().getLore();
			
			if(itemLore != null) {
				if(!itemLore.contains(ChatColor.GRAY + "Owner: " + ChatColor.YELLOW + p.getName())) {
					p.sendMessage(mainClass.prefix + ChatColor.RED + "You do not own this item!");
					return true;
				}
				return false;
			}
			return false;
		}
		return false;
	}
	
	public void openInventory(final HumanEntity ent, Inventory inv) {
		ent.openInventory(inv);
	}
	
	@EventHandler
	public void onInventoryClick(final InventoryClickEvent event) {	
		final Player p = (Player) event.getWhoClicked();
		UUID playerUUID = p.getUniqueId();
		
		ConfigurationSection playerUUIDSection = playerData.getConfigurationSection(playerUUID.toString());
		
		if(!event.getInventory().equals(inventories.get(playerUUID))) return;
		
		event.setCancelled(true);
		
		final ItemStack clickedItem = event.getCurrentItem();
		
		//verify current item is not null
		if (clickedItem == null || clickedItem.getType().isAir()) return;
		
		if(clickedItem.getType() == Material.GOLD_BLOCK) {
			int warpsAllowed = playerUUIDSection.getInt("warpsAllowed");
			if(warpsAllowed <= 8) {
				warpsAllowed += 1;
				playerUUIDSection.set("warpsAllowed", warpsAllowed);
				p.sendMessage(mainClass.prefix + ChatColor.GREEN + "You have purchased a personal warp!");
				p.sendMessage(mainClass.prefix + ChatColor.RED + "-10,000 OV-XP");
				epp1.removeLevelXP(10000, playerUUIDSection);
				mainClass.savePlayerDataFile();
			} else {
				p.sendMessage(mainClass.prefix + ChatColor.RED + "Max amount of personal warps is 8!");
			}
		} else if(clickedItem.getType() == Material.EMERALD) {
			
			//retrieve location of player
			ConfigurationSection warpSection = playerUUIDSection.getConfigurationSection("warps");
			ConfigurationSection warp = warpSection.getConfigurationSection(ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()));
			double x = Double.parseDouble(warp.getString("x"));
			double y = Double.parseDouble(warp.getString("y"));
			double z = Double.parseDouble(warp.getString("z"));
			int yaw = (int)Double.parseDouble(warp.getString("yaw"));
			int pitch = (int)Double.parseDouble(warp.getString("pitch"));
			String world = warp.getString("world");
			Location loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
			p.teleport(loc);
		}
	}
	
	@EventHandler
	public void onInventoryClick(final InventoryDragEvent event) {
		if(event.getInventory().equals(inventories.get(event.getWhoClicked().getUniqueId()))) {
			event.setCancelled(true);
		}
	}
	
	void initalizePWDCItems(Player p) {
		ConfigurationSection playerUUIDSection = playerData.getConfigurationSection(p.getUniqueId().toString());
		ConfigurationSection warpSection = playerUUIDSection.getConfigurationSection("warps");
		
		int count = 0;
		for(String warp : warpSection.getKeys(false)) {
			ConfigurationSection warpInd = warpSection.getConfigurationSection(ChatColor.stripColor(warp));
			inv.setItem(count, createGuiItem(Material.EMERALD, ChatColor.GREEN + warp,
					ChatColor.GRAY + "World: " + warpInd.get("world")));
			count++;
		}
		
		inv.setItem(8, createGuiItem(Material.GOLD_BLOCK, ChatColor.GOLD + "+1 Personal Warp",
				ChatColor.GRAY + "Cost: " + ChatColor.GOLD + "10,000 OV-XP"));
    }
	
	void playerTeleportationDeviceCheck() {
		
	}
	
	void spawnTeleportationDeviceCheck() {
		
	}
	
	void ascensionDeviceCheck() {
		
	}
}
