package org.vanillaworld.CustomMusicDiscs.MidiLib;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import java.io.IOException;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Sound;

import com.google.common.collect.Maps;

/**
 * Midi Receiver for processing note events.
 *
 * @author authorblues
 */
public class NoteBlockReceiver implements Receiver
{
	private static final float VOLUME_RANGE = 10.0f;

	private Location l;
	private final Map<Integer, Integer> channelPatches;

	public NoteBlockReceiver(Location l) throws InvalidMidiDataException, IOException
	{
		this.l = l;
		this.channelPatches = Maps.newHashMap();
	}

	@Override
	public void send(MidiMessage m, long time)
	{
		if (m instanceof ShortMessage)
		{
			ShortMessage smessage = (ShortMessage) m;
			int chan = smessage.getChannel();

			switch (smessage.getCommand())
			{
				case ShortMessage.PROGRAM_CHANGE:
					int patch = smessage.getData1();
					channelPatches.put(chan, patch);
					break;

				case ShortMessage.NOTE_ON:
					this.playNote(smessage);
					break;

				case ShortMessage.NOTE_OFF:
					break;
			}
		}
	}

	public void playNote(ShortMessage message)
	{
		// if this isn't a NOTE_ON message, we can't play it
		if (ShortMessage.NOTE_ON != message.getCommand()) return;

		// get pitch and volume from the midi message
		float pitch = (float) ToneUtil.midiToPitch(message);
		float volume = VOLUME_RANGE * (message.getData2() / 127.0f);

		// get the correct instrument
		Integer patch = channelPatches.get(message.getChannel());
		Sound instrument = Sound.NOTE_PIANO;
		if (patch != null) instrument = MidiUtil.patchToInstrument(patch);

		this.l.getWorld().playSound(l, instrument, volume, pitch);
	}

	@Override
	public void close()
	{
		this.l = null;
		channelPatches.clear();
	}
}
