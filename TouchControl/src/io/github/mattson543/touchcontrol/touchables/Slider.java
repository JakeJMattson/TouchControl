package io.github.mattson543.touchcontrol.touchables;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

/**
 * Abstract Slider - contains general slider behavior.
 *
 * @author mattson543
 */
public abstract class Slider extends Touchable
{
	/**
	 * Number of sectors that the slider is divided into
	 */
	protected int numOfDivisions;
	/**
	 * The size of a single division sector
	 */
	private double divisionSize;
	/**
	 * The division in which the detection point is located in
	 */
	protected int division;
	/**
	 * Whether or not the location line should have a label
	 */
	private boolean shouldLabel;

	private static final int DEFAULT_DIVISIONS = 100;
	private static final boolean DEFAULT_VISIBILITY = true;

	protected Slider(Rect dimensions, Scalar color)
	{
		super(dimensions, color);

		numOfDivisions = DEFAULT_DIVISIONS;
		shouldLabel = DEFAULT_VISIBILITY;

		calculateDivisionSize();
	}

	protected void setNumOfDivisions(int numOfDivisions)
	{
		this.numOfDivisions = validateDivisions(numOfDivisions);

		calculateDivisionSize();
	}

	protected void showLabel(boolean shouldLabel)
	{
		this.shouldLabel = shouldLabel;
	}

	protected int getNumOfDivisions()
	{
		return numOfDivisions;
	}

	protected boolean isLabeled()
	{
		return shouldLabel;
	}

	/**
	 * Determine the size of each division sector.
	 */
	private void calculateDivisionSize()
	{
		//Calculate the size of a single slider sector
		divisionSize = (double) dimensions.height / numOfDivisions;
	}

	/**
	 * Confine number of divisions to range.
	 *
	 * @param divisions
	 *            Attempted value
	 * @return Value within range
	 */
	private int validateDivisions(int divisions)
	{
		if (divisions < 0)
			divisions = 0;
		else if (divisions > 100)
			divisions = 100;

		return divisions;
	}

	/*
	 * (non-Javadoc)
	 * @see io.github.mattson543.touchcontrol.touchables.Touchable
	 * #updateDetectionPoint(org.opencv.core.Mat)
	 */
	@Override
	public Point updateDetectionPoint(Mat filteredImage)
	{
		//Update state
		super.updateDetectionPoint(filteredImage);

		if (hasDetection())
			division = (int) Math.ceil((detectionPoint.y - dimensions.y) / divisionSize);

		return detectionPoint;
	}

	/*
	 * (non-Javadoc)
	 * @see io.github.mattson543.touchcontrol.touchables.Touchable
	 * #drawOnto(org.opencv.core.Mat)
	 */
	@Override
	public void drawOnto(Mat image)
	{
		super.drawOnto(image);

		setSliderStatus(image);
	}

	/**
	 * Draw status line and label.
	 *
	 * @param image
	 *            Matrix
	 */
	private void setSliderStatus(Mat image)
	{
		//Calculate position of line
		double linePosition = divisionSize * division + dimensions.y;

		//Draw status line
		Imgproc.line(image, new Point(dimensions.x, linePosition),
				new Point(dimensions.x + dimensions.width, linePosition), color, 3);

		if (shouldLabel)
		{
			//Offset text to avoid collision with line and slider
			int textShift = numOfDivisions - division >= numOfDivisions / 2.0 ? 18 : -8;

			//Calculate percentage to display
			int percent = (numOfDivisions - division) * (100 / numOfDivisions);

			String unit = "";

			if (numOfDivisions == 100)
				unit = "%";

			//Draw text
			Imgproc.putText(image, percent + unit, new Point(dimensions.x + 5, linePosition + textShift),
					Core.FONT_HERSHEY_COMPLEX, 0.5, color);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see io.github.mattson543.touchcontrol.touchables.Touchable#toString()
	 */
	@Override
	public String toString()
	{
		return super.toString()
				+ format("Divisions:", numOfDivisions)
				+ format("Is Labeled:", shouldLabel);
	}
}