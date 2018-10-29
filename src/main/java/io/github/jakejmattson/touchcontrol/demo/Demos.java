package io.github.jakejmattson.touchcontrol.demo;

import io.github.jakejmattson.touchcontrol.touchables.*;
import io.github.jakejmattson.touchcontrol.utils.*;
import org.opencv.core.*;

final class Demos
{
	private static final int PADDING = 10;

	private Demos()
	{}

	static TouchableGroup createMouseDemo(double cameraWidth, double cameraHeight, Scalar color)
	{
		Grid grid = new Grid(1, 1, cameraWidth, cameraHeight, PADDING);
		MousePad mousePad = new MousePad(new Rect(), color);
		grid.align(mousePad, new Point(0, 0), new Point(0, 0));

		return new TouchableGroup(mousePad);
	}

	static TouchableGroup createPianoDemo(double cameraWidth, double cameraHeight, Scalar color)
	{
		int keyCount = 7;
		TouchableGroup group = new TouchableGroup();
		Grid grid = new Grid(keyCount, 5, cameraWidth, cameraHeight, PADDING);

		for (int i = 0; i < keyCount; i++)
		{
			PianoKey key = new PianoKey(new Rect(), color, (char) (i + 'A'));
			grid.align(key, new Point(i, 1), new Point(i, 3));
			group.addComponent(key);
		}

		return group;
	}

	static TouchableGroup createVolumeDemo(double cameraWidth, double cameraHeight, Scalar color)
	{
		Grid grid = new Grid(100, 1, cameraWidth, cameraHeight, PADDING);
		VolumeSlider volumeSlider = new VolumeSlider(new Rect(), color);
		grid.align(volumeSlider, new Point(40, 0), new Point(60, 0));

		return new TouchableGroup(volumeSlider);
	}
}