package org.onlyvanilla.ovevents.bukkitevents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.onlyvanilla.ovevents.Main;
import org.onlyvanilla.ovevents.commands.accept;
import org.onlyvanilla.ovevents.managers.InventoryManager;
import org.onlyvanilla.ovevents.prompts.WarpNamePrompt;

import net.md_5.bungee.api.ChatColor;

public class MagicItemsCheck extends InventoryManager implements Listener {
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	//Main instance
	private static MagicItemsCheck instance;
	
	ConfigurationSection playerData = mainClass.getPlayerData();
	
	EditPlayerPoints epp1 = new EditPlayerPoints();
	
	private static Inventory inv;
	
	//list to store task ids
	List<Integer> tasks = new ArrayList<Integer>();
	
	//create map to keep track of teleports
	public Map<Player, Player> playerTPToMap = new HashMap<Player, Player>();
	public Map<Player, Player> playerTPFromMap = new HashMap<Player, Player>();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(PlayerInteractEvent event) {
		
		instance = this;
		
		Player p = event.getPlayer();
		Action a = event.getAction();
		if(p.getItemInHand().getItemMeta() != null) {
			String itemDisplayName = p.getItemInHand().getItemMeta().getDisplayName();
			
			personalWarpDeviceCheck(p, a, itemDisplayName, event);
			ascensionDeviceCheck(p, a, itemDisplayName, event);
			spawnTeleportationDeviceCheck(p, a, itemDisplayName, event);
			playerTeleportationDeviceCheck(p, a, itemDisplayName, event);
		}
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
		
		//personal warp
		if(clickedItem.getType() == Material.GOLD_BLOCK) {
			int warpsAllowed = playerUUIDSection.getInt("warpsAllowed");
			if(warpsAllowed <= 8) {
				warpsAllowed += 1;
				playerUUIDSection.set("warpsAllowed", warpsAllowed);
				p.sendMessage(mainClass.prefix + ChatColor.GREEN + "You have purchased a personal warp!");
				p.sendMessage(mainClass.prefix + ChatColor.RED + "-5,000 OV-XP");
				epp1.removeLevelXP(5000, playerUUIDSection);
				mainClass.savePlayerDataFile();
			} else {
				p.sendMessage(mainClass.prefix + ChatColor.RED + "Max amount of personal warps is 8!");
			}
		} else if(clickedItem.getType() == Material.EMERALD) {
			
			//send success message & close inventory
			p.sendMessage(mainClass.prefix + ChatColor.GREEN + "Warping in 10 seconds...");
			p.closeInventory();

			new BukkitRunnable(){

                @Override
                public void run() {
                	//retrieve warp location from config
        			ConfigurationSection warpSection = playerUUIDSection.getConfigurationSection("warps");
        			ConfigurationSection warp = warpSection.getConfigurationSection(ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()));
        			double x = Double.parseDouble(warp.getString("x"));
        			double y = Double.parseDouble(warp.getString("y"));
        			double z = Double.parseDouble(warp.getString("z"));
        			int yaw = (int)Double.parseDouble(warp.getString("yaw"));
        			int pitch = (int)Double.parseDouble(warp.getString("pitch"));
        			String world = warp.getString("world");
        			Location loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
        			
        			//teleport player
        			p.teleport(loc);
                }
               
            }.runTaskLater(mainClass, 200);
		}
		
		//player teleport
		if(clickedItem.getType() == Material.PLAYER_HEAD && event.getView().getTitle() == "Online Players") {
			String playerName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
			Player pl = Bukkit.getServer().getPlayer(playerName);
			
			if(event.getClick() == ClickType.LEFT) {
				
				if(!playerTPFromMap.containsKey(p) && !playerTPToMap.containsKey(p)) {
					
					//teleporter, teleportee
					playerTPToMap.put(p, pl);
					
					pl.sendMessage(mainClass.prefix + ChatColor.GREEN + p.getName() + " is requesting to warp to you! "
					+ ChatColor.GRAY + "\nType " + ChatColor.DARK_GREEN + "/accept " + p.getName() + ChatColor.GRAY + " to accept the warp request. "
					+ ChatColor.GRAY + "\nType " + ChatColor.DARK_RED + "/decline " + p.getName() + ChatColor.GRAY + " to decline the warp request."
					+ ChatColor.DARK_GRAY + "\nThis request will expire in 1 minute.");
				} else {
					p.sendMessage(mainClass.prefix + ChatColor.RED + "You can only warp request once a minute!");
				}
				
				//cool down for player teleport
				new BukkitRunnable(){

	                @Override
	                public void run() {
	                	if(playerTPToMap.containsKey(p)) {
	                		pl.sendMessage(mainClass.prefix + ChatColor.RED + playerTPToMap.get(p).getName() + "'s warp request has expired");
	                		p.sendMessage(mainClass.prefix + ChatColor.RED + "Your warp request has expired.");
	                		playerTPToMap.remove(p);	                	
		                }
	                }
	               
	            }.runTaskLater(mainClass, 1200);
					
			} else if (event.getClick() == ClickType.RIGHT) {
				
				if(!playerTPFromMap.containsKey(p) && !playerTPToMap.containsKey(p)) {
					
					//teleportee, teleporter
					playerTPFromMap.put(p, pl);
					
					pl.sendMessage(mainClass.prefix + ChatColor.GREEN + p.getName() + " is requesting for you to warp to them! "
					+ ChatColor.GRAY + "\nType " + ChatColor.DARK_GREEN + "/accept " + p.getName() + ChatColor.GRAY + " to accept the warp request. "
					+ ChatColor.GRAY + "\nType " + ChatColor.DARK_RED + "/decline " + p.getName() + ChatColor.GRAY + " to decline the warp request."
					+ ChatColor.DARK_GRAY + "\nThis request will expire in 1 minute.");
				} else {
					p.sendMessage(mainClass.prefix + ChatColor.RED + "You can only warp request once a minute!");
				}
				
				//cool down for player teleport
				new BukkitRunnable(){

	                @Override
	                public void run() { 	
	                	if(playerTPFromMap.containsKey(p)) {
	                		pl.sendMessage(mainClass.prefix + ChatColor.RED + playerTPFromMap.get(p).getName() + "'s warp request has expired");
	                		p.sendMessage(mainClass.prefix + ChatColor.RED + "Your warp request has expired.");	                	
		                	playerTPFromMap.remove(p);
		                }
	                }
	               
	            }.runTaskLater(mainClass, 1200);
	            
	            new BukkitRunnable(){

	                @Override
	                public void run() {
	                	playerTPFromMap.remove(pl);
	                }
	               
	            }.runTaskLater(mainClass, 1200);
			}
		}
	}

	//Main instance
	public static MagicItemsCheck getInstance() {
		return instance;
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
				ChatColor.GRAY + "Cost: " + ChatColor.GOLD + "5,000 OV-XP"));
    }
	
	void playerTeleportationDeviceCheck(Player p, Action a, String itemDisplayName, PlayerInteractEvent event) {
		if((a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) && itemDisplayName.equals(ChatColor.DARK_BLUE + "Player Teleportation Device")) {
			if(event.getHand() == EquipmentSlot.HAND) {
				
				if(ensureOwnership(p) == true) {
					event.setCancelled(true);
				}
				
				inv = Bukkit.createInventory(p, 9, "Online Players");
        		inventories.put(p.getUniqueId(), inv);
            	openInventory(p, inventories.get(p.getUniqueId()));
				initalizePTDCItems(p);
			}
		} else if((a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK) && itemDisplayName.equals(ChatColor.DARK_BLUE + "Player Teleportation Device")){
			if(event.getHand() == EquipmentSlot.HAND) {
				
				//Main instance
				accept Accept = accept.getInstance();
				
				if(Accept != null) {
					if(Accept.getWaitingStatus() == true) {
						if(!Accept.waitingWarps.isEmpty()) {
							if(Accept.waitingWarps.containsKey(p)) {
								int taskID = Accept.waitingWarps.get(p);
								Bukkit.getScheduler().cancelTask(taskID);
								Accept.waitingWarps.remove(p);
								p.sendMessage(mainClass.prefix + ChatColor.RED + "Warp cancelled!");
								playerTPToMap.remove(p);
								playerTPFromMap.remove(p);
							}
						}
					}
				}
			}
		}
	}
	
	void initalizePTDCItems(Player p) {
		int count = 0;
		for(Player pl : Bukkit.getServer().getOnlinePlayers()) {
			//player head info
			if(pl.getName() != p.getName()) {
				ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
				SkullMeta meta = (SkullMeta) skull.getItemMeta();
				meta.setOwner(pl.getName());
				skull.setItemMeta(meta);
				
				inv.setItem(count, createGuiSkull(skull, ChatColor.GREEN + pl.getName(),
						"",
						ChatColor.GRAY + "LEFT CLICK - Teleport to " + pl.getName(),
						ChatColor.GRAY + "RIGHT CLICK - Teleport " + pl.getName() + " to you."));
				count++;
			}
		}
	}
	
	void spawnTeleportationDeviceCheck(Player p, Action a, String itemDisplayName, PlayerInteractEvent event) {
		if((a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) && itemDisplayName.equals(ChatColor.DARK_GREEN + "Spawn Teleportation Device")) {
			if(event.getHand() == EquipmentSlot.HAND) {
				
				if(ensureOwnership(p) == true) {
					event.setCancelled(true);
				}
				
				if(tasks.isEmpty()) {
					
					//send success message & close inventory
					p.sendMessage(mainClass.prefix + ChatColor.GREEN + "Warping in 10 seconds...");
					
					tasks.add(new BukkitRunnable(){
					
		                @Override
		                public void run() {
		                	int x = (int)(Math.random()*(250-(-250)+1)+(-250));  
		    				int z = (int)(Math.random()*(250-(-250)+1)+(-250));  
		    				
		    				Block block = p.getWorld().getHighestBlockAt(x, z).getRelative(BlockFace.UP);
		    				
		    				Location loc = block.getLocation();
		    				p.teleport(loc);
		    				tasks.clear();
		                }
		            }.runTaskLater(mainClass, 200).getTaskId());
				} else {
					p.sendMessage(mainClass.prefix + ChatColor.RED + "Please wait before doing that!");
				}
			}
		} else if((a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK) && itemDisplayName.equals(ChatColor.DARK_GREEN + "Spawn Teleportation Device")){
			if(event.getHand() == EquipmentSlot.HAND) {

				if(!tasks.isEmpty()) {
					int taskID = tasks.get(0);
					Bukkit.getScheduler().cancelTask(taskID);
					tasks.clear();
					p.sendMessage(mainClass.prefix + ChatColor.RED + "Warp cancelled!");
				}
			}
		}
	}
	
	void ascensionDeviceCheck(Player p, Action a, String itemDisplayName, PlayerInteractEvent event) {
		if((a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) && itemDisplayName.equals(ChatColor.DARK_PURPLE + "Ascension Device")) {
			if(event.getHand() == EquipmentSlot.HAND) {
				
				if(ensureOwnership(p) == true) {
					event.setCancelled(true);
				}
				
				if(tasks.isEmpty()) {
				
				//send success message & close inventory
					p.sendMessage(mainClass.prefix + ChatColor.GREEN + "Warping in 10 seconds...");
				
					tasks.add(new BukkitRunnable() {
					
		                @Override
		                public void run() {
		                	int x = p.getLocation().getBlockX();
		    				int z = p.getLocation().getBlockZ();
		    				
		    				Block block = p.getWorld().getHighestBlockAt(x, z).getRelative(BlockFace.UP);
		    				
		    				Location loc = block.getLocation();
		    				p.teleport(loc);
		    				tasks.clear();
		                }
		            }.runTaskLater(mainClass, 200).getTaskId());
				} else {
					p.sendMessage(mainClass.prefix + ChatColor.RED + "Please wait before doing that!");
				}
			}
		} else if((a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK) && itemDisplayName.equals(ChatColor.DARK_PURPLE + "Ascension Device")){
			if(event.getHand() == EquipmentSlot.HAND) {

					if(!tasks.isEmpty()) {
						int taskID = tasks.get(0);
						Bukkit.getScheduler().cancelTask(taskID);
						tasks.clear();
						p.sendMessage(mainClass.prefix + ChatColor.RED + "Warp cancelled!");
				}
			}
		}
	}
	
	void removeSelectPair(Map<Player, Player> map, Player teleportee, Player teleporter) {
	    for(Map.Entry<Player, Player> entry : map.entrySet()) {
	    	Player p1 = entry.getKey();
	    	Player p2 = entry.getValue();
	    	
	    	if(p1.equals(teleportee) && p2.equals(teleporter)) {
	    		map.remove(p1);
	    		break;
	    	}
	    }
	}
}
