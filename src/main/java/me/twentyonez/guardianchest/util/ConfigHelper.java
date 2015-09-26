package me.twentyonez.guardianchest.util;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * GuardianChest mod
 *
 * @author TwentyOneZ
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 * Based on NightKosh's GraveStone mod, Dr.Cyano's Lootable Corpses mod and Tyler15555's Death Chest mod.
 */

public class ConfigHelper {

	public ConfigHelper() {
		
	}
	
	public static boolean makeAllItemsDrop;
	private static Property makeAllItemsDropProp;
	
	public static boolean requireGuardianIdol;
	private static Property requireGuardianIdolProp;
	
	public static boolean anyEnchantSoulBinds;
	private static Property anyEnchantSoulBindsProp;

	public static Integer levelCostGuardianTier1;
	private static Property levelCostGuardianTier1Prop;

	public static Integer levelCostBoundMapTier1;
	private static Property levelCostBoundMapTier1Prop;
	
	public static Integer maxRadiusToSearchForAFreeSpot;
	private static Property maxRadiusToSearchForAFreeSpotProp;

	public static Integer timeBeforeUnsecure;
	private static Property timeBeforeUnsecureProp;

	public static Double applyDamageOnEquip;
	private static Property applyDamageOnEquipProp;
	
	public static boolean informCoords;
	private static Property informCoordsProp;

	public static boolean damageOnEquipPercentage;
	private static Property damageOnEquipPercentageProp;

	public static boolean returnChestToInventory;
	private static Property returnChestToInventoryProp;

	public static void setupConfig(Configuration cfg, Logger logger) {
		try {

			applyDamageOnEquipProp = cfg.get(LanguageRegistry.instance().getStringLocalization("config.Category.Damage"), "applyDamageOnEquip", 10.0);
			applyDamageOnEquipProp.comment = LanguageRegistry.instance().getStringLocalization("config.applyDamage.onEquip");
			applyDamageOnEquip = applyDamageOnEquipProp.getDouble(10.0);

			damageOnEquipPercentageProp = cfg.get(LanguageRegistry.instance().getStringLocalization("config.Category.Damage"), "damageOnEquipPercentage", true);
			damageOnEquipPercentageProp.comment = LanguageRegistry.instance().getStringLocalization("config.Damage.isPercentual");
			damageOnEquipPercentage = damageOnEquipPercentageProp.getBoolean(true);

			requireGuardianIdolProp = cfg.get(LanguageRegistry.instance().getStringLocalization("config.Category.General"), "requireGuardianIdol", true);
			requireGuardianIdolProp.comment = LanguageRegistry.instance().getStringLocalization("config.GuardianIdol.Requirement").replace("%1", LanguageRegistry.instance().getStringLocalization("item.guardianTier0.name"));
			requireGuardianIdol = requireGuardianIdolProp.getBoolean(true);

			anyEnchantSoulBindsProp = cfg.get(LanguageRegistry.instance().getStringLocalization("config.Category.Soulbinding"), "anyEnchantSoulBinds", false);
			anyEnchantSoulBindsProp.comment = LanguageRegistry.instance().getStringLocalization("config.Enchantments.AreAllSoulBound");
			anyEnchantSoulBinds = anyEnchantSoulBindsProp.getBoolean(false);

			makeAllItemsDropProp = cfg.get(LanguageRegistry.instance().getStringLocalization("config.Category.Soulbinding"), "makeAllItemsDrop", true);
			makeAllItemsDropProp.comment = LanguageRegistry.instance().getStringLocalization("config.AllItems.MakeDrop");
			makeAllItemsDrop = makeAllItemsDropProp.getBoolean(true);

			levelCostGuardianTier1Prop = cfg.get(LanguageRegistry.instance().getStringLocalization("config.Category.General"), "levelCostGuardianTier1", 1);
			levelCostGuardianTier1Prop.comment = LanguageRegistry.instance().getStringLocalization("config.GuardianTier1.LevelRequirement").replace("%1", LanguageRegistry.instance().getStringLocalization("item.guardianTier0.name"));
			levelCostGuardianTier1 = levelCostGuardianTier1Prop.getInt(1);

			levelCostBoundMapTier1Prop = cfg.get(LanguageRegistry.instance().getStringLocalization("config.Category.General"), "levelCostBoundMapTier1", 10);
			levelCostBoundMapTier1Prop.comment = LanguageRegistry.instance().getStringLocalization("config.BoundMapTier1.LevelRequirement").replace("%1", LanguageRegistry.instance().getStringLocalization("item.boundMapTier0.name"));
			levelCostBoundMapTier1 = levelCostBoundMapTier1Prop.getInt(10);

			maxRadiusToSearchForAFreeSpotProp = cfg.get(LanguageRegistry.instance().getStringLocalization("config.Category.General"), "maxRadiusToSearchForAFreeSpot", 5);
			maxRadiusToSearchForAFreeSpotProp.comment = LanguageRegistry.instance().getStringLocalization("config.maxDistanceToSearchForAFreeSpot.Radius").replace("%1", LanguageRegistry.instance().getStringLocalization("tile.guardianChest.name"));
			maxRadiusToSearchForAFreeSpot = maxRadiusToSearchForAFreeSpotProp.getInt(5);

			timeBeforeUnsecureProp = cfg.get(LanguageRegistry.instance().getStringLocalization("config.Category.General"), "timeBeforeUnsecure", 300);
			timeBeforeUnsecureProp.comment = LanguageRegistry.instance().getStringLocalization("config.timeBeforeUnsecure.Seconds").replace("%1", LanguageRegistry.instance().getStringLocalization("tile.guardianChest.name"));
			timeBeforeUnsecure = timeBeforeUnsecureProp.getInt(300);

			informCoordsProp = cfg.get(LanguageRegistry.instance().getStringLocalization("config.Category.General"), "informCoords", true);
			informCoordsProp.comment = LanguageRegistry.instance().getStringLocalization("config.Inform.ChestCoordinates").replace("%1", LanguageRegistry.instance().getStringLocalization("tile.guardianChest.name"));
			informCoords = informCoordsProp.getBoolean(true);

			returnChestToInventoryProp = cfg.get(LanguageRegistry.instance().getStringLocalization("config.Category.General"), "returnChestToInventory", true);
			returnChestToInventoryProp.comment = LanguageRegistry.instance().getStringLocalization("config.Inform.returnChestToInventory").replace("%1", LanguageRegistry.instance().getStringLocalization("item.guardianTier0.name	")).replace("%2", LanguageRegistry.instance().getStringLocalization("item.boundMapTier0.name"));
			returnChestToInventory = returnChestToInventoryProp.getBoolean(true);

		} catch(Exception e) {
			logger.log(Level.ERROR, LanguageRegistry.instance().getStringLocalization("config.Error.Message"));
			e.printStackTrace();
		} finally {
			if(cfg.hasChanged()) {
				cfg.save();
			}
		}
	}

}
