package io.github.jakejmattson.touchcontrol.touchables

import org.opencv.core.*
import org.opencv.core.Point
import java.awt.*

/**
 * Demo Pad - Example application of abstract pad.
 * Controls mouse pointer position.
 *
 * @author JakeJMattson
 */
class MousePad(dimensions: Rect, color: Scalar): Pad(dimensions, color) {
	/**
	 * Java robot to control the mouse position
	 */
	private var mouseMover: Robot? = Robot()

	/*
	 * (non-Javadoc)
	 * @see
	 * io.github.JakeJMattson.touchcontrol.touchables.Touchable#performAction()
	 */
	override fun performAction() {
		if (hasDetection()) {
			//Convert point in range to point on screen
			val mousePoint = adjustPoint()

			//Move mouse pointer to point
			mouseMover!!.mouseMove(mousePoint.x.toInt(), mousePoint.y.toInt())
		}
	}

	/**
	 * Convert the internal detection point from a point on the Touchable to a
	 * point on the screen so the mouse can be moved anywhere on the display.
	 *
	 * @return Adjusted point
	 */
	private fun adjustPoint(): Point {
		//Determine ratio
		val widthAdjustment = SCREEN_WIDTH / dimensions.width
		val heightAdjustment = SCREEN_HEIGHT / dimensions.height

		//Adjust point
		val adjustedPoint = Point()
		adjustedPoint.x = detectionPoint!!.x * widthAdjustment
		adjustedPoint.y = detectionPoint!!.y * heightAdjustment

		return adjustedPoint
	}

	/*
	 * (non-Javadoc)
	 * @see io.github.JakeJMattson.touchcontrol.touchables.Pad#toString()
	 */
	override fun toString() = (super.toString()
			+ format("Screen height:", SCREEN_HEIGHT)
			+ format("Screen width:", SCREEN_WIDTH))

	companion object {
		private val SCREEN_WIDTH = Toolkit.getDefaultToolkit().screenSize.getWidth()
		private val SCREEN_HEIGHT = Toolkit.getDefaultToolkit().screenSize.getHeight()
	}
}