package me.twentyonez.guardianchest.common;

import java.util.ArrayList;
import java.util.Random;

import me.twentyonez.guardianchest.block.GCBlocks;
import me.twentyonez.guardianchest.compat.GCBattlegear;
import me.twentyonez.guardianchest.compat.GCBaubles;
import me.twentyonez.guardianchest.compat.GCCampingMod;
import me.twentyonez.guardianchest.compat.GCGalacticraft;
import me.twentyonez.guardianchest.compat.GCRpgInventory;
import me.twentyonez.guardianchest.compat.GCTinkersConstruct;
import me.twentyonez.guardianchest.compat.GCTravellersGear;
import me.twentyonez.guardianchest.compat.GCTwilightForest;
import me.twentyonez.guardianchest.compat.GCminecraft;
import me.twentyonez.guardianchest.compat.GCsoulBinding;
import me.twentyonez.guardianchest.tile_entity.TileEntityGCChest;
import me.twentyonez.guardianchest.util.ConfigHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Arrays;

import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * GuardianChest mod
 *
 * @author TwentyOneZ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 * Based on NightKosh's GraveStone mod, Dr.Cyano's Lootable Corpses mod and Tyler15555's Death Chest mod.
 */
public class GCEventHandler {

	public GCEventHandler() {
		
	}
	
	ArrayList<ItemStack> playerInventoryList = new ArrayList<ItemStack>();
	ArrayList<String> playerInventoryType = new ArrayList<String>();
	ArrayList<Integer> playerInventorySlot = new ArrayList<Integer>();
	int sbInventoryLevel  = 0; 

	@SubscribeEvent(priority=EventPriority.LOWEST)
	public void playerCraftsBoundMapTier0(ItemCraftedEvent event) {
		EntityPlayer player = event.player;
		if (event.crafting.getItem() == GCMainRegistry.boundMapTier0) {
			GCMainRegistry.guardianTier1.setContainerItem(GCMainRegistry.guardianTier1);
			World world = player.worldObj;
			if (world.isRemote) {
				String warning = new String(LanguageRegistry.instance().getStringLocalization("desc.boundMapTier0.Crafted").replace("%1", LanguageRegistry.instance().getStringLocalization("tile.guardianChest.name")));
	    		player.addChatComponentMessage(new ChatComponentText(warning));
			}
    		
		} else {
			GCMainRegistry.guardianTier1.setContainerItem(null);
		}
		if (event.crafting.getItem() == GCMainRegistry.boundMapTier1) {
			GCMainRegistry.guardianTier2.setContainerItem(GCMainRegistry.guardianTier1);
		} else {
			GCMainRegistry.guardianTier2.setContainerItem(null);
		}
	}
	
	@SubscribeEvent(priority=EventPriority.HIGH)
	public void playerRespawnEvent(EntityJoinWorldEvent event) {
		if(event.entity instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer)event.entity;
			if (player.isEntityAlive()) {
	        	GCsoulBinding.getSoulboundItemsBack(playerInventoryList, playerInventorySlot, playerInventoryType, player, sbInventoryLevel);
		        playerInventoryList.clear();
		        playerInventorySlot.clear();
		        playerInventoryType.clear();
		        sbInventoryLevel = 0;
			}
		}
	}

	@SubscribeEvent(priority=EventPriority.HIGH)
	public void onPlayerSleepInBedEvent (PlayerSleepInBedEvent event) {
		EntityPlayer player = (EntityPlayer)event.entity;
		
	}

	
	@SubscribeEvent(priority=EventPriority.HIGH) 
	public void playerDeathEvent(LivingDeathEvent event) {
		if(event.entityLiving instanceof EntityPlayer){
			
			EntityPlayer player = (EntityPlayer)event.entityLiving;
			
			// Check if config file defines the Guardian Stone as a requirement for the chest to work 
			int saveItems = 0;
			if(!ConfigHelper.requireGuardianIdol) {
				saveItems = -1;
			} else {
				if (player.inventory.hasItem(GCMainRegistry.guardianTier2)) {
					player.inventory.consumeInventoryItem(GCMainRegistry.guardianTier2);
					saveItems = 2;
				} else if (player.inventory.hasItem(GCMainRegistry.guardianTier1)) {
					player.inventory.consumeInventoryItem(GCMainRegistry.guardianTier1);
					saveItems = 1;
				}
			}

			// Check for Twilight Forest Charms of Keeping
			if (GCTwilightForest.isInstalled()) {
				if (player.inventory.hasItem(twilightforest.item.TFItems.charmOfKeeping3)) {
					player.inventory.consumeInventoryItem(twilightforest.item.TFItems.charmOfKeeping3);
					sbInventoryLevel = 3;
				} else if (player.inventory.hasItem(twilightforest.item.TFItems.charmOfKeeping2)) {
					player.inventory.consumeInventoryItem(twilightforest.item.TFItems.charmOfKeeping2);
					sbInventoryLevel = 2;
				} else if (player.inventory.hasItem(twilightforest.item.TFItems.charmOfKeeping1)) {
					player.inventory.consumeInventoryItem(twilightforest.item.TFItems.charmOfKeeping1);
					sbInventoryLevel = 1;
				}
			}

			// Get Vanilla inventory
        	GCminecraft.addItems(playerInventoryList, playerInventorySlot, playerInventoryType, player, saveItems, sbInventoryLevel);
			
			// Get Battlegear inventory
            if (GCBattlegear.isInstalled()) {
            	GCBattlegear.addItems(playerInventoryList, playerInventorySlot, playerInventoryType, player, saveItems, sbInventoryLevel);
            }
			
			// Get Baubles inventory
            if (GCBaubles.isInstalled()) {
            	GCBaubles.addItems(playerInventoryList, playerInventorySlot, playerInventoryType, player, saveItems, sbInventoryLevel);
            }

			// Get Galacticraft inventory
            if (GCGalacticraft.isInstalled()) {
            	GCGalacticraft.addItems(playerInventoryList, playerInventorySlot, playerInventoryType, player, saveItems, sbInventoryLevel);
            }

			// Get RpgInventory inventory
            if (GCRpgInventory.isInstalled()) {
            	GCRpgInventory.addItems(playerInventoryList, playerInventorySlot, playerInventoryType, player, saveItems, sbInventoryLevel);
            }

			// Get The Camping Mod inventory
            if (GCCampingMod.isInstalled()) {
            	GCCampingMod.addItems(playerInventoryList, playerInventorySlot, playerInventoryType, player, saveItems, sbInventoryLevel);
            }
			
			// Get TravellersGear inventory
            if (GCTravellersGear.isInstalled()) {
            	GCTravellersGear.addItems(playerInventoryList, playerInventorySlot, playerInventoryType, player, saveItems, sbInventoryLevel);
            }

            // Get Tinker's Construct inventory
            if (GCTinkersConstruct.isInstalled()) {
            	GCTinkersConstruct.addItems(playerInventoryList, playerInventorySlot, playerInventoryType, player, saveItems, sbInventoryLevel);
            }

            // Get the player death coords. If it's out of the world, get last slept location. If player did
			// not sleep yet, get world spawn coords.
			if(saveItems != 0) {
				int posX1 = MathHelper.floor_double(player.posX);
				int posY1 = MathHelper.floor_double(player.posY);
				int posZ1 = MathHelper.floor_double(player.posZ);
				
				World world = player.worldObj;
				
				if ((posY1 <= 0) || (saveItems == 2)) {
					ChunkCoordinates bed = player.getBedLocation(player.dimension);
					if (bed == null) {
						world = MinecraftServer.getServer().worldServerForDimension(0);
						bed = player.getBedLocation(0);
					}
					
					if (bed != null) {
						posY1 = bed.posY;
						posX1 = bed.posX+1;
						posZ1 = bed.posZ+1;
					} else {
						posX1 = world.getSpawnPoint().posX+1;
						posY1 = world.getSpawnPoint().posY;
						posZ1 = world.getSpawnPoint().posZ;
					}
				}
				
				// Look for a free spot
				int newX = posX1;
				int newY = posY1;
				int newZ = posZ1;
				int window = 5;
				if (!isFreeSpot(world, posX1, posY1, posZ1)) {
					double isFreeSpotMap[][][] = new double [window*2+1] [window*2+1] [window*2+1];
					double minDistance = new Double(window*4+2);
					for (int x = -window; x <= window; x++){
						for (int z = -window; z <= window; z++){
							for (int y = -window; y <= window; y++){
								//System.out.println("Looking at " + (posX1 + x) + "," + (posY1 + y) + "," + (posZ1 + z) + ": " + isFreeSpot(world, posX1 + x, posY1 + y, posZ1 + z));
								//System.out.println("Current min distance: " + minDistance);
								if (isFreeSpot(world, posX1 + x, posY1 + y, posZ1 + z)) {
									isFreeSpotMap[x+window][y+window][z+window] = MathHelper.sqrt_double((x*x) + (y*y) + (z*z));
									//System.out.println("Spot distance C: " + MathHelper.sqrt_double((x*x) + (y*y) + (z*z)));
									//System.out.println("Spot distance V: " + isFreeSpotMap[x+window][y+window][z+window]);
									//System.out.println("Is it smaller? " + (isFreeSpotMap[x+window][y+window][z+window] < minDistance));
									if (isFreeSpotMap[x+window][y+window][z+window] <= minDistance) {
										minDistance = isFreeSpotMap[x+window][y+window][z+window];
										newX = posX1 + x;
										newY = posY1 + y;
										newZ = posZ1 + z;
										//System.out.println("New Distance: " + minDistance);
									}
								}
							}
						}
					}
				}
				// Free spot found?
				posX1 = newX;
				posY1 = newY;
				posZ1 = newZ;
				
				// Create chest
				world.setBlock(posX1, posY1, posZ1, GCBlocks.GCChest, 0, 2);
				TileEntityGCChest chest1 = (TileEntityGCChest) world.getTileEntity(posX1, posY1, posZ1);

				// Warn user of its existence
				if ((!world.isRemote) && (ConfigHelper.informCoords)) {
		    		String warning = new String(LanguageRegistry.instance().getStringLocalization("desc.SpawnLocation.Warning").replace("%1", LanguageRegistry.instance().getStringLocalization("tile.guardianChest.name")).replace("%2", player.getDisplayName()) + ": " + posX1 + "," + posY1 + "," + posZ1 + ", " + LanguageRegistry.instance().getStringLocalization("desc.dimension.word") + " " + world.provider.dimensionId + ".");
		    		player.addChatComponentMessage(new ChatComponentText(warning));
		    	}

				
				// Dump player inventory into chest 
				int chest1slot = 0;
				
				// Return a BoundMapTier0 to the chest if the chest was a Tier2.
				if (saveItems == 2 && ConfigHelper.returnChestToInventory) {
					chest1.setInventorySlotContents(chest1slot, new ItemStack(GCMainRegistry.boundMapTier0));
					chest1slot++;
				}
				// Add an ItemGuardianTier0 to chest.
				if (saveItems != -1  && ConfigHelper.returnChestToInventory) {
					if (ConfigHelper.levelCostGuardianTier1 != 0) {
						chest1.setInventorySlotContents(chest1slot, new ItemStack(GCMainRegistry.guardianTier0));
					} else {
						chest1.setInventorySlotContents(chest1slot, new ItemStack(GCMainRegistry.guardianTier1));
					}
					chest1slot++;
				}
		        // Dump collected inventory into chest				
				for(int i = 0; i < playerInventoryList.size(); i++){
					if (!GCsoulBinding.keepItem(playerInventoryList.get(i), playerInventorySlot.get(i), playerInventoryType.get(i), player, sbInventoryLevel)) {
						if (chest1slot > chest1.getSizeInventory()-1) {
							// Chest 1 is full! 
							// Returning item to player's inventory, so it drops
	                        while (!player.inventory.addItemStackToInventory(playerInventoryList.get(i))) {
	                            player.dropOneItem(true);
	                        }
						} else {
							// Filling Chest 1
							
							chest1.setInventorySlotContents(chest1slot, playerInventoryList.get(i));
							chest1slot++;
						}
					}
				}
                chest1.registerOwner(player, world, posX1, posY1, posZ1);
			}
		}
	}
	
    private static boolean isFreeSpot(World world, int posX, int posY, int posZ) {
        return world.getBlock(posX, posY-1, posZ).getMaterial().isSolid() &&
                (world.isAirBlock(posX, posY, posZ) || world.getBlock(posX, posY, posZ).getMaterial().isLiquid() || world.getBlock(posX, posY, posZ).getMaterial().isReplaceable());
    }

}