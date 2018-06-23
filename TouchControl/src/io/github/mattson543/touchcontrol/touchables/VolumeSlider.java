package io.github.mattson543.touchcontrol.touchables;

import java.io.*;

import org.opencv.core.*;

/**
 * Demo Slider - Example application of abstract slider.
 * Controls system volume.
 *
 * @author mattson543
 */
public class VolumeSlider extends Slider
{
	/**
	 * Internal representation of current system volume
	 */
	private int previousVolume;

	public VolumeSlider(Rect dimensions, Scalar color)
	{
		super(dimensions, color);
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
			//Calculate volume
			int newVolume = (numOfDivisions - division) * (100 / numOfDivisions);

			//Avoid unnecessary calls
			if (previousVolume != newVolume)
				setVolume(newVolume);
		}
	}

	/**
	 * Set system volume.
	 *
	 * @param volume
	 *            New target system volume
	 */
	private void setVolume(int volume)
	{
		//Nircmd allows volume changing
		String nircmdPath = new File("nircmd/nircmd.exe").getAbsolutePath();

		//Convert volume to nircmd standard
		double nircmdVolume = 655.35 * volume;

		//Build command to set system volume
		String command = nircmdPath + " setsysvolume " + nircmdVolume;

		//Set volume
		previousVolume = volume;
		executeCommand(command);

		//Display message for debugging
		System.out.println("Volume set to: " + volume);
	}

	/**
	 * Execute a system command (Call nircmd from command line).
	 *
	 * @param command
	 *            System command
	 */
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

	/*
	 * (non-Javadoc)
	 * @see io.github.mattson543.touchcontrol.touchables.Slider#toString()
	 */
	@Override
	public String toString()
	{
		return super.toString();
	}
}