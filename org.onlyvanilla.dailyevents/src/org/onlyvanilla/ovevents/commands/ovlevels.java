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
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.onlyvanilla.ovevents.managers.InventoryManager;

public class ovlevels extends InventoryManager implements Listener, CommandExecutor{
	
	private static Inventory inv1;
	
	public static Map<UUID, Inventory> inventories = new HashMap<UUID, Inventory>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player p = (Player) sender;
        	if(cmd.getName().equalsIgnoreCase("ovlevels")) { 
        		UUID playerUUID = p.getUniqueId();
        		inv1 = Bukkit.createInventory(p, 54, "OnlyVanilla Levels (Page 1/6)");
        		inventories.put(playerUUID, inv1);
            	openInventory(p, inventories.get(playerUUID));
            	initializeItemsPage1();
            	System.out.println(inventories);
        	}
        return true;
    }
	
	public static void initializeItemsPage1() {
		
		//NOVICE ITEMS
		inv1.setItem(2, createGuiItem(Material.BRICK, ChatColor.GRAY + "" + ChatColor.BOLD + "NOVICE",
				ChatColor.RED + "Level 1-9"));
		
		inv1.setItem(11, createGuiItem(Material.CAMPFIRE, ChatColor.RED + "Location Access:",
				ChatColor.DARK_GRAY + "Novice's Camp",
				"",
				ChatColor.YELLOW + "Coordinates:",
				ChatColor.DARK_GRAY + "???"));
		
		
		//APPRENTICE ITEMS
		inv1.setItem(6, createGuiItem(Material.IRON_INGOT, ChatColor.WHITE + "" + ChatColor.BOLD + "APPRENTICE",
				ChatColor.RED + "Level 10-19"));
		
		inv1.setItem(15, createGuiItem(Material.CARTOGRAPHY_TABLE, ChatColor.RED + "Location Access:",
				ChatColor.DARK_GRAY + "Apprentice's Village",
				"",
				ChatColor.YELLOW + "Coordinates:",
				ChatColor.DARK_GRAY + "???"));
		
		inv1.setItem(23, createGuiItemEnchantment(Material.NETHERITE_SWORD, Enchantment.DAMAGE_ARTHROPODS, 6, true, ChatColor.DARK_GRAY + "Apprentice's Arachnoid Slayer",
				"",
				ChatColor.YELLOW + "Trader:",
				ChatColor.DARK_GRAY + "???"));
		
		inv1.setItem(24, createGuiItemEnchantment(Material.NETHERITE_SWORD, Enchantment.DAMAGE_UNDEAD, 6, true, ChatColor.DARK_GRAY + "Apprentice's Haunted Blade",
				"",
				ChatColor.YELLOW + "Trader:",
				ChatColor.DARK_GRAY + "???"));
		
		inv1.setItem(25, createGuiItemEnchantment(Material.NETHERITE_BOOTS, Enchantment.SOUL_SPEED, 4, true, ChatColor.DARK_GRAY + "Apprentice's Boots of Agony",
				"",
				ChatColor.YELLOW + "Trader:",
				ChatColor.DARK_GRAY + "???"));
		
		inv1.setItem(49, createGuiItem(Material.BARRIER, ChatColor.DARK_RED + "" + ChatColor.BOLD + "EXIT"));
		inv1.setItem(53, createGuiItem(Material.LIME_WOOL, ChatColor.GREEN + "" + ChatColor.BOLD + "NEXT PAGE"));
	}
	
	public static void initializeItemsPage2() {
		
		//INITIATE ITEMS
		inv1.setItem(2, createGuiItem(Material.PAPER, ChatColor.YELLOW + "" + ChatColor.BOLD + "INITIATE",
				ChatColor.RED + "Level 20-29"));
		
		inv1.setItem(11, createGuiItem(Material.BRICKS, ChatColor.RED + "Location Access:",
				ChatColor.DARK_GRAY + "Initiate's Institution",
				"",
				ChatColor.YELLOW + "Coordinates:",
				ChatColor.DARK_GRAY + "???"));
		
		inv1.setItem(19, createGuiItemEnchantment(Material.NETHERITE_HELMET, Enchantment.OXYGEN, 4, true, ChatColor.YELLOW + "Initiate's Nautical Helmet",
				"",
				ChatColor.YELLOW + "Trader:",
				ChatColor.DARK_GRAY + "???"));
		
		inv1.setItem(20, createGuiItemEnchantment(Material.FISHING_ROD, Enchantment.LURE, 4, true, ChatColor.YELLOW + "Initiate's Casting Rod",
				"",
				ChatColor.YELLOW + "Trader:",
				ChatColor.DARK_GRAY + "???"));
		
		inv1.setItem(21, createGuiItemEnchantment(Material.FISHING_ROD, Enchantment.LUCK, 4, true, ChatColor.YELLOW + "Initiate's Cash Rod",
				"",
				ChatColor.YELLOW + "Trader:",
				ChatColor.DARK_GRAY + "???"));
		
		inv1.setItem(28, createGuiItemEnchantment(Material.NETHERITE_AXE, Enchantment.DIG_SPEED, 6, true, ChatColor.YELLOW + "Initiate's Hatchet",
				"",
				ChatColor.YELLOW + "Trader:",
				ChatColor.DARK_GRAY + "???"));
		
		
		//ADEPT ITEMS
		inv1.setItem(6, createGuiItem(Material.BOOK, ChatColor.GOLD + "" + ChatColor.BOLD + "ADEPT",
				ChatColor.RED + "Level 30-39"));
		
		inv1.setItem(15, createGuiItem(Material.BOOKSHELF, ChatColor.RED + "Location Access:",
				ChatColor.DARK_GRAY + "Adept's Library",
				"",
				ChatColor.YELLOW + "Coordinates:",
				ChatColor.DARK_GRAY + "???"));
		
		inv1.setItem(23, createGuiItemEnchantment(Material.NETHERITE_BOOTS, Enchantment.DEPTH_STRIDER, 4, true, ChatColor.GOLD + "Adept's Surf Boots",
				"",
				ChatColor.YELLOW + "Trader:",
				ChatColor.DARK_GRAY + "???"));
		
		inv1.setItem(24, createGuiItemEnchantment(Material.NETHERITE_SWORD, Enchantment.LOOT_BONUS_MOBS, 4, true, ChatColor.GOLD + "Adept's Lucky Blade",
				"",
				ChatColor.YELLOW + "Trader:",
				ChatColor.DARK_GRAY + "???"));
		
		inv1.setItem(25, createGuiItemEnchantment(Material.ELYTRA, Enchantment.DURABILITY, 4, true, ChatColor.GOLD + "Adept's Elytra",
				"",
				ChatColor.YELLOW + "Trader:",
				ChatColor.DARK_GRAY + "???"));
		
		
		inv1.setItem(45, createGuiItem(Material.RED_WOOL, ChatColor.RED + "" + ChatColor.BOLD + "GO BACK"));
		inv1.setItem(49, createGuiItem(Material.BARRIER, ChatColor.DARK_RED + "" + ChatColor.BOLD + "EXIT"));
		inv1.setItem(53, createGuiItem(Material.LIME_WOOL, ChatColor.GREEN + "" + ChatColor.BOLD + "NEXT PAGE"));
	}
	
public static void initializeItemsPage3() {
		
		//SKILLED ITEMS
		inv1.setItem(2, createGuiItem(Material.WRITTEN_BOOK, ChatColor.GREEN + "" + ChatColor.BOLD + "SKILLED",
				ChatColor.RED + "Level 40-49"));
		
		inv1.setItem(11, createGuiItem(Material.SMALL_DRIPLEAF, ChatColor.RED + "Location Access:",
				ChatColor.DARK_GRAY + "Skilled Sanctuary",
				"",
				ChatColor.YELLOW + "Coordinates:",
				ChatColor.DARK_GRAY + "???"));
		
		inv1.setItem(19, createGuiItemEnchantment(Material.NETHERITE_SWORD, Enchantment.DAMAGE_ARTHROPODS, 7, true, ChatColor.GREEN + "Skilled Theridiid Blade",
				"",
				ChatColor.YELLOW + "Trader:",
				ChatColor.DARK_GRAY + "???"));
		
		inv1.setItem(20, createGuiItemEnchantment(Material.NETHERITE_SWORD, Enchantment.DAMAGE_UNDEAD, 7, true, ChatColor.GREEN + "Skilled Walker Dagger",
				"",
				ChatColor.YELLOW + "Trader:",
				ChatColor.DARK_GRAY + "???"));
		
		inv1.setItem(21, createGuiItemEnchantment(Material.NETHERITE_BOOTS, Enchantment.SOUL_SPEED, 5, true, ChatColor.GREEN + "Skilled Boots of the Dead",
				"",
				ChatColor.YELLOW + "Trader:",
				ChatColor.DARK_GRAY + "???"));
		
		inv1.setItem(28, createGuiItemEnchantment(Material.NETHERITE_PICKAXE, Enchantment.LOOT_BONUS_BLOCKS, 4, true, ChatColor.GREEN + "Skilled Pickaxe of Chance",
				"",
				ChatColor.YELLOW + "Trader:",
				ChatColor.DARK_GRAY + "???"));
		
		inv1.setItem(29, createGuiItemEnchantment(Material.NETHERITE_AXE, Enchantment.DIG_SPEED, 7, true, ChatColor.GREEN + "Skilled Cleaver",
				"",
				ChatColor.YELLOW + "Trader:",
				ChatColor.DARK_GRAY + "???"));
		
		
		//VETERAN ITEMS
		inv1.setItem(6, createGuiItem(Material.POPPED_CHORUS_FRUIT, ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "VETERAN",
				ChatColor.RED + "Level 50-59"));
		
		inv1.setItem(15, createGuiItem(Material.DARK_OAK_DOOR, ChatColor.RED + "Location Access:",
				ChatColor.DARK_GRAY + "Veteran's Residence",
				"",
				ChatColor.YELLOW + "Coordinates:",
				ChatColor.DARK_GRAY + "???"));
		
		inv1.setItem(23, createGuiItemTwoEnchantment(Material.BOW, Enchantment.MENDING, 1, true, Enchantment.ARROW_INFINITE, 1, true, ChatColor.DARK_BLUE + "Veteran's Bow",
				"",
				ChatColor.YELLOW + "Trader:",
				ChatColor.DARK_GRAY + "???"));
		
		inv1.setItem(24, createGuiItemEnchantment(Material.NETHERITE_HELMET, Enchantment.OXYGEN, 5, true, ChatColor.DARK_BLUE + "Veteran's Scuba Mask",
				"",
				ChatColor.YELLOW + "Trader:",
				ChatColor.DARK_GRAY + "???"));
		
		inv1.setItem(25, createGuiItemEnchantment(Material.FISHING_ROD, Enchantment.LURE, 5, true, ChatColor.DARK_BLUE + "Veteran's Angle Angler",
				"",
				ChatColor.YELLOW + "Trader:",
				ChatColor.DARK_GRAY + "???"));
		
		inv1.setItem(32, createGuiItemEnchantment(Material.FISHING_ROD, Enchantment.LUCK, 5, true, ChatColor.DARK_BLUE + "Veteran's Wealth Gadget",
				"",
				ChatColor.YELLOW + "Trader:",
				ChatColor.DARK_GRAY + "???"));
		
		
		inv1.setItem(45, createGuiItem(Material.RED_WOOL, ChatColor.RED + "" + ChatColor.BOLD + "GO BACK"));
		inv1.setItem(49, createGuiItem(Material.BARRIER, ChatColor.DARK_RED + "" + ChatColor.BOLD + "EXIT"));
		inv1.setItem(53, createGuiItem(Material.LIME_WOOL, ChatColor.GREEN + "" + ChatColor.BOLD + "NEXT PAGE"));
	}

@SuppressWarnings("deprecation")
public static void initializeItemsPage4() {
	
	//SPECIALIST ITEMS
	inv1.setItem(2, createGuiItem(Material.PHANTOM_MEMBRANE, ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "SPECIALIST",
			ChatColor.RED + "Level 60-69"));
	
	inv1.setItem(11, createGuiItem(Material.PAPER, ChatColor.RED + "Location Access:",
			ChatColor.DARK_GRAY + "Specialist's Office",
			"",
			ChatColor.YELLOW + "Coordinates:",
			ChatColor.DARK_GRAY + "???"));
	
	inv1.setItem(19, createGuiItemEnchantment(Material.NETHERITE_BOOTS, Enchantment.DEPTH_STRIDER, 5, true, ChatColor.DARK_PURPLE + "Specialist's Aqua Boots",
			"",
			ChatColor.YELLOW + "Trader:",
			ChatColor.DARK_GRAY + "???"));
	
	inv1.setItem(20, createGuiItemEnchantment(Material.NETHERITE_SWORD, Enchantment.LOOT_BONUS_MOBS, 5, true, ChatColor.DARK_PURPLE + "Specialist's Blade of Prosperity",
			"",
			ChatColor.YELLOW + "Trader:",
			ChatColor.DARK_GRAY + "???"));
	
	inv1.setItem(21, createGuiItemEnchantment(Material.NETHERITE_AXE, Enchantment.DIG_SPEED, 8, true, ChatColor.DARK_PURPLE + "Specialist's Tomahawk",
			"",
			ChatColor.YELLOW + "Trader:",
			ChatColor.DARK_GRAY + "???"));
	
	inv1.setItem(28, createGuiItemEnchantment(Material.ELYTRA, Enchantment.DURABILITY, 5, true, ChatColor.DARK_PURPLE + "Specialist's Elytra",
			"",
			ChatColor.YELLOW + "Trader:",
			ChatColor.DARK_GRAY + "???"));
	
	
	//EXPERT ITEMS
	inv1.setItem(6, createGuiItem(Material.MAGMA_CREAM, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Expert",
			ChatColor.RED + "Level 70-79"));
	
	inv1.setItem(15, createGuiItem(Material.LEGACY_LINGERING_POTION, ChatColor.RED + "Location Access:",
			ChatColor.DARK_GRAY + "Expert's Lab",
			"",
			ChatColor.YELLOW + "Coordinates:",
			ChatColor.DARK_GRAY + "???"));
	
	inv1.setItem(23, createGuiItemEnchantment(Material.NETHERITE_PICKAXE, Enchantment.LOOT_BONUS_BLOCKS, 5, true, ChatColor.LIGHT_PURPLE + "Expert's Pickaxe of Affluence",
			"",
			ChatColor.YELLOW + "Trader:",
			ChatColor.DARK_GRAY + "???"));
	
	inv1.setItem(24, createGuiItemEnchantment(Material.NETHERITE_PICKAXE, Enchantment.LOOT_BONUS_BLOCKS, 6, true, ChatColor.LIGHT_PURPLE + "Expert's Pace Pickaxe",
			"",
			ChatColor.YELLOW + "Trader:",
			ChatColor.DARK_GRAY + "???"));	
	
	inv1.setItem(45, createGuiItem(Material.RED_WOOL, ChatColor.RED + "" + ChatColor.BOLD + "GO BACK"));
	inv1.setItem(49, createGuiItem(Material.BARRIER, ChatColor.DARK_RED + "" + ChatColor.BOLD + "EXIT"));
	inv1.setItem(53, createGuiItem(Material.LIME_WOOL, ChatColor.GREEN + "" + ChatColor.BOLD + "NEXT PAGE"));
}

public static void initializeItemsPage5() {
	
	//ELITE ITEMS
	inv1.setItem(2, createGuiItem(Material.DIAMOND, ChatColor.AQUA + "" + ChatColor.BOLD + "ELITE",
			ChatColor.RED + "Level 80-89"));
	
	inv1.setItem(11, createGuiItem(Material.SNOW_BLOCK, ChatColor.RED + "Location Access:",
			ChatColor.DARK_GRAY + "Elite's Hideout",
			"",
			ChatColor.YELLOW + "Coordinates:",
			ChatColor.DARK_GRAY + "???"));
	
	inv1.setItem(19, createGuiItemEnchantment(Material.NETHERITE_HELMET, Enchantment.OXYGEN, 6, true, ChatColor.AQUA + "Elite's Metabolic Helmet",
			"",
			ChatColor.YELLOW + "Trader:",
			ChatColor.DARK_GRAY + "???"));
	
	inv1.setItem(20, createGuiItemEnchantment(Material.FISHING_ROD, Enchantment.LURE, 6, true, ChatColor.AQUA + "Elite's Shark Swindler",
			"",
			ChatColor.YELLOW + "Trader:",
			ChatColor.DARK_GRAY + "???"));
	
	inv1.setItem(21, createGuiItemEnchantment(Material.FISHING_ROD, Enchantment.LUCK, 6, true, ChatColor.AQUA + "Elite's Treasure Charm",
			"",
			ChatColor.YELLOW + "Trader:",
			ChatColor.DARK_GRAY + "???"));
	
	//MASTER ITEMS
	inv1.setItem(6, createGuiItem(Material.EMERALD, ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "MASTER",
			ChatColor.RED + "Level 90-99"));
	
	inv1.setItem(15, createGuiItem(Material.SCULK, ChatColor.RED + "Location Access:",
			ChatColor.DARK_GRAY + "Master's Club",
			"",
			ChatColor.YELLOW + "Coordinates:",
			ChatColor.DARK_GRAY + "???"));
	
	inv1.setItem(23, createGuiItemEnchantment(Material.NETHERITE_SWORD, Enchantment.LOOT_BONUS_MOBS, 6, true, ChatColor.DARK_GREEN + "Master's Blade of Rapture",
			"",
			ChatColor.YELLOW + "Trader:",
			ChatColor.DARK_GRAY + "???"));
	
	inv1.setItem(24, createGuiItemEnchantment(Material.ELYTRA, Enchantment.DURABILITY, 6, true, ChatColor.DARK_GREEN + "Master's Elytra",
			"",
			ChatColor.YELLOW + "Trader:",
			ChatColor.DARK_GRAY + "???"));
	
	
	inv1.setItem(45, createGuiItem(Material.RED_WOOL, ChatColor.RED + "" + ChatColor.BOLD + "GO BACK"));
	inv1.setItem(49, createGuiItem(Material.BARRIER, ChatColor.DARK_RED + "" + ChatColor.BOLD + "EXIT"));
	inv1.setItem(53, createGuiItem(Material.LIME_WOOL, ChatColor.GREEN + "" + ChatColor.BOLD + "NEXT PAGE"));
}

@SuppressWarnings("deprecation")
public static void initializeItemsPage6() {
	
	//GRANDMASTER ITEMS
	inv1.setItem(4, createGuiItem(Material.NETHER_STAR, ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "GRANDMASTER",
			ChatColor.RED + "Level 100+"));
	
	inv1.setItem(13, createGuiItem(Material.LEGACY_EYE_OF_ENDER, ChatColor.RED + "Location Access:",
			ChatColor.DARK_GRAY + "Grandmaster's Castle",
			"",
			ChatColor.YELLOW + "Coordinates:",
			ChatColor.DARK_GRAY + "???"));
	
	inv1.setItem(21, createGuiItemEnchantment(Material.NETHERITE_PICKAXE, Enchantment.LOOT_BONUS_BLOCKS, 6, true, ChatColor.DARK_AQUA + "Grandmaster's Pickaxe of Riches",
			"",
			ChatColor.YELLOW + "Trader:",
			ChatColor.DARK_GRAY + "???"));
	
	inv1.setItem(22, createGuiItemEnchantment(Material.NETHERITE_PICKAXE, Enchantment.DIG_SPEED, 7, true, ChatColor.DARK_AQUA + "Grandmaster's Obsidian Pickaxe",
			"",
			ChatColor.YELLOW + "Trader:",
			ChatColor.DARK_GRAY + "???"));
	
	inv1.setItem(23, createGuiItemEnchantment(Material.NETHERITE_SWORD, Enchantment.LOOT_BONUS_MOBS, 7, true, ChatColor.DARK_AQUA + "Grandmaster's Legion Blade",
			"",
			ChatColor.YELLOW + "Trader:",
			ChatColor.DARK_GRAY + "???"));
	
	inv1.setItem(30, createGuiItemEnchantment(Material.ELYTRA, Enchantment.DURABILITY, 7, true, ChatColor.DARK_AQUA + "Grandmaster's Elytra",
			"",
			ChatColor.YELLOW + "Trader:",
			ChatColor.DARK_GRAY + "???"));
	
	inv1.setItem(31, createGuiItem(Material.RECOVERY_COMPASS, ChatColor.DARK_AQUA + "Grandmaster's World Jumper",
			ChatColor.GRAY + "Set 3 personal warps",
			"",
			ChatColor.YELLOW + "Trader:",
			ChatColor.DARK_GRAY + "???"));
	
	
	inv1.setItem(45, createGuiItem(Material.RED_WOOL, ChatColor.RED + "" + ChatColor.BOLD + "GO BACK"));
	inv1.setItem(49, createGuiItem(Material.BARRIER, ChatColor.DARK_RED + "" + ChatColor.BOLD + "EXIT"));
}
	
	public void openInventory(final HumanEntity ent, Inventory inv) {
		ent.openInventory(inv);
	}
	
	@EventHandler
	public void onInventoryClick(final InventoryClickEvent event) {
		final Player p = (Player) event.getWhoClicked();
		UUID playerUUID = p.getUniqueId();
		if(!event.getInventory().equals(inventories.get(playerUUID))) return;
		
		event.setCancelled(true);
		
		final ItemStack clickedItem = event.getCurrentItem();
		
		String currentTitle = event.getView().getTitle();
		
		
		//verify current item is not null
		if (clickedItem == null || clickedItem.getType().isAir()) return;
		
		if(clickedItem.getType() == Material.LIME_WOOL) {
			if(currentTitle.equals("OnlyVanilla Levels (Page 1/6)")) {
				inv1 = Bukkit.createInventory(null, 54, "OnlyVanilla Levels (Page 2/6)");
				inventories.put(playerUUID, inv1);
            	openInventory(p, inv1);
            	initializeItemsPage2();
			} else if(currentTitle.equals("OnlyVanilla Levels (Page 2/6)")) {
				inv1 = Bukkit.createInventory(null, 54, "OnlyVanilla Levels (Page 3/6)");
				inventories.put(playerUUID, inv1);
            	openInventory(p, inv1);
            	initializeItemsPage3();
			} else if(currentTitle.equals("OnlyVanilla Levels (Page 3/6)")) {
				inv1 = Bukkit.createInventory(null, 54, "OnlyVanilla Levels (Page 4/6)");
				inventories.put(playerUUID, inv1);
            	openInventory(p, inv1);
            	initializeItemsPage4();
			} else if(currentTitle.equals("OnlyVanilla Levels (Page 4/6)")) {
				inv1 = Bukkit.createInventory(null, 54, "OnlyVanilla Levels (Page 5/6)");
				inventories.put(playerUUID, inv1);
            	openInventory(p, inv1);
            	initializeItemsPage5();
			} else if(currentTitle.equals("OnlyVanilla Levels (Page 5/6)")) {
				inv1 = Bukkit.createInventory(null, 54, "OnlyVanilla Levels (Page 6/6)");
				inventories.put(playerUUID, inv1);
            	openInventory(p, inv1);
            	initializeItemsPage6();
			}
		}
		
		if(clickedItem.getType() == Material.RED_WOOL) {
			if(currentTitle.equals("OnlyVanilla Levels (Page 2/6)")) {
				inv1 = Bukkit.createInventory(null, 54, "OnlyVanilla Levels (Page 1/6)");
				inventories.put(playerUUID, inv1);
            	openInventory(p, inv1);
            	initializeItemsPage1();
			} else if(currentTitle.equals("OnlyVanilla Levels (Page 3/6)")) {
				inv1 = Bukkit.createInventory(null, 54, "OnlyVanilla Levels (Page 2/6)");
				inventories.put(playerUUID, inv1);
            	openInventory(p, inv1);
            	initializeItemsPage2();
			} else if(currentTitle.equals("OnlyVanilla Levels (Page 4/6)")) {
				inv1 = Bukkit.createInventory(null, 54, "OnlyVanilla Levels (Page 3/6)");
				inventories.put(playerUUID, inv1);
            	openInventory(p, inv1);
            	initializeItemsPage3();
			} else if(currentTitle.equals("OnlyVanilla Levels (Page 5/6)")) {
				inv1 = Bukkit.createInventory(null, 54, "OnlyVanilla Levels (Page 4/6)");
				inventories.put(playerUUID, inv1);
            	openInventory(p, inv1);
            	initializeItemsPage4();
			} else if(currentTitle.equals("OnlyVanilla Levels (Page 6/6)")) {
				inv1 = Bukkit.createInventory(null, 54, "OnlyVanilla Levels (Page 5/6)");
				inventories.put(playerUUID, inv1);
            	openInventory(p, inv1);
            	initializeItemsPage5();
			}
		}
		
		if(clickedItem.getType() == Material.BARRIER) {
			p.closeInventory();
		}
	}
	
	@EventHandler
	public void onInventoryClick(final InventoryDragEvent event) {
		if(event.getInventory().equals(inventories.get(event.getWhoClicked().getUniqueId()))) {
			event.setCancelled(true);
		}
	}
}

