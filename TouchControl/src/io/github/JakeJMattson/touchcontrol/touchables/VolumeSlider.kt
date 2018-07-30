package io.github.JakeJMattson.touchcontrol.touchables

import java.io.*

import org.opencv.core.*

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

	/*
	 * (non-Javadoc)
	 * @see
	 * io.github.JakeJMattson.touchcontrol.touchables.Touchable#performAction()
	 */
	override fun performAction() {
		if (hasDetection()) {
			//Calculate volume
			val newVolume = (numOfDivisions - division) * (100 / numOfDivisions)

			//Avoid unnecessary calls
			if (previousVolume != newVolume)
				setVolume(newVolume)
		}
	}

	/**
	 * Set system volume.
	 *
	 * @param volume
	 * New target system volume
	 */
	private fun setVolume(volume: Int) {
		//Nircmd allows volume changing
		val nircmdPath = File("nircmd/nircmd.exe").absolutePath

		//Convert volume to nircmd standard
		val nircmdVolume = 655.35 * volume

		//Build command to set system volume
		val command = "$nircmdPath setsysvolume $nircmdVolume"

		//Set volume
		previousVolume = volume
		executeCommand(command)

		//Display message for debugging
		println("Volume set to: $volume")
	}

	/**
	 * Execute a system command (Call nircmd from command line).
	 *
	 * @param command
	 * System command
	 */
	private fun executeCommand(command: String) {
		try {
			Runtime.getRuntime().exec(command)
		}
		catch (e: IOException) {
			e.printStackTrace()
		}
	}
}