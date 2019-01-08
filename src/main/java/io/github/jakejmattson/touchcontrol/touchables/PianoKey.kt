package io.github.jakejmattson.touchcontrol.touchables

import org.opencv.core.*
import javax.sound.midi.*

/**
 * Demo Button - Example application of abstract button.
 * Plays note when touched.
 *
 * @author JakeJMattson
 */
class PianoKey(dimensions: Rect, color: Scalar, note: Char): Button(dimensions, color) {
	/**
	 * Whether or not the key has already been played on a given touch
	 */
	private var hasPlayed: Boolean = false
	/**
	 * Sound to be played
	 */
	private var key: Int = determineKey(note)
	/**
	 * Audio player
	 */
	private var channel: MidiChannel = setupMidi()

	/**
	 * Determine the key of the note based on the character input.
	 *
	 * @param note
	 * Character representation of the note
	 * @return Key
	 */
	private fun determineKey(note: Char): Int {
		//Offset from char (A B C D E F G)
		val offsets = intArrayOf(-4, -2, 0, 1, 3, 5, 7)

		//Determine key
		val basekey = 60
		return basekey + offsets[note - 'A']
	}

	/**
	 * Create the audio player.
	 */
	private fun setupMidi(): MidiChannel {
		//Set up environment to play audio
		val midiSynth = MidiSystem.getSynthesizer()
		val instr = midiSynth.defaultSoundbank.instruments
		midiSynth.loadInstrument(instr[0])
		midiSynth.open()

		return midiSynth.channels[0]
	}

	override fun performAction() {
		//Play note
		if (isBeingClicked) {
			if (!hasPlayed) {
				playNote(1000)
				hasPlayed = true
			}
		}
		else
			hasPlayed = false
	}

	/**
	 * Play the note that was assigned to the key.
	 *
	 * @param duration
	 * The amount of ms that the note should be held for
	 */
	private fun playNote(duration: Int) {
		//Create thread to play note
		object: Thread() {
			override fun run() {
				//Start playing note
				channel.noteOn(key, 100)

				try {
					//Hold the note for x milliseconds
					Thread.sleep(duration.toLong())
				}
				catch (e: InterruptedException) {
					e.printStackTrace()
				}

				//Stop playing note
				channel.noteOff(key)
			}
		}.start()
	}

	override fun toString() = super.toString() + format("Key (note):", key)
}