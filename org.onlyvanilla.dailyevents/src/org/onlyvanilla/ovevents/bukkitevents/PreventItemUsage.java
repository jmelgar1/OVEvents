package org.onlyvanilla.ovevents.bukkitevents;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.onlyvanilla.ovevents.Main;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;

public class PreventItemUsage implements Listener {
	
	//Main instance
	private Main mainClass = Main.getInstance();
	
	//Luckperms api
	LuckPerms api = LuckPermsProvider.get();
	
	Group grandmaster = api.getGroupManager().getGroup("grandmaster");
	Group master = api.getGroupManager().getGroup("master");
	Group elite = api.getGroupManager().getGroup("elite");
	Group expert = api.getGroupManager().getGroup("expert");
	Group specialist = api.getGroupManager().getGroup("specialist");
	Group veteran = api.getGroupManager().getGroup("veteran");
	Group skilled = api.getGroupManager().getGroup("skilled");
	Group adept = api.getGroupManager().getGroup("adept");
	Group initiate = api.getGroupManager().getGroup("initiate");
	Group apprentice = api.getGroupManager().getGroup("apprentice");
	Group novice = api.getGroupManager().getGroup("novice");
	
	String[] levels = {"100", "90", "80", "70", "60", "50", "40", "30", "20", "10"};
	Group[] groups = {grandmaster, master, elite, expert, specialist, veteran, skilled, adept, initiate, apprentice, novice};

	//get list of restricted items
	private List<Material> toolMaterials = Arrays.asList(Material.NETHERITE_AXE, Material.NETHERITE_SWORD, Material.NETHERITE_PICKAXE, Material.RECOVERY_COMPASS, Material.BOW);
	@SuppressWarnings("deprecation")
	
	//prevent damaging mobs/players
	@EventHandler
	public void onPlayerDamage(EntityDamageByEntityEvent e) {
		Player p = (Player) e.getDamager();
		
		if(p.getItemInHand() != null) {
			
			ItemStack item = p.getItemInHand();
			Material material = item.getType();
			
			if(toolMaterials.contains(material)) {
		
				List<String> itemLore = item.getItemMeta().getLore();
				
				if(itemLore != null) {
					for(int i = 0; i < 10; i++) {
						if(cancelCheck(p, itemLore, levels[i], groups[i]) == true) {
							e.setCancelled(true);
						}
					}
				}
			}
		}
	}
	
	//prevent damaging blocks
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockDamage(BlockDamageEvent e) {
		Player p = e.getPlayer();
		
		if(p.getItemInHand() != null) {
			
			ItemStack item = p.getItemInHand();
			Material material = item.getType();
			
			if(toolMaterials.contains(material)) {
		
				List<String> itemLore = item.getItemMeta().getLore();
				
				if(itemLore != null) {
					for(int i = 0; i < 10; i++) {
						if(cancelCheck(p, itemLore, levels[i], groups[i]) == true) {
							e.setCancelled(true);
						}
					}
				}
			}
		}
	}
	
	//Get armor slots
	private List<Integer> armorSlots = Arrays.asList(36, 37, 38, 39);
	
	//get list of restricted items
	private List<Material> armorMaterials = Arrays.asList(Material.NETHERITE_HELMET, Material.NETHERITE_BOOTS, Material.ELYTRA);
	
	//prevent equiping armor
	@EventHandler
	public void onEquip(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		
		ItemStack currentItem = e.getCurrentItem();	
		ItemStack item = p.getItemOnCursor();
		
		//if item is a current item (shift clicked)
		if(currentItem != null) {
			Material currentMaterial = currentItem.getType();
			if(currentMaterial != Material.AIR && 
				armorMaterials.contains(currentMaterial)) {
			
				List<String> itemLore2 = currentItem.getItemMeta().getLore();
				
				if(itemLore2 != null) {
					for(int i = 0; i < 10; i++) {
						checkForItemEquip(p, itemLore2, levels[i], groups[i], e);
					}
				}
			}
		}
		
		//if item is a cursor item (clicked on)
		if(item != null) {
			Material material = item.getType();
			if(material != Material.AIR && 
				armorMaterials.contains(material)) {
			
			
				List<String> itemLore = item.getItemMeta().getLore();
				
				if(itemLore != null) {
					for(int i = 0; i < 10; i++) {
						checkForItemEquip(p, itemLore, levels[i], groups[i], e);
					}
				}
			}
		}
	}
	
	//PREVENT EQUIPING ARMOR WITH INVENTORY DRAG
	@EventHandler
	public void onEquipDrag(InventoryDragEvent e) {
		Player p = (Player) e.getView().getPlayer();
		
			if(e.getOldCursor() != null) {
				
				ItemStack item = e.getOldCursor();
				Material material = item.getType();
				
				if(armorMaterials.contains(material)) {
			
					List<String> itemLore = item.getItemMeta().getLore();
					
					if(itemLore != null) {
						if(e.getInventorySlots().contains(36) ||
						   e.getInventorySlots().contains(37) ||
						   e.getInventorySlots().contains(38) ||
						   e.getInventorySlots().contains(39)) {
							if(itemLore != null) {
								for(int i = 0; i < 10; i++) {
									if(cancelCheck(p, itemLore, levels[i], groups[i]) == true) {
										e.setCancelled(true);
									}
								}
							}
					}
				}
			}
		}
	}
	
	//PREVENT RIGHT CLICKING ARMOR AND USING BOW
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			if(p.getItemInHand() != null && p.getItemInHand().getItemMeta() != null && p.getItemInHand().getItemMeta().getLore() != null) {
				
				ItemStack item = p.getItemInHand();
				Material material = item.getType();
				List<String> itemLore = item.getItemMeta().getLore();
				
				if(armorMaterials.contains(material) || material == Material.BOW || material == Material.FISHING_ROD) {
					
					for(int i = 0; i < 10; i++) {
						if(cancelCheck(p, itemLore, levels[i], groups[i]) == true) {
							e.setCancelled(true);
						}
					}
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	Collection<Group> findGroups(Player p) {		
		UUID userUUID = Bukkit.getOfflinePlayer(p.getName()).getUniqueId();
		User user = api.getUserManager().loadUser(userUUID).join();
		
		//get user groups
		Collection<Group> inheritedGroups = user.getInheritedGroups(QueryOptions.defaultContextualOptions());
		
		return inheritedGroups;
	}
	
	void checkForItemEquip(Player p, List<String> itemLore, String level, Group rank, InventoryClickEvent e){
		int slot = e.getSlot();
		if(itemLore.contains(ChatColor.RED + "Level " + level + "+")) {
			if(!findGroups(p).contains(rank)) {
				if(armorSlots.contains(slot) && (e.getClick().isLeftClick() || e.getClick().isRightClick())) {
					p.sendMessage(mainClass.prefix + ChatColor.RED + "You must be level " + level + "+ to use this!");
					e.setCancelled(true);
				} else if(e.getClick().isShiftClick()){
					e.setCancelled(true);
					p.sendMessage(mainClass.prefix + ChatColor.RED + "You must be level " + level + "+ to use this!");
				}
			}
		}
	}
	
	boolean cancelCheck(Player p, List<String> itemLore, String level, Group rank) {
		if(itemLore.contains(ChatColor.RED + "Level " + level + "+")) {
			if(!findGroups(p).contains(rank)) {
				p.sendMessage(mainClass.prefix + ChatColor.RED + "You must be level " + level + "+ to use this!");
				return true;
			}
			return false;
		}
		return false;
	}
}
