import java.io.*;

import org.opencv.core.*;

public class VolumeSlider extends Slider
{
	private int volume;

	//Constructors
	public VolumeSlider(Rect sliderDim)
	{
		super(sliderDim);
	}

	public VolumeSlider(Rect sliderDim, Scalar color)
	{
		super(sliderDim, color);
	}

	//Setters

	//Getters

	//Class methods
	@Override
	public void performAction()
	{
		if (validateAction())
			if (volume >= 0 && volume <= numOfDivisions)
			{
				int newVolume = numOfDivisions - division;

				if (volume != newVolume)
					setVolume(newVolume);
			}
	}

	private void setVolume(int volume)
	{
		//Nircmd allows volume changing
		File nircmdPath = new File("./nircmd.exe");

		System.out.println("Volume set to: " + volume);

		//Calculate volume in nircmd terms
		double endVolume = 655.35 * volume;

		try
		{
			//Set volume
			this.volume = volume;
			Runtime.getRuntime().exec(nircmdPath.getCanonicalPath() + " setsysvolume " + endVolume);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public String toString()
	{
		return super.toString();
	}
}