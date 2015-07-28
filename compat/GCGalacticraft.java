package me.twentyonez.guardianchest.compat;

import java.util.List;

import me.twentyonez.guardianchest.util.ConfigHelper;
import micdoodle8.mods.galacticraft.api.inventory.AccessInventoryGC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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

public class GCGalacticraft {

    protected static boolean isInstalled = false;

    private GCGalacticraft() {

    }

    public static void addItems(List<ItemStack> items, List<Integer> slot, List<String> type, EntityPlayer player, Integer saveItems, Integer sbInventoryLevel) {
        if (isInstalled()) {
            IInventory inventory = AccessInventoryGC.getGCInventoryForPlayer((EntityPlayerMP) player);
        	if (inventory != null) {
                for (int i = 0; i < inventory.getSizeInventory(); i++) {
                    ItemStack stack = inventory.getStackInSlot(i);
                    if (stack != null) {
                        items.add(stack.copy());
                        slot.add(i);
                        type.add("galacticraft");
                        if ((saveItems != 0) || GCsoulBinding.keepItem(stack, i, "galacticraft", player, sbInventoryLevel)) {
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