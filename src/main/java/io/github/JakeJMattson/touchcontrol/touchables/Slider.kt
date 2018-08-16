package io.github.JakeJMattson.touchcontrol.touchables

import org.opencv.core.*
import org.opencv.imgproc.Imgproc

/**
 * Abstract Slider - contains general slider behavior.
 *
 * @author JakeJMattson
 */
abstract class Slider protected constructor(dimensions: Rect, color: Scalar): Touchable(dimensions, color) {
	/**
	 * Number of sectors that the slider is divided into
	 */
	protected var numOfDivisions: Int = 0
		set (value) {
			field = validateDivisions(value)
			calculateDivisionSize()
		}

	/**
	 * The size of a single division sector
	 */
	private var divisionSize: Double = 0.toDouble()
	/**
	 * The division in which the detection point is located in
	 */
	protected var division: Int = 0
	/**
	 * Whether or not the location line should have a label
	 */
	private var isLabeled: Boolean = false

	init {
		numOfDivisions = DEFAULT_DIVISIONS
		isLabeled = DEFAULT_VISIBILITY

		calculateDivisionSize()
	}

	/**
	 * Determine the size of each division sector.
	 */
	private fun calculateDivisionSize() {
		//Calculate the size of a single slider sector
		this.divisionSize = dimensions.height.toDouble() / numOfDivisions
	}

	/**
	 * Confine number of divisions to range.
	 *
	 * @param divisions
	 * Attempted value
	 * @return Value within range
	 */
	private fun validateDivisions(attemptedVal: Int): Int {

		var divisions = attemptedVal

		if (attemptedVal < 0)
			divisions = 0
		else if (attemptedVal > 100)
			divisions = 100

		return divisions
	}

	/*
	 * (non-Javadoc)
	 * @see io.github.JakeJMattson.touchcontrol.touchables.Touchable
	 * #updateDetectionPoint(org.opencv.core.Mat)
	 */
	override fun updateDetectionPoint(filteredImage: Mat): Point? {
		//Update state
		super.updateDetectionPoint(filteredImage)

		if (hasDetection())
			division = Math.ceil((detectionPoint!!.y - dimensions.y) / divisionSize).toInt()

		return detectionPoint
	}

	/*
	 * (non-Javadoc)
	 * @see io.github.JakeJMattson.touchcontrol.touchables.Touchable
	 * #drawOnto(org.opencv.core.Mat)
	 */
	override fun drawOnto(image: Mat?) {
		super.drawOnto(image)
		setSliderStatus(image)
	}

	/**
	 * Draw status line and label.
	 *
	 * @param image
	 * Matrix
	 */
	private fun setSliderStatus(image: Mat?) {
		//Calculate position of line
		val linePosition = divisionSize * division + dimensions.y

		//Draw status line
		Imgproc.line(image, Point(dimensions.x.toDouble(), linePosition),
			Point((dimensions.x + dimensions.width).toDouble(), linePosition), color, 3)

		if (isLabeled) {
			//Offset text to avoid collision with line and slider
			val textShift = if (numOfDivisions - division >= numOfDivisions / 2.0) 18 else -8

			//Calculate percentage to display
			val percent = (numOfDivisions - division) * (100 / numOfDivisions)

			var unit = ""

			if (numOfDivisions == 100)
				unit = "%"

			//Draw text
			Imgproc.putText(image, percent.toString() + unit,
				Point((dimensions.x + 5).toDouble(), linePosition + textShift),
				Core.FONT_HERSHEY_COMPLEX, 0.5, color)
		}
	}

	override fun toString(): String {
		return (super.toString()
				+ format("Divisions:", numOfDivisions)
				+ format("Is Labeled:", isLabeled))
	}

	companion object {
		private const val DEFAULT_DIVISIONS = 100
		private const val DEFAULT_VISIBILITY = true
	}
}