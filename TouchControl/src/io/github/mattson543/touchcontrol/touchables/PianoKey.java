package io.github.mattson543.touchcontrol.touchables;

import javax.sound.midi.*;

import org.opencv.core.*;

/**
 * Demo Button - Example application of abstract button.
 * Plays note when touched.
 *
 * @author mattson543
 */
public class PianoKey extends Button
{
	/**
	 * Whether or not the key has already been played on a given touch
	 */
	private boolean hasPlayed;
	/**
	 * Sound to be played
	 */
	private int key;
	/**
	 * Audio player
	 */
	private MidiChannel channel;

	public PianoKey(Rect dimensions, Scalar color, char note)
	{
		super(dimensions, color);
		init(note);
	}

	/**
	 * Initialize fields.
	 *
	 * @param note
	 *            Character representation of the note
	 */
	private void init(char note)
	{
		//Determine key to play based on note
		key = determineKey(note);

		//Get channel to play note on
		setupMidi();
	}

	/**
	 * Determine the key of the note based on the character input.
	 *
	 * @param note
	 *            Character representation of the note
	 * @return Key
	 */
	private int determineKey(char note)
	{
		//Offset from char (A B C D E F G)
		int[] offsets = {-4, -2, 0, 1, 3, 5, 7};

		//Determine key
		int basekey = 60;
		return basekey + offsets[note - 'A'];
	}

	/**
	 * Create the audio player.
	 */
	private void setupMidi()
	{
		try
		{
			//Set up environment to play audio
			Synthesizer midiSynth = MidiSystem.getSynthesizer();
			Instrument[] instr = midiSynth.getDefaultSoundbank().getInstruments();
			midiSynth.loadInstrument(instr[0]);
			midiSynth.open();

			channel = midiSynth.getChannels()[0];
		}
		catch (MidiUnavailableException e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * io.github.mattson543.touchcontrol.touchables.Touchable#performAction()
	 */
	@Override
	public void performAction()
	{
		//Play note
		if (isBeingClicked)
		{
			if (!hasPlayed)
			{
				playNote(1000);
				hasPlayed = true;
			}
		}
		else
			hasPlayed = false;
	}

	/**
	 * Play the note that was assigned to the key.
	 *
	 * @param duration
	 *            The amount of ms that the note should be held for
	 */
	private void playNote(int duration)
	{
		//Create thread to play note
		new Thread()
		{
			@Override
			public void run()
			{
				//Start playing note
				channel.noteOn(key, 100);

				try
				{
					//Hold the note for x milliseconds
					Thread.sleep(duration);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}

				//Stop playing note
				channel.noteOff(key);
			}
		}.start();
	}

	/*
	 * (non-Javadoc)
	 * @see io.github.mattson543.touchcontrol.touchables.Button#toString()
	 */
	@Override
	public String toString()
	{
		return super.toString() + format("Key (note):", key);
	}
}