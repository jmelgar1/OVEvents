package org.onlyvanilla.ovevents.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.onlyvanilla.ovevents.Main;
import org.onlyvanilla.ovevents.bukkitevents.EditPlayerPoints;
import org.onlyvanilla.ovevents.managers.InventoryManager;

public class ovshop extends InventoryManager implements Listener, CommandExecutor{
	
	EditPlayerPoints epp1 = new EditPlayerPoints();
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	private static Inventory inv1;
	
	public static Map<UUID, Inventory> inventories = new HashMap<UUID, Inventory>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player p = (Player) sender;
        	if(cmd.getName().equalsIgnoreCase("ovshop")) { 
        		UUID playerUUID = p.getUniqueId();
        		inv1 = Bukkit.createInventory(p, 27, "OnlyVanilla OV-XP Shop");
        		inventories.put(playerUUID, inv1);
            	openInventory(p, inventories.get(playerUUID));
            	initializeItems();
            	System.out.println(inventories);
        	}
        return true;
    }
		
	public static void initializeItems() {

			inv1.setItem(10, createGuiItem(Material.BRICK, ChatColor.DARK_RED + "" + ChatColor.BOLD + "Personal Warping Device",
					ChatColor.GRAY + "Cost: " + ChatColor.GOLD + "10,000 OV-XP",
					ChatColor.GRAY + "Additional Warp: " + ChatColor.GOLD + "5,000 OV-XP",
					ChatColor.GRAY + "OV-XP/TP: " + ChatColor.GOLD + "500 OV-XP",
					"",
					ChatColor.GRAY + "Set one personal warp!",
					"",
					ChatColor.DARK_GRAY + "LEFT CLICK - Buy Item",
					ChatColor.DARK_GRAY + "RIGHT CLICK - Buy Additional Warp"));
			
			
			inv1.setItem(12, createGuiItem(Material.FEATHER, ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Player Teleportation Device",
					ChatColor.GRAY + "Cost: " + ChatColor.GOLD + "10,000 OV-XP",
					ChatColor.GRAY + "OV-XP/TP: " + ChatColor.GOLD + "500 OV-XP",
					"",
					ChatColor.GRAY + "Teleport to a player or teleport a player to you!",
					"",
					ChatColor.DARK_GRAY + "LEFT CLICK - Buy Item"));
			
			inv1.setItem(14, createGuiItem(Material.RABBIT_FOOT, ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Spawn Warping Device",
					ChatColor.GRAY + "Cost: " + ChatColor.GOLD + "5,000 OV-XP",
					ChatColor.GRAY + "OV-XP/TP: " + ChatColor.GOLD + "250 OV-XP",
					"",
					ChatColor.GRAY + "Teleport to spawn!",
					"",
					ChatColor.DARK_GRAY + "LEFT CLICK - Buy Item"));
			
			inv1.setItem(16, createGuiItem(Material.ECHO_SHARD, ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Ascension Device",
					ChatColor.GRAY + "Cost: " + ChatColor.GOLD + "2,500 OV-XP",
					ChatColor.GRAY + "OV-XP/TP: " + ChatColor.GOLD + "125 OV-XP",
					"",
					ChatColor.GRAY + "Teleport to the highest block above you!",
					"",
					ChatColor.DARK_GRAY + "LEFT CLICK - Buy Item"));
		}
	
	public void openInventory(final HumanEntity ent, Inventory inv) {
		ent.openInventory(inv);
	}
	
	void onPurchase(Player p, ItemStack itemStack, String itemName, String price, int cost, ConfigurationSection section) {
		p.getInventory().addItem(itemStack);
		
		p.sendMessage(mainClass.prefix + ChatColor.GREEN + "You purchased a " + itemName + "!");
		p.sendMessage(mainClass.prefix + ChatColor.RED + "-" + price + " OV-XP");
		epp1.removeLevelXP(cost, section);
	}
	
	@EventHandler
	public void onInventoryClick(final InventoryClickEvent event) {
		final Player p = (Player) event.getWhoClicked();
		UUID playerUUID = p.getUniqueId();
		if(!event.getInventory().equals(inventories.get(playerUUID))) return;
		
		event.setCancelled(true);
		
		final ItemStack clickedItem = event.getCurrentItem();
		
		ConfigurationSection playerUUIDSection = mainClass.getPlayerData().getConfigurationSection(playerUUID.toString());

		//verify current item is not null
		if (clickedItem == null || clickedItem.getType().isAir()) return;
		
		if(clickedItem.getType() == Material.BRICK) {
			if(epp1.getPlayerXP(playerUUIDSection) >= 10000) {
				if(event.getClick() == ClickType.LEFT) {
					
					//create item
					ItemStack personalWarpDevice = createGuiItem(Material.BRICK, ChatColor.RED + "Personal Warping Device",
							ChatColor.GRAY + "Owner: " + ChatColor.YELLOW + p.getName(),
							"",
							ChatColor.DARK_GRAY + "LEFT CLICK - Go to warp",
							ChatColor.DARK_GRAY + "RIGHT CLICK - Set warp");
					
					onPurchase(p, personalWarpDevice, "Personal Warping Device", "10,000", 10000, playerUUIDSection);
					
					if(playerUUIDSection.getInt("warpsAllowed") == 0) {
						playerUUIDSection.set("warpsAllowed", 1);
						mainClass.savePlayerDataFile();
					}
					
				} else if(event.getClick() == ClickType.RIGHT) {
					p.sendMessage(ChatColor.GREEN + "Right Click");
				}
			} else {
				p.sendMessage(mainClass.prefix + ChatColor.RED + "You need 10,000 OV-XP to purchase this item.");
			}
		} else if(clickedItem.getType() == Material.FEATHER) {
			if(epp1.getPlayerXP(playerUUIDSection) >= 10000) {
				if(event.getClick() == ClickType.LEFT) {	
					//create item
					ItemStack playerTeleportationDevice = createGuiItem(Material.FEATHER, ChatColor.DARK_BLUE + "Player Teleportation Device",
							ChatColor.GRAY + "Owner: " + ChatColor.YELLOW + p.getName(),
							"",
							ChatColor.DARK_GRAY + "LEFT CLICK - Teleport to a player",
							ChatColor.DARK_GRAY + "RIGHT CLICK - Teleport a player to you");
					
					onPurchase(p, playerTeleportationDevice, "Player Teleportation Device", "10,000", 10000, playerUUIDSection);
				} 
			} else {
				p.sendMessage(mainClass.prefix + ChatColor.RED + "You need 10,000 OV-XP to purchase this item.");
			}
		} else if(clickedItem.getType() == Material.RABBIT_FOOT) {
			if(epp1.getPlayerXP(playerUUIDSection) >= 5000) {
				if(event.getClick() == ClickType.LEFT) {	
					//create item
					ItemStack spawnTeleportationDevice = createGuiItem(Material.RABBIT_FOOT, ChatColor.DARK_GREEN + "Spawn Teleportation Device",
							ChatColor.GRAY + "Owner: " + ChatColor.YELLOW + p.getName(),
							"",
							ChatColor.DARK_GRAY + "RIGHT CLICK - Teleport to spawn!",
							ChatColor.DARK_GRAY + "LEFT CLICK - Cancel");
					
					onPurchase(p, spawnTeleportationDevice, "Spawn Teleportation Device", "5,000", 5000, playerUUIDSection);
				}
			} else {
				p.sendMessage(mainClass.prefix + ChatColor.RED + "You need 5,000 OV-XP to purchase this item.");
			}
		} else if(clickedItem.getType() == Material.ECHO_SHARD) {
			if(epp1.getPlayerXP(playerUUIDSection) >= 2500) {
				if(event.getClick() == ClickType.LEFT) {	
					//create item
					ItemStack ascensionTeleportationDevice = createGuiItem(Material.ECHO_SHARD, ChatColor.DARK_PURPLE + "Ascension Device",
							ChatColor.GRAY + "Owner: " + ChatColor.YELLOW + p.getName(),
							"",
							ChatColor.DARK_GRAY + "RIGHT CLICK - Teleport to the highest block above you!",
							ChatColor.DARK_GRAY + "LEFT CLICK - Cancel");
					
					onPurchase(p, ascensionTeleportationDevice, "Ascension Teleportation Device", "2,500", 2500, playerUUIDSection);
				}
			} else {
				p.sendMessage(mainClass.prefix + ChatColor.RED + "You need 2,500 OV-XP to purchase this item.");
			}
		}
	}

	@EventHandler
	public void onInventoryClick(final InventoryDragEvent event) {
		if(event.getInventory().equals(inventories.get(event.getWhoClicked().getUniqueId()))) {
			event.setCancelled(true);
	}
}
}