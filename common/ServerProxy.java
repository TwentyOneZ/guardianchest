package me.twentyonez.guardianchest.common;

import me.twentyonez.guardianchest.tile_entity.TileEntityGCChest;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * GuardianChest mod
 *
 * @author TwentyOneZ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 * Based on NightKosh's GraveStone mod, Dr.Cyano's Lootable Corpses mod and Tyler15555's Death Chest mod.
 */
public class ServerProxy {

	public void registerRenderThings() {

	}

	public void registerTileEntities(){
		GameRegistry.registerTileEntity(TileEntityGCChest.class, "GuardianChest");
	}
}