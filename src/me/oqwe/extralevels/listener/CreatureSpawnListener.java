package me.oqwe.extralevels.listener;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import me.oqwe.extralevels.config.Config;
import me.oqwe.extralevels.util.entity.EntityUtil;

public class CreatureSpawnListener implements Listener {

	private final EntityType[] harmlessEntities = { EntityType.ALLAY, EntityType.AXOLOTL, EntityType.BAT,
			EntityType.CAT, EntityType.CHICKEN, EntityType.COD, EntityType.COW, EntityType.DONKEY, EntityType.FOX,
			EntityType.FROG, EntityType.GLOW_SQUID, EntityType.HORSE, EntityType.MUSHROOM_COW, EntityType.MULE,
			EntityType.OCELOT, EntityType.PARROT, EntityType.PIG, EntityType.RABBIT, EntityType.SALMON,
			EntityType.SHEEP, EntityType.SKELETON_HORSE, EntityType.SNOWMAN, EntityType.SQUID, EntityType.STRIDER,
			EntityType.TADPOLE, EntityType.TROPICAL_FISH, EntityType.TURTLE, EntityType.VILLAGER,
			EntityType.WANDERING_TRADER };

	/**
	 * Handles spawned creatures
	 * 
	 * @param e
	 */
	@EventHandler
	public void onEvent(CreatureSpawnEvent e) {

		if (e.getEntity() instanceof Player)
			return;

		if (!Config.PROCESS_HARMLESS) {
			for (var harmless : harmlessEntities)
				if (e.getEntity().getType().equals(harmless))
					return;
		}
		
		if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.COMMAND) && Config.PROCESS_COMMAND_SPAWNS) {
			EntityUtil.processEntity(e.getEntity());
			return;

		} else if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM) && Config.PROCESS_PLUGIN_SPAWNS) {

			EntityUtil.processEntity(e.getEntity());
			return;

		} else if (!(e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM))
				&& !(e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.COMMAND))) {

			EntityUtil.processEntity(e.getEntity());
			return;

		}
	}

}
