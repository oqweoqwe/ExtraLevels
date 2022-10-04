package me.oqwe.extralevels;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.oqwe.extralevels.command.ExtraLevels;
import me.oqwe.extralevels.listener.CreatureSpawnListener;
import me.oqwe.extralevels.listener.WorldLoadListener;
import me.oqwe.extralevels.util.Lvl;
import me.oqwe.extralevels.util.LvlFile;
import me.oqwe.extralevels.util.LvlUtil;

// remind users to disable log named deaths in spigot.yml
// specific handling of bosses, eg. disable levelling (or set to constant lvl) or allow them to be insane
// special loot tables for levels
// lvl can either modify an attribute or set it to a flat value
// lvl must have chance in levels.yml

public class Main extends JavaPlugin {

	private static Main instance;
	
	private static Set<Lvl> lvls;
	private static HashMap<Lvl, Double> chanceMap;
	
	public void onEnable() {
		
		// ensure that there is a data folder
		if (!getDataFolder().exists()) {
			getDataFolder().mkdirs();
		}
		
		instance = this;
		new LvlFile();
		
		registerListeners();
		registerCommands();
		
		lvls = LvlUtil.loadLevels();
		chanceMap = LvlUtil.generateChanceMap();
		
	}
	
	public void reload() {
		// reload config stuffs
		lvls = LvlUtil.loadLevels();
		chanceMap = LvlUtil.generateChanceMap();
		
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
	
	public static Set<Lvl> getLvls() {
		return lvls;
	}
	
	public static HashMap<Lvl, Double> getChanceMap() {
		return chanceMap;
	}
	
}
