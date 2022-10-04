package me.oqwe.extralevels.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.oqwe.extralevels.Main;

public final class LvlFile {

	private File file;
	private FileConfiguration config;
	
	private static LvlFile instance;

	/**
	 * Creates levels.yml if it doesn't exist and loads the contents into memory
	 * 
	 * @param main Instance of main class
	 */
	public LvlFile() {

		instance = this;
		
		this.file = new File(Main.getInstance().getDataFolder(), "levels.yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
				
				// set file configuration as default and save
				config = getDefaultConfig();				
				save();
				config = YamlConfiguration.loadConfiguration(file);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.config = YamlConfiguration.loadConfiguration(file);
		
	}
	
	public static LvlFile getInstance() {
		return instance;
	}
	
	/**
	 * Save configuration to file
	 */
	public void save() {
		try {
			config.save(file);
		} catch	(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Reset levels.yml to default configuration
	 */
	public void resetFile() {
		this.config = getDefaultConfig();
		save();
		config = YamlConfiguration.loadConfiguration(file);
	}
	
	/**
	 * @return The default configuration of levels.yml
	 */
	private FileConfiguration getDefaultConfig() {
		FileConfiguration config = new YamlConfiguration();
		
		config.set("1.chance", 0.5);
		config.set("1.movement_speed", "/2");
		config.set("1.namecolor", "&a");
		
		config.set("2.chance", 0.5);
		config.set("2.max_health", "*5");
		config.set("2.namecolor", "&6");
		
		config.set("10.chance", 0.1);
		config.set("10.attack_damage", "*2");
		config.set("10.flying_speed", "*2");
		config.set("10.movement_speed", "*2");
		config.set("10.namecolor", "&c");
		
		return config;
	}
	
	public File getFile() {
		return this.file;
	}
	
	public FileConfiguration getConfig() {
		return config;
	}

}