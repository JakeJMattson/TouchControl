import java.awt.*;

import org.opencv.core.*;
import org.opencv.core.Point;

public class MousePad extends Pad
{
	private Robot mouseMover;

	private final double SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private final double SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	//Constructors
	public MousePad(Rect dimensions)
	{
		this(dimensions, DEFAULT_COLOR);
	}

	public MousePad(Rect dimensions, Scalar color)
	{
		super(dimensions, color);
		init();
	}

	private void init()
	{
		createRobot();
	}

	//Setters

	//Getters

	//Class methods
	@Override
	public void performAction()
	{
		if (hasDetection())
		{
			//Convert point in range to point on screen
			Point adjustedMousePoint = adjustPoint();

			//Move mouse pointer to point
			moveMouse(adjustedMousePoint);
		}
	}

	private Point adjustPoint()
	{
		//Local variables
		Point adjustedPoint = new Point();
		double widthAdjustment = SCREEN_WIDTH / dimensions.width;
		double heightAdjustment = SCREEN_HEIGHT / dimensions.height;

		//Adjust point
		adjustedPoint.x = detectionPoint.x * widthAdjustment;
		adjustedPoint.y = detectionPoint.y * heightAdjustment;

		return adjustedPoint;
	}

	private void moveMouse(Point point)
	{
		//Use robot to move mouse
		mouseMover.mouseMove((int) point.x, (int) point.y);
	}

	private void createRobot()
	{
		try
		{
			mouseMover = new Robot();
		}
		catch (AWTException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public String toString()
	{
		return super.toString() + String.format(TO_STRING_FORMAT, "Screen height:", this.SCREEN_HEIGHT)
				+ String.format(TO_STRING_FORMAT, "Screen width:", this.SCREEN_WIDTH);
	}
}