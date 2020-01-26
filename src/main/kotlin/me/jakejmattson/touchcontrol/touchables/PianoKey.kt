package me.jakejmattson.touchcontrol.touchables

import kotlinx.coroutines.*
import org.opencv.core.*
import javax.sound.midi.*

/**
 * Demo Button - Example application of abstract button.
 * Plays note when touched.
 *
 * @author JakeJMattson
 */
class PianoKey(dimensions: Rect, color: Scalar, note: Char) : Button(dimensions, color) {
    /**
     * Whether or not the key has already been played on a given touch
     */
    private var hasPlayed: Boolean = false

    private var key: Int = determineKey(note)

    private var audioPlayer: MidiChannel = MidiSystem.getSynthesizer().run {
        val instrument = defaultSoundbank.instruments.first()
        loadInstrument(instrument)
        open()
        channels.first()
    }

    private fun determineKey(note: Char): Int {
        //Offset from char (A B C D E F G)
        val offsets = intArrayOf(-4, -2, 0, 1, 3, 5, 7)

        //Determine key
        val basekey = 60
        return basekey + offsets[note - 'A']
    }

    override fun performAction() {
        //Play note
        if (isBeingClicked) {
            if (!hasPlayed) {
                playNote()
                hasPlayed = true
            }
        } else
            hasPlayed = false
    }

    private fun playNote() {
        GlobalScope.launch {
            audioPlayer.noteOn(key, 100)
            delay(1000)
            audioPlayer.noteOff(key)
        }
    }

    override fun toString() = super.toString() + format("Key (note):", key)
}