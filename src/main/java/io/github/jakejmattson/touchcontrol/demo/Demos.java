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
		//Create component dimensions
		Point topLeft = new Point(PADDING, PADDING);
		Point bottomRight = new Point(cameraWidth - PADDING, cameraHeight - PADDING);
		Rect mousePadRect = new Rect(topLeft, bottomRight);

		//Create component
		MousePad mousePad = new MousePad(mousePadRect, color);

		//Create group
		return new TouchableGroup(mousePad);
	}

	static TouchableGroup createPianoDemo(double cameraWidth, double cameraHeight, Scalar color)
	{
		//Create group
		TouchableGroup group = new TouchableGroup();

		//Keys for demo piano
		char[] notes = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};

		//Create keys
		for (int i = 0; i < notes.length; i++)
		{
			//Base positions
			double x1 = (double) i / notes.length * cameraWidth;
			double x2 = (double) (i + 1) / notes.length * cameraWidth;
			double y1 = 1 / 4.0 * cameraHeight;
			double y2 = 3 / 4.0 * cameraHeight;

			//Create component dimensions
			Point topLeft = new Point(x1 + PADDING, y1 + PADDING);
			Point bottomRight = new Point(x2 - PADDING, y2 - PADDING);
			Rect keyRect = new Rect(topLeft, bottomRight);

			//Create component
			PianoKey key = new PianoKey(keyRect, color, notes[i]);

			//Add component to group
			group.addComponent(key);
		}

		return group;
	}

	static TouchableGroup createVolumeDemo(double cameraWidth, double cameraHeight, Scalar color)
	{
		//Create component dimensions
		Point topLeft = new Point(0.4 * cameraWidth, PADDING);
		Point bottomRight = new Point(0.6 * cameraWidth - PADDING, cameraHeight - PADDING);
		Rect volumeSliderRect = new Rect(topLeft, bottomRight);

		//Create component
		VolumeSlider volumeSlider = new VolumeSlider(volumeSliderRect, color);

		//Create group
		return new TouchableGroup(volumeSlider);
	}
}