import java.awt.*;

import org.opencv.core.*;
import org.opencv.core.Point;

public class MousePad extends Pad
{
	private Robot mouseMover;
	private double screenWidth;
	private double screenHeight;

	//Constructors
	public MousePad(Rect padDim)
	{
		super(padDim);
		init();
	}

	public MousePad(Rect padDim, Scalar color)
	{
		super(padDim, color);
		init();
	}

	private void init()
	{
		screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		createRobot();
	}

	//Setters
	public void setScreenHeight(double screenHeight)
	{
		this.screenHeight = screenHeight;
	}

	public void setScreenWidth(double screenWidth)
	{
		this.screenWidth = screenWidth;
	}

	public void setScreenDimensions(double screenHeight, double screenWidth)
	{
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
	}

	//Getters
	public double getScreenHeight()
	{
		return this.screenHeight;
	}

	public double getScreenWidth()
	{
		return this.screenWidth;
	}

	//Class methods
	@Override
	public void performAction()
	{
		//If a non-background point is found in range
		if (validateAction())
		{
			//Convert point in range to point on screen
			Point adjustedMousePoint = adjustPoint();

			moveMouse(adjustedMousePoint);
		}
	}

	private Point adjustPoint()
	{
		//Local variables
		Point adjustedPoint = new Point();
		double widthAdjustment = screenWidth / dimensions.width;
		double heightAdjustment = screenHeight / dimensions.height;

		//Adjust point
		adjustedPoint.x = detectionPoint.x * widthAdjustment;
		adjustedPoint.y = detectionPoint.y * heightAdjustment;

		return adjustedPoint;
	}

	private void moveMouse(Point point)
	{
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
		return super.toString() + String.format(TO_STRING_FORMAT, "Screen height:", this.screenHeight)
				+ String.format(TO_STRING_FORMAT, "Screen width:", this.screenWidth);
	}
}