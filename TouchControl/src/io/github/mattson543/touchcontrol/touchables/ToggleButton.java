package io.github.mattson543.touchcontrol.touchables;

import org.opencv.core.*;

/**
 * Abstract Button - contains general (toggle) button behavior.
 *
 * @author mattson543
 */
public abstract class ToggleButton extends Button
{
	/**
	 * The current state of the button
	 */
	protected boolean isToggledOn;
	/**
	 * Whether or not the button has already switched states internally
	 */
	private boolean hasSwitched;

	protected ToggleButton(Rect dimensions, Scalar color)
	{
		super(dimensions, color);
		isToggledOn = false;
		hasSwitched = false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * io.github.mattson543.touchcontrol.touchables.Button
	 * #updateDetectionPoint(org.opencv.core.Mat)
	 */
	@Override
	public Point updateDetectionPoint(Mat filteredImage)
	{
		//Save previous state
		boolean wasClicked = isBeingClicked;

		//Update state
		super.updateDetectionPoint(filteredImage);

		//Toggle state
		if (isBeingClicked && !wasClicked)
		{
			if (!hasSwitched)
			{
				isToggledOn = !isToggledOn;
				hasSwitched = true;
			}
		}
		else
			hasSwitched = false;

		return detectionPoint;
	}

	/*
	 * (non-Javadoc)
	 * @see io.github.mattson543.touchcontrol.touchables.Button#toString()
	 */
	@Override
	public String toString()
	{
		return super.toString() + format("Toggled on:", isToggledOn);
	}
}