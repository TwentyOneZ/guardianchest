package me.twentyonez.guardianchest.item;

import java.util.List;
import java.util.Random;

import me.twentyonez.guardianchest.common.GCMainRegistry;
import me.twentyonez.guardianchest.util.ConfigHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * GuardianChest mod
 *
 * @author TwentyOneZ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 * Based on NightKosh's GraveStone mod, Dr.Cyano's Lootable Corpses mod and Tyler15555's Death Chest mod.
 */
public class ItemBoundMapTier0 extends Item {

	public ItemBoundMapTier0() {
		setUnlocalizedName("boundMapTier0");
		setTextureName("guardianchest:boundMapTier0");
		setCreativeTab(CreativeTabs.tabMisc);
		setMaxStackSize(1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add(LanguageRegistry.instance().getStringLocalization("desc.boundMapTier0.Line1").replace("%1", LanguageRegistry.instance().getStringLocalization("item.guardianTier1.name")).replace("%2", ConfigHelper.levelCostBoundMapTier1.toString()));
		list.add(LanguageRegistry.instance().getStringLocalization("desc.boundMapTier0.Line2").replace("%1", LanguageRegistry.instance().getStringLocalization("item.guardianTier1.name")).replace("%2", ConfigHelper.levelCostBoundMapTier1.toString()));
		list.add(LanguageRegistry.instance().getStringLocalization("desc.boundMapTier0.Line3").replace("%1", LanguageRegistry.instance().getStringLocalization("item.guardianTier1.name")).replace("%2", ConfigHelper.levelCostBoundMapTier1.toString()));
		list.add(LanguageRegistry.instance().getStringLocalization("desc.boundMapTier0.Line4").replace("%1", LanguageRegistry.instance().getStringLocalization("item.guardianTier1.name")).replace("%2", ConfigHelper.levelCostBoundMapTier1.toString()));
	}

    /**
     * Called whenever this item is equipped and the right mouse button is
     * pressed. Args: itemStack, world, entityPlayer
	*/
    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
	    	if ((player.experienceLevel >= ConfigHelper.levelCostBoundMapTier1) || (player.capabilities.isCreativeMode)) {
	    		world.playSoundAtEntity(player, "random.fizz", 0.6F, world.rand.nextFloat() * 0.1F + 0.9F);
	    		world.playSoundAtEntity(player, "random.pop", 0.4F, world.rand.nextFloat() * 0.2F + 0.8F);
	    		if (!player.capabilities.isCreativeMode) {
	    			player.addExperienceLevel(-ConfigHelper.levelCostBoundMapTier1);
	    		}
	    		for (int i = 0; i < 100; i++) {
	    			Random rand = new Random();
	    	        world.spawnParticle(
	    	                "reddust", 
	    	                player.posX - 0.125F + (rand.nextGaussian() * 0.25D), 
	    	                player.posY - 0.8F + (i * 0.02D), 
	    	                player.posZ - 0.125F + (rand.nextGaussian() * 0.25D), 
	    	                rand.nextGaussian() * 0.02D, 
	    	                1.0F, 
	    	                rand.nextGaussian() * 0.02D);
	    		}
	    		return new ItemStack(GCMainRegistry.boundMapTier1);
	    	} else {
	        	if(world.isRemote) {
	        		player.addChatComponentMessage(new ChatComponentText(LanguageRegistry.instance().getStringLocalization("desc.boundMapTier0.Warning").replace("%1", ConfigHelper.levelCostBoundMapTier1.toString())));
	        	}
	    	}
        return itemStack;
    }

    /**
     * Callback for item usage. If the item does something special on right
     * clicking, he will have one of those. Return True if something happen and
     * false if it don't. This is for ITEMS, not BLOCKS
     */
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {
        return false;
    }
}
