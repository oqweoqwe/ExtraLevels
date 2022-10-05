package me.oqwe.extralevels.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;

import me.oqwe.extralevels.persistentdata.PersistentDataHandler;
import me.oqwe.extralevels.util.entity.AttributeHandler;
import me.oqwe.extralevels.util.entity.NameHandler;

public class LvlUtil {

	private static Random random = new Random();
	private static Set<Lvl> levelSet;
	private static HashMap<Lvl, Double> chanceMap;

	/**
	 * Selects a level to apply to the entity, and stores it in the persistent data
	 * 
	 * @param entity
	 */
	public static void setRandomLvl(Entity entity) {

		double chanceTotal = 0;
		// count chance total
		for (var lvlObj : chanceMap.keySet())
			chanceTotal += chanceMap.get(lvlObj);

		// select random and define accumulated chance
		double choice = random.nextDouble(chanceTotal), accumulatedChance = 0;

		// iterate over levels, accumulate chances and check if acc chance has excedded
		// the random
		for (var lvlObj : chanceMap.keySet()) {
			accumulatedChance += chanceMap.get(lvlObj);
			if (choice <= accumulatedChance) {
				PersistentDataHandler.setLvl(entity, lvlObj.getLvl());
				break;
			}
		}
	}

	/**
	 * Removes the level from persistent data and removes all modifiers and custom name
	 * 
	 * @param entity
	 */
	public static void removeLevel(Entity entity) {
		PersistentDataHandler.removeLvl(entity);
		AttributeHandler.removeModifiers(entity);
		NameHandler.removeCustomName(entity);
	}
	
	/**
	 * Gets the level object corresponding to an entities level
	 * 
	 * @param entity
	 * @return Lvl object
	 */
	public static Lvl getLvlObject(Entity entity) {
		for (var lvlObj : levelSet) {
			if (lvlObj.getLvl() == PersistentDataHandler.getLvl(entity))
				return lvlObj;
		}
		return null;
	}

	/**
	 * Loads the levels from levels.yml and resets old level set, also generates chance map
	 * 
	 * @return Set of level objects
	 */
	public static void loadLevels() {

		// reset levelSet
		levelSet = new HashSet<Lvl>();
		
		YamlConfiguration config = YamlConfiguration.loadConfiguration(LvlFile.getInstance().getFile());

		// first iterate over levels, ie top level of file
		for (var level : config.getKeys(false)) {

			double chance = 0;
			Set<AttributeModifier> attributeModifiers = new HashSet<>();
			String nameColor = "";
			int lvl = -1;
			
			try {
				lvl = Integer.parseInt(level);
			} catch (NumberFormatException e) {
				Bukkit.getLogger().warning("Failed to load level " + level + " since " + level + " is not an integer");
				continue;
			}
			
			// iterate over elements under each level
			for (var element : config.getKeys(true)) {
				if (element.contains(".")) {
					
					if (element.substring(0, element.indexOf('.')).equalsIgnoreCase(level.toString())) {
						// this element belongs to level, parse it and construct the level
						if (element.contains("chance")) {
							chance = config.getDouble(element);
						}
						else if (element.contains("namecolor"))
							nameColor = config.getString(element);
						else {
							
							// element is an attribute modifier
							var attrib = AttributeHandler.translateAttribute(element.substring(element.indexOf('.') + 1));
							var mod = AttributeHandler.getAttributeModifier(attrib, config.getString(element));
							
							attributeModifiers.add(mod);

						}
					}
					
				}

			}
			
			if (chance == 0) {
				Bukkit.getLogger().warning("Failed to load level " + level + " since the chance is 0");
				continue;
			}
			levelSet.add(new Lvl(lvl, chance, nameColor, attributeModifiers));

		}
		// generate chance map
		chanceMap = new HashMap<Lvl, Double>();
		levelSet.forEach(lvl -> chanceMap.put(lvl, lvl.getChance()));
	}

}
