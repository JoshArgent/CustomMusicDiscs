package org.vanillaworld.CustomMusicDiscs;

import java.io.File;

public class Setup {
	
	static File pluginFolder;
	static File discFolder;
	
	public static void setupFolders()
	{
		pluginFolder = Main.plugin.getDataFolder();
		discFolder = new File(pluginFolder.getPath() + "/discs");
		if(!pluginFolder.exists())
		{
			pluginFolder.mkdir();
		}
		if(!discFolder.exists())
		{
			discFolder.mkdir();
		}
	}
	

}
