package me.oqwe.extralevels.listener;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import me.oqwe.extralevels.config.Config;
import me.oqwe.extralevels.util.LvlUtil;
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

		// ignore players
		if (e.getEntity() instanceof Player)
			return;

		// HANDLE SPAWN REASON
		// command
		if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.COMMAND)) {
			if (Config.PROCESS_COMMAND_SPAWNS) {
				EntityUtil.processEntity(e.getEntity(), true);
				return;
			} else
				return;
		}

		// plugin
		if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM)) {
			if (Config.PROCESS_PLUGIN_SPAWNS) {
				EntityUtil.processEntity(e.getEntity(), true);
				return;
			} else
				return;
		}

		// cured
		if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CURED)) {
			// normally the level shouldn't change here, however villagers are passive and
			// zombies are hostile
			// so the level of the entity after curing might not be allowed
			// TODO check if level is allowed
			// if configured to ignore harmless, reset entity
			if (!Config.PROCESS_HARMLESS && e.getEntity().getType().equals(EntityType.VILLAGER)) {
				LvlUtil.removeLevel(e.getEntity());
				return;
			} else if (Config.PROCESS_HARMLESS && e.getEntity().getType().equals(EntityType.VILLAGER)) {
				// if dont ignore harmless, update name and mods
			}
				EntityUtil.processEntity(e.getEntity(), false);
				return;
		}

		// infected
		if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.INFECTION)) {
			// normally the level shouldn't change here, however villagers are passive and
			// zombies are hostile
			// so the level of the entity after infection might not be allowed
			// TODO check if level is allowed
			return;
		}

		// metamorphosis
		if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.METAMORPHOSIS)) {
			// level shouldn't be changed, as this isn't percieved as a new entity
			// reprocess to update name and modifiers
			EntityUtil.processEntity(e.getEntity(), true);
			return;
		}

		// sheared (mooshroom to cow using shears)
		if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.SHEARED)) {
			// level shouldn't be changed, as this isn't percieved as a new entity
			// reprocess to update name and modifiers
			EntityUtil.processEntity(e.getEntity(), true);
			return;
		}

		// shoudler_entity (when parrot on shoulder dismounts)
		if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.SHOULDER_ENTITY)) {
			// nothing should be changed, as this isn't percieved as a new entity
			return;
		}

		// ignore if entity is harmless and process-harmless is false in config
		if (!Config.PROCESS_HARMLESS) {
			for (var harmless : harmlessEntities)
				if (e.getEntity().getType().equals(harmless))
					return;
		}

		// if no spawn reason is caught
		EntityUtil.processEntity(e.getEntity(), true);

	}

}
