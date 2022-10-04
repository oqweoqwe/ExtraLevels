package me.oqwe.extralevels.command;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import me.oqwe.extralevels.Main;
import me.oqwe.extralevels.command.sub.Summon;

public class ExtraLevels implements CommandExecutor {

	/**
	 * Main command
	 */
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (args.length >= 1) {
			
			// switch over subcommand argument and run a subcommand
			switch(args[0]) {
			
			case "reload":
				Main.getInstance().reload();
				sender.sendMessage("reloaded");
				break;
				
			case "kill":
				for (var entity : Bukkit.getPlayer("oqwe").getWorld().getEntities()) {
					if (!(entity instanceof Player)) {
						entity.remove();
					}
				}
				break;
			case "mod":
				for (var entity : Bukkit.getPlayer("oqwe").getWorld().getEntities())
					if (!(entity instanceof Player) && entity instanceof LivingEntity) {
						LivingEntity e = (LivingEntity) entity;
						System.out.println(e.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
					}
				break;
						
			
			case "summon":
				Summon.run(sender, args);
				break;
			
			}
			
			return true;
			
		}
		sender.sendMessage("bro, args!"); // TODO format
		return true;
	}
	
}
