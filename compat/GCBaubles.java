package me.twentyonez.guardianchest.compat;

import java.util.List;

import me.twentyonez.guardianchest.util.ConfigHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import baubles.api.BaublesApi;

/**
 * GuardianChest mod
 *
 * @author TwentyOneZ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 * Based on NightKosh's GraveStone mod, Dr.Cyano's Lootable Corpses mod and Tyler15555's Death Chest mod.
 */
public class GCBaubles {

    protected static boolean isInstalled = false;

    private GCBaubles() {

    }

    public static void addItems(List<ItemStack> items, List<Integer> slot, List<String> type, EntityPlayer player, Integer saveItems, Integer sbInventoryLevel) {
        if (isInstalled()) {
            IInventory inventory = BaublesApi.getBaubles(player);
            if (inventory != null) {
                for (int i = 0; i < inventory.getSizeInventory(); i++) {
                    ItemStack stack = inventory.getStackInSlot(i);
                    if (stack != null) {
                        items.add(stack.copy());
                        slot.add(i);
                        type.add("baubles");
                        if ((saveItems != 0) || GCsoulBinding.keepItem(stack, i, "battlegear", player, sbInventoryLevel)) {
                        	inventory.setInventorySlotContents(i, null);
                        } else if (ConfigHelper.makeAllItemsDrop) {
                        	player.inventory.addItemStackToInventory(stack);
                        	inventory.setInventorySlotContents(i, null);
                        }
                    }
                }
            }
        }
    }

    public static boolean isInstalled() {
        return isInstalled;
    }
}