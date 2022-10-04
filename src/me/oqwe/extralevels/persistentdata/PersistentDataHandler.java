package me.oqwe.extralevels.persistentdata;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataType;

import me.oqwe.extralevels.Main;

public class PersistentDataHandler {

	private static final NamespacedKey LVLKEY = new NamespacedKey(Main.getInstance(), "lvl");
	
	public static boolean hasLvl(Entity entity) {
		return entity.getPersistentDataContainer().has(LVLKEY, PersistentDataType.INTEGER);
	}
	
	public static void setLvl(Entity entity, int lvl) {
		entity.getPersistentDataContainer().set(LVLKEY, PersistentDataType.INTEGER, lvl);
	}
	
	public static int getLvl(Entity entity) {
		return entity.getPersistentDataContainer().get(LVLKEY, PersistentDataType.INTEGER);
	}
	
}
