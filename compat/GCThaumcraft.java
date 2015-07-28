package me.twentyonez.guardianchest.compat;

import me.twentyonez.guardianchest.block.GCBlocks;
import me.twentyonez.guardianchest.common.GCMainRegistry;
import net.minecraft.item.ItemStack;
import thaumcraft.api.ItemApi;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

/**
 * GuardianChest mod
 *
 * @author TwentyOneZ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 * Based on NightKosh's GraveStone mod, Dr.Cyano's Lootable Corpses mod and Tyler15555's Death Chest mod.
 */
public class GCThaumcraft {

    protected static boolean isInstalled = false;

    private GCThaumcraft() {

    }

    public static void addAspects() {
        try {
            // Maps
            ThaumcraftApi.registerObjectTag(new ItemStack(GCMainRegistry.boundMapTier0), new int[]{0}, new AspectList().add(Aspect.MIND, 3)
            		.add(Aspect.VOID, 3).add(Aspect.METAL, 1));
            ThaumcraftApi.registerObjectTag(new ItemStack(GCMainRegistry.boundMapTier1), new int[]{0}, new AspectList().add(Aspect.MIND, 3)
            		.add(Aspect.ENERGY, 2).add(Aspect.TRAVEL, 1).add(Aspect.METAL, 1));
            // Guardians
            ThaumcraftApi.registerObjectTag(new ItemStack(GCMainRegistry.guardianTier0), new int[]{0}, new AspectList().add(Aspect.TREE, 4)
                    .add(Aspect.VOID, 3).add(Aspect.MECHANISM, 1));
            ThaumcraftApi.registerObjectTag(new ItemStack(GCMainRegistry.guardianTier1), new int[]{0}, new AspectList().add(Aspect.TREE, 4)
                    .add(Aspect.ENERGY, 1).add(Aspect.MECHANISM, 1).add(Aspect.MIND, 1).add(Aspect.MAGIC, 1));
            ThaumcraftApi.registerObjectTag(new ItemStack(GCMainRegistry.guardianTier2), new int[]{0}, new AspectList().add(Aspect.TREE, 4)
                    .add(Aspect.ENERGY, 3).add(Aspect.MECHANISM, 1).add(Aspect.MIND, 4).add(Aspect.MAGIC, 1).add(Aspect.TRAVEL, 1));
            // Chest
            ThaumcraftApi.registerObjectTag(new ItemStack(GCBlocks.GCChest), new int[]{0}, new AspectList().add(Aspect.TREE, 4)
                    .add(Aspect.ENERGY, 3).add(Aspect.MECHANISM, 1).add(Aspect.MIND, 1).add(Aspect.SOUL, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static boolean isInstalled() {
        return isInstalled;
    }
}