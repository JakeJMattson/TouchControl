package io.github.jakejmattson.touchcontrol.touchables

import org.opencv.core.*
import org.opencv.imgproc.Imgproc

/**
 * Contains fundamental functions for a Touchable object of any kind.
 *
 * @author JakeJMattson
 */

abstract class Touchable protected constructor(
	/**
	 * Location and size of the Touchable
	 */
	var dimensions: Rect,
	/**
	 * Color that the Touchable will be drawn in
	 */
	var color: Scalar) {
	/**
	 * Farthest non-background pixel found within range
	 */
	protected var detectionPoint: Point? = null

	/**
	 * Search through the range of the Touchable and find the first occurrence
	 * of a non-background pixel.
	 *
	 * @param filteredImage
	 * Binary image
	 * @return Detection point (for external use)
	 */
	open fun updateDetectionPoint(filteredImage: Mat): Point? {
		//Point of possible detection
		var farthestPoint: Point? = null
		val background = 0.0

		//Range variables
		val tl = dimensions.tl() //Top left
		val br = dimensions.br() //Bottom right
		val tlX = tl.x.toInt()
		val tlY = tl.y.toInt()
		val brX = br.x.toInt()
		val brY = br.y.toInt()

		//Search for non-background pixel
		for (y in tlY until brY) {
			for (x in brX downTo tlX)
				if (filteredImage.get(y, x)[0] != background) {
					farthestPoint = Point(x.toDouble(), y.toDouble())
					break
				}

			if (farthestPoint != null)
				break
		}

		//Save detection
		detectionPoint = farthestPoint

		return detectionPoint
	}

	/**
	 * Determine whether or not the current detection point is null.
	 *
	 * @return Detection status
	 */
	protected fun hasDetection(): Boolean {
		return detectionPoint != null
	}

	/**
	 * Draw Touchable onto image.
	 *
	 * @param image
	 * Matrix to draw the Touchable object onto
	 */
	open fun drawOnto(image: Mat?) {
		if (image != null) {
			//Draw component
			Imgproc.rectangle(image, dimensions.tl(), dimensions.br(), color, 3)

			//Draw circle around detection
			if (hasDetection())
				Imgproc.circle(image, detectionPoint!!, 10, color)
		}
	}

	/**
	 * Abstract action that each Touchable subclass will perform.
	 */
	abstract fun performAction()

	/**
	 * Formatter to create a uniform toString() for all Touchable objects.
	 *
	 * @param name
	 * Name of field being formatted
	 * @param data
	 * Data of field being formatted
	 * @return Formatted string
	 */
	protected fun <T> format(name: String, data: T): String = String.format("   %-17s %-1s%n", name, data)

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	override fun toString(): String {
		return (this.javaClass.simpleName
				+ " (" + super.toString() + "):" + System.lineSeparator()
				+ format("Dimensions:", dimensions)
				+ format("Color:", color))
	}
}