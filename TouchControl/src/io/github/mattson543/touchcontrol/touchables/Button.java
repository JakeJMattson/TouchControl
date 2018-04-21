package io.github.mattson543.touchcontrol.touchables;

import org.opencv.core.*;

/**
 * Abstract Button - contains general (clickable) button behavior
 *
 * @author mattson543
 */
public abstract class Button extends Touchable
{
	/**
	 * Whether or not a non-background pixel is currently being detected
	 */
	protected boolean isBeingClicked;

	protected Button(Rect dimensions, Scalar color)
	{
		super(dimensions, color);
		isBeingClicked = false;
	}

	/*
	 * (non-Javadoc)
	 * @see io.github.mattson543.touchcontrol.touchables.Touchable#
	 * updateDetectionPoint(org.opencv.core.Mat)
	 */
	@Override
	public Point updateDetectionPoint(Mat filteredImage)
	{
		//Update state
		super.updateDetectionPoint(filteredImage);

		//Determine if button is currently detecting non-background pixel
		if (hasDetection() && !isBeingClicked)
			isBeingClicked = true;
		else if (!hasDetection() && isBeingClicked)
			isBeingClicked = false;

		return detectionPoint;
	}

	/*
	 * (non-Javadoc)
	 * @see io.github.mattson543.touchcontrol.touchables.Touchable#toString()
	 */
	@Override
	public String toString()
	{
		return super.toString();
	}
}