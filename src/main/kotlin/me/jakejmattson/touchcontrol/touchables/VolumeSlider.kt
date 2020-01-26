package me.jakejmattson.touchcontrol.touchables

import org.opencv.core.*
import java.io.File

/**
 * Demo Slider - Example application of abstract slider.
 * Controls system volume.
 *
 * @author JakeJMattson
 */
class VolumeSlider(dimensions: Rect, color: Scalar) : Slider(dimensions, color) {
    /**
     * Internal representation of current system volume
     */
    private var previousVolume: Int = 0

    override fun performAction() {
        if (hasDetection()) {
            val newVolume = (numOfDivisions - currentDivision) * (100 / numOfDivisions)

            if (previousVolume != newVolume)
                setVolume(newVolume)
        }
    }

    private fun setVolume(volume: Int) {
        setSystemVolume(volume)
        previousVolume = volume
    }

    private fun setSystemVolume(volume: Int) {
        val nircmd = File("nircmd/nircmd.exe")

        if (!nircmd.exists())
            return

        //Convert volume to nircmd standard
        val nircmdVolume = 655.35 * volume

        //Build command to set system volume
        val command = "${nircmd.absolutePath} setsysvolume $nircmdVolume"

        Runtime.getRuntime().exec(command)
    }
}