/**
 * Class Description:
 * Abstract Pad - contains general pad behavior
 */

package io.github.mattson543.touchcontrol.touchables;

import org.opencv.core.*;

public abstract class Pad extends Touchable
{
	//Constructors
	protected Pad(Rect dimensions, Scalar color)
	{
		super(dimensions, color);
	}

	//Setters

	//Getters

	//Class methods
	@Override
	public String toString()
	{
		return super.toString();
	}
}