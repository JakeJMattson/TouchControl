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
		Rect dimensions = grid.calculateDimensions(new Point(0, 0), new Point(0, 0));
		MousePad mousePad = new MousePad(dimensions, color);

		return new TouchableGroup(mousePad);
	}

	static TouchableGroup createPianoDemo(double cameraWidth, double cameraHeight, Scalar color)
	{
		int keyCount = 7;
		TouchableGroup group = new TouchableGroup();
		Grid grid = new Grid(keyCount, 5, cameraWidth, cameraHeight, PADDING);

		for (int i = 0; i < keyCount; i++)
		{
			Rect dimensions = grid.calculateDimensions(new Point(i, 1), new Point(i, 3));
			PianoKey key = new PianoKey(dimensions, color, (char) (i + 'A'));
			group.addComponent(key);
		}

		return group;
	}

	static TouchableGroup createVolumeDemo(double cameraWidth, double cameraHeight, Scalar color)
	{
		Grid grid = new Grid(100, 1, cameraWidth, cameraHeight, PADDING);
		Rect dimensions = grid.calculateDimensions(new Point(40, 0), new Point(60, 0));
		VolumeSlider volumeSlider = new VolumeSlider(dimensions, color);

		return new TouchableGroup(volumeSlider);
	}
}