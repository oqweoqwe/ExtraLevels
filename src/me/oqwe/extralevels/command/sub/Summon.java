package me.oqwe.extralevels.command.sub;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import me.oqwe.extralevels.persistentdata.PersistentDataHandler;
import me.oqwe.extralevels.util.ChatUtil;
import me.oqwe.extralevels.util.entity.EntityUtil;

public class Summon {

	/**
	 * Runs summon command
	 * 
	 * @param sender CommandSender from main command
	 * @param args args from main command
	 */
	public static void run(CommandSender sender, String[] args) {

		if (!sender.hasPermission("extralevels.summon")) {
			ChatUtil.nopermisison(sender);
			return;
		}

		if (!(args.length >= 3)) {
			ChatUtil.wronguse(sender);
			return;
		}

		if (!(sender instanceof Player)) {
			ChatUtil.wronguse(sender);
			return;
		}

		EntityType entityType;

		// parse entity argument
		try {
			entityType = EntityType.valueOf(args[1].toUpperCase());
		} catch (IllegalArgumentException e) {
			ChatUtil.noentity(sender, args[1]);
			return;
		}

		int lvl;

		// parse level argument
		try {
			lvl = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			ChatUtil.lvlnotint(sender, args[2]);
			return;
		}

		// summon entity, set level and reprocess to ensure that the random level
		// applied at spawn is overruled
		Player p = (Player) sender;
		Entity entity = p.getWorld().spawnEntity(p.getLocation(), entityType);
		PersistentDataHandler.setLvl(entity, lvl);
		EntityUtil.processEntity(entity);
		ChatUtil.summonedentity(sender, entity.getCustomName().toString());

	}

}
