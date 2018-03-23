/**
 * Class Description:
 * Abstract Button - contains general (clickable) button behavior
 */

package touchcontrol.touchables;

import org.opencv.core.*;

public abstract class Button extends Touchable
{
	protected boolean isBeingClicked;

	//Constructors
	protected Button(Rect dimensions, Scalar color)
	{
		super(dimensions, color);
		isBeingClicked = false;
	}

	//Setters

	//Getters
	protected boolean isBeingClicked()
	{
		return isBeingClicked;
	}

	//Class methods
	@Override
	public void updateDetectionPoint(Mat filteredImage)
	{
		//Update state
		super.updateDetectionPoint(filteredImage);

		//Determine if button is currently detecting non-background pixel
		if (hasDetection() && !isBeingClicked)
			isBeingClicked = true;
		else if (!hasDetection() && isBeingClicked)
			isBeingClicked = false;
	}

	@Override
	public String toString()
	{
		return super.toString();
	}
}