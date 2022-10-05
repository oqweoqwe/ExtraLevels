package me.oqwe.extralevels.config;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {

	// holds all config values in memory
	
	public static boolean SHOW_CUSTOM_NAME, SHOW_LEVEL, PROCESS_PLUGIN_SPAWNS, PROCESS_COMMAND_SPAWNS, PROCESS_HARMLESS;
	
	public static void load(FileConfiguration config) {
		SHOW_CUSTOM_NAME = config.getBoolean("show-custom-name");
		SHOW_LEVEL = config.getBoolean("show-level");
		PROCESS_PLUGIN_SPAWNS = config.getBoolean("process-plugin-spawns");
		PROCESS_COMMAND_SPAWNS = config.getBoolean("process-command-spawns");
		PROCESS_HARMLESS = config.getBoolean("process-harmless");
	}
	
}
