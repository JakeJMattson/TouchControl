package me.jakejmattson.touchcontrol.touchables

import me.jakejmattson.touchcontrol.utils.*
import org.opencv.core.*
import org.opencv.core.Point
import java.awt.*

/**
 * Demo Pad - Example application of abstract pad.
 * Controls mouse pointer position.
 *
 * @author JakeJMattson
 */
class MousePad(dimensions: Rect, color: Scalar) : Pad(dimensions, color) {
    private var mouseMover: Robot = Robot()

    override fun performAction() {
        if (hasDetection()) {
            //Convert point in range to point on screen
            val mousePoint = adjustPoint()

            //Move mouse pointer to point
            mouseMover.mouseMove(mousePoint)
        }
    }

    /**
     * Convert the internal detection point from a point on the Touchable to a
     * point on the screen so the mouse can be moved anywhere on the display.
     *
     * @return Adjusted point
     */
    private fun adjustPoint(): Point {
        val ratioPoint = Point(SCREEN_WIDTH / dimensions.width, SCREEN_HEIGHT / dimensions.height)

        return detectionPoint!! * ratioPoint
    }

    override fun toString() = super.toString() +
        format("Screen height:", SCREEN_HEIGHT) +
        format("Screen width:", SCREEN_WIDTH)

    companion object {
        private val SCREEN_WIDTH = Toolkit.getDefaultToolkit().screenSize.getWidth()
        private val SCREEN_HEIGHT = Toolkit.getDefaultToolkit().screenSize.getHeight()
    }
}