import org.opencv.core.*;

public abstract class Pad extends Touchable
{
	//Constructors
	public Pad(Rect padDim)
	{
		super(padDim);
	}

	public Pad(Rect padDim, Scalar color)
	{
		super(padDim, color);
	}

	//Class methods
	@Override
	public String toString()
	{
		return super.toString();
	}
}