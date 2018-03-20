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
		return this.isBeingClicked;
	}

	//Class methods
	@Override
	protected void updateDetectionPoint(Mat filteredImage)
	{
		super.updateDetectionPoint(filteredImage);

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