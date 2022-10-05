package me.oqwe.extralevels;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.oqwe.extralevels.command.ExtraLevels;
import me.oqwe.extralevels.config.Config;
import me.oqwe.extralevels.listener.CreatureSpawnListener;
import me.oqwe.extralevels.listener.WorldLoadListener;
import me.oqwe.extralevels.util.LvlFile;
import me.oqwe.extralevels.util.LvlUtil;

// specific handling of bosses, eg. disable levelling (or set to constant lvl) or allow them to be insane
// special loot tables for levels
// lvl can either modify an attribute or set it to a flat value
// expand config
// avoid negative level values
// specific handling of infection and curing of villagers (look at spawnreason)
// look through spawnreasons for things that should be handled specifically
// when a villager is cured or infected check that the villager/zombie can have the old level (after implementing entityspecific levels)
// update to other mc versions (consider new creatures ex frog) and attributemodifier api

public class Main extends JavaPlugin {

	private static Main instance;

	public void onEnable() {

		// ensure that there is a data folder
		if (!getDataFolder().exists()) {
			getDataFolder().mkdirs();
		}

		saveDefaultConfig();
		Config.load(getConfig());

		instance = this;
		new LvlFile();

		registerListeners();
		registerCommands();

		LvlUtil.loadLevels();

	}

	public void reload() {
		reloadConfig();
		Config.load(getConfig());
		LvlUtil.loadLevels();
	}

	private void registerListeners() {
		Bukkit.getPluginManager().registerEvents(new CreatureSpawnListener(), this);
		Bukkit.getPluginManager().registerEvents(new WorldLoadListener(), this);
	}

	private void registerCommands() {
		getCommand("extralevels").setExecutor(new ExtraLevels());
	}

	public static Main getInstance() {
		return instance;
	}

}
