package me.twentyonez.guardianchest.block;

import net.minecraft.block.Block;
import me.twentyonez.guardianchest.common.GCMainRegistry;
import me.twentyonez.guardianchest.util.ConfigHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ChatComponentText;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * GuardianChest mod
 *
 * @author TwentyOneZ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 * Based on NightKosh's GraveStone mod, Dr.Cyano's Lootable Corpses mod and Tyler15555's Death Chest mod.
 */
public class GCBlocks {

	public static void mainRegistry() {
		initialiseBlock();
		registerBlock();
	}

	public static Block GCChest;
	
	private static void registerBlock() {
		GameRegistry.registerBlock(GCChest, "GuardianChest");		
	}

	private static void initialiseBlock() {
		// TODO Auto-generated method stub
		GCChest = new GCChest(0).setBlockName("guardianChest").setCreativeTab(GCMainRegistry.GCtab).setBlockTextureName("guardianchest:GuardianChest").setBlockUnbreakable().setLightLevel(0.7F).setResistance(6000.0F);
	}
	
}
