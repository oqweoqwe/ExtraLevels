package me.oqwe.extralevels.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.oqwe.extralevels.Main;
import me.oqwe.extralevels.command.sub.Summon;
import me.oqwe.extralevels.util.ChatUtil;

public class ExtraLevels implements CommandExecutor {

	/**
	 * Main command
	 */
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (args.length >= 1) {
			
			// switch over subcommand argument and run a subcommand
			switch(args[0]) {
				
			case "help":
				if (sender.hasPermission("extralevels.help")) {
					ChatUtil.help(sender);
				} else
					ChatUtil.nopermisison(sender);
				break;
			case "reload":
				if (sender.hasPermission("extralevels.reload")) {
					Main.getInstance().reload();
					ChatUtil.reloaded(sender);
				} else
					ChatUtil.nopermisison(sender);	
				break;
				
			case "kill": // command used for testing, only works for me
				if (!(sender.getName().equalsIgnoreCase("oqwe"))) {
					ChatUtil.unknowncommand(sender);
					break;
				}
				for (var entity : Bukkit.getPlayer("oqwe").getWorld().getEntities()) {
					if (!(entity instanceof Player)) {
						entity.remove();
					}
				}
				break;			
			
			case "summon":
				Summon.run(sender, args);
				break;
				
			default:
				ChatUtil.unknowncommand(sender);
				break;
			}
			
			return true;
			
		}
		ChatUtil.unknowncommand(sender);
		return true;
	}
	
}
