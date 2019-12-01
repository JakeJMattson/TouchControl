package io.github.jakejmattson.touchcontrol.touchables

import kotlinx.coroutines.*
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
	private fun setupMidi() =
		with (MidiSystem.getSynthesizer()) {
			loadInstrument(defaultSoundbank.instruments[0])
			open()
			channels.first()
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
	private fun playNote(duration: Long) {
		GlobalScope.launch {
			//Start playing note
			channel.noteOn(key, 100)

			//Hold the note for x milliseconds
			delay(duration)

			//Stop playing note
			channel.noteOff(key)
		}
	}

	override fun toString() = super.toString() + format("Key (note):", key)
}