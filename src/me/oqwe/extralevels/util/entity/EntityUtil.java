package me.oqwe.extralevels.util.entity;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import me.oqwe.extralevels.persistentdata.PersistentDataHandler;
import me.oqwe.extralevels.util.LvlUtil;

public class EntityUtil {

	/**
	 * Ensures that an entity has a level, and that name and modifiers have been
	 * applied
	 * 
	 * @param entity
	 */
	public static void processEntity(Entity entity, boolean assignRandomLevelIfNoLevelIsFound) {

		if (!PersistentDataHandler.hasLvl(entity) && assignRandomLevelIfNoLevelIsFound)
			LvlUtil.setRandomLvl(entity);

		// ensure that name and midifiers are removed before adding to avoid appending
		// the name and adding stacking modifiers
		NameHandler.removeCustomName(entity);
		AttributeHandler.removeModifiers(entity);

		NameHandler.setupCustomName(entity);
		AttributeHandler.setupModifyers((LivingEntity) entity);

	}
	
	/**
	 * Heals an entity to max health if it has a health modifier
	 * 
	 * @param entity Entity
	 */
	public static void healToMax(LivingEntity entity) {
		
		while(true) {
			try {
				entity.setHealth(entity.getHealth()+1);
			} catch (IllegalArgumentException e) {
				// max health reached
				break;
			}
		}
	}

	/**
	 * Checks that all living entities in world have been processed
	 * 
	 * @param world
	 */
	public static void checkMobs(World world) {
		for (var entity : world.getEntities()) {

			// ensure livingentity
			if (entity instanceof LivingEntity && !(entity instanceof Player)) {

				if (!PersistentDataHandler.hasLvl(entity)) {
					EntityUtil.processEntity(entity, true);
				}
			}
		}
	}

}
