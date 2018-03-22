/*This project is designed to be a touch screen without the screen.
 *
 *Current configuration:
 *Object to detect should be lighter than background (darker is better)
 *Camera should be rotated 180 degrees
 */

package touchcontrol.demo;

import org.opencv.core.*;
import org.opencv.videoio.*;

import touchcontrol.display.ImageFrame;
import touchcontrol.filter.ImageHandler;
import touchcontrol.touchables.*;

public class TouchController
{
	public static void main(String[] args)
	{
		TouchController driver = new TouchController();
		driver.start();
	}

	private void start()
	{
		//Load OpenCV
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		//Run program
		capture();

		//Force exit
		System.out.print("Program terminated.");
		System.exit(0);
	}

	private void capture()
	{
		//Start camera
		VideoCapture camera = new VideoCapture(0);

		//Do not start if no camera is available
		if (!camera.isOpened())
		{
			System.out.println("No camera detected!");
			return;
		}

		//Get camera properties
		double cameraWidth = camera.get(Videoio.CAP_PROP_FRAME_WIDTH);
		double cameraHeight = camera.get(Videoio.CAP_PROP_FRAME_HEIGHT);

		//Create display
		ImageFrame filteredDisplay = new ImageFrame();
		ImageFrame rawDisplay = new ImageFrame();

		//Create demo groups
		//TouchableGroup group = new TouchableGroup();
		//TouchableGroup group = createBasicDemo(cameraWidth, cameraHeight);
		TouchableGroup group = createPianoDemo(cameraWidth, cameraHeight);

		//Print objects in group
		System.out.print(group);

		//Create image modifier
		ImageHandler handler = new ImageHandler();

		//Give sample background images to subtractor
		for (int i = 0; i < 5; i++)
		{
			Mat background = new Mat();
			camera.read(background);
			Core.flip(background, background, -1);
			handler.trainSubtractor(background);
		}

		//While frame is open and camera is detected
		while (rawDisplay.isOpen() && camera.isOpened())
		{
			//Create matrices
			Mat cameraImage = new Mat();
			Mat filteredImage = new Mat();

			//Read image from camera
			camera.read(cameraImage);

			//Flip image 180 degrees
			Core.flip(cameraImage, cameraImage, -1);

			//Filter image for processing
			filteredImage = handler.filterImage(cameraImage);

			//Find the farthest point that is not background
			group.updateDetectionPoint(filteredImage);

			//Draw components onto image
			group.drawOnto(cameraImage);

			//Perform class action
			group.performAction();

			//Display components
			filteredDisplay.showImage(handler.convertMatToImage(filteredImage));
			rawDisplay.showImage(handler.convertMatToImage(cameraImage));
		}

		//Return camera control to OS
		camera.release();
	}

	private TouchableGroup createBasicDemo(double cameraWidth, double cameraHeight)
	{
		//Create component dimensions
		int padding = 10;
		Point topLeft = new Point(padding, padding);
		Point bottomRight = new Point(0.75 * cameraWidth - padding, cameraHeight - padding);
		Rect mousePadRect = new Rect(topLeft, bottomRight);

		topLeft = new Point(0.75 * cameraWidth, padding);
		bottomRight = new Point(cameraWidth - padding, cameraHeight - padding);
		Rect volumeSliderRect = new Rect(topLeft, bottomRight);

		//Create components
		MousePad mousePad = new MousePad(mousePadRect);
		VolumeSlider volumeSlider = new VolumeSlider(volumeSliderRect);

		//Create group of Touchable objects
		TouchableGroup group = new TouchableGroup(mousePad, volumeSlider);

		return group;
	}

	private TouchableGroup createPianoDemo(double cameraWidth, double cameraHeight)
	{
		//Create group of Touchable objects
		TouchableGroup group = new TouchableGroup();

		//Keys for demo piano
		char[] notes = {'C', 'D', 'E', 'F', 'G', 'A', 'B'};

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

			//Create piano key
			PianoKey key = new PianoKey(keyRect, notes[i]);

			//Add current key to group
			group.addComponent(key);
		}

		return group;
	}
}