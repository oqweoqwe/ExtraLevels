package me.oqwe.extralevels.util.entity;

import org.bukkit.entity.Entity;

import me.oqwe.extralevels.persistentdata.PersistentDataHandler;
import me.oqwe.extralevels.util.ChatUtil;
import me.oqwe.extralevels.util.LvlUtil;

public class NameHandler {

	/**
	 * Sets the custom name of an entity based on the level stored in the persistent
	 * data of the entity
	 * 
	 * @param entity
	 */
	public static void setupCustomName(Entity entity) {
		if (PersistentDataHandler.hasLvl(entity)) {
			entity.setCustomName(ChatUtil.cc("&7Lvl " + PersistentDataHandler.getLvl(entity)
					+ LvlUtil.getLvlObject(entity).getNameColor() + " " + entity.getName()));
			entity.setCustomNameVisible(true);
		}
	}

	/**
	 * Sets custom name to null and invisible
	 * 
	 * @param entity
	 */
	public static void removeCustomName(Entity entity) {
		entity.setCustomName(null);
		entity.setCustomNameVisible(false);
	}

}
