import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

public abstract class Touchable
{
	protected Rect dimensions;
	private Scalar color;
	protected Point detectionPoint;
	private Point previousDetection;

	protected final String TO_STRING_FORMAT = "   %-17s %-1s%n";
	private final static Scalar DEFAULT_COLOR = new Scalar(255, 0, 0);

	//Constructors
	public Touchable(Rect dimensions)
	{
		this(dimensions, DEFAULT_COLOR);
	}

	public Touchable(Rect dimensions, Scalar color)
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
		return this.dimensions;
	}

	public Scalar getColor()
	{
		return this.color;
	}

	public Point getDetectionPoint()
	{
		return this.detectionPoint;
	}

	public void updateDetectionPoint(Mat filteredImage)
	{
		//Local variables
		Rect range = this.dimensions;
		Point farthestPoint = new Point();
		boolean doneSearching = false;

		//Starting in the lower right corner, search for non-background pixel
		outerLoop: for (int y = range.y; y < range.y + range.height; y++)
		{
			innerLoop: for (int x = range.x + range.width; x >= range.x; x--)
			{
				//Get pixel at coordinate
				double[] pixelColor = filteredImage.get(y, x);

				//Check pixel for non-background coloring
				if (pixelColor[0] != 255.0)
				{
					farthestPoint.x = x;
					farthestPoint.y = y;
					doneSearching = true;
				}

				if (doneSearching)
					break innerLoop;
			}
			if (doneSearching)
				break outerLoop;
		}

		//No non-background pixel found
		if (farthestPoint.x == range.x || farthestPoint.y == range.y || farthestPoint.x == 0 || farthestPoint.y == 0)
			farthestPoint = null;

		previousDetection = detectionPoint;
		detectionPoint = farthestPoint;
	}

	protected boolean validateAction()
	{
		boolean detected = false, changed = false;

		detected = (detectionPoint != null);

		if (detected)
			changed = (!detectionPoint.equals(previousDetection));

		return detected && changed;
	}

	public Point calculateRelativePoint()
	{
		Point relative = new Point();

		return relative;
	}

	public void drawOnto(Mat image)
	{
		//Local Variables
		Point upperLeft;
		Point lowerRight;

		//Determine state
		upperLeft = new Point(dimensions.x, dimensions.y);
		lowerRight = new Point(dimensions.x + dimensions.width, dimensions.y + dimensions.height);

		if (image != null)
		{
			//Draw components
			Imgproc.rectangle(image, upperLeft, lowerRight, color, 3);

			drawDetection(image);
		}
	}

	private void drawDetection(Mat image)
	{
		if (detectionPoint != null)
			Imgproc.circle(image, detectionPoint, 10, new Scalar(255, 0, 255));
	}

	abstract void performAction();

	@Override
	public String toString()
	{
		return this.getClass().getSimpleName() + " (" + super.toString() + "): \n"
				+ String.format(TO_STRING_FORMAT, "Dimensions:", this.dimensions)
				+ String.format(TO_STRING_FORMAT, "Color:", this.color);
	}
}