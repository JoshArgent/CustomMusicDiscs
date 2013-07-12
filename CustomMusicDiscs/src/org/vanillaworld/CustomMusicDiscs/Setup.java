package org.vanillaworld.CustomMusicDiscs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
	
	public static List<Disc> getDiscs()
	{
		List<Disc> discs = new ArrayList<Disc>();
		String files;
		File[] listOfFiles = discFolder.listFiles(); 
		for (int i = 0; i < listOfFiles.length; i++) 
		{
			if (listOfFiles[i].isFile()) 
			{
				files = listOfFiles[i].getName();
				if (files.endsWith(".mid") || files.endsWith(".MID") || files.endsWith(".MIDI") || files.endsWith(".midi"))
				{
					discs.add(new Disc(listOfFiles[i]));
				}
			}
		}
		return discs;
	}

}
