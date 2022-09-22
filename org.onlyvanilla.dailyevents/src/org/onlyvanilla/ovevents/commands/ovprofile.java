package org.onlyvanilla.ovevents.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
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

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.ChatColor;

public class ovprofile implements Listener, CommandExecutor{
	
	private static Inventory inv;
	
	//Luckperms api
	static LuckPerms api = LuckPermsProvider.get();
	
	//Main instance
	private static Main mainClass = Main.getInstance();
	
     //Get playerdataconfig
	static FileConfiguration playerDataConfig = mainClass.getPlayerData();
	
	//convert this to using a config instead (like ovevote)
	static Material[] items = {Material.FISHING_ROD, Material.GHAST_TEAR, Material.SHULKER_SHELL, Material.DRAGON_HEAD, Material.COOKED_PORKCHOP,
						Material.CARROT, Material.BLACK_DYE, Material.DIAMOND, Material.SCULK_SENSOR, Material.COOKIE,
						Material.NETHERITE_INGOT, Material.POTATO, Material.IRON_SWORD, Material.COOKED_BEEF, Material.ROTTEN_FLESH};
	
	static ChatColor[] colors = {ChatColor.LIGHT_PURPLE, ChatColor.WHITE, ChatColor.DARK_PURPLE, ChatColor.DARK_GRAY, ChatColor.LIGHT_PURPLE,
								 ChatColor.GOLD, ChatColor.DARK_GRAY, ChatColor.AQUA, ChatColor.DARK_BLUE, ChatColor.YELLOW,
								 ChatColor.GRAY, ChatColor.YELLOW, ChatColor.DARK_RED, ChatColor.WHITE, ChatColor.DARK_GREEN};
	
	public static String[] events = {"Fish Frenzy", "Ghast Hunter", "Shulker Hunt", "Dragon Slayer", "Bring Home The Bacon", "Crazy Carrots",
							  "Bad Bats", "Deep Diamonds", "Warden Warrior", "Cookie Clicker", "Nasty Netherite",
							  "Precious Potatoes", "Hilarious Homicide", "Cow Tipper", "World War Z"};
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player p = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("ovprofile")) { 	
        	if(args.length > 0) {
        		String IGN = args[0];
        		String uniqueIDString = Bukkit.getServer().getOfflinePlayer(IGN).getUniqueId().toString();
        		
        		if(playerDataConfig.get(uniqueIDString) == null) {
        			p.sendMessage(ChatColor.RED + IGN + " has never joined the server!");
        			
        			//ignore admin name
        		} else if(IGN.equals("ADMIN_10")){
        			p.sendMessage(ChatColor.RED + IGN + " has never joined the server!");
        			
        		} else {
        			inv = Bukkit.createInventory(null, 54, "OnlyVanilla Profile | " + IGN);
                	openInventory(p);
                	initializeItems(IGN);
        		}
        	} else {
        		inv = Bukkit.createInventory(null, 54, "OnlyVanilla Profile | " + p.getName());
            	openInventory(p);
            	initializeItems(p.getName());
        	}
        }	
		return true;
	}
	
	public static void initializeItems(String IGN) {
		
	//user data
	String playerUUID = Bukkit.getServer().getOfflinePlayer(IGN).getUniqueId().toString();
	ConfigurationSection playerUUIDSection = playerDataConfig.getConfigurationSection(playerUUID);
	
	//get stats section
	ConfigurationSection statsSection = playerUUIDSection.getConfigurationSection("stats");
	
	//get big events section
	ConfigurationSection bigEventsSection = statsSection.getConfigurationSection("big-events");

	//player head info
	ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
	SkullMeta meta = (SkullMeta) skull.getItemMeta();
	meta.setOwner(IGN);
	skull.setItemMeta(meta);
	
	//add rank
	double playerXP = playerUUIDSection.getDouble("levelExperience");
	int playerLevel = playerUUIDSection.getInt("level");
	inv.setItem(4, createGuiSkull(skull, ChatColor.GRAY + "" + ChatColor.BOLD + "USER: " + ChatColor.WHITE + IGN,
			"",
			ChatColor.GRAY + "" + ChatColor.BOLD + "Rank: " + ChatColor.WHITE + getPlayerGroup(IGN).toUpperCase(),
			ChatColor.GRAY + "" + ChatColor.BOLD + "Date Joined: " + ChatColor.WHITE + "Coming Soon",
			"",
			ChatColor.GRAY + "" + ChatColor.BOLD + "Level: " + ChatColor.GOLD + playerLevel,
			ChatColor.GRAY + "" + ChatColor.BOLD + "Experience: " + ChatColor.YELLOW + (int)playerXP + 
			ChatColor.GRAY + "/" + ChatColor.GOLD + (int)Math.pow(((playerLevel+1)/0.71), 2.2)));
	
	//get champions tour section
	ConfigurationSection championsTourSection = bigEventsSection.getConfigurationSection("champions-tour");
	inv.setItem(11, createGuiItem(Material.GOLDEN_HELMET, ChatColor.GOLD + "" + ChatColor.BOLD + "CHAMPIONS TOUR", 
			ChatColor.GRAY + "1st: " + ChatColor.YELLOW + championsTourSection.getInt("1st"),
			ChatColor.GRAY + "2nd: " + ChatColor.YELLOW + championsTourSection.getInt("2nd"),
			ChatColor.GRAY + "3rd: " + ChatColor.YELLOW + championsTourSection.getInt("3rd"),
			ChatColor.GRAY + "4th: " + ChatColor.YELLOW + championsTourSection.getInt("4th"),
			ChatColor.GRAY + "5th-6th: " + ChatColor.YELLOW + championsTourSection.getInt("5th-6th"),
			ChatColor.GRAY + "",
			ChatColor.GRAY + "Open Event Wins: " + ChatColor.YELLOW + championsTourSection.getInt("Open Event Wins"),
			ChatColor.GRAY + "",
			ChatColor.GRAY + "Participations: " + ChatColor.YELLOW + championsTourSection.getInt("Participations")));
	
	//get champions tour section
	ConfigurationSection buildCompetitionSection = bigEventsSection.getConfigurationSection("build-competitions");
	inv.setItem(12, createGuiItem(Material.BRICKS, ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "BUILD COMPETITIONS", 
			ChatColor.GRAY + "1st: " + ChatColor.YELLOW + buildCompetitionSection.getInt("1st"),
			ChatColor.GRAY + "2nd: " + ChatColor.YELLOW + buildCompetitionSection.getInt("2nd"),
			ChatColor.GRAY + "3rd: " + ChatColor.YELLOW + buildCompetitionSection.getInt("3rd"),
			"",
			ChatColor.GRAY + "Participations: " + ChatColor.YELLOW + buildCompetitionSection.getInt("Participations")));
	
	//get map art contest section
	ConfigurationSection mapArtSection = bigEventsSection.getConfigurationSection("map-art-contests");
	inv.setItem(13, createGuiItem(Material.FILLED_MAP, ChatColor.GREEN + "" + ChatColor.BOLD + "MAP ART CONTESTS", 
			ChatColor.GRAY + "1st: " + ChatColor.YELLOW + mapArtSection.getInt("1st"),
			ChatColor.GRAY + "2nd: " + ChatColor.YELLOW + mapArtSection.getInt("2nd"),
			ChatColor.GRAY + "3rd: " + ChatColor.YELLOW + mapArtSection.getInt("3rd"),
			"",
			ChatColor.GRAY + "Participations: " + ChatColor.YELLOW + mapArtSection.getInt("Participations")));
	
	//get red vs blue contest section
	ConfigurationSection RVBSection = bigEventsSection.getConfigurationSection("red-vs-blue");
	inv.setItem(14, createBannerItem(redvsblueBanner(), ChatColor.RED + "" + ChatColor.BOLD + "RED " +
														ChatColor.YELLOW + "" + ChatColor.BOLD + "VS " +
														ChatColor.BLUE + "" + ChatColor.BOLD + "BLUE", 
			ChatColor.GRAY + "1st: " + ChatColor.YELLOW + RVBSection.getInt("1st"),
			ChatColor.GRAY + "2nd: " + ChatColor.YELLOW + RVBSection.getInt("2nd"),
			ChatColor.GRAY + "3rd: " + ChatColor.YELLOW + RVBSection.getInt("3rd"),
			ChatColor.GRAY + "",
			ChatColor.GRAY + "MVP: " + ChatColor.YELLOW + RVBSection.getInt("mvp"),
			ChatColor.GRAY + "",
			ChatColor.GRAY + "Team Wins: " + ChatColor.YELLOW + RVBSection.getInt("Team Wins"),
			ChatColor.GRAY + "",
			ChatColor.GRAY + "Participations: " + ChatColor.YELLOW + RVBSection.getInt("Participations")));
	
	inv.setItem(15, createGuiItem(Material.GRAY_DYE, ChatColor.GRAY + "Future Server Event"));
	
	//Small events section
	ConfigurationSection smallEventsSection = statsSection.getConfigurationSection("small-events");
	
	int inventorySlot = 29;	
	for(int i = 0; i < 15; i++) {
		inv.setItem(inventorySlot, createGuiItem(items[i], colors[i] + events[i], 
				ChatColor.GRAY + "Wins: " + ChatColor.YELLOW + smallEventsSection.getInt(events[i])));
		
		if(i==4 || i==9) {
			inventorySlot += 5;
		} else {
			inventorySlot++;
			}
		}	
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
		
		//verify current item is not null
		if (clickedItem == null || clickedItem.getType().isAir()) return;
		
		final Player p = (Player) event.getWhoClicked();
	}
	
	@EventHandler
	public void onInventoryClick(final InventoryDragEvent event) {
		if(event.getInventory().equals(inv)) {
			event.setCancelled(true);
		}
	}
	
//    @EventHandler
//    public void onInventoryClose(final InventoryCloseEvent e, Player p) {
//    	HandlerList.unregisterAll(this);
//    	Bukkit.getServer().getScheduler().runTaskLater(Main.getPlugin(Main.class), p::updateInventory, 1L);
//    }
    
    //create red vs blue banner
    public static ItemStack redvsblueBanner() {
    	ItemStack itemStack = new ItemStack(Material.RED_BANNER, 1);
    	BannerMeta m = (BannerMeta)itemStack.getItemMeta();
    	
    	List<Pattern> patterns = new ArrayList<Pattern>();
    	
    	//add banner design 3 times
    	for(int i = 0; i <3; i ++) {
    		patterns.add(new Pattern(DyeColor.BLUE, PatternType.HALF_HORIZONTAL_MIRROR));
    	}

    	m.setPatterns(patterns);
    	
    	m.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
    	itemStack.setItemMeta(m);
    	
    	return itemStack;
    }
    
    public static String getPlayerGroup(String username) {
		//Find user group
		UUID userUUID = Bukkit.getOfflinePlayer(username).getUniqueId();
		User user = api.getUserManager().loadUser(userUUID).join();
		
		//get user groups
		String group = user.getPrimaryGroup();
		
		//return player group
		return group;
    }
}
