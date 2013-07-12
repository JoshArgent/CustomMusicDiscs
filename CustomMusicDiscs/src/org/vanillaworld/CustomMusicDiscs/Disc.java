package org.vanillaworld.CustomMusicDiscs;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.midi.Sequencer;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.vanillaworld.CustomMusicDiscs.MidiLib.MidiUtil;

public class Disc {
	
	public File midi;
	public String name;
	public ItemStack disc;
	Map<Location, Sequencer> playing;
	
	Disc(File midi)
	{
		this.midi = midi;
		String name = midi.getName();
		int pos = name.lastIndexOf(".");
		if (pos > 0) {
		    this.name = name.substring(0, pos);
		}
		ItemStack item = new ItemStack(Material.RECORD_5, 1);
		List<String> lore = new ArrayList<String>();
		lore.add("*Custom Disc*");
		lore.add(ChatColor.RESET + "" + ChatColor.GRAY + this.name);
		ItemMeta meta = item.getItemMeta();
		meta.setLore(new ArrayList<String>());
		meta.setLore(lore);
		item.setItemMeta(meta);
		this.disc = item;
		playing = new HashMap<Location, Sequencer>();
	}
	
	public void play(Location l)
	{
		Sequencer seq = MidiUtil.playMidiQuietly(midi, l);
		playing.put(l, seq);
	}
	
	public void stop(Location l)
	{
		if(playing.containsKey(l))
		{
			playing.get(l).stop();
			playing.remove(l);
		}
	}

}
