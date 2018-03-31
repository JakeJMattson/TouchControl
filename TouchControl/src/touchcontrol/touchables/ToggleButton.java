/**
 * Class Description:
 * Abstract Button - contains general (toggle) button behavior
 */

package touchcontrol.touchables;

import org.opencv.core.*;

public abstract class ToggleButton extends Button
{
	protected boolean isOn;
	private boolean hasSwitched;

	//Constructors
	protected ToggleButton(Rect dimensions, Scalar color)
	{
		super(dimensions, color);
		init();
	}

	private void init()
	{
		isOn = false;
		hasSwitched = false;
	}

	//Setters

	//Getters

	//Class methods
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
				isOn = !isOn;
				hasSwitched = true;
			}
		}
		else
			hasSwitched = false;

		return detectionPoint;
	}

	@Override
	public String toString()
	{
		return super.toString() + format("Toggled on:", isOn);
	}
}