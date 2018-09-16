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

class TouchController
{
	public static void main(String[] args)
	{
		boolean isDebugging = true;

		//Load OpenCV
		Loader.load(opencv_java.class);

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
		ImageFrame filteredDisplay = isDebugging ? new ImageFrame("Debug frame") : null;

		//Create demo
		Scalar color = new Scalar(0, 255, 0);
		TouchableGroup group = Demos.createPianoDemo(cameraWidth, cameraHeight, color);

		if (isDebugging)
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

			if (isDebugging)
				filteredDisplay.showImage(handler.convertMatToImage(filteredImage));
		}

		//Return camera control to OS
		camera.release();

		//Force exit
		System.exit(0);
	}
}