package io.github.JakeJMattson.touchcontrol.touchables;

import org.opencv.core.*;

/**
 * Abstract Pad - contains general pad behavior.
 *
 * @author JakeJMattson
 */
public abstract class Pad extends Touchable
{
	protected Pad(Rect dimensions, Scalar color)
	{
		super(dimensions, color);
	}

	/*
	 * (non-Javadoc)
	 * @see io.github.JakeJMattson.touchcontrol.touchables.Touchable#toString()
	 */
	@Override
	public String toString()
	{
		return super.toString();
	}
}