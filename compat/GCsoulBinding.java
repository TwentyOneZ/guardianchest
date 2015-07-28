package me.twentyonez.guardianchest.compat;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import tconstruct.api.IPlayerExtendedInventoryWrapper;
import tconstruct.api.TConstructAPI;
import me.twentyonez.guardianchest.util.ConfigHelper;
import micdoodle8.mods.galacticraft.api.inventory.AccessInventoryGC;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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

public class GCsoulBinding {

    public static List<String> soulBindingEnchantmentsList = new ArrayList<String>();

    private GCsoulBinding() {
    }

    public static void getSoulboundItemsBack(List<ItemStack> items, List<Integer> slot, List<String> type, EntityPlayer player, Integer sbInventoryLevel) {
        Iterator<ItemStack> it = items.iterator();
        Iterator<Integer> sl = slot.iterator();
        Iterator<String> ty = type.iterator();
        while (it.hasNext()) {
            ItemStack stack = it.next();
        	Integer stackSlot = sl.next();
        	String stackInvType = ty.next();
            if (stack != null && keepItem(stack, stackSlot, stackInvType, player, sbInventoryLevel))  {
            	//player.inventory.addItemStackToInventory(stack.copy());
        		if ((stackInvType == "vanillaMain")) {
        			if (player.inventory.getStackInSlot(stackSlot) != null) {
                        while (!player.inventory.addItemStackToInventory(stack)) {
                            player.dropOneItem(true);
                        }
        			} else {
        				player.inventory.setInventorySlotContents((stackSlot), stack);
        			}
                    it.remove();
                    ty.remove();
                    sl.remove();
            	} else if ((stackInvType == "vanillaArmor")) {
                    player.inventory.setInventorySlotContents((stackSlot+36), stack);
                    it.remove();
                    ty.remove();
                    sl.remove();
            	} else if ((stackInvType == "baubles") && GCBaubles.isInstalled) {
                    IInventory inventory = BaublesApi.getBaubles(player);
            		inventory.setInventorySlotContents(stackSlot, stack);
                    it.remove();
                    ty.remove();
                    sl.remove();
            	} else if ((stackInvType == "rpgInventory") && GCRpgInventory.isInstalled) {
                    try {
                        Class<?> clazz = Class.forName("rpgInventory.gui.rpginv.PlayerRpgInventory");
                        Method m = clazz.getDeclaredMethod("get", EntityPlayer.class);
                        Object result = m.invoke(null, player);

                        IInventory inventory = (IInventory) result;
	            		inventory.setInventorySlotContents(stackSlot, stack);
	                    it.remove();
	                    ty.remove();
	                    sl.remove();
                    } catch (Exception e) {
                    	//Error trying to give back RPG Inventory... erm... inventory.
                    }
            	} else if ((stackInvType == "galacticraft") && GCGalacticraft.isInstalled) {
                    IInventory inventory = AccessInventoryGC.getGCInventoryForPlayer((EntityPlayerMP) player);
            		inventory.setInventorySlotContents(stackSlot, stack);
                    it.remove();
                    ty.remove();
                    sl.remove();
            	} else if ((stackInvType == "battlegear") && GCBattlegear.isInstalled)  {
                    player.inventory.setInventorySlotContents(stackSlot, stack);
                    it.remove();
                    ty.remove();
                    sl.remove();
            	} else if ((stackInvType == "campingMod") && GCCampingMod.isInstalled)  {
                    while (!player.inventory.addItemStackToInventory(stack)) {
                    	player.dropOneItem(true);
                    }
                    it.remove();
                    ty.remove();
                    sl.remove();
            	} else if ((stackInvType == "tcAccessory") && GCTinkersConstruct.isInstalled)  {
                    while (!player.inventory.addItemStackToInventory(stack)) {
                    	player.dropOneItem(true);
                    }
                    it.remove();
                    ty.remove();
                    sl.remove();
            	} else if ((stackInvType == "tcKnapsack") && GCTinkersConstruct.isInstalled)  {
                    while (!player.inventory.addItemStackToInventory(stack)) {
                        player.dropOneItem(true);
                    }
                    it.remove();
                    ty.remove();
                    sl.remove();
            	} else {
        			if (player.inventory.getStackInSlot(stackSlot) != null) {
                        while (!player.inventory.addItemStackToInventory(stack)) {
                            player.dropOneItem(true);
                        }
        			} else {
        				player.inventory.setInventorySlotContents((stackSlot), stack);
        			}
                    it.remove();
                    ty.remove();
                    sl.remove();
            	}
            }
        }
    }

    public static boolean hasSoulbound(ItemStack stack) {
        Map enchantments = EnchantmentHelper.getEnchantments(stack);
        for (Object id : enchantments.keySet()) {
            Enchantment ench = Enchantment.enchantmentsList[((Integer) id).shortValue()];
            if (ench != null) {
            	if ((ConfigHelper.anyEnchantSoulBinds) || GCsoulBinding.soulBindingEnchantmentsList().contains(ench.getClass().getName())) {
            		return true;
            	}
            }
        }
        return false;
    }
    
    public static boolean keepItem(ItemStack item, Integer slot, String type, EntityPlayer player, int sbInventoryLevel) {
    	if (hasSoulbound(item)) {
    		return true;
    	}
    	// Checks for charms of keeping lvl 3
    	if (sbInventoryLevel == 3) {
    		return true;
    	}
    	// Checks for charms of keeping lvl 2 AND if (item was in hotbar OR (is not main inventory AND is not battlegear AND is not miscellaneous) ).
    	if ((sbInventoryLevel == 2) && ( ( (type == "vanillaMain") && (slot < player.inventory.getHotbarSize()) ) || ( (type != "vanillaMain") && (type != "battlegear") && (type != "misc") ) ) ) {
    		return true;
    	}
    	// Checks for charms of keeping lvl 1 AND if (item was the currently held OR (is not main inventory AND is not battlegear AND is not miscellaneous) ) .
    	if ( (sbInventoryLevel == 1) && ( ( (type == "vanillaMain") && (player.inventory.currentItem == slot) ) || ( (type != "vanillaMain") && (type != "battlegear") && (type != "misc") ) ) )  {
    		return true;
    	}
    	return false;
    }
    
    public static List soulBindingEnchantmentsList() {
    	if (GCAM2.isInstalled()) {
    		soulBindingEnchantmentsList.add("am2.enchantments.EnchantmentSoulbound");
    	}
    	if (GCEnderIO.isInstalled()) {
    		soulBindingEnchantmentsList.add("enchantment.enderio.soulBound");
    	}
    	return soulBindingEnchantmentsList;
    }
    
}