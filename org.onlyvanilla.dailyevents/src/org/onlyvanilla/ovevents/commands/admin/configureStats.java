package org.onlyvanilla.ovevents.commands.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.onlyvanilla.ovevents.Main;
import org.onlyvanilla.ovevents.commands.ovprofile;

import net.md_5.bungee.api.ChatColor;

public class configureStats implements CommandExecutor, Listener {
	
	//OVProfile Instance
	ovprofile ovp1 = new ovprofile();
	
	private static Inventory inv;
	
	String IGN = "";
	
	//Main instance
	private static Main mainClass = Main.getInstance();
	
     //Get playerdataconfig
	static FileConfiguration playerDataConfig = mainClass.getPlayerData();
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player p = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("configurestats")) {
        	if(p.hasPermission("ove.configurestats")) {
	        	IGN = args[0];
	        	String uniqueIDString = Bukkit.getServer().getOfflinePlayer(IGN).getUniqueId().toString();
        		
        		if(playerDataConfig.get(uniqueIDString) == null) {
        			p.sendMessage(ChatColor.RED + IGN + " has never joined the server!");
        		} else {
        			inv = Bukkit.createInventory(null, 9, IGN);
    	        	openInventory(p);
    	        	initializeMenuItems(IGN);
        		}
        	}
        }	
		return true;
	}
	
	//create main menu inventory
	@SuppressWarnings("deprecation")
	public static void initializeMenuItems(String IGN) {
	//player head info
	ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
	SkullMeta meta = (SkullMeta) skull.getItemMeta();
	meta.setOwner(IGN);
	skull.setItemMeta(meta);
		
	inv.setItem(0, createGuiSkull(skull, ChatColor.GRAY + "" + ChatColor.BOLD + "USER: " + ChatColor.WHITE + IGN, 
			ChatColor.GRAY + "" + ChatColor.BOLD + "Rank: " + ChatColor.WHITE + ovprofile.getPlayerGroup(IGN).toUpperCase(),
			ChatColor.GRAY + "" + ChatColor.BOLD + "Date Joined: " + ChatColor.WHITE + "Coming Soon"));
	
	inv.setItem(2, createGuiItem(Material.GOLDEN_HELMET, ChatColor.GOLD + "" + ChatColor.BOLD + "CHAMPIONS TOUR"));
	inv.setItem(3, createGuiItem(Material.BRICKS, ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "BUILD COMPETITIONS"));
	inv.setItem(4, createGuiItem(Material.FILLED_MAP, ChatColor.GREEN + "" + ChatColor.BOLD + "MAP ART CONTESTS"));
	inv.setItem(5, createBannerItem(redvsblueBanner(), ChatColor.RED + "" + ChatColor.BOLD + "RED " +
													   ChatColor.YELLOW + "" + ChatColor.BOLD + "VS " +
													   ChatColor.BLUE + "" + ChatColor.BOLD + "BLUE"));
	inv.setItem(6, createGuiItem(Material.GRAY_DYE, ChatColor.GRAY + "Future Server Event"));	
	}
	
	//create champions tour inventory
	@SuppressWarnings("deprecation")
	public static void initializeChampionsTourItems(String IGN) {
	//player head info
	ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
	SkullMeta meta = (SkullMeta) skull.getItemMeta();
	meta.setOwner(IGN);
	skull.setItemMeta(meta);
		
	inv.setItem(0, createGuiSkull(skull, ChatColor.GRAY + "" + ChatColor.BOLD + "USER: " + ChatColor.WHITE + IGN, 
			ChatColor.GRAY + "" + ChatColor.BOLD + "Rank: " + ChatColor.WHITE + ovprofile.getPlayerGroup(IGN).toUpperCase(),
			ChatColor.GRAY + "" + ChatColor.BOLD + "Date Joined: " + ChatColor.WHITE + "Coming Soon"));
	
	inv.setItem(2, createGuiItem(Material.GOLD_BLOCK, ChatColor.GOLD + "1st Place Finish", 
													  ChatColor.GREEN + "Left click to add 1",
													  ChatColor.RED + "Right click to remove 1"));
	
	inv.setItem(3, createGuiItem(Material.IRON_BLOCK, ChatColor.GRAY + "2nd Place Finish", 
			  ChatColor.GREEN + "Left click to add 1",
			  ChatColor.RED + "Right click to remove 1"));	
	
	inv.setItem(4, createGuiItem(Material.COPPER_BLOCK, ChatColor.YELLOW + "3rd Place Finish", 
			  ChatColor.GREEN + "Left click to add 1",
			  ChatColor.RED + "Right click to remove 1"));
	
	inv.setItem(5, createGuiItem(Material.LAPIS_BLOCK, ChatColor.BLUE + "4th Place Finish", 
			  ChatColor.GREEN + "Left click to add 1",
			  ChatColor.RED + "Right click to remove 1"));
	
	inv.setItem(6, createGuiItem(Material.COAL_BLOCK, ChatColor.LIGHT_PURPLE + "5th-6th Place Finish", 
			  ChatColor.GREEN + "Left click to add 1",
			  ChatColor.RED + "Right click to remove 1"));
	
	inv.setItem(7, createGuiItem(Material.DIAMOND, ChatColor.AQUA + "Open Event Wins", 
			  ChatColor.GREEN + "Left click to add 1",
			  ChatColor.RED + "Right click to remove 1"));
	
	inv.setItem(8, createGuiItem(Material.GOLD_INGOT, ChatColor.GOLD + "Participations", 
			  ChatColor.GREEN + "Left click to add 1",
			  ChatColor.RED + "Right click to remove 1"));
	}
	
	//create champions tour inventory
	@SuppressWarnings("deprecation")
	public static void initializeBuildCompetitionItems(String IGN) {
	//player head info
	ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
	SkullMeta meta = (SkullMeta) skull.getItemMeta();
	meta.setOwner(IGN);
	skull.setItemMeta(meta);
		
	inv.setItem(0, createGuiSkull(skull, ChatColor.GRAY + "" + ChatColor.BOLD + "USER: " + ChatColor.WHITE + IGN, 
			ChatColor.GRAY + "" + ChatColor.BOLD + "Rank: " + ChatColor.WHITE + ovprofile.getPlayerGroup(IGN).toUpperCase(),
			ChatColor.GRAY + "" + ChatColor.BOLD + "Date Joined: " + ChatColor.WHITE + "Coming Soon"));
	
	inv.setItem(2, createGuiItem(Material.GOLD_BLOCK, ChatColor.GOLD + "1st Place Finish", 
													  ChatColor.GREEN + "Left click to add 1",
													  ChatColor.RED + "Right click to remove 1"));
	
	inv.setItem(3, createGuiItem(Material.IRON_BLOCK, ChatColor.GRAY + "2nd Place Finish", 
			  ChatColor.GREEN + "Left click to add 1",
			  ChatColor.RED + "Right click to remove 1"));	
	
	inv.setItem(4, createGuiItem(Material.COPPER_BLOCK, ChatColor.YELLOW + "3rd Place Finish", 
			  ChatColor.GREEN + "Left click to add 1",
			  ChatColor.RED + "Right click to remove 1"));
	
	inv.setItem(8, createGuiItem(Material.GOLD_INGOT, ChatColor.GOLD + "Participations", 
			  ChatColor.GREEN + "Left click to add 1",
			  ChatColor.RED + "Right click to remove 1"));
	}
	
	protected static ItemStack createGuiItem(final Material material, final String name, final String... lore) {
		final ItemStack item = new ItemStack(material, 1);
		final ItemMeta meta = item.getItemMeta();
		
		//set the name of item
		meta.setDisplayName(name);
		
		//set lore of item
		meta.setLore(Arrays.asList(lore));
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	protected static ItemStack createBannerItem(final ItemStack itemStack, final String name, final String... lore) {
		final ItemMeta meta = itemStack.getItemMeta();
		
		//set the name of item
		meta.setDisplayName(name);
		
		//set lore of item
		meta.setLore(Arrays.asList(lore));
		
		itemStack.setItemMeta(meta);
		
		return itemStack;
	}
	
    protected static ItemStack createGuiSkull(final ItemStack skull, final String name, final String... lore) {
    	final ItemMeta meta = skull.getItemMeta();
    	
    	meta.setDisplayName(name);
    	
    	meta.setLore(Arrays.asList(lore));
    	
    	meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	skull.setItemMeta(meta);
    	
    	return skull;
    }
	
	public void openInventory(final HumanEntity ent) {
		ent.openInventory(inv);
	}
	
	@EventHandler
	public void onInventoryClick(final InventoryClickEvent event) {
		if(!event.getInventory().equals(inv)) return;
		
		event.setCancelled(true);
		
		final ItemStack clickedItem = event.getCurrentItem();
		
		String IGN = event.getView().getTitle();
		
		//get player who clicked on item
		final Player p = (Player) event.getWhoClicked();
		
		//if golden helmet go to champions tour inventory
		if(clickedItem.getType() == Material.GOLDEN_HELMET) {
			p.performCommand("configurestatsct " + IGN);
		} else if(clickedItem.getType() == Material.BRICKS) {
			p.performCommand("configurestatsbc " + IGN);
		} else if(clickedItem.getType() == Material.FILLED_MAP) {
			p.performCommand("configurestatsma " + IGN);
		} else if(clickedItem.getType() == redvsblueBanner().getType()) {
			p.performCommand("configurestatsrvb " + IGN);
		}
		
		//verify current item is not null
		if (clickedItem == null || clickedItem.getType().isAir()) return;
	}
	
	@EventHandler
	public void onInventoryClick(final InventoryDragEvent event) {
		if(event.getInventory().equals(inv)) {
			event.setCancelled(true);
		}
	}
    
    //create red vs blue banner
    public static ItemStack redvsblueBanner() {
    	ItemStack itemStack = new ItemStack(Material.RED_BANNER, 1);
    	BannerMeta m = (BannerMeta)itemStack.getItemMeta();
    	
    	List<Pattern> patterns = new ArrayList<Pattern>();
    	
    	patterns.add(new Pattern(DyeColor.BLUE, PatternType.HALF_HORIZONTAL_MIRROR));
    	patterns.add(new Pattern(DyeColor.BLUE, PatternType.HALF_HORIZONTAL_MIRROR));
    	patterns.add(new Pattern(DyeColor.BLUE, PatternType.HALF_HORIZONTAL_MIRROR));
    	m.setPatterns(patterns);
    	
    	m.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
    	itemStack.setItemMeta(m);
    	
    	return itemStack;
    }
}
