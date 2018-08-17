package io.github.jakejmattson.touchcontrol.touchables

import org.opencv.core.*

/**
 * Abstract Button - contains general (clickable) button behavior.
 *
 * @author JakeJMattson
 */
abstract class Button protected constructor(dimensions: Rect, color: Scalar) : Touchable(dimensions, color) {
	/**
	 * Whether or not a non-background pixel is currently being detected
	 */
	protected var isBeingClicked: Boolean = false

	/*
	 * (non-Javadoc)
	 * @see io.github.JakeJMattson.touchcontrol.touchables.Touchable
	 * #updateDetectionPoint(org.opencv.core.Mat)
	 */
	override fun updateDetectionPoint(filteredImage: Mat): Point? {
		//Update state
		super.updateDetectionPoint(filteredImage)

		//Determine if button is currently detecting non-background pixel
		if (hasDetection() && !isBeingClicked)
			isBeingClicked = true
		else if (!hasDetection() && isBeingClicked)
			isBeingClicked = false

		return detectionPoint
	}
}