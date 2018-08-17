package io.github.jakejmattson.touchcontrol.touchables

import org.opencv.core.*

/**
 * Abstract Button - contains general (toggle) button behavior.
 *
 * @author JakeJMattson
 */
abstract class ToggleButton protected constructor(dimensions: Rect, color: Scalar) : Button(dimensions, color) {
	/**
	 * The current state of the button
	 */
	protected var isToggledOn: Boolean = false
	/**
	 * Whether or not the button has already switched states internally
	 */
	private var hasSwitched: Boolean = false

	/*
	 * (non-Javadoc)
	 * @see
	 * io.github.JakeJMattson.touchcontrol.touchables.Button
	 * #updateDetectionPoint(org.opencv.core.Mat)
	 */
	override fun updateDetectionPoint(filteredImage: Mat): Point? {
		//Save previous state
		val wasClicked = isBeingClicked

		//Update state
		super.updateDetectionPoint(filteredImage)

		//Toggle state
		if (isBeingClicked && !wasClicked) {
			if (!hasSwitched) {
				isToggledOn = !isToggledOn
				hasSwitched = true
			}
		}
		else
			hasSwitched = false

		return detectionPoint
	}

	/*
	 * (non-Javadoc)
	 * @see io.github.JakeJMattson.touchcontrol.touchables.Button#toString()
	 */
	override fun toString(): String {
		return super.toString() + format("Toggled on:", isToggledOn)
	}
}