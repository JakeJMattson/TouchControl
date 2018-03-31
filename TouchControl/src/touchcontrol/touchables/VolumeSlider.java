/**
 * Class Description:
 * Demo Slider - example application of abstract slider
 * Controls system volume
 */

package touchcontrol.touchables;

import java.io.*;

import org.opencv.core.*;

public class VolumeSlider extends Slider
{
	private int volume;

	//Constructors
	public VolumeSlider(Rect dimensions, Scalar color)
	{
		super(dimensions, color);
	}

	//Setters

	//Getters

	//Class methods
	@Override
	public void performAction()
	{
		if (hasDetection())
		{
			//Calculate volume
			int newVolume = (numOfDivisions - division) * (100 / numOfDivisions);

			//Avoid unnecessary calls
			if (volume != newVolume)
				setVolume(newVolume);
		}
	}

	private void setVolume(int volume)
	{
		//Nircmd allows volume changing
		File nircmd = new File("./nircmd/nircmd.exe");
		String nircmdPath = nircmd.getAbsolutePath();

		//Convert volume to nircmd standard
		double nircmdVolume = 655.35 * volume;

		//Build command to set system volume
		String command = nircmdPath + " setsysvolume " + nircmdVolume;

		//Set volume
		this.volume = volume;
		executeCommand(command);

		//Display message for debugging
		System.out.println("Volume set to: " + volume);
	}

	private void executeCommand(String command)
	{
		try
		{
			Runtime.getRuntime().exec(command);
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