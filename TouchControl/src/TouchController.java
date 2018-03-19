/*This project is designed to be a touch screen without the screen.
 *
 *Current configuration:
 *Background must be black
 *Camera should be rotated 180 degrees
 */

import org.opencv.core.*;
import org.opencv.videoio.*;

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
		System.exit(0);
	}

	private void capture()
	{
		//Start camera
		VideoCapture camera = new VideoCapture(0);

		//Get camera properties
		double cameraWidth = camera.get(Videoio.CAP_PROP_FRAME_WIDTH);
		double cameraHeight = camera.get(Videoio.CAP_PROP_FRAME_HEIGHT);

		//Create display
		ImageDisplay display = new ImageDisplay();

		//Create image modifier
		ImageHandler handler = new ImageHandler();

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

		//Create matrices
		Mat cameraImage = new Mat();
		Mat filteredImage = new Mat();

		//Wait for camera to get images before proceeding
		do
			camera.read(cameraImage);
		while (cameraImage.empty());

		//While frame is open
		while (display.isOpen())
		{
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
			display.showImage(handler.convertMatToImage(cameraImage));

			//Dispose matrices
			cameraImage.release();
			filteredImage.release();
		}

		//Return camera control to OS
		camera.release();
	}
}