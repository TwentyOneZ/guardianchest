package me.twentyonez.guardianchest.compat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import me.twentyonez.guardianchest.util.ConfigHelper;






import java.lang.reflect.Method;
import java.util.List;

import tconstruct.api.IPlayerExtendedInventoryWrapper;
import tconstruct.api.TConstructAPI;

/**
 * GuardianChest mod
 *
 * @author TwentyOneZ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 * Based on NightKosh's GraveStone mod, Dr.Cyano's Lootable Corpses mod and Tyler15555's Death Chest mod.
 */

public class GCTinkersConstruct {

    protected static boolean isInstalled = false;
    
    private static final int ACCESSORIES_SLOTS_COUNT = 4;

    private GCTinkersConstruct() {
    }

    public static void addItems(List<ItemStack> items, List<Integer> slot, List<String> type, EntityPlayer player, Integer saveItems, Integer sbInventoryLevel) {
        if (isInstalled()) {
            IPlayerExtendedInventoryWrapper inventoryWrapper = TConstructAPI.getInventoryWrapper(player);
            if (inventoryWrapper != null) {
                IInventory knapsackInventory = inventoryWrapper.getKnapsackInventory(player);
                if (knapsackInventory != null) {
                    for (int i = 0; i < knapsackInventory.getSizeInventory(); i++) {
                        ItemStack stack = knapsackInventory.getStackInSlot(i);
                        if (stack != null) {
                            items.add(stack.copy());
                            slot.add(i);
                            type.add("tcKnapsack");
                            if ((saveItems != 0) || GCsoulBinding.keepItem(stack, i, "tcKnapsack", player, sbInventoryLevel)) {
                            	knapsackInventory.setInventorySlotContents(i, null);
                            }
                        }
                    }
                }

                IInventory accessoryInventory = inventoryWrapper.getAccessoryInventory(player);
                if (accessoryInventory != null) {
                    //Heart Canisters should not go in the grave as they are not supposed to be dropped on death, so only first 4 slots required
                    for (int i = 0; i < ACCESSORIES_SLOTS_COUNT; i++) {
                        ItemStack stack = accessoryInventory.getStackInSlot(i);
                        if (stack != null) {
                            items.add(stack.copy());
                            slot.add(i);
                            type.add("tcAccessory");
                            if ((saveItems != 0) || GCsoulBinding.keepItem(stack, i, "tcAccessory", player, sbInventoryLevel)) {
                            	accessoryInventory.setInventorySlotContents(i, null);
                            }
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