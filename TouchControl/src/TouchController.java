/*Notes and ideas:
 *This project is designed to be a touch screen without the screen.
 *
 *Current configuration:
 *Background must be black
 *Camera should be rotated 90 degrees counterclockwise
 */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

public class TouchController
{
	public static void main(String[] args)
	{
		//Create instance of class
		TouchController driver = new TouchController();
		driver.start();
	}

	private void start()
	{
		//Load OpenCV
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		//Begin Capture
		try
		{
			capture();
		}
		catch(Exception e)
		{
			System.out.println("Exception: " + e.getClass().getSimpleName());
		}

		//Exit
		System.out.println("Program terminated.");
		System.exit(0);
	}

	private void capture()
	{
		//Local variables
	    Mat rawImage = new Mat();
	    Mat filteredImage = new Mat();
	    int numOfDivisions = 100;
	    Point farthestPoint;
	    double divisionSize;
	    int volume;
	    int shiftFromEdges = 50;
	    int previousVolume = 0;

	    //Start camera
	    VideoCapture camera = new VideoCapture(0);

	    //Get camera properties
	    double cameraWidth = camera.get(Videoio.CAP_PROP_FRAME_WIDTH);
	    double cameraHeight = camera.get(Videoio.CAP_PROP_FRAME_HEIGHT);

	    //Create display
	    ImageDisplay display = new ImageDisplay((int)cameraWidth, (int)cameraHeight);
	    display.buildGUI();

	    //Create components
	    Rect volumeSlider = new Rect(new Point(shiftFromEdges, cameraHeight/2.0 + shiftFromEdges),
	    							 new Point(cameraWidth - shiftFromEdges, cameraHeight - shiftFromEdges));
	    
	    //Image divided into volume sectors
	    divisionSize = calculateDivisionSize(volumeSlider.width, numOfDivisions);

		//Wait for camera to get images
	    while (rawImage.empty())
	    {
			camera.read(rawImage);
	    }

	    //While frame is not closed
    	while (display.getStatus())
    	{
    		camera.read(rawImage);
    		filteredImage = filterImage(rawImage);
    		drawSlider(rawImage, volumeSlider, numOfDivisions, previousVolume);

    		//Find the farthest point on the screen that is not background
			farthestPoint = findFarthestPoint(filteredImage, volumeSlider);

			//If a non-background point is found
			if (farthestPoint != null)
			{
				//Mark detection
				Imgproc.circle(rawImage, farthestPoint, 10, new Scalar(0, 255, 0));

				//Determine the volume sector in which the point is located
				volume = determineDivision(farthestPoint, shiftFromEdges, divisionSize);

				if (volume != previousVolume)
				{
					setVolume(volume);
					previousVolume = volume;
				}
			}

			showImage(display, rawImage);

			//Cleanup to avoid memory exception
			rawImage.release();
		    filteredImage.release();
    	}

    	//Return camera control to OS
    	camera.release();
	}

	private void drawSlider(Mat rawImage, Rect slider, int numOfDivisions, int previousVolume)
	{
		//Local Variables
		double divisionSize;
		double xPosition;
		Point upperLeft;
		Point lowerRight;
		Scalar sliderColor = new Scalar(255, 0, 0); //Scalar is BGR
		Scalar infoColor = new Scalar(0, 255, 0);

		//Determine slider state
		divisionSize = calculateDivisionSize(slider.width, numOfDivisions);
		xPosition = divisionSize * previousVolume + slider.x;
		upperLeft = new Point(slider.x, slider.y);
		lowerRight = new Point(slider.x + slider.width, slider.y + slider.height);

		//Draw components
		Imgproc.rectangle(rawImage, upperLeft, lowerRight, sliderColor, 3);
		Imgproc.line(rawImage, new Point(xPosition, slider.y),
					 new Point(xPosition, slider.y + slider.height), infoColor, 3);
		Imgproc.putText(rawImage, previousVolume + "%", new Point(xPosition + 5, slider.y + 15),
						Core.FONT_HERSHEY_COMPLEX, 0.5, infoColor);
	}

	private double calculateDivisionSize(double sliderWidth, int numOfDivisions)
	{
		//Local variables
		double divisionSize;

		//Calculations
		divisionSize = sliderWidth / numOfDivisions;

		return divisionSize;
	}

	private Mat filterImage(Mat image)
	{
		//Local variables
		int scalar = 175;
		Mat hsvImage = new Mat();
		Mat grayImage = new Mat();
		Mat filteredImage = new Mat();
		Scalar backgroundColor = new Scalar(0, 0, 0);
		Scalar rangeColor = new Scalar(scalar, scalar, scalar);

		//Filter image
		Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);
		hsvImage = colorToHSV(grayImage, backgroundColor, rangeColor);
	    filteredImage = filterNoise(hsvImage);

		return filteredImage;
	}

	private Mat colorToHSV(Mat image, Scalar lowerBound, Scalar upperBound)
	{
		//Local variables
		Mat hsvImage = new Mat();

		//Convert image
		Core.inRange(image, lowerBound, upperBound, hsvImage);

		return hsvImage;
	}

	private Mat filterNoise(Mat image)
	{
		//Local Variables
		Mat modifiedImage = new Mat();
		Size erodeSize = new Size(7, 7);
		Size dilateSize = new Size(2, 2);

		//Remove noise from image
		Imgproc.erode(image, modifiedImage, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, erodeSize));
		Imgproc.dilate(modifiedImage, modifiedImage, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, dilateSize));

		return modifiedImage;
	}

	private Point findFarthestPoint(Mat filteredImage, Rect range)
	{
		//Local variables
		Point farthestPoint = new Point(0, 0);
		boolean doneSearching = false;
		int counter = 0;
		int errorThreshold = 25;

		//Starting in the lower right corner, search for non-background pixel
		outerLoop:
		for (int x = range.x + range.width; x >= range.x; x--)
		{
			innerLoop:
			for (int y = range.y + range.height; y >= range.y; y--)
			{
				//Get pixel at coordinate
				double[] pixelColors = filteredImage.get(y, x);

				if (pixelColors != null)
				{
					//Check pixel for non-background coloring
					if (pixelColors[0] != 255.0)
					{
						counter++;

						if (counter >= errorThreshold)
						{
							farthestPoint.x = x;
							farthestPoint.y = y;
							doneSearching = true;
						}
					}
				}
				if (doneSearching)
					break innerLoop;
			}
			if (doneSearching)
				break outerLoop;
		}

		//No non-background pixel found
		if (farthestPoint.x == 0 || farthestPoint.y == 0)
			farthestPoint = null;

		return farthestPoint;
	}

	private int determineDivision(Point farthestPoint, int shift, double divisionSize)
	{
		//Local variables
		int division;

		//Calculations
		division = (int) Math.ceil(((farthestPoint.x - shift) / divisionSize));

		return division;
	}

	private void setVolume(int volume)
	{
		//Nircmd allows volume changing
		File nircmdPath = new File("./nircmd.exe");

		//Validate volume
	    if (volume >= 0 && volume <= 100)
	    {
	    	//Calculate volume in nircmd terms
	        double endVolume = 655.35 * volume;

	        try
	        {
	        	//Set volume
	        	Runtime.getRuntime().exec(nircmdPath.getCanonicalPath() + " setsysvolume " + endVolume);
	        }
	        catch (IOException e)
	        {
	            e.printStackTrace();
	        }
	    }
	}

	private void showImage(ImageDisplay display, Mat rawImage)
	{
		//Local variables
		MatToImage converter = new MatToImage();
	    BufferedImage convertedImage;

	    //Show image
		converter.setMatrix(rawImage, ".jpg");
		convertedImage = converter.getBufferedImage();
		display.setImage(convertedImage);
		display.repaint();
	}
}