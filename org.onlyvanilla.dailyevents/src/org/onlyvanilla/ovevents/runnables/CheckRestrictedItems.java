package org.onlyvanilla.ovevents.runnables;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.onlyvanilla.ovevents.Main;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;

public class CheckRestrictedItems extends BukkitRunnable {
	
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

	@Override
	public void run() {
		new BukkitRunnable() {
			@SuppressWarnings("deprecation")
			public void run() {
				
				//use playerinteractevent instead. Maybe just prevent player from left clicking instead of deleting item.
				
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(p.getItemInHand() != null) {
						
						ItemStack item = p.getItemInHand();
						Material material = item.getType();
						
						if(material == Material.NETHERITE_AXE
								|| material == Material.NETHERITE_PICKAXE
								|| material == Material.NETHERITE_SWORD
								|| material == Material.NETHERITE_HELMET
								|| material == Material.NETHERITE_BOOTS
								|| material == Material.FISHING_ROD
								|| material == Material.ELYTRA
								|| material == Material.BOW
								|| material == Material.RECOVERY_COMPASS) {
					
							List<String> itemLore = item.getItemMeta().getLore();
							
							if(itemLore != null) {
								checkForItem(p, item, itemLore, "100", grandmaster);
								checkForItem(p, item, itemLore, "90", master);
								checkForItem(p, item, itemLore, "80", elite);
								checkForItem(p, item, itemLore, "70", expert);
								checkForItem(p, item, itemLore, "60", specialist);
								checkForItem(p, item, itemLore, "50", veteran);
								checkForItem(p, item, itemLore, "40", skilled);
								checkForItem(p, item, itemLore, "30", adept);
								checkForItem(p, item, itemLore, "20", initiate);
								checkForItem(p, item, itemLore, "10", apprentice);
							}
						}
					}
				}
			}
		}.runTaskTimer(mainClass, 1l, 1l);
	}
	
	void checkForItem(Player p, ItemStack item, List<String> itemLore, String level, Group rank){
		if(itemLore.contains(ChatColor.RED + "Level " + level + "+")) {
			if(!findGroups(p).contains(rank)) {
				p.getInventory().removeItem(item);
				p.sendMessage(mainClass.prefix + ChatColor.RED + "You must be level " + level + "+ to use this!");
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
}
