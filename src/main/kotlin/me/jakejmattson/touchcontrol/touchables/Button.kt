package me.jakejmattson.touchcontrol.touchables

import org.opencv.core.*

/**
 * Abstract Button - contains general (clickable) button behavior.
 *
 * @author JakeJMattson
 */
abstract class Button protected constructor(dimensions: Rect, color: Scalar) : Touchable(dimensions, color) {

    protected var isBeingClicked: Boolean = false

    override fun updateDetectionPoint(filteredImage: Mat): Point? {
        super.updateDetectionPoint(filteredImage)

        //Determine if button is currently detecting non-background pixel
        if (hasDetection() && !isBeingClicked)
            isBeingClicked = true
        else if (!hasDetection() && isBeingClicked)
            isBeingClicked = false

        return detectionPoint
    }
}