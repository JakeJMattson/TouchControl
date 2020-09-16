package me.jakejmattson.touchcontrol.touchables

import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import kotlin.math.ceil

/**
 * Abstract Slider - contains general slider behavior.
 *
 * @author JakeJMattson
 */
abstract class Slider protected constructor(dimensions: Rect, color: Scalar) : Touchable(dimensions, color) {
    /**
     * Number of sectors that the slider is divided into
     */
    protected var numOfDivisions: Int = DEFAULT_DIVISIONS
        set(value) {
            field = value.coerceIn(0..100)
        }

    /**
     * The size of a single division sector
     */
    private val divisionSize: Double
        get() = dimensions.height.toDouble() / numOfDivisions

    /**
     * The division in which the detection point is located in
     */
    protected var currentDivision: Int = 0

    /**
     * Whether or not the location line should have a label
     */
    private var isLabeled: Boolean = DEFAULT_VISIBILITY

    override fun updateDetectionPoint(filteredImage: Mat): Point? {
        super.updateDetectionPoint(filteredImage)

        if (hasDetection())
            currentDivision = ceil((detectionPoint!!.y - dimensions.y) / divisionSize).toInt()

        return detectionPoint
    }

    override fun drawOnto(image: Mat) {
        super.drawOnto(image)
        setSliderStatus(image)
    }

    private fun setSliderStatus(image: Mat) {
        //Calculate position of line
        val linePosition = divisionSize * currentDivision + dimensions.y

        //Draw status line
        Imgproc.line(image, Point(dimensions.x.toDouble(), linePosition),
            Point((dimensions.x + dimensions.width).toDouble(), linePosition), color, 3)

        if (isLabeled) {
            //Offset text to avoid collision with line and slider
            val textShift = if (numOfDivisions - currentDivision >= numOfDivisions / 2.0) 18 else -8

            //Calculate percentage to display
            val percent = (numOfDivisions - currentDivision) * (100 / numOfDivisions)

            val unit =
                when (numOfDivisions) {
                    100 -> "%"
                    else -> ""
                }

            //Draw text
            Imgproc.putText(image, percent.toString() + unit,
                Point((dimensions.x + 5).toDouble(), linePosition + textShift),
                Imgproc.FONT_HERSHEY_COMPLEX, 0.5, color)
        }
    }

    override fun toString() = super.toString() +
        format("Divisions:", numOfDivisions) +
        format("Is Labeled:", isLabeled)

    companion object {
        private const val DEFAULT_DIVISIONS = 100
        private const val DEFAULT_VISIBILITY = true
    }
}