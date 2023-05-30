package de.balou.stats;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public static MySQL api;
	
	@Override
	public void onEnable() {
		System.out.println("StatsAPI wird aktiviert");
		
		api = new MySQL("localhost", "stats", "stats", "94sEVRniE1yngoBh");
	}

}
