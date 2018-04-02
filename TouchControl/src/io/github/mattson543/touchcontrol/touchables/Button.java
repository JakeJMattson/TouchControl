/**
 * Class Description:
 * Abstract Button - contains general (clickable) button behavior
 */

package io.github.mattson543.touchcontrol.touchables;

import org.opencv.core.*;

public abstract class Button extends Touchable
{
	protected boolean isBeingClicked;

	//Constructors
	protected Button(Rect dimensions, Scalar color)
	{
		super(dimensions, color);
		init();
	}

	private void init()
	{
		isBeingClicked = false;
	}

	//Setters

	//Getters

	//Class methods
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

	@Override
	public String toString()
	{
		return super.toString();
	}
}