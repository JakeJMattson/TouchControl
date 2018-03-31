/**
 * Class Description:
 * Abstract Slider - contains general slider behavior
 */

package touchcontrol.touchables;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

public abstract class Slider extends Touchable
{
	protected int numOfDivisions;
	protected int division;
	private boolean percentageVisible;
	private double divisionSize;

	private static int DEFAULT_DIVISIONS = 100;
	private static boolean DEFAULT_VISIBILITY = true;

	//Constructors
	protected Slider(Rect dimensions, Scalar color)
	{
		super(dimensions, color);
		init();
	}

	private void init()
	{
		numOfDivisions = DEFAULT_DIVISIONS;
		percentageVisible = DEFAULT_VISIBILITY;

		calculateDivisionSize();
	}

	//Setters
	protected void setNumOfDivisions(int numOfDivisions)
	{
		this.numOfDivisions = validateDivisions(numOfDivisions);

		calculateDivisionSize();
	}

	protected void setPercentageVisible(boolean percentageVisible)
	{
		this.percentageVisible = percentageVisible;
	}

	//Getters
	protected int getNumOfDivisions()
	{
		return numOfDivisions;
	}

	protected boolean isPercentageVisible()
	{
		return percentageVisible;
	}

	//Class methods
	private void calculateDivisionSize()
	{
		//Calculate the size of a single slider sector
		divisionSize = (double) dimensions.height / numOfDivisions;
	}

	private int validateDivisions(int divisions)
	{
		if (divisions < 0)
			divisions = 0;
		else if (divisions > 100)
			divisions = 100;

		return divisions;
	}

	@Override
	public Point updateDetectionPoint(Mat filteredImage)
	{
		//Update state
		super.updateDetectionPoint(filteredImage);

		if (hasDetection())
			division = (int) Math.ceil((detectionPoint.y - dimensions.y) / divisionSize);

		return detectionPoint;
	}

	@Override
	public void drawOnto(Mat image)
	{
		super.drawOnto(image);

		setSliderStatus(image);
	}

	private void setSliderStatus(Mat rawImage)
	{
		//Calculate position of line
		double linePosition = divisionSize * division + dimensions.y;

		//Draw status line
		Imgproc.line(rawImage, new Point(dimensions.x, linePosition),
				new Point(dimensions.x + dimensions.width, linePosition), color, 3);

		if (percentageVisible)
		{
			//Offset text to avoid collision with line and slider
			int textShift = numOfDivisions - division >= numOfDivisions / 2.0 ? 18 : -8;

			//Calculate percentage to display
			int percent = (numOfDivisions - division) * (100 / numOfDivisions);

			String unit = "";

			if (numOfDivisions == 100)
				unit = "%";

			//Draw text
			Imgproc.putText(rawImage, percent + unit, new Point(dimensions.x + 5, linePosition + textShift),
					Core.FONT_HERSHEY_COMPLEX, 0.5, color);
		}
	}

	@Override
	public String toString()
	{
		return super.toString()
				+ format("Divisions:", numOfDivisions)
				+ format("Percent visible:", percentageVisible);
	}
}