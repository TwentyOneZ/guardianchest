package me.twentyonez.guardianchest.compat;

import cpw.mods.fml.common.Loader;
/**
 * GuardianChest mod
 *
 * @author TwentyOneZ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 * Based on NightKosh's GraveStone mod, Dr.Cyano's Lootable Corpses mod and Tyler15555's Death Chest mod.
 */

public class InitCompatCheck {

    private static InitCompatCheck instance;

    private InitCompatCheck() {
        instance = this;
    }

    public static InitCompatCheck getInstance() {
        return (instance == null) ? new InitCompatCheck() : instance;
    }

    public void checkMods() {
        if (Loader.isModLoaded("arsmagica2")) {
            GCAM2.isInstalled = true;
        }

        if (Loader.isModLoaded("Baubles")) {
            GCBaubles.isInstalled = true;
        }

        if (Loader.isModLoaded("EnderIO")) {
            GCEnderIO.isInstalled = true;
        }

        if (Loader.isModLoaded("GalacticraftCore")) {
            GCGalacticraft.isInstalled = true;
        }

        if (Loader.isModLoaded("battlegear2")) {
            GCBattlegear.isInstalled = true;
        }

        if (Loader.isModLoaded("rpginventorymod")) {
            GCRpgInventory.isInstalled = true;
        }

        if (Loader.isModLoaded("TwilightForest")) {
            GCTwilightForest.isInstalled = true;
        }

        if (Loader.isModLoaded("TravellersGear")) {
            GCTravellersGear.isInstalled = true;
        }
        
        if (Loader.isModLoaded("Thaumcraft")) {
        	GCThaumcraft.isInstalled = true;
        	GCThaumcraft.addAspects();
        }

        if (Loader.isModLoaded("TConstruct")) {
            GCTinkersConstruct.isInstalled = true;
        }

        if (Loader.isModLoaded("camping")) {
            GCCampingMod.isInstalled = true;
        }

    }
}