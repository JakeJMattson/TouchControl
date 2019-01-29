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
import io.github.jakejmattson.touchcontrol.utils.*;
import org.bytedeco.javacpp.*;
import org.opencv.core.*;

import static io.github.jakejmattson.touchcontrol.demo.DemosKt.*;

class TouchController
{
	private static final boolean DEBUG_MODE = true;

	public static void main(String[] args)
	{
		Loader.load(opencv_java.class);
		Camera camera = new Camera(-1);

		if (!camera.isOpened())
			return;

		double cameraWidth = camera.getWidth();
		double cameraHeight = camera.getHeight();
		Scalar color = new Scalar(0, 255, 0);
		TouchableGroup group = createPianoDemo(cameraWidth, cameraHeight, color);

		if (DEBUG_MODE)
			System.out.print(group);

		ImageFrame rawDisplay = new ImageFrame("Touch Control");
		ImageFrame filteredDisplay = DEBUG_MODE ? new ImageFrame("Debug frame") : null;
		placeFrames(rawDisplay, filteredDisplay, (int) cameraWidth);

		ImageHandler handler = new ImageHandler();

		//Give sample background images to subtractor
		for (int i = 0; i < 5; i++)
			handler.trainSubtractor(camera.getFrame());

		while (rawDisplay.isOpen() && camera.isOpened())
		{
			Mat rawImage = camera.getFrame();
			Mat filteredImage = handler.filterImage(rawImage);

			group.updateDetectionPoint(filteredImage);
			group.drawOnto(rawImage);
			group.performAction();

			rawDisplay.showImage(handler.convertMatToImage(rawImage));

			if (DEBUG_MODE)
				filteredDisplay.showImage(handler.convertMatToImage(filteredImage));
		}

		camera.release();
		System.exit(0);
	}

	private static void placeFrames(ImageFrame frame1, ImageFrame frame2, int offset)
	{
		java.awt.Point location = new java.awt.Point(0, 0);
		frame1.setLocation(location);
		location.x = offset;

		if (frame2 != null)
			frame2.setLocation(location);
	}
}