package me.twentyonez.guardianchest.compat;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * GuardianChest mod
 *
 * @author TwentyOneZ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 * Based on NightKosh's GraveStone mod, Dr.Cyano's Lootable Corpses mod and Tyler15555's Death Chest mod.
 */

public class GCminecraft {

    private GCminecraft() {

    }

    public static void addItems(List<ItemStack> items, List<Integer> slot, List<String> type, EntityPlayer player, Integer saveItems, Integer sbInventoryLevel) {
		// Get main inventory
		for(int i = 0; i < player.inventory.mainInventory.length; i++){
			ItemStack droppedItem = player.inventory.getStackInSlot(i);
			if (droppedItem != null) {
				items.add(droppedItem);
                slot.add(i);
                type.add("vanillaMain");
                if ((saveItems != 0) || GCsoulBinding.keepItem(droppedItem, i, "vanillaMain", player, sbInventoryLevel)) {
                	player.inventory.mainInventory[i] = null;
                }
			}
		}
		
		// Get armor inventory
		for(int i = 0; i < player.inventory.armorInventory.length; i++){
			ItemStack droppedItem = player.inventory.armorItemInSlot(i);
			
			if (droppedItem != null) {
				items.add(droppedItem);
                slot.add(i);
                type.add("vanillaArmor");
                if ((saveItems != 0) || GCsoulBinding.keepItem(droppedItem, i, "vanillaArmor", player, sbInventoryLevel)) {
                	player.inventory.armorInventory[i] = null;
                }
			}
		}
		
    }

}