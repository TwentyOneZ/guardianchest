package me.twentyonez.guardianchest.compat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.LinkedList;
import java.util.List;

/**
 * GraveStone mod
 *
 * @author NightKosh
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class GCCampingMod {

    protected static boolean isInstalled = false;

    private GCCampingMod() {
    }

    public static void addItems(List<ItemStack> items, List<Integer> slot, List<String> type, EntityPlayer player, Integer saveItems, Integer sbInventoryLevel) {
        if (isInstalled()) {
            NBTTagCompound tag = player.getEntityData().getCompoundTag("campInv");
            NBTTagList inventory = tag.getTagList("Items", 10);
            for (int i = 0; i < inventory.tagCount(); ++i) {
                NBTTagCompound Slots = inventory.getCompoundTagAt(i);
                Slots.getInteger("Slot");
                items.add(ItemStack.loadItemStackFromNBT(Slots).copy());
                slot.add(i);
                type.add("campingMod");
            }
            player.getEntityData().setTag("campInv", new NBTTagCompound());
        }
    }


    public static boolean isInstalled() {
        return isInstalled;
    }
}