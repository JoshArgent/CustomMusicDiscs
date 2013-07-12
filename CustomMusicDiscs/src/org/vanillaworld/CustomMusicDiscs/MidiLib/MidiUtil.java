package org.vanillaworld.CustomMusicDiscs.MidiLib;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import org.bukkit.Location;
import org.bukkit.Sound;

/**
 * Utility for playing midi files for players to hear.
 *
 * @author authorblues
 */
public class MidiUtil
{
	private static Sequencer playMidi(Sequence seq, float tempo, Location l)
		throws InvalidMidiDataException, IOException, MidiUnavailableException
	{
		Sequencer sequencer = MidiSystem.getSequencer(false);
		sequencer.setSequence(seq);
		sequencer.open();

		// slow it down just a bit
		sequencer.setTempoFactor(tempo);

		NoteBlockReceiver noteblockRecv = new NoteBlockReceiver(l);
		sequencer.getTransmitter().setReceiver(noteblockRecv);
		sequencer.start();
		return sequencer;
	}

	public static Sequencer playMidi(File file, float tempo, Location l)
		throws InvalidMidiDataException, IOException, MidiUnavailableException
	{ return playMidi(MidiSystem.getSequence(file), tempo, l); }

	public static Sequencer playMidi(InputStream stream, float tempo, Location l)
		throws InvalidMidiDataException, IOException, MidiUnavailableException
	{ return playMidi(MidiSystem.getSequence(stream), tempo, l); }

	public static Sequencer playMidiQuietly(File file, float tempo, Location l)
	{
		try { return MidiUtil.playMidi(file, tempo, l); }
		catch (MidiUnavailableException e) { e.printStackTrace(); return null; }
		catch (InvalidMidiDataException e) { e.printStackTrace(); return null; }
		catch (IOException e) { e.printStackTrace(); return null; }
	}
	public static Sequencer playMidiQuietly(InputStream stream, float tempo, Location l)
	{
		try { MidiUtil.playMidi(stream, tempo, l); }
		catch (MidiUnavailableException e) { e.printStackTrace(); return null; }
		catch (InvalidMidiDataException e) { e.printStackTrace(); return null; }
		catch (IOException e) { e.printStackTrace(); return null; }

		return null;
	}

	public static Sequencer playMidiQuietly(File file, Location l)
	{ return playMidiQuietly(file, 1.0f, l); }

	public static Sequencer playMidiQuietly(InputStream stream, Location l)
	{ return playMidiQuietly(stream, 1.0f, l); }

	// provided by github.com/sk89q/craftbook
	private static final int[] instruments = {
		0, 0, 0, 0, 0, 0, 0, 5, //   8
		6, 0, 0, 0, 0, 0, 0, 0, //  16
		0, 0, 0, 0, 0, 0, 0, 5, //  24
		5, 5, 5, 5, 5, 5, 5, 5, //  32
		6, 6, 6, 6, 6, 6, 6, 6, //  40
		5, 5, 5, 5, 5, 5, 5, 2, //  48
		5, 5, 5, 5, 0, 0, 0, 0, //  56
		0, 0, 0, 0, 0, 0, 0, 0, //  64
		0, 0, 0, 0, 0, 0, 0, 0, //  72
		0, 0, 0, 0, 0, 0, 0, 0, //  80
		0, 0, 0, 0, 0, 0, 0, 0, //  88
		0, 0, 0, 0, 0, 0, 0, 0, //  96
		0, 0, 0, 0, 0, 0, 0, 0, // 104
		0, 0, 0, 0, 0, 0, 0, 0, // 112
		1, 1, 1, 3, 1, 1, 1, 5, // 120
		1, 1, 1, 1, 1, 2, 4, 3, // 128
	};

	public static Sound patchToInstrument(int patch)
	{
		// look up the instrument matching the patch
		switch (instruments[patch])
		{
			case 1: return Sound.NOTE_BASS_GUITAR;
			case 2: return Sound.NOTE_SNARE_DRUM;
			case 3: return Sound.NOTE_STICKS;
			case 4: return Sound.NOTE_BASS_DRUM;
			case 5: return Sound.NOTE_PLING;
			case 6: return Sound.NOTE_BASS;
		}

		// if no instrument match is found, use piano
		return Sound.NOTE_PIANO;
	}
}
