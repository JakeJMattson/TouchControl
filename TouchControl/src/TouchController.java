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
		Rect mousePadRect = new Rect(new Point(padding, padding),
				new Point(0.75 * cameraWidth - padding, cameraHeight - padding));
		Rect volumeSliderRect = new Rect(new Point(0.75 * cameraWidth, padding),
				new Point(cameraWidth - padding, cameraHeight - padding));

		//Create components
		MousePad mousePad = new MousePad(mousePadRect);
		VolumeSlider volumeSlider = new VolumeSlider(volumeSliderRect);

		//Create matrices
		Mat rawImage = new Mat();
		Mat filteredImage = new Mat();

		//Wait for camera to get images before proceeding
		while (rawImage.empty())
			camera.read(rawImage);

		//While frame is open
		while (display.isOpen())
		{
			//Read image from camera
			camera.read(rawImage);

			//Flip image 180 degrees
			Core.flip(rawImage, rawImage, -1);

			//Filter image for processing
			filteredImage = handler.filterImage(rawImage);

			//Find the farthest point that is not background
			mousePad.updateDetectionPoint(filteredImage);
			volumeSlider.updateDetectionPoint(filteredImage);

			//Draw components onto image
			mousePad.drawOnto(rawImage);
			volumeSlider.drawOnto(rawImage);

			//Perform class action
			mousePad.performAction();
			volumeSlider.performAction();

			//Display components
			display.showImage(handler.convertMatToImage(rawImage));

			//Dispose matrices
			rawImage.release();
			filteredImage.release();
		}

		//Return camera control to OS
		camera.release();
	}
}