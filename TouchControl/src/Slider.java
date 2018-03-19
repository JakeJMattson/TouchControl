import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

public abstract class Slider extends Touchable
{
	protected int numOfDivisions;
	protected int division;
	private boolean percentageVisible;
	private double divisionSize;

	private final static int DEFAULT_DIVISIONS = 100;
	private final static boolean DEFAULT_VISIBILITY = true;

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
		return this.numOfDivisions;
	}

	protected boolean isPercentageVisible()
	{
		return this.percentageVisible;
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
	protected void updateDetectionPoint(Mat filteredImage)
	{
		super.updateDetectionPoint(filteredImage);

		if (hasDetection())
			division = (int) Math.ceil(((detectionPoint.y - dimensions.y) / divisionSize));
	}

	@Override
	protected void drawOnto(Mat image)
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
			int textShift = (numOfDivisions - division >= (numOfDivisions / 2.0)) ? 18 : -8;

			//Calculate percentage to display
			int percent = (numOfDivisions - division) * (100 / numOfDivisions);

			//Draw text
			Imgproc.putText(rawImage, percent + "%", new Point(dimensions.x + 5, linePosition + textShift),
					Core.FONT_HERSHEY_COMPLEX, 0.5, color);
		}
	}

	@Override
	public String toString()
	{
		return super.toString() + String.format(TO_STRING_FORMAT, "Divisions:", this.numOfDivisions)
				+ String.format(TO_STRING_FORMAT, "Percent visible:", this.percentageVisible);
	}
}