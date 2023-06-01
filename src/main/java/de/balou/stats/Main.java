package de.balou.stats;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public static MySQL api;
	
	@Override
	public void onEnable() {
		System.out.println("StatsAPI wird aktiviert");

		this.saveDefaultConfig();
		FileConfiguration cfg = this.getConfig();
		
		api = new MySQL(
				cfg.getString("MySQL.Host"),
				cfg.getString("MySQL.Database"),
				cfg.getString("MySQL.Username"),
				cfg.getString("MySQL.Password"),
				cfg.getInt("MySQL.Port")
		);
	}

}
