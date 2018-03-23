/**
 * Class Description:
 * Demo Button - example application of abstract button
 * Plays note when touched
 */

package touchcontrol.touchables;

import javax.sound.midi.*;

import org.opencv.core.*;

public class PianoKey extends Button
{
	private boolean hasPlayed;
	private int key;
	private MidiChannel channel;

	//Constructors
	public PianoKey(Rect dimensions, char note)
	{
		this(dimensions, DEFAULT_COLOR, note);
	}

	public PianoKey(Rect dimensions, Scalar color, char note)
	{
		super(dimensions, color);
		init(note);
	}

	private void init(char note)
	{
		//Determine key to play based on note
		key = determineKey(note);

		//Get channel to play note on
		setupMidi();
	}

	//Setters

	//Getters

	//Class methods
	private int determineKey(char note)
	{
		//Offset from char (A B C D E F G)
		int[] offsets = {-4, -2, 0, 1, 3, 5, 7};

		//Determine key
		int basekey = 60;
		int key = basekey + offsets[note - 'A'];
		return key;
	}

	private void setupMidi()
	{
		try
		{
			//Set up environment to play audio
			@SuppressWarnings("resource")
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

	@Override
	public void performAction()
	{
		//Play note
		if (isBeingClicked)
		{
			if (!hasPlayed)
			{
				playNote();

				hasPlayed = true;
			}
		}
		else
			hasPlayed = false;
	}

	private void playNote()
	{
		//Create thread to play note
		MusicPlayer player = new MusicPlayer(channel, key);
		Thread thread = new Thread(player);
		thread.start();
	}

	@Override
	public String toString()
	{
		return super.toString() + format("Key (note):", key);
	}
}

//Thread class to play note
class MusicPlayer implements Runnable
{
	private final MidiChannel channel;
	private final int key;

	//Constructors
	public MusicPlayer(MidiChannel channel, int key)
	{
		this.channel = channel;
		this.key = key;
	}

	//Setters

	//Getters

	//Class methods
	@Override
	public void run()
	{
		//Start playing note
		channel.noteOn(key, 100);

		try
		{
			//Duration of note
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		//Stop playing note
		channel.noteOff(key);
	}
}