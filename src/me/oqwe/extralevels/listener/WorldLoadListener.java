package me.oqwe.extralevels.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

import me.oqwe.extralevels.util.entity.EntityUtil;

public class WorldLoadListener implements Listener {

	/**
	 * Ensures that all mobs have been processed when loading a new world
	 * 
	 * @param e
	 */
	@EventHandler
	public void onEvent(WorldLoadEvent e) {
		EntityUtil.checkMobs(e.getWorld());
	}
	
}
