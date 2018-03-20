import org.opencv.core.*;

public abstract class ToggleButton extends Button
{
	protected boolean isOn;
	private boolean hasSwitched;

	//Constructors
	protected ToggleButton(Rect dimensions, Scalar color)
	{
		super(dimensions, color);
		isOn = false;
		hasSwitched = false;
	}

	//Setters

	//Getters

	//Class methods
	@Override
	protected void updateDetectionPoint(Mat filteredImage)
	{
		boolean wasClicked = isBeingClicked;

		super.updateDetectionPoint(filteredImage);

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
	}

	@Override
	public String toString()
	{
		return super.toString() + String.format(TO_STRING_FORMAT, "Toggled on:", isOn);
	}
}