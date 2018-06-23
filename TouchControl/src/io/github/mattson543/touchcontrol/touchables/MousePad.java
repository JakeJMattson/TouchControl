package io.github.mattson543.touchcontrol.touchables;

import java.awt.*;

import org.opencv.core.*;
import org.opencv.core.Point;

/**
 * Demo Pad - Example application of abstract pad.
 * Controls mouse pointer position.
 *
 * @author mattson543
 */
public class MousePad extends Pad
{
	/**
	 * Java robot to control the mouse position
	 */
	private Robot mouseMover;

	private final double SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private final double SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	public MousePad(Rect dimensions, Scalar color)
	{
		super(dimensions, color);
		init();
	}

	/**
	 * Initialize fields.
	 */
	private void init()
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

	/*
	 * (non-Javadoc)
	 * @see
	 * io.github.mattson543.touchcontrol.touchables.Touchable#performAction()
	 */
	@Override
	public void performAction()
	{
		if (hasDetection())
		{
			//Convert point in range to point on screen
			Point mousePoint = adjustPoint();

			//Move mouse pointer to point
			mouseMover.mouseMove((int) mousePoint.x, (int) mousePoint.y);
		}
	}

	/**
	 * Convert the internal detection point from a point on the Touchable to a
	 * point on the screen so the mouse can be moved anywhere on the display.
	 *
	 * @return Adjusted point
	 */
	private Point adjustPoint()
	{
		//Determine ratio
		double widthAdjustment = SCREEN_WIDTH / dimensions.width;
		double heightAdjustment = SCREEN_HEIGHT / dimensions.height;

		//Adjust point
		Point adjustedPoint = new Point();
		adjustedPoint.x = detectionPoint.x * widthAdjustment;
		adjustedPoint.y = detectionPoint.y * heightAdjustment;

		return adjustedPoint;
	}

	/*
	 * (non-Javadoc)
	 * @see io.github.mattson543.touchcontrol.touchables.Pad#toString()
	 */
	@Override
	public String toString()
	{
		return super.toString()
				+ format("Screen height:", SCREEN_HEIGHT)
				+ format("Screen width:", SCREEN_WIDTH);
	}
}