package me.oqwe.extralevels.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ChatUtil {

	private static final String[] help = {
			"&eExtraLevels &7help:",
			"&7- &e/el reload",
			"&7Reloads the configuration of the plugin",
			"&7- &e/el summon &6<entity> <level>",
			"&7Summons an entity with specified level at your position (for testing purposes)",
			"&7For a guide on how to configure levels please see the spigot page for this plugin"
	};
	
	/**
	 * Translated alternate color codes in string
	 * 
	 * @param msg Text to be translated
	 * @return Translated string
	 */
	public static String cc(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
	
	public static void wronguse(CommandSender sender) {
		sender.sendMessage(cc("&cUnknown args, try /el help"));
	}
	
	public static void nopermisison(CommandSender sender) {
		sender.sendMessage(cc("&cYou do not have permission to use this command"));
	}
	
	public static void noentity(CommandSender sender, String entity) {
		sender.sendMessage(cc("&cFound no entity with name &e" + entity));
	}
	
	public static void lvlnotint(CommandSender sender, String lvl) {
		sender.sendMessage(cc("&cLevel must be an integer, &e"+lvl+" &cis not an integer"));
	}
	
	public static void summonedentity(CommandSender sender, String entity) {
		sender.sendMessage(cc("&eSummoned "+entity));
	}
	
	public static void reloaded(CommandSender sender) {
		sender.sendMessage(cc("&eReloaded &6config.yml &eand &6levels.yml"));
	}
	
	public static void unknowncommand(CommandSender sender) {
		sender.sendMessage(cc("&cUnknown command, try /el help"));
	}
	
	public static void help(CommandSender sender) {
		for (var msg : help) 
			sender.sendMessage(cc(msg));
	}
	
}
