/*
 * Project Description:
 * This project is designed to be a touch screen without the screen.
 * Current configuration:
 * Optimal:
 * Object to detect should be lighter than background
 * (darker background is better)
 * Class Description:
 * Demo (main) class to run functions
 */

package io.github.jakejmattson.touchcontrol.demo;

import io.github.jakejmattson.touchcontrol.display.ImageFrame;
import io.github.jakejmattson.touchcontrol.touchables.*;
import io.github.jakejmattson.touchcontrol.utils.*;
import org.bytedeco.javacpp.*;
import org.opencv.core.*;

class TouchController
{
	public static void main(String[] args)
	{
		//Load OpenCV
		Loader.load(opencv_java.class);

		//Start program (with debug mode switch)
		new TouchController().capture(true);

		//Force exit
		System.exit(0);
	}

	private void capture(boolean debugMode)
	{
		//Start camera
		Camera camera = new Camera(-1);

		//Do not start if no camera is available
		if (!camera.isOpened())
			return;

		//Get camera properties
		double cameraWidth = camera.getWidth();
		double cameraHeight = camera.getHeight();

		//Create display
		ImageFrame rawDisplay = new ImageFrame("Touch Control");
		ImageFrame filteredDisplay = debugMode ? new ImageFrame("Debug frame") : null;

		//Create demo groups
		Scalar color = new Scalar(0, 255, 0);

		//TouchableGroup group = createMouseDemo(cameraWidth, cameraHeight, color);
		//TouchableGroup group = createPianoDemo(cameraWidth, cameraHeight, color);
		TouchableGroup group = createVolumeDemo(cameraWidth, cameraHeight, color);

		//Print objects in group
		if (debugMode)
			System.out.print(group);

		//Create image modifier
		ImageHandler handler = new ImageHandler();

		//Give sample background images to subtractor
		for (int i = 0; i < 5; i++)
			handler.trainSubtractor(camera.getFrame());

		//While frame is open and camera is detected
		while (rawDisplay.isOpen() && camera.isOpened())
		{
			//Read image from camera
			Mat cameraImage = camera.getFrame();

			//Filter image for processing
			Mat filteredImage = handler.filterImage(cameraImage);

			//Find the farthest point that is not background
			group.updateDetectionPoint(filteredImage);

			//Draw components onto image
			group.drawOnto(cameraImage);

			//Perform class action
			group.performAction();

			//Display components
			rawDisplay.showImage(handler.convertMatToImage(cameraImage));

			if (debugMode)
				filteredDisplay.showImage(handler.convertMatToImage(filteredImage));
		}

		//Return camera control to OS
		camera.release();
	}

	private TouchableGroup createMouseDemo(double cameraWidth, double cameraHeight, Scalar color)
	{
		//Create component dimensions
		int padding = 10;
		Point topLeft = new Point(padding, padding);
		Point bottomRight = new Point(cameraWidth - padding, cameraHeight - padding);
		Rect mousePadRect = new Rect(topLeft, bottomRight);

		//Create component
		MousePad mousePad = new MousePad(mousePadRect, color);

		//Create group
		return new TouchableGroup(mousePad);
	}

	private TouchableGroup createPianoDemo(double cameraWidth, double cameraHeight, Scalar color)
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
			int padding = 10;
			Point topLeft = new Point(x1 + padding, y1 + padding);
			Point bottomRight = new Point(x2 - padding, y2 - padding);
			Rect keyRect = new Rect(topLeft, bottomRight);

			//Create component
			PianoKey key = new PianoKey(keyRect, color, notes[i]);

			//Add component to group
			group.addComponent(key);
		}

		return group;
	}

	private TouchableGroup createVolumeDemo(double cameraWidth, double cameraHeight, Scalar color)
	{
		//Create component dimensions
		int padding = 10;
		Point topLeft = new Point(0.4 * cameraWidth, padding);
		Point bottomRight = new Point(0.6 * cameraWidth - padding, cameraHeight - padding);
		Rect volumeSliderRect = new Rect(topLeft, bottomRight);

		//Create component
		VolumeSlider volumeSlider = new VolumeSlider(volumeSliderRect, color);

		//Create group
		return new TouchableGroup(volumeSlider);
	}
}