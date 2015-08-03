package me.twentyonez.guardianchest.common;

import me.twentyonez.guardianchest.block.GCBlocks;
import me.twentyonez.guardianchest.compat.GCThaumcraft;
import me.twentyonez.guardianchest.compat.InitCompatCheck;
import me.twentyonez.guardianchest.item.ItemBoundMapTier0;
import me.twentyonez.guardianchest.item.ItemBoundMapTier1;
import me.twentyonez.guardianchest.item.ItemGuardianTier0;
import me.twentyonez.guardianchest.item.ItemGuardianTier1;
import me.twentyonez.guardianchest.item.ItemGuardianTier2;
import me.twentyonez.guardianchest.util.ConfigHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Level;

import thaumcraft.api.ItemApi;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.creativetab.CreativeTabs;
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
@Mod(modid = "GuardianChest", name = "Guardian Chest", version = MinecraftForge.MC_VERSION)
public class GCMainRegistry {

	@SidedProxy(clientSide = "me.twentyonez.guardianchest.common.ClientProxy", serverSide = "me.twentyonez.guardianchest.common.ServerProxy") 
	public static ServerProxy proxy;
	
	public static ItemGuardianTier0 guardianTier0 = new ItemGuardianTier0();
	public static ItemGuardianTier1 guardianTier1 = new ItemGuardianTier1();
	public static ItemGuardianTier2 guardianTier2 = new ItemGuardianTier2();
	public static ItemBoundMapTier0 boundMapTier0 = new ItemBoundMapTier0();
	public static ItemBoundMapTier1 boundMapTier1 = new ItemBoundMapTier1();
	
	public static GCMainRegistry modInstance;
	
	public static CreativeTabs GCtab = new CreativeTabs("CreativeTabName") {
	    @Override
	    @SideOnly(Side.CLIENT)
	    public Item getTabIconItem() {
	        return Item.getItemFromBlock(GCBlocks.GCChest);
	    }
	};
	
	public GCMainRegistry() {
		
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		event.getModLog().log(Level.INFO, "Starting to load Guardian Chest!");
		ConfigHelper.setupConfig(new Configuration(event.getSuggestedConfigurationFile()), event.getModLog());
		if (ConfigHelper.requireGuardianIdol) {
			GameRegistry.registerItem(guardianTier0, "guardianTier0");
			GameRegistry.registerItem(guardianTier1, "guardianTier1");
			GameRegistry.registerItem(guardianTier2, "guardianTier2");
			GameRegistry.registerItem(boundMapTier0, "boundMapTier0");
			GameRegistry.registerItem(boundMapTier1, "boundMapTier1");
			this.guardianTier0.setCreativeTab(GCtab);
			this.guardianTier1.setCreativeTab(GCtab);
			this.guardianTier2.setCreativeTab(GCtab);
			this.boundMapTier0.setCreativeTab(GCtab);
			this.boundMapTier1.setCreativeTab(GCtab);
		}
		GCBlocks.mainRegistry();
		proxy.registerTileEntities();
		proxy.registerRenderThings();
	}
	
	@EventHandler
	public void loadMod(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new GCEventHandler());
		FMLCommonHandler.instance().bus().register(new GCEventHandler()); 
		
		if (ConfigHelper.requireGuardianIdol) {
			GameRegistry.addRecipe(new ItemStack(GCMainRegistry.guardianTier0), "srs", "tct", "srs", 's', Blocks.stone, 'c', Blocks.chest, 'r', Items.redstone, 't', Blocks.redstone_torch);
			GameRegistry.addShapelessRecipe(new ItemStack(GCMainRegistry.guardianTier2), GCMainRegistry.guardianTier1, GCMainRegistry.boundMapTier1);
			GameRegistry.addShapelessRecipe(new ItemStack(GCMainRegistry.boundMapTier0), GCMainRegistry.guardianTier1, Items.map);
			GameRegistry.addShapelessRecipe(new ItemStack(GCMainRegistry.boundMapTier1), GCMainRegistry.guardianTier2);
		}
	}
	
	@EventHandler
	public void finishLoading(FMLPostInitializationEvent event) {
		InitCompatCheck.getInstance().checkMods();
	}

}