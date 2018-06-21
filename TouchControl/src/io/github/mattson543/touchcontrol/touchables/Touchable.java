package io.github.mattson543.touchcontrol.touchables;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

/**
 * Contains fundamental functions for a Touchable object of any kind
 *
 * @author mattson543
 */

public abstract class Touchable
{
	/**
	 * Location and size of the Touchable
	 */
	protected Rect dimensions;
	/**
	 * Color that the Touchable will be drawn in
	 */
	protected Scalar color;
	/**
	 * Farthest non-background pixel found within range
	 */
	protected Point detectionPoint;

	protected Touchable(Rect dimensions, Scalar color)
	{
		this.dimensions = dimensions;
		this.color = color;
	}

	public void setDimensions(Rect dimensions)
	{
		this.dimensions = dimensions;
	}

	public void setColor(Scalar color)
	{
		this.color = color;
	}

	public Rect getDimensions()
	{
		return dimensions;
	}

	public Scalar getColor()
	{
		return color;
	}

	/**
	 * Search through the range of the Touchable and find the first occurrence
	 * of a non-background pixel
	 *
	 * @param filteredImage
	 *            Binary image
	 * @return Detection point (for external use)
	 */
	public Point updateDetectionPoint(Mat filteredImage)
	{
		//Point of possible detection
		Point farthestPoint = null;

		//Range variables
		Point tl = dimensions.tl(); //Top left
		Point br = dimensions.br(); //Bottom right
		int tlX = (int) tl.x, tlY = (int) tl.y;
		int brX = (int) br.x, brY = (int) br.y;

		//Search for non-background pixel
		for (int y = tlY; y < brY; y++)
		{
			for (int x = brX; x >= tlX; x--)
			{
				//Get pixel at coordinate
				double[] pixelColor = filteredImage.get(y, x);

				//Check pixel for non-background coloring
				if (pixelColor[0] != 0.0)
				{
					farthestPoint = new Point(x, y);
					break;
				}
			}
			if (farthestPoint != null)
				break;
		}

		//Save detection
		detectionPoint = farthestPoint;

		return detectionPoint;
	}

	/**
	 * Determine whether or not the current detection point is null
	 *
	 * @return Detection status
	 */
	protected boolean hasDetection()
	{
		return detectionPoint != null;
	}

	/**
	 * Draw Touchable onto image
	 *
	 * @param image
	 *            Matrix to draw the Touchable object onto
	 */
	public void drawOnto(Mat image)
	{
		if (image != null)
		{
			//Draw component
			Imgproc.rectangle(image, dimensions.tl(), dimensions.br(), color, 3);

			//Draw circle around detection
			if (hasDetection())
				Imgproc.circle(image, detectionPoint, 10, color);
		}
	}

	/**
	 * Enforce method requirement on subclasses
	 */
	public abstract void performAction();

	/**
	 * Formatter to create a uniform toString() for all Touchable objects
	 *
	 * @param name
	 *            Name of field being formatted
	 * @param data
	 *            Data of field being formatted
	 * @return Formatted string
	 */
	protected <T> String format(String name, T data)
	{
		final String TO_STRING_FORMAT = "   %-17s %-1s%n";

		//Generic toString formatter
		return String.format(TO_STRING_FORMAT, name, data);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return this.getClass().getSimpleName()
				+ " (" + super.toString() + "):" + System.lineSeparator()
				+ format("Dimensions:", dimensions)
				+ format("Color:", color);
	}
}