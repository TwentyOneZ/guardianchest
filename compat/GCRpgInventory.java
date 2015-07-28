package me.twentyonez.guardianchest.compat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import me.twentyonez.guardianchest.util.ConfigHelper;




import java.lang.reflect.Method;
import java.util.List;

/**
 * GuardianChest mod
 *
 * @author TwentyOneZ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 * Based on NightKosh's GraveStone mod, Dr.Cyano's Lootable Corpses mod and Tyler15555's Death Chest mod.
 */

public class GCRpgInventory {

    protected static boolean isInstalled = false;

    private GCRpgInventory() {
    }

    public static void addItems(List<ItemStack> items, List<Integer> slot, List<String> type, EntityPlayer player, Integer saveItems, Integer sbInventoryLevel) {
        if (isInstalled()) {
            try {
                Class<?> clazz = Class.forName("rpgInventory.gui.rpginv.PlayerRpgInventory");
                Method m = clazz.getDeclaredMethod("get", EntityPlayer.class);
                Object result = m.invoke(null, player);

                IInventory inventory = (IInventory) result;
                if (inventory != null) {
                    for (int i = 0; i < inventory.getSizeInventory(); i++) {
                        ItemStack stack = inventory.getStackInSlot(i);
                        if (stack != null) {
                            items.add(stack.copy());
                            slot.add(i);
                            type.add("rpgInventory");
                            if ((saveItems != 0) || GCsoulBinding.keepItem(stack, i, "rpgInventory", player, sbInventoryLevel)) {
                            	inventory.setInventorySlotContents(i, null);
                            } else if (ConfigHelper.makeAllItemsDrop) {
                            	player.inventory.addItemStackToInventory(stack);
                            	inventory.setInventorySlotContents(i, null);
                            }
                        }
                    }
                }
            } catch (Exception e) {
            	//Error trying to get RPG Inventory... erm... inventory.
            }
        }
    }

    public static boolean isInstalled() {
        return isInstalled;
    }
}