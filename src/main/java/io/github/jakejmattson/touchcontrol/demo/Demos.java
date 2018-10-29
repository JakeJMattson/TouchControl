package io.github.jakejmattson.touchcontrol.demo;

import io.github.jakejmattson.touchcontrol.touchables.*;
import io.github.jakejmattson.touchcontrol.utils.TouchableGroup;
import org.opencv.core.*;

final class Demos
{
	private static final int PADDING = 10;

	private Demos()
	{}

	static TouchableGroup createMouseDemo(double cameraWidth, double cameraHeight, Scalar color)
	{
		Point topLeft = new Point(PADDING, PADDING);
		Point bottomRight = new Point(cameraWidth - PADDING, cameraHeight - PADDING);
		Rect mousePadRect = new Rect(topLeft, bottomRight);

		MousePad mousePad = new MousePad(mousePadRect, color);
		return new TouchableGroup(mousePad);
	}

	static TouchableGroup createPianoDemo(double cameraWidth, double cameraHeight, Scalar color)
	{
		TouchableGroup group = new TouchableGroup();
		char[] notes = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};

		for (int i = 0; i < notes.length; i++)
		{
			double x1 = (double) i / notes.length * cameraWidth;
			double x2 = (double) (i + 1) / notes.length * cameraWidth;
			double y1 = 1 / 4.0 * cameraHeight;
			double y2 = 3 / 4.0 * cameraHeight;

			Point topLeft = new Point(x1 + PADDING, y1 + PADDING);
			Point bottomRight = new Point(x2 - PADDING, y2 - PADDING);
			Rect keyRect = new Rect(topLeft, bottomRight);

			PianoKey key = new PianoKey(keyRect, color, notes[i]);
			group.addComponent(key);
		}

		return group;
	}

	static TouchableGroup createVolumeDemo(double cameraWidth, double cameraHeight, Scalar color)
	{
		Point topLeft = new Point(0.4 * cameraWidth, PADDING);
		Point bottomRight = new Point(0.6 * cameraWidth - PADDING, cameraHeight - PADDING);
		Rect volumeSliderRect = new Rect(topLeft, bottomRight);

		VolumeSlider volumeSlider = new VolumeSlider(volumeSliderRect, color);
		return new TouchableGroup(volumeSlider);
	}
}