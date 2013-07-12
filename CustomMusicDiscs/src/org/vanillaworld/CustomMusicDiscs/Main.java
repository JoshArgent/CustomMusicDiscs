package org.vanillaworld.CustomMusicDiscs;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	public static JavaPlugin plugin;
	
	public void onEnable()
	{
		plugin = this;
	}
	
	public void onDisable()
	{
		plugin = null;
	}

}
