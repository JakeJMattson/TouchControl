import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

public abstract class Slider extends Touchable
{
	protected int numOfDivisions;
	private boolean percentageVisible;
	protected int division;
	private double divisionSize;

	final static int DEFAULT_DIVISIONS = 100;

	//Constructors
	public Slider(Rect sliderDim)
	{
		super(sliderDim);
		init(DEFAULT_DIVISIONS);
	}

	public Slider(Rect sliderDim, Scalar color)
	{
		super(sliderDim, color);
		init(DEFAULT_DIVISIONS);
	}

	public Slider(Rect dimensions, boolean percentageVisible)
	{
		super(dimensions);
		this.percentageVisible = percentageVisible;
		init(DEFAULT_DIVISIONS);
	}

	public Slider(Rect dimensions, Scalar color, int numOfDivisions, boolean percentageVisible)
	{
		super(dimensions, color);
		this.numOfDivisions = numOfDivisions;
		this.percentageVisible = percentageVisible;
		init(numOfDivisions);
	}

	private void init(int divisions)
	{
		numOfDivisions = divisions;

		calculateDivisionSize();
	}

	//Setters
	public void setNumOfDivisions(int numOfDivisions)
	{
		this.numOfDivisions = numOfDivisions;

		calculateDivisionSize();
	}

	public void setPercentageVisible(boolean percentageVisible)
	{
		this.percentageVisible = percentageVisible;
	}

	//Getters
	public int getNumOfDivisions()
	{
		return this.numOfDivisions;
	}

	public boolean isPercentageVisible()
	{
		return this.percentageVisible;
	}

	private void calculateDivisionSize()
	{
		divisionSize = (double) dimensions.height / numOfDivisions;
	}

	@Override
	public void updateDetectionPoint(Mat filteredImage)
	{
		super.updateDetectionPoint(filteredImage);

		//TODO relative position
		if (detectionPoint != null)
			division = (int) Math.ceil(((detectionPoint.y - dimensions.y) / divisionSize));
	}

	@Override
	public void drawOnto(Mat image)
	{
		super.drawOnto(image);

		setSliderStatus(image);
	}

	private void setSliderStatus(Mat rawImage)
	{
		if (detectionPoint != null)
		{
			//Local variables
			Scalar infoColor = new Scalar(0, 255, 0); //Scalar is BGR

			//Determine status
			int shift = (100 - division >= 50) ? 18 : -8;

			double yPosition = divisionSize * division + dimensions.y;

			//Draw status
			Imgproc.line(rawImage, new Point(dimensions.x, yPosition),
					new Point(dimensions.x + dimensions.width, yPosition), infoColor, 3);
			Imgproc.putText(rawImage, 100 - division + "%", new Point(dimensions.x + 5, yPosition + shift),
					Core.FONT_HERSHEY_COMPLEX, 0.5, infoColor);
		}
	}

	@Override
	public String toString()
	{
		return super.toString() + String.format(TO_STRING_FORMAT, "Divisions:", this.numOfDivisions)
				+ String.format(TO_STRING_FORMAT, "Percent visible:", this.percentageVisible);
	}
}