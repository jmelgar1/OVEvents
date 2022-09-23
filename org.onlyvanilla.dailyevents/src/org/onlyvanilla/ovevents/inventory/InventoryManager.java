package org.onlyvanilla.ovevents.inventory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryManager implements Listener{
	
	public static Inventory inv1;
	public static Map<UUID, Inventory> inventories = new HashMap<UUID, Inventory>();
	
	//create gui item
	public static ItemStack createGuiItem(final Material material, final String name, final String... lore) {
		final ItemStack item = new ItemStack(material, 1);
		final ItemMeta meta = item.getItemMeta();
		
		//set the name of item
		meta.setDisplayName(name);
		
		//set lore of item
		meta.setLore(Arrays.asList(lore));
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	//create skull item
    public static ItemStack createGuiSkull(final ItemStack skull, final String name, final String... lore) {
    	final ItemMeta meta = skull.getItemMeta();
    	
    	meta.setDisplayName(name);
    	
    	meta.setLore(Arrays.asList(lore));
    	
    	meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	skull.setItemMeta(meta);
    	
    	return skull;
    }
	
    //create item with enchantment
	public static ItemStack createGuiItemEnchantment(final Material material, Enchantment enchantment, int enchantLevel, boolean showEnchant, final String name, final String... lore) {
		final ItemStack item = new ItemStack(material, 1);
		final ItemMeta meta = item.getItemMeta();
		
		meta.addEnchant(enchantment, enchantLevel, showEnchant);
		
		//set the name of item
		meta.setDisplayName(name);
		
		//set lore of item
		meta.setLore(Arrays.asList(lore));
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	//create item with two enchantments
	public static ItemStack createGuiItemTwoEnchantment(final Material material, Enchantment enchantment, int enchantLevel, boolean showEnchant, 
			Enchantment enchantment2, int enchantLevel2, boolean showEnchant2, final String name, final String... lore) {
		
		final ItemStack item = new ItemStack(material, 1);
		final ItemMeta meta = item.getItemMeta();
		
		meta.addEnchant(enchantment, enchantLevel, showEnchant);
		
		meta.addEnchant(enchantment2, enchantLevel2, showEnchant2);
		
		//set the name of item
		meta.setDisplayName(name);
		
		//set lore of item
		meta.setLore(Arrays.asList(lore));
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	//create banner item
	public static ItemStack createBannerItem(final ItemStack itemStack, final String name, final String... lore) {
		final ItemMeta meta = itemStack.getItemMeta();
		
		//set the name of item
		meta.setDisplayName(name);
		
		//set lore of item
		meta.setLore(Arrays.asList(lore));
		
		itemStack.setItemMeta(meta);
		
		return itemStack;
	}
	
	//EVENTHANDLERS
	
	
}
