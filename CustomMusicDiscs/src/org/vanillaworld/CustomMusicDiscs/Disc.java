package org.vanillaworld.CustomMusicDiscs;

import java.io.File;

public class Disc {
	
	public File midi;
	public String name;
	
	Disc(File midi)
	{
		this.midi = midi;
		String name = midi.getName();
		int pos = name.lastIndexOf(".");
		if (pos > 0) {
		    this.name = name.substring(0, pos);
		}
	}

}
