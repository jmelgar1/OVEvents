package org.onlyvanilla.ovevents.runnables;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

	@Override
	public void run() {
		new BukkitRunnable() {
			@SuppressWarnings({ "deprecation", "unlikely-arg-type" })
			public void run() {
				
				//use playerinteractevent instead. Maybe just prevent player from left clicking instead of deleting item.
				
				for(Player p : Bukkit.getOnlinePlayers()) {
					ItemStack item = p.getItemInHand();
					List<String> itemLore = item.getItemMeta().getLore();
					if(itemLore.contains(ChatColor.RED + "Level 100+")) {
						if(!findGroups(p).contains("grandmaster")) {
							p.getInventory().removeItem(item);
						}
					} else if(itemLore.contains(ChatColor.RED + "Level 90+")) {
						if(!findGroups(p).contains("master")) {
							p.getInventory().removeItem(item);
						}
					} else if(itemLore.contains(ChatColor.RED + "Level 80+")) {
						if(!findGroups(p).contains("elite")) {
							p.getInventory().removeItem(item);
						}
					} else if(itemLore.contains(ChatColor.RED + "Level 70+")) {
						if(!findGroups(p).contains("expert")) {
							p.getInventory().removeItem(item);
						}
					} else if(itemLore.contains(ChatColor.RED + "Level 60+")) {
						if(!findGroups(p).contains("specialist")) {
							p.getInventory().removeItem(item);
						}
					} else if(itemLore.contains(ChatColor.RED + "Level 50+")) {
						if(!findGroups(p).contains("veteran")) {
							p.getInventory().removeItem(item);
						}
					} else if(itemLore.contains(ChatColor.RED + "Level 40+")) {
						if(!findGroups(p).contains("skilled")) {
							p.getInventory().removeItem(item);
						}
					} else if(itemLore.contains(ChatColor.RED + "Level 30+")) {
						if(!findGroups(p).contains("adept")) {
							p.getInventory().removeItem(item);
						}
					} else if(itemLore.contains(ChatColor.RED + "Level 20+")) {
						if(!findGroups(p).contains("initiate")) {
							p.getInventory().removeItem(item);
						}
					} else if(itemLore.contains(ChatColor.RED + "Level 10+")) {
						if(!findGroups(p).contains("apprentice")) {
							p.getInventory().removeItem(item);
						}
					}
				}
			}
		}.runTaskTimer(mainClass, 1l, 1l);
	}
	
	@SuppressWarnings("deprecation")
	public Collection<Group> findGroups(Player p) {		
		UUID userUUID = Bukkit.getOfflinePlayer(p.getName()).getUniqueId();
		User user = api.getUserManager().loadUser(userUUID).join();
		
		//get user groups
		Collection<Group> inheritedGroups = user.getInheritedGroups(QueryOptions.defaultContextualOptions());
		
		return inheritedGroups;
	}
}
