package me.twentyonez.guardianchest.compat;

import java.util.List;

import me.twentyonez.guardianchest.util.ConfigHelper;
import micdoodle8.mods.galacticraft.core.entities.player.GCEntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 * GuardianChest mod
 *
 * @author TwentyOneZ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 * Based on NightKosh's GraveStone mod, Dr.Cyano's Lootable Corpses mod and Tyler15555's Death Chest mod.
 */
public class GCBattlegear {

    protected static boolean isInstalled = false;

    private static final short FIRST_SLOT = 150;
    private static final short LAST_SLOT = 155;
    
    private GCBattlegear() {

    }

    public static void addItems(List<ItemStack> items, List<Integer> slot, List<String> type, EntityPlayer player, Integer saveItems, Integer sbInventoryLevel) {
        if (isInstalled()) {
			// Get main inventory
			for(int i = FIRST_SLOT; i <= LAST_SLOT; i++){
				ItemStack droppedItem = player.inventory.getStackInSlot(i);
				if (droppedItem != null) {
					items.add(applyItemDamage(droppedItem));
	                slot.add(i);
	                type.add("battlegear");
	                if ((saveItems != 0) || GCsoulBinding.keepItem(droppedItem, i, "battlegear", player, sbInventoryLevel)) {
                    	player.inventory.setInventorySlotContents(i, null);
	                } else if (ConfigHelper.makeAllItemsDrop) {
                    	player.inventory.addItemStackToInventory(droppedItem);
                    	player.inventory.setInventorySlotContents(i, null);
                    }
				}
			}
        }
    }

    public static boolean isInstalled() {
        return isInstalled;
    }
    
    public static ItemStack applyItemDamage(ItemStack itemstack){
    	if(ConfigHelper.applyDamageOnEquip == 0) return itemstack;
    	if (itemstack != null 
                && itemstack.isItemStackDamageable()
                && itemstack.getItem().isDamageable()
                && (!itemstack.isStackable())
                && (!itemstack.getItem().getHasSubtypes())
                && (itemstack.getItem().getMaxDamage() > 0) ) {
    		if (ConfigHelper.damageOnEquipPercentage) {
        		final int newDamageValue = (int) (itemstack.getMaxDamage() * (ConfigHelper.applyDamageOnEquip/100) + itemstack.getItemDamage());
                itemstack.setItemDamage(Math.min(newDamageValue, itemstack.getMaxDamage() - 1));
    		} else {
        		final int newDamageValue = (int) (itemstack.getItemDamage() + ConfigHelper.applyDamageOnEquip);
                itemstack.setItemDamage(Math.min(newDamageValue, itemstack.getMaxDamage() - 1));
    		}
        }
    	return itemstack;
    }
}

