package me.oqwe.extralevels.util;

import org.bukkit.ChatColor;

public class ChatUtil {

	/**
	 * Translated alternate color codes in string
	 * 
	 * @param msg Text to be translated
	 * @return Translated string
	 */
	public static String cc(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
	
	
	
}
