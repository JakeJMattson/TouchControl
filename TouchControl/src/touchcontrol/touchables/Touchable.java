/**
 * Class Description:
 * Abstract Touchable - contains fundamental functions for a Touchable object of any kind
 */

package touchcontrol.touchables;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

public abstract class Touchable
{
	protected Rect dimensions;
	protected Scalar color;
	protected Point detectionPoint;

	private final String TO_STRING_FORMAT = "   %-17s %-1s%n";
	protected static Scalar DEFAULT_COLOR = new Scalar(0, 255, 0);

	//Constructors
	protected Touchable(Rect dimensions, Scalar color)
	{
		this.dimensions = dimensions;
		this.color = color;
	}

	//Setters
	public void setDimensions(Rect dimensions)
	{
		this.dimensions = dimensions;
	}

	public void setColor(Scalar color)
	{
		this.color = color;
	}

	//Getters
	public Rect getDimensions()
	{
		return dimensions;
	}

	public Scalar getColor()
	{
		return color;
	}

	public void updateDetectionPoint(Mat filteredImage)
	{
		//Point of possible detection
		Point farthestPoint = new Point();

		//Control flow
		boolean doneSearching = false;

		//Search for non-background pixel
		for (int y = dimensions.y; y < dimensions.y + dimensions.height; y++)
		{
			for (int x = dimensions.x + dimensions.width; x >= dimensions.x; x--)
			{
				//Get pixel at coordinate
				double[] pixelColor = filteredImage.get(y, x);

				//Check pixel for non-background coloring
				if (pixelColor[0] != 255.0)
				{
					farthestPoint.x = x;
					farthestPoint.y = y;
					doneSearching = true;

					break;
				}
			}
			if (doneSearching)
				break;
		}

		//No detections
		if (farthestPoint.y == 0)
			farthestPoint = null;

		//Save detection
		detectionPoint = farthestPoint;
	}

	protected boolean hasDetection()
	{
		boolean isValid = false;

		//Validate detection
		if (detectionPoint != null)
			isValid = true;

		return isValid;
	}

	public void drawOnto(Mat image)
	{
		if (image != null)
		{
			//Determine rectangle points
			Point upperLeft = new Point(dimensions.x, dimensions.y);
			Point lowerRight = new Point(dimensions.x + dimensions.width, dimensions.y + dimensions.height);

			//Draw component
			Imgproc.rectangle(image, upperLeft, lowerRight, color, 3);

			//Draw circle around detection
			drawDetection(image);
		}
	}

	private void drawDetection(Mat image)
	{
		if (hasDetection())
			Imgproc.circle(image, detectionPoint, 10, color);
	}

	//Enforce method
	public abstract void performAction();

	protected <T> String format(String label, T data)
	{
		//Generic toString formatter
		return String.format(TO_STRING_FORMAT, label, data);
	}

	@Override
	public String toString()
	{
		return this.getClass().getSimpleName()
				+ " (" + super.toString() + "):\n"
				+ format("Dimensions:", dimensions)
				+ format("Color:", color);
	}
}