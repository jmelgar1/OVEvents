package org.onlyvanilla.ovevents.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.onlyvanilla.ovevents.Main;
import org.onlyvanilla.ovevents.managers.InventoryManager;
import org.onlyvanilla.ovevents.smallevents.DetermineEventData;

import net.md_5.bungee.api.ChatColor;

public class ovevote extends InventoryManager implements CommandExecutor, Listener{
	
	//Main instance
	public static Main mainClass = Main.getInstance();
	
	static DetermineEventData dev1 = new DetermineEventData();
	
	private static Inventory inv;
	
	public static Map<UUID, Inventory> inventories = new HashMap<UUID, Inventory>();
	
    //create empty hashmap for voting
    static Map<String, ConfigurationSection> eventVote = new HashMap<String, ConfigurationSection>();
		
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	
    	int eventID = mainClass.getEventData().getInt("eventid");
    	
        Player p = (Player) sender;
            if(cmd.getName().equalsIgnoreCase("ovevote")) {     	
            	UUID playerUUID = p.getUniqueId();
            	
            	if(eventID != 0) {
            		if(checkIfSignedUp(p) == false){
                		inv = Bukkit.createInventory(p, 9, "OnlyVanilla | Daily Event Vote");
                		inventories.put(playerUUID, inv);
                    	openInventory(p, inventories.get(playerUUID));
                    	initalizeItems();
                	} else {
                		p.sendMessage(mainClass.prefix + ChatColor.RED + "You already voted!");
                	}
            	} else {
            		p.sendMessage(mainClass.prefix + ChatColor.RED + "Voting for this event has ended!");
            	}
        }
        return true;
    }
    
    @SuppressWarnings("deprecation")
	public static void initalizeItems() {
    	
    	int inventorySlot = 3;
    	
    	//gets all events from list
    	for(String names : mainClass.dev1.getList().subList(0, 3)) {
    		
    		ConfigurationSection eventName = mainClass.getSmallEvents().getConfigurationSection(names);
    		
    		String description = eventName.getString("description");				
    		String item = eventName.getString("item");
    		String name = eventName.getString("name");
    		String color = eventName.getString("color");
    		int duration = eventName.getInt("duration");
    		
    		//add event to eventVote HashMap with 0 votes
    		eventVote.put(name, eventName);
    		
    		ConfigurationSection eventPlacement = eventName.getConfigurationSection("placements");
    		int firstPlaceXP = eventPlacement.getInt("first");
    		int secondPlaceXP = eventPlacement.getInt("second");
    		int thirdPlaceXP = eventPlacement.getInt("third");

    		inv.setItem(inventorySlot, createGuiItem(Material.getMaterial(item), ChatColor.valueOf(color) + name,
    								  "",
    								  ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "Description",
    								  ChatColor.GOLD + "" + description,
    								  "",
    								  ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "Duration",
    								  ChatColor.GOLD + "" + duration + " minutes",
    								  "",
    								  ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "Rewards",
    								  ChatColor.GRAY + "1st: " + ChatColor.GOLD + firstPlaceXP + " XP",
    								  ChatColor.GRAY + "2nd: " + ChatColor.GOLD + secondPlaceXP + " XP",
    								  ChatColor.GRAY + "3rd: " + ChatColor.GOLD + thirdPlaceXP + " XP"));
    		inventorySlot++;
    	}
    }
    
    public void openInventory(final HumanEntity ent, Inventory inv) {
    	ent.openInventory(inv);
    }
    
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
    	final Player p = (Player) e.getWhoClicked();
		UUID playerUUID = p.getUniqueId();
		if(!e.getInventory().equals(inventories.get(playerUUID))) return;
        
    	e.setCancelled(true);
    	
        final ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType().isAir()) return;
        
        //get the itemname
        String itemName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
        
        //get configuration section of the item
        ConfigurationSection eventSection = eventVote.get(itemName);

        //get current votes
		int previousVotes = eventSection.getInt("votes");
		
		//add 1 to the vote
		eventSection.set("votes", previousVotes+1);
		mainClass.saveSmallEventsFile();
		
		//add player to temp event list
		addPlayerToEventList(p, (List<String>)mainClass.getEventData().getStringList("participants"), itemName); 
    }

    
    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
		if(e.getInventory().equals(inventories.get(e.getWhoClicked().getUniqueId()))) {
			e.setCancelled(true);
		}
    }
    
    //add player to event list
    public void addPlayerToEventList(Player p, List<String> participantList, String itemName) {
    	try {   		
    		participantList.add(p.getName());
        	mainClass.getEventData().set("participants", participantList);
        	mainClass.saveEventDataFile();
    	} catch (Exception e){
    		System.out.println(e);
    	}
    	
        //success sound/messages
        Bukkit.broadcastMessage(mainClass.prefix + p.getName() + " voted for " + itemName);
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1F, 1F);	
        p.closeInventory(); 
    }
    
    //check if player is in event list
	public boolean checkIfSignedUp(Player p) {	
		for(String s : (List<String>)mainClass.getEventData().getStringList("participants")) {
			if(s.equals(p.getName())) { 
				return true;	
			}
    	}
		return false;
	}
	
	public void clearAllInventories() {
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			UUID playerUUID = p.getUniqueId();
			Inventory voteInv = inventories.get(playerUUID);
			if(voteInv != null) {
				voteInv.clear();
			}
		}
	}
}
