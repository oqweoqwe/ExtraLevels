package me.oqwe.extralevels.util.entity;

import org.bukkit.entity.Entity;

import me.oqwe.extralevels.config.Config;
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
		// check config
		if (!Config.SHOW_CUSTOM_NAME)
			return;
		
		if (PersistentDataHandler.hasLvl(entity)) {
			
			String customName = "";
			
			if (Config.SHOW_LEVEL)
				customName += "&7Lvl " + PersistentDataHandler.getLvl(entity) + " ";
			
			customName += LvlUtil.getLvlObject(entity).getNameColor() + entity.getName();
			
			entity.setCustomName(ChatUtil.cc(customName));
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
