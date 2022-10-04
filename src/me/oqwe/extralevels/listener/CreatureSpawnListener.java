package me.oqwe.extralevels.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import me.oqwe.extralevels.util.entity.EntityUtil;

public class CreatureSpawnListener implements Listener {

	/**
	 * Handles spawned entities
	 * 
	 * @param e
	 */
	@EventHandler
	public void onEvent(CreatureSpawnEvent e) {
		if (!(e.getEntity() instanceof Player))
			EntityUtil.processEntity(e.getEntity());
	}
	
}
