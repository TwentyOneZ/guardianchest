package me.twentyonez.guardianchest.compat;

import java.util.List;

import me.twentyonez.guardianchest.util.ConfigHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import baubles.api.BaublesApi;

/**
 * GuardianChest mod
 *
 * @author TwentyOneZ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 * Based on NightKosh's GraveStone mod, Dr.Cyano's Lootable Corpses mod and Tyler15555's Death Chest mod.
 */
public class GCTravellersGear {

    protected static boolean isInstalled = false;

    private GCTravellersGear() {

    }

    public static void addItems(List<ItemStack> items, List<Integer> slot, List<String> type, EntityPlayer player, Integer saveItems, Integer sbInventoryLevel) {
        if (isInstalled()) {
            ItemStack[] inventory = travellersgear.api.TravellersGearAPI.getExtendedInventory(player);
            for (int i = 0; i < inventory.length; ++i) {
            	if (inventory[i] != null) {
	            	ItemStack stack = inventory[i];
	                items.add(inventory[i].copy());
	                slot.add(i);
	                type.add("travellersGear");
	                if ((saveItems != 0) || GCsoulBinding.keepItem(stack, i, "travellersGear", player, sbInventoryLevel)) {
	                	travellersgear.api.TGSaveData.setPlayerData(player, null);
	                	travellersgear.api.TGSaveData.setDirty();
	                } else if (ConfigHelper.makeAllItemsDrop) {
	                	player.inventory.addItemStackToInventory(stack);
	                }
            	}
            }
        	travellersgear.api.TGSaveData.setPlayerData(player, null);
        	travellersgear.api.TGSaveData.setDirty();
        }
    }

    public static boolean isInstalled() {
        return isInstalled;
    }
}