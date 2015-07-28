package me.twentyonez.guardianchest.common;

import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import me.twentyonez.guardianchest.block.GCBlocks;
import me.twentyonez.guardianchest.render.item.*;
import me.twentyonez.guardianchest.render.tile_entity.*;
import me.twentyonez.guardianchest.tile_entity.*;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

/**
 * GuardianChest mod
 *
 * @author TwentyOneZ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 * Based on NightKosh's GraveStone mod, Dr.Cyano's Lootable Corpses mod and Tyler15555's Death Chest mod.
 */
public class ClientProxy extends ServerProxy{
	
	public void registerRenderThings(){

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGCChest.class, new GCChestRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(GCBlocks.GCChest), new ItemRenderGCChest());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityItemGuardianTier0.class, new ItemGuardianTier0Renderer());
		MinecraftForgeClient.registerItemRenderer(GCMainRegistry.guardianTier0, new ItemRenderItemGuardianTier0());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityItemGuardianTier1.class, new ItemGuardianTier1Renderer());
		MinecraftForgeClient.registerItemRenderer(GCMainRegistry.guardianTier1, new ItemRenderItemGuardianTier1());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityItemGuardianTier2.class, new ItemGuardianTier2Renderer());
		MinecraftForgeClient.registerItemRenderer(GCMainRegistry.guardianTier2, new ItemRenderItemGuardianTier2());
	}

}